package com.hireboost.dto;

import com.hireboost.model.enums.FileType;
import com.hireboost.model.enums.Languages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Translate selected resume")
public class ResumeTranslateRequest {

    @Schema(description = "Resume id", example = "321")
    private Long id;

    @Schema(description = "The language into which we translate", example = "ENGLISH")
    private Languages language;

    @Schema(description = "Type of file for translated resume")
    private FileType fileType;
}
