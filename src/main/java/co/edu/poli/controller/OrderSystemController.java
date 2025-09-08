package co.edu.poli.controller;

import co.edu.poli.entity.ClientEntity;
import co.edu.poli.exception.IdDuplicadoException;
import co.edu.poli.exception.InvalidDataException;
import co.edu.poli.service.ClientService;
import co.edu.poli.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * Controller for handling customer and order requests.
 * <p>
 *     The {@code @RestController} annotation marks this class as a Spring controller.
 *     That handles HTTP requests and returns the response directly.
 *     This class delegates business logic to service classes.
 * </p>
 * */
@RestController
@RequestMapping("api/clientes")
public class OrderSystemController {
    private static final Logger log = LoggerFactory.getLogger(OrderSystemController.class);
    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public OrderSystemController(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    /**
     * Endpoint to register a new client.
     * The client data is received in the request body as a JSON object.
     * @param client The Client object from the request body.
     * @return A message indicating success.
     */
    @PostMapping("/registrar")
    public ResponseEntity<String> registerClient (@RequestBody ClientEntity client) {
        log.info("Iniciando registro de cliente");
        try {
            // The service now works with ClientEntity
            clientService.registerClient(client);
            // Return a better response
            return new ResponseEntity<>("Cliente registrado exitosamente: " + client.getName(), HttpStatus.CREATED);
        }catch (IdDuplicadoException err) {
            log.error("Error al registrar cliente: {}", err.getMessage());
            return new ResponseEntity<>(err.getMessage(), HttpStatus.CONFLICT); // 409
        }catch (InvalidDataException err) {
            log.error("Error al registrar el cliente: {}", err.getMessage());
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST); // 400
        }catch (Exception err) {
            log.error("Error inesperado al registrar cliente: {}", err.getMessage());
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Example endpoint to list all clients.
     * @return A list of all clients.
     */
    @GetMapping
    public ResponseEntity<List<ClientEntity>> listClients() {
        log.info("Solicitud para listar todos los clientes");
        try {
            List<ClientEntity> clients = clientService.listAllClients();
            log.info("Se han recuperado {} clientes.", clients.size());
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }catch (Exception err) {
            log.error("Error al listar clientes: {}", err.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}