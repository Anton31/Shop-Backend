package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.SuccessResponse;
import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.dto.UserInfo;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import com.example.authserverresourceserversameapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public SuccessResponse register(UserDto dto) throws MessagingException {
        String text = "Message for confirmation registration sand to your email";
        userService.registerNewUserAccount(dto);
        return new SuccessResponse(text);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam String token) {
        VerificationToken verificationToken = userService.getToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:http://localhost:4200";
    }

    @GetMapping("/resendRegistrationToken")
    @ResponseBody
    public SuccessResponse resendRegistrationToken(@RequestParam("token") final String existingToken) throws MessagingException {
        userService.generateNewVerificationToken(existingToken);
        return new SuccessResponse("Message for confirmation registration sand to your email");
    }

    @GetMapping
    @ResponseBody
    public UserInfo getUserInfo(Principal principal) {
        if (principal == null) {
            return null;
        } else {
            User user = userService.findByUsername(principal.getName());
            return new UserInfo(user.getUsername(), user.getRole().getName());
        }
    }

    @PutMapping
    @ResponseBody
    public SuccessResponse editUser(UserDto dto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        String text = "Password changed";
        userService.editExistingUserAccount(dto, user);
        return new SuccessResponse(text);
    }
}
