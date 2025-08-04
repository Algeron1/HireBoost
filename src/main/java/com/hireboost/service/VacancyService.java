package com.hireboost.service;

import com.hireboost.dto.ResumeUploadRequest;
import com.hireboost.dto.VacancyUpdateRequest;
import com.hireboost.dto.VacancyUploadRequest;
import com.hireboost.exception.ResumeAlreadyExistsException;
import com.hireboost.exception.ResumeFileEmptyException;
import com.hireboost.exception.VacancyNotFoundException;
import com.hireboost.model.Resume;
import com.hireboost.model.User;
import com.hireboost.model.Vacancy;
import com.hireboost.repository.ResumeRepository;
import com.hireboost.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserService userService;
    private final ResumeRepository resumeRepository;
    private final AiParserService aiParserService;
    private final VacancyScoreService vacancyScoreService;

    public Vacancy getById(Long id) {
        log.info("Get vacancy by Id");
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found"));
    }

    public List<Vacancy> getAllUsersVacancy(User user) {
        log.info("Retrieving all vacancy for user: {}", user.getUsername());
        return vacancyRepository.findAllByUser(user);
    }

    @Transactional
    public void removeById(Long id) {
        log.info("Remove vacancy with id:{}", id);
        vacancyRepository.deleteById(id);
    }

    public Vacancy findByFileName(String filename) {
        log.info("Search vacancy with filename: {}", filename);
        return vacancyRepository.findByFilename(filename)
                .orElseThrow(() -> new VacancyNotFoundException("Error wile search vacancy by filename"));
    }

    @Transactional
    public Vacancy createNewVacancy(VacancyUploadRequest request) {
        try {
            Vacancy newVacancy = Vacancy.builder()
                    .name(request.getName())
                    .company(request.getCompany())
                    .createdAt(LocalDateTime.now())
                    .user(userService.getCurrentUser())
                    .salary(request.getSalary())
                    .description(request.getDescription())
                    .build();
            log.info("Saving vacancy for: {}", userService.getCurrentUser());
            return vacancyRepository.save(newVacancy);
        } catch (Exception e) {
            log.error("Error while saving vacancy");
            throw new RuntimeException("Error while saving vacancy");
        }
    }

    @Transactional
    public Vacancy updateVacancy(VacancyUpdateRequest request) {
        Vacancy vacancyFromDb = vacancyRepository.findById(request.getId())
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy for update not found"));
        try {
            vacancyFromDb.setId(request.getId());
            vacancyFromDb.setName(request.getName());
            vacancyFromDb.setCompany(request.getCompany());
            vacancyFromDb.setUpdatedAt(LocalDateTime.now());
            vacancyFromDb.setSalary(request.getSalary());
            vacancyFromDb.setDescription(request.getDescription());
            log.info("Updating vacancy for: {}", userService.getCurrentUser());
            return vacancyRepository.save(vacancyFromDb);
        } catch (Exception e) {
            log.error("Error while updating vacancy");
            throw new RuntimeException("Error while updating vacancy");
        }
    }

    @Transactional
    public Vacancy saveVacancyFileAndParse(MultipartFile file, ResumeUploadRequest request) {
        if (file.isEmpty()) {
            throw new ResumeFileEmptyException("File cannot be empty");
        }
        if (resumeRepository.existsByFileName("hr-" + request.getFileName())) {
            log.error("Resume with filename: hr-{} exists", request.getFileName());
            throw new ResumeAlreadyExistsException("Resume with filename: hr-" + request.getFileName() + " exists");
        }
        Vacancy vacancy = vacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy not found"));

        if (vacancy.getDescription() == null) {
            log.error("Vacancy text is empty");
            throw new VacancyNotFoundException("Vacancy text is empty");
        }

        try {
            User currentUser = userService.getCurrentUser();

            byte[] fileBytes = file.getBytes();
            String extractedText = aiParserService.extractText(fileBytes, file.getOriginalFilename());

            Resume resume = new Resume();
            resume.setUser(currentUser);
            resume.setResumeText(extractedText);
            resume.setFileName(request.getFileName());
            resume.setLanguage(request.getLanguage());
            resume.setProfession(request.getProfession());
            resumeRepository.save(resume);

            vacancyScoreService.createVacancyScore(resume, vacancy);

            log.info("Saving resume for user {}", currentUser.getUsername());
            return vacancy;
        } catch (Exception e) {
            throw new RuntimeException("Error saving and parsing file", e);
        }
    }

}
