package co.edu.poli.repository;

import co.edu.poli.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}