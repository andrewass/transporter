package com.transporter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.transporter.controller.requests.SignInRequest;
import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.UsernameNotAvailableException;
import com.transporter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnStatusOkWhenUsernameIsAvailableForSignUp() throws Exception {
        User signedUpUser = new User();
        when(userService.createUser(any(SignUpRequest.class))).thenReturn(signedUpUser);

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSignUpRequest()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus400WhenSignUpRequestIsEmpty() throws Exception {
        User signedUpUser = new User();
        when(userService.createUser(any(SignUpRequest.class))).thenReturn(signedUpUser);

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createEmptySignUpRequest()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnStatus401WhenUsernameNotAvailable() throws Exception {
        User signedUpUser = new User();
        when(userService.createUser(any(SignUpRequest.class))).thenThrow(UsernameNotAvailableException.class);

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSignUpRequest()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnStatusOkWhenSigningIn() throws Exception {
        when(userService.signInUser(any(SignInRequest.class))).thenReturn(new SignInResponse("testtoken"));

        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSignInRequest()))
                .andExpect(status().isOk());
    }

    private String createSignInRequest() throws JsonProcessingException {
        SignInRequest request = new SignInRequest("testuser", "p@sswOrd");
        return objectMapper.writeValueAsString(request);
    }

    private String createEmptySignUpRequest() throws JsonProcessingException {
        SignUpRequest request = new SignUpRequest();
        return objectMapper.writeValueAsString(request);
    }

    private String createSignUpRequest() throws JsonProcessingException {
        SignUpRequest request = new SignUpRequest("testuser", "p@sswOrd", "test@mail.com");
        return objectMapper.writeValueAsString(request);
    }


}