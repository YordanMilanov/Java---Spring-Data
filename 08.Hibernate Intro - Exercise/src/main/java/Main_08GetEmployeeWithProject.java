import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main_08GetEmployeeWithProject {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Employee ID: ");
        int inputID = scanner.nextInt();

        Employee employee = entityManager
                .createQuery("SELECT e FROM Employee AS e WHERE e.id = ?1", Employee.class)
                .setParameter(1, inputID)
                .getSingleResult();

        String outputFormat = String.format("%s %s - %s", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        System.out.println(outputFormat);
        employee.getProjects()
                .stream()
                .map(p -> p.getName())
                .sorted()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
