import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main_06AddingNewAddressAndUpdatingEmployee {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);

        String addressText = "Vitoshka 15";
        Address address = new Address();
        address.setText(addressText);
        entityManager.persist(address);

        String employeeLastName = scanner.nextLine();

        Employee employee = entityManager
                .createQuery("select e from Employee e WHERE e.lastName = :last_name", Employee.class)
                .setParameter("last_name", employeeLastName)
                .getSingleResult();

        employee.setAddress(address);
        entityManager.persist(employee);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
