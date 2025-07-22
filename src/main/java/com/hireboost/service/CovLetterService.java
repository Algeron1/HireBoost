package com.hireboost.service;

import com.hireboost.dto.LetterGenerationRequest;
import com.hireboost.exception.ResumeNotFoundException;
import com.hireboost.model.CoverageLetter;
import com.hireboost.model.Resume;
import com.hireboost.model.enums.FileType;
import com.hireboost.model.enums.Languages;
import com.hireboost.promt.Promts;
import com.hireboost.repository.CoverageLetterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CovLetterService {

    private final ResumeService resumeService;
    private final OpenAiService openAiService;
    private final CoverageLetterRepository coverageLetterRepository;
    private final FileService letterFileService;

    @Transactional
    public CoverageLetter generateAndSaveCoverLetter(LetterGenerationRequest request) {
        log.info("Generating cover letter for resume ID: {}", request.getResumeId());

        Resume resume = resumeService.getById(request.getResumeId());
        if (resume == null) {
            log.error("Resume by id {} not found!", request.getResumeId());
            throw new ResumeNotFoundException("Resume with id " + request.getResumeId() + " not found");
        }

        String prompt = buildPrompt(
                resume.getResumeText(),
                request.getCompany(),
                request.getProfession(),
                request.getLanguage()
        );

        String letterText = openAiService.generateText(prompt);
        byte[] fileBytes = letterFileService.generateFile(letterText, request.getFiletype());

        CoverageLetter letter = CoverageLetter.builder()
                .company(request.getCompany())
                .fileType(request.getFiletype())
                .fileData(fileBytes)
                .fileName(generateSafeFileName((long) resume.getCoverageLetterList().size(), request.getFiletype()))
                .resume(resume)
                .createdAt(LocalDateTime.now())
                .profession(request.getProfession())
                .language(request.getLanguage())
                .build();

        coverageLetterRepository.save(letter);
        return letter;
    }

    public MediaType resolveMediaType(FileType fileType) {
        return switch (fileType) {
            case PDF -> MediaType.APPLICATION_PDF;
            case DOCX -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        };
    }

    @Transactional
    public void removeById(Long id) {
        log.info("Remove cov letter by ID: {}", id);
        coverageLetterRepository.deleteById(id);
    }

    private String buildPrompt(String resumeText, String company, String position, Languages language) {
        if (language == Languages.ENGLISH) {
            return Promts.COV_LETTER_GENERATION_EN.formatted(company, position, resumeText);
        } else {
            return Promts.COV_LETTER_GENERATION_RU.formatted(company, position, resumeText);
        }
    }

    public CoverageLetter getById(Long id) {
        return coverageLetterRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Cov letter with id " + id + " not found"));
    }

    private String generateSafeFileName(Long id, FileType fileType) {
        String extension = switch (fileType) {
            case PDF -> ".pdf";
            case DOCX -> ".docx";
        };
        return "cover_letter_ " +  id + extension;
    }

}
