import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main_04RichEmployeesFirstNames {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<String> employeesNames = entityManager
                .createQuery("SELECT e.firstName FROM Employee e WHERE e.salary >= 50000", String.class)
                .getResultList();

        employeesNames.forEach(System.out::println);

        entityManager.getTransaction().commit();
    }
}
