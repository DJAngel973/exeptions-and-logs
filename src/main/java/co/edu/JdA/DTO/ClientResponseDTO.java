package co.edu.JdA.DTO;

import co.edu.JdA.entity.ClientEntity;
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
    private LocalDate date;
    private Double total;
    private String details;

    /**
     * Converts a {@link ClientEntity} object into a {@code ClientResponseDTO}.
     * This method acts as a factory method to map the entity to its corresponding DTO,
     * ensuring a clear separation of concerns between the persistence and presentation layers.
     *
     * @param entity The {@link ClientEntity} to be converted.
     * @param total The total amount associated with the client.
     * @param details Additonal details about the client.
     * @return A {@code ClientResponseDTO} containing the mapped data.
     */
    public static ClientResponseDTO fromEntity(ClientEntity entity, Double total, String details) {
        // Aseguramos que los tipos sean explícitos
        Double validatedTotal = total != null ? total : 0.0;
        String validatedDetails = details != null ? details : "";

        return new ClientResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRegistrationDate(),
                validatedTotal,
                validatedDetails
        );
    }
    // Overloading for when you only have the ClientEntity.
    public static ClientResponseDTO fromEntity(ClientEntity entity) {
        return fromEntity(entity, 0.0, "");
    }
}