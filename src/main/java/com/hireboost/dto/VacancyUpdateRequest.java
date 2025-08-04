package com.hireboost.dto;

import com.hireboost.util.MonetaryAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Vacancy update request")
public class VacancyUpdateRequest {

    @Schema(description = "Vacancy ID", example = "123")
    @NotBlank
    private long id;

    @Schema(description = "Vacancy name", example = "Fullstack Developer")
    @Size(min = 3, max = 30, message = "Vacancy name must be kept between 3 and 30 characters.")
    @NotBlank(message = "Vacancy name cannot be empty")
    private String name;

    @Schema(description = "Company name", example = "My Company")
    @Size(min = 1, max = 30, message = "Company name must be kept between 1 and 30 characters.")
    @NotBlank(message = "Company name cannot be empty")
    private String company;

    @Schema(description = "Vacancy description", example = "description")
    @NotBlank(message = "Vacancy description cannot be empty")
    private String description;

    @Schema(description = "Salary amount with currency", example = "5000 USD")
    private MonetaryAmount salary;
}
