package co.edu.JdA.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Data Transfer Object (DTO) for creating a new order.
 * This class encapsulates the data required to register an order
 * from an API request, ensuring a clear separation of responsibilities
 * between the API layer and the persistence layer.
 * <p>
 *     The {@code @Data} annotation from Lombok automatically generates
 *     getters, setters, equals, hashCode, and the toString method.
 *     The {@code @AllArgsConstructor} and {@code @NoArgsConstructor} annotations
 *     create a constructor with all fields and a default constructor, respectively.
 * </p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
    private String clientId;
    private Double total;
    private List<String> details;
}