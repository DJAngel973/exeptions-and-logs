package co.edu.JdA.controller;

import co.edu.JdA.DTO.OrderCreateDTO;
import co.edu.JdA.DTO.OrderResponseDTO;
import co.edu.JdA.entity.OrderEntity;
import co.edu.JdA.exception.ClientNotFoundException;
import co.edu.JdA.exception.IdDuplicadoException;
import co.edu.JdA.exception.InvalidDataException;
import co.edu.JdA.exception.OrderNotFoundException;
import co.edu.JdA.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing orders.
 * <p>
 * This class exposes a REST API for order-related operations. It handles
 * incoming HTTP requests, delegates business logic to the {@link OrderService},
 * and formats the responses to be sent back to the client.
 * </p>
 *
 * @see OrderService
 * @see OrderCreateDTO
 */
@RestController
@RequestMapping("api/ordenes")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Registers a new order in the system.
     * @param orderDTO The DTO containing the order data (clientId, total, details).
     * @return A {@link ResponseEntity} with a success message and status 201 (Created).
     * In case of an error, returns an appropriate status code and message.
     * */
    @PostMapping("/registrar")
    public ResponseEntity<String> registerOrder (@RequestBody OrderCreateDTO orderDTO) {
        log.info("Creando orden para el cliente: {}", orderDTO.getClientId());
        try {
            orderService.createOrder(orderDTO.getClientId(), orderDTO.getTotal(), orderDTO.getDetails());
            return new ResponseEntity<>("Orden creada exitosamente", HttpStatus.CREATED);
        } catch (IdDuplicadoException error) {
            log.error("Error al registrar orden: {}", error.getMessage());
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        } catch (InvalidDataException | ClientNotFoundException error) {
            log.error("Error al registrar la orden, datos inválidos o cliente no encontrado: {}", error.getMessage());
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception error) {
            log.error("Error inesperado al crear orden: {}", error.getMessage());
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to search for an order by its ID.
     * @param id The ID of the order to search fot, passed in the URL path.
     * @return A ResponseEntity containing the order data or an error message.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> searchOrderById(@PathVariable String id) {
        log.info("Iniciando búsqueda de orden con ID: {}", id);
        try {
            // Call the service to search for the order entity
            OrderEntity orderEntity = orderService.searchOrder(id);
            // Convert the entity to a DTO fot the response
            OrderResponseDTO responseDTO = OrderResponseDTO.fromEntity(orderEntity);
            // Return the DTO with a 200 OK status
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (OrderNotFoundException error) {
            // If the order is not found, return a 404 error
            log.warn("No se encontró la orden con ID {}: {}", id, error.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception error) {
            // For any other error, return a 500 error
            log.error("Error inesperado al buscar orden con ID {}: {}", id, error.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}