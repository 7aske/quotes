package com._7aske.quotes.model;

import com._7aske.quotes.validation.QuoteValid;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@QuoteValid
@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Quote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quote_id")
    private String id;

    @NotBlank(message = "Quote text cannot be empty")
    @Column(name = "text")
    private String text;

    @NotBlank(message = "Author cannot be empty")
    @Column(name = "author")
    private String author;

    @Column(name = "author_image")
    private String authorImage;

    @NotNull
    @Column(name = "shared")
    private Boolean shared = true;
}
