package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response with cov letter filename and id")
public class LetterGenerationResponse {

    @Schema(description = "filename", example = "my-company-cov-letter.pdf")
    private String filename;

    @Schema(description = "file id", example = "123")
    private Long id;
}
