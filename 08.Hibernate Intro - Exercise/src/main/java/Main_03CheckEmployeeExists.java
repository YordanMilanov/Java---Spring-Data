import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main_03CheckEmployeeExists {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);
        String[] searchFor = scanner.nextLine().split(" ");
        String firstName = searchFor[0];
        String lastName = searchFor[1];

        Long employeeCount = entityManager.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.firstName = :first_name AND e.lastName = :last_name", Long.class)
                .setParameter("first_name", firstName)
                .setParameter("last_name", lastName)
                .getSingleResult();

        if (employeeCount > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
