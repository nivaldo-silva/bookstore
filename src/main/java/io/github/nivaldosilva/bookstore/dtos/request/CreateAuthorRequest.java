package io.github.nivaldosilva.bookstore.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados do autor")
public class CreateAuthorRequest {

    @Schema(description = "ID único do autor")
    private UUID id;

    @Schema(description = "Nome do autor")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Schema(description = "Nacionalidade do autor")
    @NotBlank(message = "Nationality cannot be empty")
    @Size(max = 100, message = "Nationality cannot exceed 100 characters")
    private String nationality;

    @Schema(description = "Data de nascimento do autor")
    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @Schema(description = "Biografia do autor")
    @NotBlank(message = "Biography cannot be empty")
    private String biography;

}
