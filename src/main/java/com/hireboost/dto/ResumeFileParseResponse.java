package com.hireboost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResumeFileParseResponse {
    private Long id;
    private String fileName;
    private String extractedText;
    private String status;
}
