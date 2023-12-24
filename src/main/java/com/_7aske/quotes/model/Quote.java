package com._7aske.quotes.model;

import com._7aske.quotes.validation.QuoteValid;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 2048, message = "Quote text must be between 3 and 2048 characters")
    @Column(name = "text")
    private String text;

    @NotBlank(message = "Author cannot be empty")
    @Size(min = 3, max = 255, message = "Author must be between 3 and 255 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "author_image")
    private String authorImage;

    @NotNull
    @Column(name = "shared")
    private Boolean shared = true;

    @Column(name = "added_by")
    @Size(min = 3, max = 255, message = "Added by must be between 3 and 255 characters")
    private String addedBy;
}
