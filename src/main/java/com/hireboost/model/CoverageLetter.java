package com.hireboost.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hireboost.model.enums.FileType;
import com.hireboost.model.enums.Languages;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "coverage_letters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoverageLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(name = "letter_text", columnDefinition = "TEXT")
    private String letterText;

    @Column(name = "language", length = 30)
    private Languages language;

    @Column(name = "company", length = 100)
    private String company;

    @Column(name = "profession", length = 100)
    private String profession;

    @Column(name = "file_type", length = 20)
    private FileType fileType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_data", columnDefinition = "bytea")
    private byte[] fileData;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

