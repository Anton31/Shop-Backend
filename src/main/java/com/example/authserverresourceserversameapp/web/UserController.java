package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.exception.RegistrationResponse;
import com.example.authserverresourceserversameapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegisterDto dto) {
        String username = userService.addUser(dto).getUsername();

            RegistrationResponse response = new RegistrationResponse("user with username: \"" + username + "\" successfully registered!");

            return ResponseEntity.ok(response);

    }
}
