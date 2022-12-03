package exam.model;

import exam.model.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Column(nullable = false, name = "mac_address")
    private String macAddress;

    @Column(nullable = false, name = "cpu_speed")
    private Double cpuSpeed;

    @Column(nullable = false)
    private int ram;

    @Column(nullable = false)
    private int storage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;
}
