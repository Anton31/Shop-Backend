package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.UserDto;
import com.example.authserverresourceserversameapp.exception.PasswordsDontMatchException;
import com.example.authserverresourceserversameapp.exception.UserExistsException;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import com.example.authserverresourceserversameapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockitoBean
    AppUserDetailsService userDetailsService;
    @MockitoBean
    JavaMailSender mailSender;

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;


    @Test
    @WithMockUser
    public void addUserTest() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        given(userService.registerNewUserAccount(any(UserDto.class))).willReturn(user);
        this.mockMvc.perform(post("/user").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password\", \"passwordConfirmed\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Message for confirmation registration sand to your email")));
    }

    @Test
    @WithMockUser
    public void handleUserAlreadyExistsException() throws Exception {
        User user = new User();
        user.setUsername("user");
        given(userService.registerNewUserAccount(any(UserDto.class)))
                .willThrow(new UserExistsException("User with username: user already exists!"));
        this.mockMvc.perform(post("/user").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password\", \"passwordConfirmed\": \"password\"}"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("User with username: user already exists!")));
    }

    @Test
    @WithMockUser
    public void handlePasswordsDoNotMatchException() throws Exception {
        User user = new User();
        user.setUsername("user");
        given(userService.registerNewUserAccount(any(UserDto.class)))
                .willThrow(new PasswordsDontMatchException());
        this.mockMvc.perform(post("/user").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password1\", \"passwordConfirmed\": \"password2\"}"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("passwords don't match!")));
    }
}
