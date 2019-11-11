package com.transporter.entities.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @Test
    void test1(){

    }

}