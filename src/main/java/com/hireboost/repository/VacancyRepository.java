package com.hireboost.repository;

import com.hireboost.model.Resume;
import com.hireboost.model.User;
import com.hireboost.model.Vacancy;
import com.hireboost.model.VacancyScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllByUser(User user);

    Optional<Vacancy> findByFilename(String filename);

    Boolean existsByFilename(String filename);
}
