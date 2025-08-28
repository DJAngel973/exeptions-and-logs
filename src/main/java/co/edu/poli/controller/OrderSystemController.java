package co.edu.poli.controller;

import co.edu.poli.model.Client;
import co.edu.poli.service.ClientService;
import co.edu.poli.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Example endpoint for registering a client.
     * In a real-world application, a POST would be used and the data would be received in the request body.
     * @return A message indicating success.
     */
    @PostMapping("/api/clientes/registrar")
    public String registerClient() {
        Client client = new Client("J001", "Jose Salazar", "jose.98@gmail.com");
        clientService.registerClient(client);
        return "Cliente registrado exitosamente: " + client.getName();
    }

    /**
     * Example endpoint to list all clients.
     * @return A list of all clients.
     */
    @GetMapping("/api/clientes")
    public List<Client> listClients() {
        return List.copyOf(clientService.listAllClients());
    }
}