package com.transporter.controller;

import com.transporter.controller.requests.SignOutRequest;
import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.requests.SignInRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.IncorrectPasswordException;
import com.transporter.exceptions.InvalidTokenException;
import com.transporter.exceptions.UsernameNotAvailableException;
import com.transporter.service.DefaultAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    DefaultAuthService authService;

    @PostMapping("/sign-up")
    @CrossOrigin(value = "*")
    public ResponseEntity<SignInResponse> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            User user = authService.createUser(signUpRequest);
            SignInResponse response = authService.signInUser(convertSignUpRequestToSignInRequest(signUpRequest));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UsernameNotAvailableException | IncorrectPasswordException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-in")
    @CrossOrigin(value = "*")
    public ResponseEntity<SignInResponse> signInUser(@Valid @RequestBody SignInRequest request) {
        try {
            SignInResponse response = authService.signInUser(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            log.error("Incorrect password for username " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            log.error("Username not found : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-out")
    @CrossOrigin(value = "*")
    public ResponseEntity<HttpStatus> signOutUser(@Valid @RequestBody SignOutRequest request) {
        try {
            authService.signOutUser(request);
            return null;
        } catch (InvalidTokenException e){
            log.error(e.getMessage());
        }
        return null;
    }

    private SignInRequest convertSignUpRequestToSignInRequest(SignUpRequest signUpRequest) {
        return new SignInRequest(signUpRequest.getUsername(), signUpRequest.getPassword());
    }
}
