package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Resume file parse response")
public class ResumeFileParseResponse {

    @Schema(description = "file id", example = "123")
    private Long id;

    @Schema(description = "filename", example = "myResume.doc")
    private String fileName;

    @Schema(description = "text of resume")
    private String extractedText;

    @Schema(description = "File parse status", example = "File uploaded and parsed successfully")
    private String status;
}
