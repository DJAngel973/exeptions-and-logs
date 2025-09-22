package co.edu.JdA.service;

import co.edu.JdA.entity.ClientEntity;
import co.edu.JdA.exception.ClientNotFoundException;
import co.edu.JdA.exception.IdDuplicadoException;
import co.edu.JdA.exception.InvalidDataException;
import co.edu.JdA.exception.OrderNotFoundException;
import co.edu.JdA.DTO.ClientResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private final Map<String, ClientResponseDTO> orders;
    private final ClientService clientService;

    @Autowired
    public OrderService(Map<String, ClientResponseDTO> orders, ClientService clientService) {
        this.orders = orders;
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
     * @throws IdDuplicadoException if a duplicate order ID is generated.
     * */
    public ClientResponseDTO createOrder(String clientId, Double total, List<String> details) throws ClientNotFoundException {
        log.debug("Intentando crear un nuevo pedido para el cliente {}", clientId);
        if (clientId == null || clientId.trim().isEmpty() || total <= 0 || details == null || details.isEmpty()) {
            log.error("Datos de pedido inválidos para el cliente {}", clientId);
            throw new InvalidDataException("Datos del pedido incompletos o inválidos.");
        }
        ClientEntity existingClient = clientService.searchClient(clientId);

        String orderId = UUID.randomUUID().toString(); // Genera un ID único para el pedido.
        if (orders.containsKey(orderId)) { // Buena práctica verificar.
            log.warn("ID de pedido duplicado generado: {}", orderId);
            throw new IdDuplicadoException("ID del pedido generado duplicado. Intente de nuevo.");
        }
        ClientResponseDTO newOrder = new ClientResponseDTO(orderId, clientId, existingClient.getEmail(), LocalDate.now(), total, String.join(", ", details));
        orders.put(orderId, newOrder);
        log.info("Pedido {} creado exitosamente para el cliente {}", orderId, clientId);
        return newOrder;
    }

    /**
     * Searches for an order by its ID.
     * @param id The ID of the order to search for.
     * @return The found order object.
     * @throws OrderNotFoundException if the order is not found.
     * */
    public ClientResponseDTO searchOrder(String id) throws OrderNotFoundException {
        log.debug("Buscando pedido con ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            log.error("ID de pedido nulo o vació en la búsqueda.");
            throw new OrderNotFoundException("El ID del pedido no puede ser nulo o vacío.");
        }
        ClientResponseDTO order = orders.get(id);
        if (order == null) {
            log.warn("Pedido con ID {} no encontrado.", id);
            throw new OrderNotFoundException(String.format("El pedido con ID %s no encontrado.", id));
        }
        log.info("Pedido con ID {} encontrado exitosamente.", id);
        return order;
    }

    /**
     * Finds all orders placed by a specific customer.
     * @param clientId The customer ID.
     * @return A list of the customer's orders.
     * @throws ClientNotFoundException if the customer does not exist.
     * */
    public List<ClientResponseDTO> searchOrdersClient(String clientId) throws ClientNotFoundException {
        log.debug("Buscando pedido para el cliente con ID: {}", clientId);
        if (clientId == null || clientId.trim().isEmpty()) {
            log.error("ID de cliente nulo o vació en la búsqueda de pedidos.");
            throw new ClientNotFoundException("El ID del cliente no puede ser nulo o vacío.");
        }
        clientService.searchClient(clientId);

        List<ClientResponseDTO> ordersClient = new ArrayList<>();
        for (ClientResponseDTO order : orders.values()) {
            if (order.getId().equals(clientId)){
                ordersClient.add(order);
            }
        }
        log.info("Se encontraron {} pedidos para el cliente con ID {}", ordersClient.size(), clientId);
        return ordersClient;
    }

    /**
     * List all orders registered in the system.
     * @return A list of all orders.
     * */
    public List<ClientResponseDTO> listAllOrders(){
        log.info("Listando todos los pedidos.");
       return new ArrayList<>(orders.values());
    }
}