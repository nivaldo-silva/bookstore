package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.User;
import io.github.nivaldosilva.bookstore.domain.repositories.UserRepository;
import io.github.nivaldosilva.bookstore.dtos.UserDTO;
import io.github.nivaldosilva.bookstore.exceptions.EmailAlreadyExistsException;
import io.github.nivaldosilva.bookstore.mappers.UserMapper;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + userDTO.getEmail() + "' is already registered.");
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);

        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);

    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserByEmailAsDTO(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<UserDTO> updateUser(UUID id, UserDTO userDTO) {
        return userRepository.findById(id).map(existingUser -> {
            if (!existingUser.getEmail().equals(userDTO.getEmail())
                    && userRepository.existsByEmail(userDTO.getEmail())) {
                throw new EmailAlreadyExistsException(
                        "Email '" + userDTO.getEmail() + "' is already registered by another user.");
            }

            existingUser.setFullName(userDTO.getFullName());
            existingUser.setEmail(userDTO.getEmail());

            User updatedUser = userRepository.save(existingUser);
            return UserMapper.toDTO(updatedUser);
        });
    }

    @Transactional
    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updatePassword(UUID id, String newPassword) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

}
