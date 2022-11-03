package org.example;

import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        createConnection("root", "", "custom-orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

        User user = new User("pesho", 25, LocalDate.now());
        user.setId(2);
        user.setUsername("pesho_new_new");
       // userEntityManager.doAlter(User.class);
        //userEntityManager.persist(user);

        userEntityManager.persist(user);
    }
}