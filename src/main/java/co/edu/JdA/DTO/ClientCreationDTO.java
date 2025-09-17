package co.edu.JdA.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating a new client.
 * This class encapsulates the data required to register a client
 * form an API request, ensuring a clear separation of concerns
 * between the API layer and the persistence layer
 * <p>
 *     The {@code @Data} annotation from Lombok automatically generates
 *     getters, setters, equals, hashCode, and toString methods.
 *     The {@code @AllArgsConstructor} and {@code @NoArgsConstructor}
 *     create a constructor with all fields and a default constructor, respectively.
 * </p>
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreationDTO {
    private String id;
    private String name;
    private String email;
}