package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Registration request")
public class SignUpRequest {

    @Schema(description = "User name", example = "John")
    @Size(min = 5, max = 30, message = "Username must be kept between 5 and 30 characters.")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Email", example = "JohnDoe@gmail.com")
    @Size(min = 5, max = 255, message = "Email must be kept between 5 and 255 characters.")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be in the format user@example.com")
    private String email;

    @Schema(description = "password", example = "MySuperSecretPassWord")
    @Size(min=8, max = 255, message = "The password length must be between 8 and 255 characters.")
    private String password;

    @Schema(description = "firstname", example = "John")
    @Size(min=2, max = 40, message = "The firstname length must be between 2 and 40 characters.")
    private String firstname;

    @Schema(description = "lastname", example = "Doe")
    @Size(min=2, max = 40, message = "The lastName length must be between 2 and 40 characters.")
    private String lastname;

}
