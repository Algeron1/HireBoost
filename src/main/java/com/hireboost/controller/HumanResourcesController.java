package com.hireboost.controller;

import com.hireboost.dto.ResumeUploadRequest;
import com.hireboost.dto.VacancyUpdateRequest;
import com.hireboost.dto.VacancyUploadRequest;
import com.hireboost.model.User;
import com.hireboost.model.Vacancy;
import com.hireboost.service.UserService;
import com.hireboost.service.VacancyScoreService;
import com.hireboost.service.VacancyService;
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
@RequiredArgsConstructor
@RequestMapping("/api/hr")
@Tag(name = "HR API", description = "operations for HRs")
public class HumanResourcesController {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final VacancyScoreService vacancyScoreService;

    @Operation(description = "Get all vacancy for current user")
    @GetMapping("/user/current")
    List<Vacancy> getAllVacancyForCurrentUser() {
        User currenUser = userService.getCurrentUser();
        log.info("Get all vacancy for user: {}", currenUser.getUsername());
        return vacancyService.getAllUsersVacancy(currenUser);
    }

    @Operation(description = "delete vacancy by id")
    @DeleteMapping("/vacancy/{id}")
    ResponseEntity<String > deleteVacancyById(@PathVariable Long id) {
        log.info("Remove vacancy by ID: {}", id);
        vacancyService.removeById(id);
        return ResponseEntity.ok("Vacancy removed");
    }

    @Operation(description = "Create new Vacancy")
    @PostMapping("/vacancy/create")
    ResponseEntity<Vacancy> createNewVacancy(@RequestBody @Valid VacancyUploadRequest request) {
        log.info("Start creating new Vacancy: {}", request.getName());
        Vacancy vacancy = vacancyService.createNewVacancy(request);
        return ResponseEntity.ok(vacancy);
    }

    @Operation(description = "Create new Vacancy")
    @PutMapping("/vacancy/update")
    ResponseEntity<Vacancy> updateVacancy(@RequestBody @Valid VacancyUpdateRequest request) {
        log.info("Start updating Vacancy: {}", request.getId());
        Vacancy vacancy = vacancyService.updateVacancy(request);
        return ResponseEntity.ok(vacancy);
    }

    @Operation(description = "Upload vacancy file")
    @PostMapping("/vacancy/upload-and-parse-applicant-resume")
    ResponseEntity<Vacancy> uploadVacancyFile(@RequestPart("file") MultipartFile file, @RequestPart("request") @Valid ResumeUploadRequest request) {
        log.info("Upload and parse new resume file HR: {}", userService.getCurrentUser().getUsername());
        Vacancy vacancy = vacancyService.saveVacancyFileAndParse(file, request);
        return ResponseEntity.ok(vacancy);
    }

    @Operation(description = "delete vacancy score by id")
    @DeleteMapping("/vacancy-score/{id}")
    ResponseEntity<String > deleteVacancyScoreById(@PathVariable Long id) {
        log.info("Remove vacancy score by ID: {}", id);
        vacancyScoreService.removeVacancyScoreById(id);
        return ResponseEntity.ok("Vacancy score removed");
    }

    @Operation(summary = "Analyze applicant resume by Id")
    @GetMapping("/{id}/analyze")
    public ResponseEntity<String> analyzeResume(@PathVariable Long id) {
        log.info("Analyzing applicant resume with vacancy score id '{}'", id);
        String analysisResult = vacancyScoreService.analyzeResume(id);
        return ResponseEntity.ok(analysisResult);
    }

}

