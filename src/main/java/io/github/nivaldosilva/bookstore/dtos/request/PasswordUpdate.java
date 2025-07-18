package io.github.nivaldosilva.bookstore.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO para atualização de senha")
public class PasswordUpdate {

    @Schema(description = "Senha atual do cliente")
    @NotBlank(message = "Current password cannot be empty")
    private String currentPassword;

    @Schema(description = "Nova senha do cliente")
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;

}
