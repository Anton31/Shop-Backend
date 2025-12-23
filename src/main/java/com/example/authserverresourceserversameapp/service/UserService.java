package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.model.VerificationToken;
import jakarta.mail.MessagingException;


public interface UserService {

    User registerNewUserAccount(UserDto accountDto) throws MessagingException;

    void editExistingUserAccount(UserDto dto, User user);

    VerificationToken getToken(String token);

    void createVerificationTokenForUser(User user, String token);

    User findByUsername(String username);

    void generateNewVerificationToken(String existingVerificationToken) throws MessagingException;

    void saveRegisteredUser(User user);
}



