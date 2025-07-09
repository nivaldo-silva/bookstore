package io.github.nivaldosilva.bookstore.domain.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.ISBN;
import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"author", "orderItems"})
@ToString(exclude = {"author", "orderItems"})
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 17)
    @ISBN
    private String isbn;
    
    @Column(nullable = false, length = 200)
    @NotBlank
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String synopsis;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;
    
    @Column(name = "publication_date", nullable = false)
    @NotNull
    @Past
    private LocalDate publicationDate;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal price;
    
    @Column(name = "stock_quantity", nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer stockQuantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private Author author;
    
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    @CreationTimestamp
    private Instant creationTimestamp;
    
    @UpdateTimestamp
    private Instant updateTimestamp;
}
