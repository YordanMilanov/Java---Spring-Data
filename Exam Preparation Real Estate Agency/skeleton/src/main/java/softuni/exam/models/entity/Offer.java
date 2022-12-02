package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
@Getter
@Setter
@NoArgsConstructor
public class Offer extends BaseEntity {

    @Column(nullable = false)
    private Double price;

    @Column(name = "published_on", nullable = false)
    private LocalDate publishedOn;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Agent agent;

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №%d:\n" +
                        "\t-Apartment area: %.2f\n" +
                        "\t--Town: %s\n" +
                        "\t---Price: %.2f$",
                agent.getFirstName(), agent.getLastName(), this.getId(),
                apartment.getArea(),
                apartment.getTown().getTownName(),
                this.price);
    }
}
