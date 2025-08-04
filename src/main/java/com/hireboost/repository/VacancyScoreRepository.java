package com.hireboost.repository;

import com.hireboost.model.Resume;
import com.hireboost.model.VacancyScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacancyScoreRepository extends JpaRepository<VacancyScore, Long> {

    Optional<VacancyScore> findByResume(Resume resume);
}
