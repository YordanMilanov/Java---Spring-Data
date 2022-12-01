package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByEmail(String email);

    @Query("SELECT p FROM Player p WHERE p.birthDate between ?1 AND ?2 ORDER BY p.stat.shooting DESC,p.stat.passing DESC, p.stat.endurance DESC, p.lastName")
    Optional<List<Player>>findBestPlayers(LocalDate begin, LocalDate end);
}
