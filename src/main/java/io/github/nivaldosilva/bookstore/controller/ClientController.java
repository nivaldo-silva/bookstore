package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.service.ClientService;
import io.github.nivaldosilva.bookstore.dtos.ApiResponse;
import io.github.nivaldosilva.bookstore.dtos.PasswordUpdateDTO;
import io.github.nivaldosilva.bookstore.dtos.ClientDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClientDTO>> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        logger.info("Iniciando registro de cliente para email: {}", clientDTO.getEmail());
        ClientDTO registeredClient = clientService.registerClient(clientDTO);
        ApiResponse<ClientDTO> response = new ApiResponse<>("Cliente registrado com sucesso!", registeredClient);
        logger.info("Cliente registrado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> getClientById(@PathVariable("id") UUID id) {
        logger.info("Iniciando busca de cliente por ID: {}", id);
        ClientDTO clientDTO = clientService.findClientById(id);
        ApiResponse<ClientDTO> response = new ApiResponse<>("Cliente encontrado com sucesso!", clientDTO);
        logger.info("Cliente encontrado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients() {
        logger.info("Iniciando busca por todos os clientes.");
        List<ClientDTO> clients = clientService.findAllClients();
        ApiResponse<List<ClientDTO>> response = new ApiResponse<>("Lista de clientes recuperada com sucesso!", clients);
        logger.info("Busca por todos os clientes concluída.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> updateClient(@PathVariable("id") UUID id,
            @Valid @RequestBody ClientDTO clientDTO) {
        logger.info("Iniciando atualização de cliente para ID: {}", id);
        ClientDTO updatedClientDTO = clientService.updateClient(id, clientDTO);
        ApiResponse<ClientDTO> response = new ApiResponse<>("Cliente atualizado com sucesso!", updatedClientDTO);
        logger.info("Cliente atualizado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") UUID id) {
        logger.info("Iniciando exclusão de cliente por ID: {}", id);
        clientService.deleteClient(id);
        logger.info("Cliente excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> updatePassword(@PathVariable("id") UUID id,
            @Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        logger.info("Iniciando atualização de senha para cliente com ID: {}", id);
        ClientDTO updatedClientDTO = clientService.updatePassword(id, passwordUpdateDTO.getNewPassword());
        ApiResponse<ClientDTO> response = new ApiResponse<>("Senha atualizada com sucesso!", updatedClientDTO);
        logger.info("Senha atualizada com sucesso.");
        return ResponseEntity.ok(response);
    }
}
