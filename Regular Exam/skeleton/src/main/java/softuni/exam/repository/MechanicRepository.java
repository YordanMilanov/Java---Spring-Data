package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Mechanic;

import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {

    Optional<Mechanic> findByEmail(String email);

    @Query("SELECT m FROM Mechanic m WHERE m.firstName = :firstName")
    Optional<Mechanic> findByFirstName(String firstName);
}
