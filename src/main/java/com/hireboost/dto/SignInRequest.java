package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "SignIn Request")
public class SignInRequest {

    @Schema(description = "User name", example = "John")
    @Size(min = 5, max = 30, message = "Username must be kept between 5 and 30 characters.")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "password")
    @Size(min=8, max = 255, message = "The password length must be between 8 and 255 characters.")
    private String password;

}
