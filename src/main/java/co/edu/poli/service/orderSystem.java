package co.edu.poli.service;

import co.edu.poli.exception.IdDuplicadoException;
import co.edu.poli.exception.InvalidDataException;
import co.edu.poli.model.Client;
import co.edu.poli.model.Order;

import java.util.Map;

public class orderSystem {
    private Map<String, Client> clients;
    private Map<String, Order> orders;

    public orderSystem(Map<String, Client> clients, Map<String, Order> orders) {
        this.clients = clients;
        this.orders = orders;
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * Lanza DatosInvalidosException si los datos del cliente no son válidos.
     * Lanza IdDuplicadoException si el ID del cliente ya existe.
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
    
}