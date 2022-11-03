import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main_05EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        String department = "Research and Development";

        entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.department.name = :departmentName ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("departmentName", department)
                .getResultStream().forEach(e -> {
            String output = String.format("%s %S from %s - %.2f$", e.getFirstName(), e.getLastName(), department, e.getSalary());
                    System.out.println(output);
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
