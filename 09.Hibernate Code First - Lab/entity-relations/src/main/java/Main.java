import Entities.Bike;
import Entities.Car;
import Entities.Plane;
import Entities.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("relations");

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Vehicle car = new Car("Ford Focus", "Petrol", 5);
        Vehicle bike = new Bike();
        Plane plane = new Plane("Airbus", "petrol", 200);

        entityManager.persist(car);
        entityManager.persist(bike);
        entityManager.persist(plane);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
