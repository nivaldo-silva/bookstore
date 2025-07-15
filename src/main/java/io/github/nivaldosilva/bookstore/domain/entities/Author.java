package io.github.nivaldosilva.bookstore.domain.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "authors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "books" })
@ToString(exclude = { "books" })
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nationality;

    @Column(name = "birth_date", nullable = false)
    @NotNull
    @Past
    private LocalDate birthDate;

    @Column(name = "biography", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String biography;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Book> books;

    @CreationTimestamp
    @Column(name = "creation_timestamp", nullable = false, updatable = false)
    @Builder.Default
    private Instant creationTimestamp = Instant.now();

    @UpdateTimestamp
    @Column(name = "update_timestamp", nullable = false)
    @Builder.Default
    private Instant updateTimestamp = Instant.now();
}
