package co.edu.JdA.repository;

import co.edu.JdA.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing persistence operations for the {@link OrderEntity}.
 * <p>
 *     This interface extends {@link JpaRepository}, providing standard methods for
 *     interface with the database. The implementation is automatically handled by Spring at runtime.
 * </p>
 * <p>
 *     The generic parameters are:
 *     <ul>
 *         <li>{@code OrderEntity}: The entity type this repository is responsible for.</li>
 *         <li>{@code String}: The data type of the entity's primary key (ID).</li>
 *     </ul>
 * </p>
 * */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    /**
     * Finds all orders belonging to a specific client.
     * <p>
     *     Spring Data JPA automatically generates the query for this method
     *     based on the method name.
     * </p>
     * @param clientId The ID of the client.
     * @return A list of orders belonging to the specified client.
     * */
    List<OrderEntity> findByClientId(String clientId);
}