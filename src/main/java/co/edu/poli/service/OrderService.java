package co.edu.poli.service;

import co.edu.poli.exception.ClientNotFoundException;
import co.edu.poli.exception.IdDuplicadoException;
import co.edu.poli.exception.InvalidDataException;
import co.edu.poli.exception.OrderNotFoundException;
import co.edu.poli.model.Client;
import co.edu.poli.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderSystem {

    private Map<String, Order> orders;

    public OrderSystem(Map<String, Client> clients, Map<String, Order> orders) {
        this.clients = clients;
        this.orders = orders;
    }

    public OrderSystem() {
        this.clients = clients;
        this.orders = orders;
    }



    /**
     * Crea un nuevo pedido para un cliente existente.
     * */
    public Order createOrder(String clientId, Double total, List<String> details) throws ClientNotFoundException {
        if (clientId == null || clientId.trim().isEmpty() || total <= 0 || details == null || details.isEmpty()) {
            throw new InvalidDataException("Datos del pedido incompletos o inválidos.");
        }
        if (!clients.containsKey(clientId)){
            throw new ClientNotFoundException(String.format("El cliente con ID %s no existe", clientId));
        }
        String orderId = UUID.randomUUID().toString(); // Genera un ID único para el pedido.
        if (orders.containsKey(orderId)) { // Buena práctica verificar.
            throw new IdDuplicadoException("ID del pedido generado duplicado. Intente de nuevo.");
        }
        Order newOrder = new Order(orderId, clientId, LocalDate.now(),total, details);
        orders.put(orderId, newOrder);
        System.out.printf("Pedido %s creado exitosamente para el cliente %s.", newOrder.getId(), clientId);
        return newOrder;
    }



    /**
     * Buscar un pedido por su ID.
     * Search order for ID
     * */
    public Order searchOrder(String id) throws OrderNotFoundException {
        if (id == null || id.trim().isEmpty()) {
            throw new OrderNotFoundException("El ID del pedido no puede ser nulo o vacío.");
        }
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException(String.format("El pedido con ID %s no encontrado.", id));
        }
        return order;
    }

    /**
     * Buscar todos los pedidos realizados por un cliente en específico.
     * Search all orders for a client.
     * */
    public List<Order> searchOrdersClient(String clientId) throws ClientNotFoundException {
        if (clientId == null || clientId.trim().isEmpty()) {
            throw new ClientNotFoundException("El ID del cliente no puede ser nulo o vacío.");
        }
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException(String.format("El cliente con ID %s no existe.", clientId));
        }
        List<Order> ordersClient = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getClientId().equals(clientId)){
                ordersClient.add(order);
            }
        }
        return ordersClient;
    }



    /**
     * Listar todos los pedidos.
     * */
    public void listAllOrders(){
        if (orders.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        System.out.println("\n------ Pedidos registrados ------");
        orders.values().forEach(System.out::println);
        System.out.println("-----------------------------------");
    }
}