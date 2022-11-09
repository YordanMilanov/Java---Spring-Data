import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main_10IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<String>departmentNames = new ArrayList<>();
        departmentNames.add("Engineering");
        departmentNames.add("Tool Design");
        departmentNames.add("Marketing");
        departmentNames.add("Information Services");

        //update salaries
       entityManager
                .createQuery(
                        "UPDATE Employee AS e " +
                                "SET e.salary = e.salary * 1.12 " +
                                "WHERE e.department.name IN('Engineering', 'Tool Design', 'Marketing', 'Information Services')").executeUpdate();

        entityManager
                .createQuery(
                        "SELECT e FROM Employee e " +
                                "WHERE e.department.name IN('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n",e.getFirstName(), e.getLastName(), e.getSalary()));




        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
