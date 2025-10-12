package co.edu.JdA.DTO;

import co.edu.JdA.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Data Transfer Object (DTO) for order-related responses.
 * This class encapsulates the data to be returned to the client
 * after a successful operation, providing a clean representation
 * of the order's information.
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private String id;
    private String clientId;
    private LocalDate orderDate;
    private Double total;
    private List<String> details;

    /**
     * Converts an {@link OrderEntity} object into an {@code OrderResponseDTO}.
     * This method acts as a factory method to map the entity to its corresponding DTO.
     * @param entity The {@link OrderEntity} to be converted
     * @return An {@code OrderResponseDTO} containing the mapped data.
     * */
    public static OrderResponseDTO fromEntity(OrderEntity entity) {
        List<String> detailsList = entity.getDetails() != null && !entity.getDetails().isEmpty()
                ? Array.asList(entity.getDetails().split(", "))
                : List.of();
        return new OrderResponseDTO(
                entity.getId(),
                entity.getClient().getId(),
                entity.getOrderDate(),
                entity.getTotal(),
                detailsList
        );
    }
}