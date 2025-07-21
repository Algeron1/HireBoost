package com.hireboost.repository;

import com.hireboost.model.Resume;
import com.hireboost.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

    List<Resume> findAllByUser(User user);

    Optional<Resume> findByFileName(String filename);

    Boolean existsByFileName(String filename);
}
