package co.edu.JdA.controller;

import co.edu.JdA.DTO.ClientResponseDTO;
import co.edu.JdA.entity.ClientEntity;
import co.edu.JdA.exception.ClientNotFoundException;
import co.edu.JdA.exception.IdDuplicadoException;
import co.edu.JdA.exception.InvalidDataException;
import co.edu.JdA.service.ClientService;
import co.edu.JdA.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.JdA.DTO.ClientCreationDTO;

import java.util.List;
import java.util.stream.Collectors;

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
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;
    private final OrderService orderService;

    @Autowired
    public ClientController(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    /**
     * Endpoint to register a new client using a DTO..
     * The client data is received in the request body as a JSON object.
     * @param clientDTO The Client object from the request body.
     * @return A message indicating success.
     */
    @PostMapping("/registrar")
    public ResponseEntity<String> registerClient (@RequestBody ClientCreationDTO clientDTO) {
        log.info("Iniciando registro de cliente");
        try {
            // The service now works with ClientEntity
            clientService.registerClient(clientDTO);
            // Return a better response
            return new ResponseEntity<>("Cliente registrado exitosamente: " + clientDTO.getName(), HttpStatus.CREATED);
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
     * Endopoint to search for a client by their ID.
     * @param id The ID of the client to search for, passed in the URL path.
     * @return A ResponseEntity containing the client dara or an error messagt.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> searchClientById(@PathVariable String id) {
        log.info("Iniciando bùsquedad de cliente con ID: {}", id);
        try {
            // Call the service to search for the client's entity.
            ClientEntity clientEntity = clientService.searchClient(id);
            // Converts the entity to a DTO for the response.
            ClientResponseDTO responseDTO = ClientResponseDTO.fromEntity(clientEntity);
            // Returns the DTO with a 200 OK status
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (ClientNotFoundException err) {
            // It the client is not found, a 404 error is retured.
            log.warn("No se encontró el cliente con ID {}: {}", id. err.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }  catch (Exception err) {
        // 5. Para cualquier otro error, devuelve un error 500.
        log.error("Error inesperado al buscar cliente con ID {}: {}", id, err.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    /**
     * Example endpoint to list all clients.
     * Returns a list of ClientResponseDTO objects to avoid exposing the database entity.
     * @return A list of all clients.
     */
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> listClients() {
        log.info("Solicitud para listar todos los clientes");
        try {
            List<ClientEntity> clients = clientService.listAllClients();
            log.info("Se han recuperado {} clientes.", clients.size());

            // Map each ClientEntity to a ClientResponseDto using the Stream API
            List<ClientResponseDTO> responseList = clients.stream()
                    .map(ClientResponseDTO::fromEntity)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }catch (Exception err) {
            log.error("Error al listar clientes: {}", err.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}