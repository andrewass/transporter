package com.transporter.controller.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @NotBlank
    String token;

    @NonNull
    LocalDateTime tripTime;

    @NotBlank
    String description;

    @NotBlank
    String area;
}
