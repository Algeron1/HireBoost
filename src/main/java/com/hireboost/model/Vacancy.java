package com.hireboost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hireboost.model.enums.FileType;
import com.hireboost.util.MonetaryAmount;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnoreProperties("vacancy")
    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VacancyScore> vacancyScores;

    @Column(columnDefinition = "TEXT", name = "recommendations")
    private String recommendations;

    @Column(name = "score")
    private String score;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "text", columnDefinition = "TEXT")
    private String vacancyText;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "salary_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "salary_currency"))
    })
    private MonetaryAmount salary;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_data", columnDefinition = "bytea")
    private byte[] fileData;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "file_type", length = 20)
    private FileType fileType;

}
