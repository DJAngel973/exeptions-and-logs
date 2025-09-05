package co.edu.poli.controller;

import co.edu.poli.entity.ClientEntity;
import co.edu.poli.service.ClientService;
import co.edu.poli.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling customer and order requests.
 * <p>
 *     The {@code @RestController} annotation marks this class as a Spring controller.
 *     That handles HTTP requests and returns the response directly.
 *     This class delegates business logic to service classes.
 * </p>
 * */
@RestController
public class OrderSystemController {
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
    @PostMapping("/api/clientes/registrar")
    public ResponseEntity<String> registerClient (@RequestBody ClientEntity client) {
        clientService.registerClient(client);
        return new ResponseEntity<>("Cliente registrado exitosamente: " + client.getName(), HttpStatus.CREATED);
    }

    /**
     * Example endpoint to list all clients.
     * @return A list of all clients.
     */
    @GetMapping("/api/clientes")
    public List<ClientEntity> listClients() {
        return clientService.listAllClients();
    }
}