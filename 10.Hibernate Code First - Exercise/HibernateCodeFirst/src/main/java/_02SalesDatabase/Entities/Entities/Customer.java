package _02SalesDatabase.Entities.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumbers;

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    private Set<Sale> sales;

    public Customer() {
    }

    public Customer(String name, String email, String creditCardNumbers) {
        this.name = name;
        this.email = email;
        this.creditCardNumbers = creditCardNumbers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumbers() {
        return creditCardNumbers;
    }

    public void setCreditCardNumbers(String creditCardNumbers) {
        this.creditCardNumbers = creditCardNumbers;
    }
}
