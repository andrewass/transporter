package com.transporter.controller.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @Email
    @Size(max = 70)
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}