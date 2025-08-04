package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Resume upload request")
public class ResumeUploadRequest {

    @Schema(description = "Vacancy id")
    private Long vacancyId;

    @Schema(description = "Resume text")
    @NotBlank(message = "Resume cannot be empty")
    private String resumeText;

    @Schema(description = "File name", example = "resume_1")
    @Size(min = 3, max = 50, message = "File name must be kept between 3 and 50 characters.")
    @NotBlank(message = "File cannot be empty")
    private String fileName;

    @Schema(description = "Profession", example = "developer")
    @Size(min = 2, max = 30, message = "Profession must be kept between 2 and 30 characters.")
    @NotBlank(message = "Profession cannot be empty")
    private String profession;

    @Schema(description = "Is primary", example = "true")
    private boolean isPrimary;

    @Schema(description = "Language", example = "English")
    @Size(min = 2, max = 30, message = "Language must be kept between 2 and 30 characters.")
    @NotBlank(message = "Language cannot be empty")
    private String language;
}
