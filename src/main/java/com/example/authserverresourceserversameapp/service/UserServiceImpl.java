package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.exception.PasswordsDontMatchException;
import com.example.authserverresourceserversameapp.exception.UserExistsException;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Role;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.repository.CartRepository;
import com.example.authserverresourceserversameapp.repository.RoleRepository;
import com.example.authserverresourceserversameapp.repository.TokenRepository;
import com.example.authserverresourceserversameapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private static final String NOREPLY_ADDRESS = "noreply@test.com";
    private static final String APP_URL = "http://localhost:8080";

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    public UserServiceImpl(JavaMailSender mailSender,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           TokenRepository tokenRepository,
                           RoleRepository roleRepository, CartRepository cartRepository) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto dto) throws MessagingException {
        if (!dto.getPassword().equals(dto.getPasswordConfirmed())) {
            throw new PasswordsDontMatchException();
        }
        User user = new User();
        Role role;
        role = roleRepository.findAllByName(dto.getRole());
        if (role == null) {
            role = new Role();
        }
        role.setName(dto.getRole());
        role.addUser(user);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (userRepository.getByUsername(dto.getUsername()) != null) {
            throw new UserExistsException("User with username: \"" + dto.getUsername() + "\" already exists!");
        }
        if (userRepository.getByEmail(dto.getEmail()) != null) {
            throw new UserExistsException("User with email: \"" + dto.getEmail() + "\" already exists!");
        }
        User registered = userRepository.save(user);
        Cart cart = new Cart(registered);
        cartRepository.save(cart);
        MimeMessage message = constructVerificationTokenEmail(registered);
        mailSender.send(message);
        return registered;
    }

    @Override
    public void editExistingUserAccount(UserDto dto, User user) {
        if (!dto.getPassword().equals(dto.getPasswordConfirmed())) {
            throw new PasswordsDontMatchException();
        }
        Role role = roleRepository.findAllByName(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public VerificationToken getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void generateNewVerificationToken(String existingVerificationToken) throws MessagingException {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = tokenRepository.save(vToken);
        MimeMessage message = constructResendVerificationTokenEmail(vToken);
        mailSender.send(message);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public MimeMessage constructVerificationTokenEmail(User user) throws MessagingException {
        String token = UUID.randomUUID().toString();
        createVerificationTokenForUser(user, token);
        final String html = "<p><a href=\"" + APP_URL + "/user/registrationConfirm?token="
                + token + "\">Confirm registration</a></p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject("Registration Confirmation");
        helper.setText(html, true);
        return message;
    }

    public MimeMessage constructResendVerificationTokenEmail(VerificationToken newToken) throws MessagingException {
        final String html = "<p><a href=\"" + APP_URL + "/user/registrationConfirm?token="
                + newToken.getToken() + "\">Confirm registration</a></p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(newToken.getUser().getEmail());
        helper.setSubject("Registration Confirmation");
        helper.setText(html, true);
        return message;
    }
}
