package com.transporter.service;

import com.transporter.controller.requests.SignInRequest;
import com.transporter.controller.requests.SignOutRequest;
import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.IncorrectPasswordException;
import com.transporter.exceptions.InvalidTokenException;
import com.transporter.exceptions.UsernameNotAvailableException;

public interface AuthService {

    public User createUser(SignUpRequest request) throws UsernameNotAvailableException;

    public SignInResponse signInUser(SignInRequest request) throws IncorrectPasswordException;

    public void signOutUser(SignOutRequest request) throws InvalidTokenException;

    public User getUserFromToken(String token) throws InvalidTokenException;
}

