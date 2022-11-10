package _02SalesDatabase.Entities;

import _02SalesDatabase.Entities.Entities.Customer;
import _02SalesDatabase.Entities.Entities.Product;
import _02SalesDatabase.Entities.Entities.Sale;
import _02SalesDatabase.Entities.Entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class _02Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("code");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Customer customer = new Customer("Ahmed", "ahmed@gmail.com", "naAhmedKartata");
        Product product = new Product("shoes", 20, BigDecimal.TEN);
        StoreLocation storeLocation = new StoreLocation("Vinica");
        Sale sale = new Sale(product, customer, storeLocation);

        entityManager.persist(customer);
        entityManager.persist(product);
        entityManager.persist(storeLocation);
        entityManager.persist(sale);


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
