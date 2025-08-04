package com.hireboost.controller;

import com.hireboost.dto.ResumeFileParseResponse;
import com.hireboost.dto.ResumeTranslateRequest;
import com.hireboost.dto.ResumeUpdateRequest;
import com.hireboost.dto.ResumeUploadRequest;
import com.hireboost.model.Resume;
import com.hireboost.model.User;
import com.hireboost.service.ResumeService;
import com.hireboost.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@Tag(name = "Resumes API", description = "Operations for managing resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @Operation(summary = "Upload new resume")
    @PostMapping
    public Resume uploadResume(@RequestBody @Valid ResumeUploadRequest request) {
        log.info("Uploading resume for user '{}'", userService.getCurrentUser());
        return resumeService.save(request);
    }

    @Operation(summary = "Get all resumes of a user by username")
    @GetMapping("/user/{username}")
    public List<Resume> getUserResumes(@PathVariable String username) {
        log.info("Fetching resumes for user '{}'", username);
        User user = userService.getUserByUserName(username);
        return resumeService.getAllUserResumes(user);
    }

    @Operation(summary = "Get all resumes for current user")
    @GetMapping("/user/current/")
    public List<Resume> getCurrentUserResumes() {
        User current = userService.getCurrentUser();
        log.info("Get resumes for user '{}'", current.getUsername());
        return resumeService.getAllUserResumes(current);
    }

    @Operation(summary = "Get resume by file name")
    @GetMapping("/{fileName}")
    public Resume getResumeByFileName(@PathVariable String fileName) {
        log.info("Fetching resume by file name '{}'", fileName);
        return resumeService.getResumeByFilename(fileName);
    }

    @Operation(summary = "Remove resume by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeResumeById(@PathVariable Long id) {
        log.info("Remove resume by Id '{}'", id);
        resumeService.removeById(id);
        return ResponseEntity.ok("Resume with id: " + id + " was deleted");
    }

    @Operation(summary = "Update existing resume by ID")
    @PutMapping("/{id}")
    public Resume updateResume(
            @PathVariable Long id,
            @RequestBody @Valid ResumeUpdateRequest request
    ) {
        User currentUser = userService.getCurrentUser();
        log.info("User '{}' updating resume with ID {}", currentUser.getUsername(), id);
        return resumeService.updateResume(id, request, currentUser);
    }

    @Operation(summary = "Analyze resume by Id")
    @GetMapping("/{id}/analyze")
    public ResponseEntity<String> analyzeResume(@PathVariable Long id) {
        log.info("Analyzing resume with id '{}'", id);
        String analysisResult = resumeService.analyzeResume(id);
        return ResponseEntity.ok(analysisResult);
    }

    @Operation(summary = "Upload resume file and parse content into text field")
    @PostMapping("/upload-file-and-parse")
    public ResponseEntity<ResumeFileParseResponse> uploadAndParseResumeFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading and parsing resume file: {}", file.getOriginalFilename());
        Resume savedResume = resumeService.saveResumeFileAndParse(file);

        ResumeFileParseResponse response = new ResumeFileParseResponse(
                savedResume.getId(),
                savedResume.getFileName(),
                savedResume.getResumeText(),
                "File uploaded and parsed successfully"
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Download selected resume")
    @GetMapping("/download-file/{id}")
    public ResponseEntity<byte[]> downloadResumeFile(@PathVariable Long id) {
        Resume resume = resumeService.getById(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + resume.getFileName() + "\"")
                .body(resume.getFileData());
    }

    @Operation(summary = "Set resume as primary for current user")
    @PutMapping("/{id}/set-primary/{isPrimary}")
    public ResponseEntity<String> setResumeAsPrimary(@PathVariable Long id, @PathVariable Boolean isPrimary) {
        User currentUser = userService.getCurrentUser();
        log.info("User '{}' is setting resume ID {} as primary", currentUser.getUsername(), id);
        resumeService.setPrimaryResume(id, currentUser, isPrimary);
        return ResponseEntity.ok("Resume with ID " + id + " set as primary");
    }

    @Operation(summary = "Translate resume on selected language")
    @PostMapping("/translate")
    public Resume translateResume(@RequestBody @Valid ResumeTranslateRequest request) {
        log.info("Translating resume with id '{}'", request.getId());
        return resumeService.translateResume(request);
    }

    @Operation(summary = "Optimize resume")
    @PutMapping("/optimize/{id}")
    public Resume optimizeResumeById(@PathVariable Long id) {
        log.info("Optimizing resume with id '{}'", id);
        return resumeService.optimizeResumeById(id);
    }

}
