package com.hireboost.dto;

import com.hireboost.model.enums.FileType;
import com.hireboost.model.enums.Languages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Coverage letter generation request")
public class LetterGenerationRequest {

    @Schema(description = "Company Name", example = "My Company LTD.")
    @Size(min = 2, max = 50)
    private String company;

    @Schema(description = "Letter language", example = "ENGLISH")
    @Size(min = 2, max = 30)
    private Languages language;

    @Schema(description = "Resume id", example = "123")
    private Long resumeId;

    @Schema(description = "Profession", example = "developer")
    private String profession;

    @Schema(description = "File type", example = "PDF")
    private FileType filetype;
}
