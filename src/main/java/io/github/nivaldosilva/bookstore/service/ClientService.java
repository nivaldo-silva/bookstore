package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Client;
import io.github.nivaldosilva.bookstore.domain.repositories.ClientRepository;
import io.github.nivaldosilva.bookstore.dtos.ClientDTO;
import io.github.nivaldosilva.bookstore.exceptions.ClientNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.EmailAlreadyExistsException;
import io.github.nivaldosilva.bookstore.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ClientDTO registerClient(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + clientDTO.getEmail() + "' já está registrado.");
        }

        String encryptedPassword = passwordEncoder.encode(clientDTO.getPassword());
        clientDTO.setPassword(encryptedPassword);

        Client client = ClientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);

        return ClientMapper.toDTO(savedClient);
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado com ID: " + id));
        return ClientMapper.toDTO(client);
    }

    @Transactional(readOnly = true)
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientDTO updateClient(UUID id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado com ID: " + id));

        if (!existingClient.getEmail().equals(clientDTO.getEmail())
                && clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email '" + clientDTO.getEmail() + "' já está registrado por outro cliente.");
        }

        existingClient.setFullName(clientDTO.getFullName());
        existingClient.setEmail(clientDTO.getEmail());

        Client updatedClient = clientRepository.save(existingClient);
        return ClientMapper.toDTO(updatedClient);
    }

    @Transactional
    public void deleteClient(UUID id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    public ClientDTO updatePassword(UUID id, String newPassword) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado com ID: " + id));
        client.setPassword(passwordEncoder.encode(newPassword));
        Client updatedClient = clientRepository.save(client);
        return ClientMapper.toDTO(updatedClient);
    }
}