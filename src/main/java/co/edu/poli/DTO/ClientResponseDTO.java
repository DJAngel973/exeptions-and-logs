package co.edu.poli.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for client-related responses.
 * This class encapsulates the data to be returned to be client
 * after a successful operation, providing a clean and secure
 * representation of the client's information without exposing
 * internal database details.
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
public class ClientResponseDTO {
    private String id;
    private String name;
    private String email;
    private LocalDate registrationDate;
}