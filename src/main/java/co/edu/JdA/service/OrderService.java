package co.edu.JdA.service;

import co.edu.JdA.entity.ClientEntity;
import co.edu.JdA.entity.OrderEntity;
import co.edu.JdA.exception.ClientNotFoundException;
import co.edu.JdA.exception.IdDuplicadoException;
import co.edu.JdA.exception.InvalidDataException;
import co.edu.JdA.exception.OrderNotFoundException;
import co.edu.JdA.DTO.ClientResponseDTO;
import co.edu.JdA.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Service for business logic related to orders.
 * This class is responsible for creating, searching, and listing orders.
 * <p>
 *     The {@code @Service} annotation marks this class as a Spring service component.
 *     This allows Spring to automatically detect it and manage its lifecycle, making dependency injection easier.
 * </p>
 * */
@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ClientService clientService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
    }

    /**
     * Create a new order for an existing customer.
     * @param clientId The customer ID for the order.
     * @param total The order total.
     * @param details The list of order details.
     * @return The created order object.
     * @throws InvalidDataException if the order data is invalid.
     * @throws ClientNotFoundException if the customer cannot be found.
     * */
    public OrderEntity createOrder(String clientId, Double total, List<String> details) throws ClientNotFoundException {
        log.debug("Intentando crear un nuevo pedido para el cliente {}", clientId);
        if (clientId == null || clientId.trim().isEmpty() || total <= 0 || details == null || details.isEmpty()) {
            log.error("Datos de pedido inválidos para el cliente {}", clientId);
            throw new InvalidDataException("Datos del pedido incompletos o inválidos.");
        }
        ClientEntity existingClient = clientService.searchClient(clientId);

        OrderEntity newOrder = new OrderEntity();
        newOrder.setId(UUID.randomUUID().toString());
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setTotal(total);
        newOrder.setDetails(String.join(", ",details));
        newOrder.setClient(existingClient);

        OrderEntity savedOrder = orderRepository.save(newOrder);
        log.info("Pedido {} creado exitosamente para el cliente {}", savedOrder.getId(), clientId);

        return savedOrder;
    }

    /**
     * Searches for an order by its ID.
     * @param id The ID of the order to search for.
     * @return The found order object.
     * @throws OrderNotFoundException if the order is not found.
     * @throws InvalidDataException if the ID is null or empty.
     * */
    public OrderEntity searchOrder(String id) throws OrderNotFoundException {
        log.debug("Buscando pedido con ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            log.error("ID de pedido nulo o vació en la búsqueda.");
            throw new InvalidDataException("El ID del pedido no puede ser nulo o vacío.");
        }
        Optional<OrderEntity> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            log.warn("Pedido con ID {} no encontrado.", id);
            throw new OrderNotFoundException(String.format("El pedido con ID %s no encontrado.", id));
        }
        log.info("Pedido con ID {} encontrado exitosamente.", id);
        return order.get();
    }

    /**
     * Finds all orders placed by a specific customer.
     * @param clientId The customer ID.
     * @return A list of the customer's orders.
     * @throws ClientNotFoundException if the customer does not exist.
     * */
    public List<OrderEntity> searchOrdersClient(String clientId) throws ClientNotFoundException {
        log.debug("Buscando pedido para el cliente con ID: {}", clientId);
        if (clientId == null || clientId.trim().isEmpty()) {
            log.error("ID de cliente nulo o vació en la búsqueda de pedidos.");
            throw new InvalidDataException("El ID del cliente no puede ser nulo o vacío.");
        }
        clientService.searchClient(clientId);

        List<OrderEntity> ordersClient = orderRepository.findByClientId(clientId);
        log.info("Se encontraron {} pedidos para el cliente con ID {}", ordersClient.size(), clientId);
        return ordersClient;
    }

    /**
     * List all orders registered in the system.
     * @return A list of all orders.
     * */
    public List<OrderEntity> listAllOrders(){
        log.info("Listando todos los pedidos.");
       return orderRepository.findAll();
    }
}