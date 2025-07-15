package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.entities.Client;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.domain.entities.OrderItem;
import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import io.github.nivaldosilva.bookstore.domain.repositories.BookRepository;
import io.github.nivaldosilva.bookstore.domain.repositories.OrderRepository;
import io.github.nivaldosilva.bookstore.domain.repositories.ClientRepository;
import io.github.nivaldosilva.bookstore.dtos.OrderItemRequestDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderRequestDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderResponseDTO;
import io.github.nivaldosilva.bookstore.exceptions.BookNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.ClientNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.InsufficientStockException;
import io.github.nivaldosilva.bookstore.exceptions.OrderNotFoundException;
import io.github.nivaldosilva.bookstore.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Client client = clientRepository.findByEmail(orderRequestDTO.getClientEmail())
                .orElseThrow(
                        () -> new ClientNotFoundException(
                                "Cliente não encontrado com email: " + orderRequestDTO.getClientEmail()));

        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        for (OrderItemRequestDTO itemRequestDTO : orderRequestDTO.getItems()) {
            Book book = bookRepository.findByIsbn(itemRequestDTO.getBookIsbn())
                    .orElseThrow(() -> new BookNotFoundException(
                            "Livro não encontrado com ISBN: " + itemRequestDTO.getBookIsbn()));

            if (book.getStockQuantity() < itemRequestDTO.getQuantity()) {
                throw new InsufficientStockException("Estoque insuficiente para o livro '" + book.getTitle()
                        + "'. Disponível: " + book.getStockQuantity() + ", Solicitado: "
                        + itemRequestDTO.getQuantity());
            }
            BigDecimal unitPrice = book.getPrice();
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemRequestDTO.getQuantity()));

            // Atualiza o estoque do livro
            book.setStockQuantity(book.getStockQuantity() - itemRequestDTO.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .quantity(itemRequestDTO.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(totalPrice)
                    .build();
            orderItems.add(orderItem);

            totalOrderAmount = totalOrderAmount.add(totalPrice);
        }

        Order order = Order.builder()
                .client(client)
                .totalAmount(totalOrderAmount)
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
        }
        savedOrder.setItems(orderItems); 
        return OrderMapper.toResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado com ID: " + id));

        return OrderMapper.toResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID id, OrderStatus newStatus) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado com ID: " + id));

        existingOrder.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(existingOrder);

        return OrderMapper.toResponseDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Pedido não encontrado com ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
