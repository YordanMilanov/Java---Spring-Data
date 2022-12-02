package softuni.exam.models.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer extends BaseEntity{

    @Column
    private Double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "has_gold_status")
    private boolean hasGoldStatus;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Seller seller;

    @ManyToMany
    private Set<Picture> pictures;

}
