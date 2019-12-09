package com.transporter.service;

import com.transporter.controller.requests.SignOutRequest;
import com.transporter.controller.requests.SignUpRequest;
import com.transporter.controller.requests.SignInRequest;
import com.transporter.controller.responses.SignInResponse;
import com.transporter.entities.user.User;
import com.transporter.exceptions.IncorrectPasswordException;
import com.transporter.exceptions.InvalidTokenException;
import com.transporter.exceptions.UsernameNotAvailableException;
import com.transporter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultAuthService implements AuthService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    DefaultAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Map<String, User> userTokenMap = new HashMap<>();

    public boolean usernameIsAvailable(String username) {
        return userRepository.findUserByUsername(username) == null;
    }

    @Override
    public User createUser(SignUpRequest request) throws UsernameNotAvailableException {
        if (usernameIsAlreadyInUse(request.getUsername())) {
            throw new UsernameNotAvailableException("Username " + request.getUsername() + " not available");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    @Override
    public SignInResponse signInUser(SignInRequest request) throws IncorrectPasswordException {
        User user = findUser(request.getUsername());
        authenticateUser(request.getPassword(), user);
        String token = generateUserToken();
        return new SignInResponse("ff");
    }

    @Override
    public void signOutUser(SignOutRequest request) throws InvalidTokenException {
        getUserFromToken(request.getToken());
        userTokenMap.remove(request.getToken());
    }

    @Override
    public User getUserFromToken(String token) throws InvalidTokenException {
        User signedInUser = userTokenMap.get(token);
        if (signedInUser == null) {
            throw new InvalidTokenException("Invalid token : " + token);
        }
        return signedInUser;
    }

    private boolean usernameIsAlreadyInUse(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    private String generateUserToken() {
        SecureRandom secureRandom = new SecureRandom();
        return null;
    }

    private User findUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
    }

    private void authenticateUser(String submittedPassword, User user) throws IncorrectPasswordException {
        if (!passwordEncoder.matches(submittedPassword, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password");
        }
    }
}
