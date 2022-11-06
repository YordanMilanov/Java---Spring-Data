import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main_09FindLast10Projects {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Project> projects = entityManager
                .createQuery("SELECT p FROM Project AS p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        String outputFormat ="Project name: %s%n" +
                "\t\tProject Description:%s%n" +
                "\t\tProject Start Date:%s%n" +
                "\t\tProject End Date:%s";
        projects
                .stream()
                .forEach(p -> System.out.println(String.format
                        (outputFormat, p.getName(), p.getDescription(),validateDate(p.getStartDate()), validateDate(p.getEndDate()))
                ));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static String validateDate(LocalDateTime date) {
        if (date == null) {
            return "null";
        }
        return date.toString();
    }
}
