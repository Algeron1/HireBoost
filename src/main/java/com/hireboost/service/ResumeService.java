package com.hireboost.service;

import com.hireboost.dto.ResumeUpdateRequest;
import com.hireboost.dto.ResumeUploadRequest;
import com.hireboost.exception.*;
import com.hireboost.model.Resume;
import com.hireboost.model.User;
import com.hireboost.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeRepository repository;
    private final UserService userService;
    private final AiParserService aiParserService;
    private final OpenAiService openAiService;

    @Transactional
    public Resume save(ResumeUploadRequest request) {

        if(repository.existsByFileName(request.getFileName())) {
            log.error("Resume with filename: {} exists", request.getFileName());
            throw new ResumeAlreadyExistsException("Resume with filename: " + request.getFileName() + " exists");
        }

        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error saving and parsing file", e);
        }
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
        if(repository.existsByFileName(file.getOriginalFilename())) {
            throw new ResumeAlreadyExistsException("Resume with filename: " + file.getOriginalFilename() + " exists");
        }

        try {
            byte[] fileBytes = file.getBytes();
            Resume resume = new Resume();
            resume.setFileName(file.getOriginalFilename());
            resume.setFileData(fileBytes);

            String extractedText = aiParserService.extractText(fileBytes, resume.getFileName());
            String score = openAiService.evaluateResumeScore(extractedText);

            resume.setScore(score);

            resume.setResumeText(extractedText);
            resume.setUser(userService.getCurrentUser());
            return repository.save(resume);
        } catch (Exception e) {
            throw new RuntimeException("Error saving and parsing file", e);
        }
    }

    @Transactional
    public void setPrimaryResume(Long id, User user, Boolean isPrimary) {
        if (user == null) {
            throw new UserNotFoundException("User cannot be null");
        }
        List<Resume> resumeList = repository.findAllByUser(user);

        Resume selected = resumeList.stream()
                .filter(resume -> resume.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found or does not belong to the user"));

        try {
            resumeList.forEach(resume -> {
                if (resume.getIsPrimary()) {
                    resume.setIsPrimary(false);
                    repository.save(resume);
                }
            });

            selected.setIsPrimary(isPrimary);
            repository.save(selected);
        } catch (Exception e) {
            throw new ResumeUpdateException("Error while updating resume");
        }
    }

}
