package Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle{
    private static final String PlANE_TYPE = "PLANE";

    @Column
    private int passengerCapacity;

    public Plane() {
        super(PlANE_TYPE);
    }

    public Plane(String model, String fuelType, int passengerCapacity) {
        this();

        this.model = model;
        this.fuelType = fuelType;
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}