package com.hireboost.controller;

import com.hireboost.dto.LetterGenerationRequest;
import com.hireboost.dto.LetterGenerationResponse;
import com.hireboost.model.CoverageLetter;
import com.hireboost.model.Resume;
import com.hireboost.service.CovLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/letters")
@RequiredArgsConstructor
@Tag(name = "Coverage Letters API", description = "Operations for managing coverage letters")
public class CoverageLetterController {

    private final CovLetterService covLetterService;

    @Operation(summary = "Generate and save coverage letter")
    @PostMapping("/generate")
    public ResponseEntity<LetterGenerationResponse> generateLetter(@RequestBody LetterGenerationRequest request) {
        log.info("Start generating cover letter for resume {}", request.getResumeId());
        CoverageLetter letter = covLetterService.generateAndSaveCoverLetter(request);

        LetterGenerationResponse response = new LetterGenerationResponse();
        response.setFilename(letter.getFileName());
        response.setId(letter.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Remove cov letter by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLetterById(@PathVariable Long id) {
        log.info("Remove resume by Id '{}'", id);
        covLetterService.removeById(id);
        return ResponseEntity.ok("Cov letter with id: " + id + " was deleted");
    }

    @Operation(summary = "Download selected cov letter")
    @GetMapping("/download-file/{id}")
    public ResponseEntity<byte[]> downloadCovLetterFile(@PathVariable Long id) {
        CoverageLetter letter = covLetterService.getById(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + letter.getFileName() + "\"")
                .body(letter.getFileData());
    }

}
