package com.hireboost.service;

import com.hireboost.dto.ResumeUpdateRequest;
import com.hireboost.dto.ResumeUploadRequest;
import com.hireboost.exception.ResumeAlreadyExistsException;
import com.hireboost.exception.ResumeFileEmptyException;
import com.hireboost.exception.ResumeNotFoundException;
import com.hireboost.exception.UnauthorizedAccessException;
import com.hireboost.model.Resume;
import com.hireboost.model.User;
import com.hireboost.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository repository;
    private final UserService userService;
    private final AiParserService aiParserService;

    @Transactional
    public Resume save(ResumeUploadRequest request) {

        if(repository.existsByFileName(request.getFileName())) {
            log.error("Resume with filename: {} exists", request.getFileName());
            throw new ResumeAlreadyExistsException("Resume with filename: " + request.getFileName() + " exists");
        }

        User currentUser = userService.getCurrentUser();
        Resume resume = new Resume();
        resume.setUser(currentUser);
        resume.setResumeText(request.getResumeText());
        resume.setFileName(request.getFileName());
        resume.setLanguage(request.getLanguage());
        resume.setProfession(request.getProfession());
        resume.setIsPrimary(request.isPrimary());

        log.info("Saving resume for user {}", currentUser.getUsername());
        return repository.save(resume);
    }

    public Resume getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume with id " + id + " not found"));
    }


    public List<Resume> getAllUserResumes(User user) {
        log.info("Retrieving all resumes for user: {}", user.getUsername());
        return repository.findAllByUser(user);
    }

    public Resume getResumeByFilename(String filename) {
        log.info("Searching for resume with filename: {}", filename);
        return repository.findByFileName(filename)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found: " + filename));
    }

    @Transactional
    public void removeById(Long id) {
        log.info("Remove resume by ID: {}", id);
        repository.deleteById(id);
    }

    @Transactional
    public Resume updateResume(Long id, ResumeUpdateRequest request, User currentUser) {
        Resume resume = repository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found"));

        if (!resume.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedAccessException("You do not have permission to edit this resume.");
        }

        if (request.getProfession() != null) {
            resume.setProfession(request.getProfession());
        }
        if (request.getLanguage() != null) {
            resume.setLanguage(request.getLanguage());
        }
        if (request.getIsPrimary() != null) {
            resume.setIsPrimary(request.getIsPrimary());
        }
        if (request.getResumeText() != null) {
            resume.setResumeText(request.getResumeText());
        }

        resume.setUpdatedAt(java.time.LocalDateTime.now());
        return repository.save(resume);
    }

    public String analyzeResume(Long id) {
        Resume resume = getById(id);
        // Заглушка логики анализа
        if (resume.getResumeText() != null && resume.getResumeText().length() > 100) {
            return "Resume is valid";
        } else {
            return "Resume is not valid!";
        }
    }

    @Transactional
    public Resume saveResumeFileAndParse(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResumeFileEmptyException("File cannot be empty");
        }

        try {
            byte[] fileBytes = file.getBytes();
            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());
            resume.setFileData(fileBytes);

            String extractedText = aiParserService.extractText(fileBytes, resume.getFileName());

            resume.setResumeText(extractedText);
            resume.setUser(userService.getCurrentUser());
            return repository.save(resume);
        } catch (Exception e) {
            throw new RuntimeException("Error saving and parsing file", e);
        }
    }

}
