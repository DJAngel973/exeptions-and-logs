package co.edu.JdA.service;

import co.edu.JdA.DTO.ClientCreationDTO;
import co.edu.JdA.entity.ClientEntity;
import co.edu.JdA.exception.ClientNotFoundException;
import co.edu.JdA.exception.IdDuplicadoException;
import co.edu.JdA.exception.InvalidDataException;
import co.edu.JdA.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    /**
     * Registers a new client in the system.
     * It maps the DTO received from the controller to an entity to be persisted.
     *
     * @param clientDTO The DTO with the client's data.
     * @return The created ClientEntity.
     * @throws InvalidDataException if the client data is incomplete or invalid.
     * @throws  IdDuplicadoException if a client with the same ID or email already exists.
     */
    public ClientEntity registerClient(ClientCreationDTO clientDTO) throws IdDuplicadoException, InvalidDataException {
        log.info("Iniciando registro de clientes con ID: {}", clientDTO.getId());

        if(clientDTO.getId() == null || clientDTO.getId().trim().isEmpty() ||
                clientDTO.getName() == null || clientDTO.getName().trim().isEmpty() ||
                clientDTO.getEmail() == null || clientDTO.getEmail().trim().isEmpty()) {
            log.error("Error al registrar cliente: datos incompletos o inválidos.");
            throw new InvalidDataException("Datos del cliente incompletos o inválidos.");
        }
        // Use the repository to check for existence.
        if ( clientRepository.existsById(clientDTO.getId()) || clientRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            log.warn("Intento de registro de cliente con ID {} o email {} duplicado.", clientDTO.getId(), clientDTO.getEmail());
            throw new IdDuplicadoException(String.format("El cliente con ID %s o email %s ya existe",clientDTO.getId(), clientDTO.getEmail()));
        }
        // Map the DTO to the entity and set the registration date.
        ClientEntity newClient = new ClientEntity();
        newClient.setId(clientDTO.getId());
        newClient.setName(clientDTO.getName());
        newClient.setEmail(clientDTO.getEmail());
        newClient.setRegistrationDate(LocalDate.now());

        // Saves the entity to the database.
        ClientEntity savedClient = clientRepository.save(newClient);
        log.info("Cliente registrado exitosamente: {} (ID: {}).", savedClient.getName(), savedClient.getId());

        return savedClient;
    }

    /**
     * Search for a client by its ID.
     * @param id The ID of the client to search for.
     * @return The client found.
     * @throws InvalidDataException if the ID is null or empty.
     * @throws ClientNotFoundException if the client is not found.
     * */
    public ClientEntity searchClient(String id) throws ClientNotFoundException {
        log.info("Buscando cliente con ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            log.error("ID de cliente nulo o vacío en la búsqueda.");
            throw new InvalidDataException("El ID del cliente no puede ser nulo o vacío.");
        }
        Optional<ClientEntity> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            log.warn("Cliente con ID {} no encontrado.", id);
            throw new ClientNotFoundException(String.format("Cliente con ID %s no encontrado.",id));
        }
        log.info("Cliente con ID {} encontrado.", id);
        return client.get();
    }

    /**
     * List all clients registered in the system.
     * @return A list of all clients.
     * */
    public List<ClientEntity> listAllClients() {
        log.info("Listando todos los clientes.");
        return clientRepository.findAll();
    }

    /**
     * Delete a client by their ID
     * @param id The ID of the client to be deleted.
     * @return The deleted client
     * @throws InvalidDataException if the ID is null or empty.
     * @throws ClientNotFoundException if the client does not exist.
     * */
    public ClientEntity deleteClient(String id) throws ClientNotFoundException {
        log.info("Eliminando el cliente con ID: {}", id);
        if(id == null || id.trim().isEmpty()) {
            log.error("ID de cliente nulo o vacío.");
            throw new InvalidDataException("El ID del cliente no puede ser nulo o vació.");
        }
        Optional<ClientEntity> clientOpt = clientRepository.findById(id);
        if(clientOpt.isEmpty()) {
            log.warn("Cliente con ID {} no se encontro para eliminar.", id);
            throw new ClientNotFoundException(String.format("Cliente con ID %s no encontrado.", id));
        }
        clientRepository.deleteById(id);
        log.info("Cliente con ID {} eliminado exitosamente.", id);
        return clientOpt.get();
    }

    /**
     * Updates the data of an existing client.
     * <p>
     *     Searches for the client by its ID and updates the allowed fields (name and email)
     *     with the values provided in the DTO. Does not modify the ID or registration date.
     * </p>
     * @param id The ID of the client to update.
     * @param clientDTO DTO with the new client data.
     * @return The updated client.
     * @throws ClientNotFoundException if the client does not exist.
     * @throws InvalidDataException if the ID is null or empty.
     * */
    public ClientEntity updateClient(String id, ClientCreationDTO clientDTO) throws ClientNotFoundException, InvalidDataException {
        log.info("Actualización cliente con ID: {}", id);
        if(id == null || id.trim().isEmpty()) {
            log.error("ID de cliente nulo o vacìo en actualización.");
            throw new InvalidDataException("El ID del cliente no puede ser nulo o vacío.");
        }
        Optional<ClientEntity> clientOpt = clientRepository.findById(id);
        if(clientOpt.isEmpty()) {
            log.warn("Cliente con ID {} no encontrado para actualizar.", id);
            throw new ClientNotFoundException(String.format("Cliente con ID %s no encontrado.", id));
        }
        ClientEntity client = clientOpt.get();
        // Update the allowed fields
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        // Registration date and ID are not updated
        ClientEntity updatedClient = clientRepository.save(client);
        log.info("Cliente con ID {} actualizado exitosamente.", id);
        return updatedClient;
    }
}