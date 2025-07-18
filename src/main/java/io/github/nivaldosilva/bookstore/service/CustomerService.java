package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Customer;
import io.github.nivaldosilva.bookstore.domain.repositories.CustomerRepository;
import io.github.nivaldosilva.bookstore.dtos.request.CustomerRegistrationRequest;
import io.github.nivaldosilva.bookstore.dtos.response.CustomerProfileResponse;
import io.github.nivaldosilva.bookstore.exceptions.CustomerNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.EmailAlreadyExistsException;
import io.github.nivaldosilva.bookstore.utils.mappers.CustomerMapper;
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
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerProfileResponse registerCustomer(CustomerRegistrationRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + request.getEmail() + "' já está registrado.");
        }
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encryptedPassword);

        Customer customer = CustomerMapper.toEntity(request);
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toProfileResponseDTO(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerProfileResponse findCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado com ID: " + id));
        return CustomerMapper.toProfileResponseDTO(customer);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<CustomerProfileResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toProfileResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerProfileResponse updateCustomer(UUID id, CustomerRegistrationRequest request) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado com ID: " + id));

        if (!existingCustomer.getEmail().equals(request.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email '" + request.getEmail() + "' já está registrado por outro cliente.");
        }

        existingCustomer.setFullName(request.getFullName());
        existingCustomer.setEmail(request.getEmail());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toProfileResponseDTO(updatedCustomer);
    }

     @Transactional
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Cliente não encontrado com ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Transactional
    public CustomerProfileResponse updatePassword(UUID id, String newPassword) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado com ID: " + id));
        customer.setPassword(passwordEncoder.encode(newPassword));
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.toProfileResponseDTO(updatedCustomer);
    }
}
