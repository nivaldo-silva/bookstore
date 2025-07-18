package io.github.nivaldosilva.bookstore.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados para registro de um novo cliente")
public class CustomerRegistrationRequest {

    @Schema(description = "ID único do cliente (gerado automaticamente no registro)")
    private UUID id;

    @Schema(description = "Nome completo do cliente")
    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 150, message = "Full name cannot exceed 150 characters")
    private String fullName;

    @Schema(description = "Email do cliente")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Schema(description = "Senha do cliente")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
