package com.transporter.controller;

import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.requests.SingInRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.IncorrectPasswordException;
import com.transporter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    @CrossOrigin(value = "*")
    public ResponseEntity<User> signUpUser(@RequestBody SignUpRequest request) {
        if (userService.usernameIsAvailable(request.getUsername())) {
            User user = userService.createUser(request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sign-in")
    @CrossOrigin(value = "*")
    public ResponseEntity<SignInResponse> signInUser(@RequestBody SingInRequest request) {
        try {
            SignInResponse response = userService.signInUser(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            log.error("Incorrect password exception");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
