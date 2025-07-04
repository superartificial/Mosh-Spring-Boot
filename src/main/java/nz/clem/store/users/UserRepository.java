package nz.clem.store.users;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"tags","addresses"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"addresses"})
    @Query("select u from User u") // don't need to add JPQL to get addresses because of @EntityGraph
    List<User> findAllWithAddresses();

    boolean existsByEmail(String email);

    // dont need @EntityGraph because profile eagerly loaded
//    @Query("SELECT u.id as id, u.email as email FROM User u WHERE u.profile.loyaltyPoints > :minPoints ORDER BY u.email")
//    List<UserSummary> findLoyalUsers(@Param("minPoints") Integer minPoints);

}
