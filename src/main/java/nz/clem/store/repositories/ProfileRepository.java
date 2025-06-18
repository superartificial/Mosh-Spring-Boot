package nz.clem.store.repositories;

import nz.clem.store.entities.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Profile> findAllByLoyaltyPointsGreaterThanOrderByUserEmail(Integer minPoints);

//    @EntityGraph(attributePaths = {"user"})
//    @Query("SELECT u.id as id, p.user.email as email FROM Profile p JOIN p.user u WHERE p.loyaltyPoints > :minPoints ORDER BY u.email")
//    List<UserSummary> findAllWithMinLoyaltyPoints(@Param("minPoints") Integer minPoints);
}
