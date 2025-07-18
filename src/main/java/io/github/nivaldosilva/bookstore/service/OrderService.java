package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.entities.Customer;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.domain.entities.OrderItem;
import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import io.github.nivaldosilva.bookstore.domain.repositories.BookRepository;
import io.github.nivaldosilva.bookstore.domain.repositories.OrderRepository;
import io.github.nivaldosilva.bookstore.dtos.request.OrderItemRequest;
import io.github.nivaldosilva.bookstore.dtos.request.OrderRequest;
import io.github.nivaldosilva.bookstore.dtos.response.OrderResponse;
import io.github.nivaldosilva.bookstore.exceptions.BookNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.CustomerNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.InsufficientStockException;
import io.github.nivaldosilva.bookstore.exceptions.OrderNotFoundException;
import io.github.nivaldosilva.bookstore.domain.repositories.CustomerRepository;
import io.github.nivaldosilva.bookstore.utils.mappers.OrderMapper;
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
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 1. Buscar o cliente
        Customer customer = customerRepository.findByEmail(orderRequest.getCustomerEmail())
                .orElseThrow(
                        () -> new CustomerNotFoundException(
                                "Cliente não encontrado com email: " + orderRequest.getCustomerEmail()));

        Order order = Order.builder()
                .customer(customer)
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

       
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Book book = bookRepository.findByIsbn(itemRequest.getBookIsbn())
                    .orElseThrow(() -> new BookNotFoundException(
                            "Livro não encontrado com ISBN: " + itemRequest.getBookIsbn()));

            if (book.getStockQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Estoque insuficiente para o livro '" + book.getTitle()
                        + "'. Disponível: " + book.getStockQuantity() + ", Solicitado: "
                        + itemRequest.getQuantity());
            }

            BigDecimal unitPrice = book.getPrice();
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            book.setStockQuantity(book.getStockQuantity() - itemRequest.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(totalPrice)
                    .order(order)
                    .build();
            orderItems.add(orderItem);

            totalOrderAmount = totalOrderAmount.add(totalPrice);
        }

        order.setTotalAmount(totalOrderAmount);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado com ID: " + id));

        return OrderMapper.toResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(UUID id, OrderStatus newStatus) {
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
