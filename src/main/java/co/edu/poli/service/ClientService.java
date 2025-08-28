package co.edu.poli.service;

import co.edu.poli.exception.ClientNotFoundException;
import co.edu.poli.exception.IdDuplicadoException;
import co.edu.poli.exception.InvalidDataException;
import co.edu.poli.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service for customer-related business logic.
 * This class is responsible for registering, searching and listing customers.
 * <p>
 *     The {@code @Service} annotation marks this class as a Spring service component.
 *     This allows Spring to automatically detect it and manage ist lifecycle,
 *     which facilitates dependency injection.
 * </p>
 * */
@Service
public class ClientService {
    private Map<String, Client> clients;

    @Autowired
    public ClientService(){
        this.clients = clients;
    }

    @Autowired
    public ClientService(Map<String, Client> clients){
        this.clients = clients;
    }

    /**
     * Registers a new client in the system.
     * @param client The client object to register.
     * @throws InvalidDataException if the client data is incomplete or invalid.
     * @throws  IdDuplicadoException if a client with the same ID already exists.
     */
    public void registerClient(Client client){
        if(client == null || client.getId() == null || client.getId().trim().isEmpty() || client.getName() == null ||
                client.getName().trim().isEmpty() || client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("Datos del cliente incompletos o inválidos.");
        }
        if (clients.containsKey(client.getId())){
            throw new IdDuplicadoException(String.format("El cliente con ID %s ya existe",client.getId()));
        }
        clients.put(client.getId(), client);
        System.out.printf("Cliente registrado exitosamente: %s (Id: %s)",client.getName(), client.getId());
    }

    /**
     * Search for a client by its ID.
     * @param id The ID of the client to search for.
     * @return The client found.
     * @throws InvalidDataException if the ID is null or empty.
     * @throws ClientNotFoundException if the client is not found.
     * */
    public Client searchClient(String id) throws ClientNotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("El ID del cliente no puede ser nulo o vacío.");
        }
        Client client = clients.get(id);
        if (client == null) {
            throw new ClientNotFoundException(String.format("Cliente con ID %s no encontrado.",id));
        }
        return client;
    }

    /**
     * List all clients registered in the system.
     * @return A list of all clients.
     * */
    public List<Client> listAllClients() {
        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return List.of();
        }
        System.out.println("\n------ Clientes registrados ------");
        clients.values().forEach(System.out::println);
        System.out.println("----------------------------------");
        return List.copyOf(clients.values());
    }
}