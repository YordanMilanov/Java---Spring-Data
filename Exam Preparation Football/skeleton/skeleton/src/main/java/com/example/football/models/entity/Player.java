package com.example.football.models.entity;

import com.example.football.models.entity.enums.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private PlayerPosition position;

    //many players can be from one town
    @ManyToOne(optional = false)
    private Town town;

    //many players can play in one team
    @ManyToOne
    private Team team;

    //one player can have one statistics.
    @OneToOne
    private Stat stat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id) && email.equals(player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    public String BestPlayerExport(){
        return String.format("Player - %s %s\n" +
                "\tPosition - %s\n" +
                "\tTeam - %s\n" +
                "\tStadium - %s\n",
                this.getFirstName(), this.getLastName(),
                this.getPosition().toString(),
                this.team.getName(),
                this.team.getStadiumName());
    }
}
