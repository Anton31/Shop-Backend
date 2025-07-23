package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.RoleInfo;
import com.example.authserverresourceserversameapp.dto.SuccessResponse;
import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.dto.UserInfo;
import com.example.authserverresourceserversameapp.model.Role;
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
    public SuccessResponse register(@RequestBody UserDto dto) throws MessagingException {
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
            return new UserInfo("none", "none", "none");
        } else {
            User user = userService.findByUsername(principal.getName());
            return new UserInfo(user.getUsername(), user.getEmail(), user.getRole().getName());
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public RoleInfo getRoleUser(Principal principal) {
        if (principal == null) {
            return new RoleInfo("none");
        } else {
            User user = this.userService.findByUsername(principal.getName());
            return new RoleInfo(user.getRole().getName());
        }
    }
    @GetMapping("/admin")
    @ResponseBody
    public RoleInfo getRoleAdmin(Principal principal) {
        if (principal == null) {
            return new RoleInfo("none");
        } else {
            User user = this.userService.findByUsername(principal.getName());
            return new RoleInfo(user.getRole().getName());
        }
    }
    @PutMapping
    @ResponseBody
    public SuccessResponse editUser(@RequestBody UserDto dto) {
        String text = "Password changed";
        userService.editExistingUserAccount(dto);
        return new SuccessResponse(text);
    }
}
