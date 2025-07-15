package com.hireboost.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Resume update request")
public class ResumeUpdateRequest {

    @Schema(description = "New profession")
    @Size(min = 2, max = 50)
    private String profession;

    @Schema(description = "New language")
    @Size(min = 2, max = 30)
    private String language;

    @Schema(description = "Is primary resume")
    private Boolean isPrimary;

    @Schema(description = "Resume text")
    private String resumeText;
}
