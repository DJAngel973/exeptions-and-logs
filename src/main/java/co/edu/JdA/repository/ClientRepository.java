package co.edu.JdA.repository;

import co.edu.JdA.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing persistence operations for the {@link ClientEntity}.
 * <p>
 *     This interface extends {@link JpaRepository}, with provides a complete set of
 *     CRUD (Create, Read, Update, Delete) methods out of the box. Spring Data JPA
 *     automatically generates the implement at runtime.
 * </p>
 * <p>
 *     The generic parameters are:
 *     <u>
 *         <li>{@code ClientEntity}: The entity type this repository is responsible for.</li>
 *         <li>{@code String}: The data type of the entity's primary key (ID).</li>
 *     </u>
 * </p>
 * */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {

    /**
     * Finds a client by their email address.
     * <p>
     *     Spring Data JPA automatically generates the query for this method.
     * </p>
     * @param email The email address of the client.
     * @return An {@link Optional} containing the found client, or empty if not found.
     * */
    Optional<ClientEntity> findByEmail(String email);
}