package com.hireboost.service;

import com.hireboost.exception.ResumeNotFoundException;
import com.hireboost.exception.VacancyNotFoundException;
import com.hireboost.model.Resume;
import com.hireboost.model.Vacancy;
import com.hireboost.model.VacancyScore;
import com.hireboost.promt.Promts;
import com.hireboost.repository.VacancyScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyScoreService {

    private final VacancyScoreRepository vacancyScoreRepository;
    private final OpenAiService openAiService;
    private final ResumeService resumeService;

    @Transactional
    public void removeVacancyScoreById(Long id) {
        log.info("Remove vacancy score with id:{}", id);
        VacancyScore vacancyScore = vacancyScoreRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("VacancyScore not found"));
        Resume resume = vacancyScore.getResume();
        if (resume != null) {
            resumeService.removeById(resume.getId());
        }
        vacancyScore.setResume(null);
        vacancyScore.setVacancy(null);
        vacancyScoreRepository.delete(vacancyScore);
    }

    @Transactional
    public void createVacancyScore(Resume resume, Vacancy vacancy) {
        String promtScore = Promts.VACANCY_RESUME_SCORE.formatted(vacancy.getDescription(), resume.getResumeText());
        VacancyScore vacancyScore = new VacancyScore();
        vacancyScore.setResume(resume);
        vacancyScore.setVacancy(vacancy);
        String score = openAiService.generateText(promtScore);
        vacancyScore.setScore(score);
        vacancyScoreRepository.save(vacancyScore);
    }

    @Transactional
    public String analyzeResume(Long id) {
        VacancyScore vacancyScore = vacancyScoreRepository.findById(id)
                .orElseThrow(() -> new VacancyNotFoundException("Vacancy Score not found for resume"));

        Resume resume = vacancyScore.getResume();
        if(resume == null) {
            throw new ResumeNotFoundException("Resume not found for vacancy score");
        }

        String summaryPromt = Promts.VACANCY_APPLICANT_RESUME_ANALYZE.formatted(vacancyScore.getVacancy().getDescription(), resume.getResumeText());
        String questionsPromt = Promts.VACANCY_RECOMMENDED_QUESTIONS.formatted(vacancyScore.getVacancy().getDescription(), resume.getResumeText());

        String summary = openAiService.generateText(summaryPromt);
        String questions = openAiService.generateText(questionsPromt);

        vacancyScore.setSummary(summary);
        vacancyScore.setQuestions(questions);
        vacancyScoreRepository.save(vacancyScore);

        return summary;
    }
}
