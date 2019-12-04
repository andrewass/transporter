package com.transporter.controller;

import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.requests.SignInRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.IncorrectPasswordException;
import com.transporter.exceptions.UsernameNotAvailableException;
import com.transporter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    @CrossOrigin(value = "*")
    public ResponseEntity<User> signUpUser(@Valid @RequestBody SignUpRequest request) {
        try {
            User user = userService.createUser(request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UsernameNotAvailableException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/sign-in")
    @CrossOrigin(value = "*")
    public ResponseEntity<SignInResponse> signInUser(@Valid @RequestBody SignInRequest request) {
        try {
            SignInResponse response = userService.signInUser(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            log.error("Incorrect password for username "+request.getUsername());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e){
            log.error("Username not found : "+request.getUsername());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
