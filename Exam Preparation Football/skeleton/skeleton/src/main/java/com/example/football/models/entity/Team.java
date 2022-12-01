package com.example.football.models.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "stadium_name", nullable = false)
    private String stadiumName;


    @Column(name = "fan_base",nullable = false)
    private Long fanBase;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String history;


    //many teams can be from one town
    @ManyToOne(optional = false)
    private Town town;

    //one team has many players
    //targetEntity <- to witch class | mappedBy <- to witch field
    //mappedBy is used when it is the second relation (biDirectional) <- response side
    //biDirectional is not mandatory, it is used only when needed.
    @OneToMany(targetEntity = Player.class, mappedBy = "team")
    private Set<Player> players;
}
