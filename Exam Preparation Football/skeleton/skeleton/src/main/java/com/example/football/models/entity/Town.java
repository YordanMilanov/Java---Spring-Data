package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "towns")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Town {

/*    Town
•	id – accepts integer values, a primary identification field, an auto incremented field.
•	name – accepts char sequences as values where their character length value higher than or equal to 2. The values are unique in the database.
•	population – accepts number values (must be a positive number), 0 as a value is exclusive.
•	travel guide – a long and detailed description of all known places with a character length value higher than or equal to 10.*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long population;

    @Column(nullable = false, name = "travel_guide", columnDefinition = "TEXT")
    private String travelGuide;
}
