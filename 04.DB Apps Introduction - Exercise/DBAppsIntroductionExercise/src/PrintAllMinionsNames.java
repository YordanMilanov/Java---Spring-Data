import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionsNames {
    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getSQLConnection();

        PreparedStatement getAllMinionsNames = connection.prepareStatement("SELECT name FROM minions;");
        ResultSet getAllMinionsNamesSet = getAllMinionsNames.executeQuery();
        List<String> orderedNames = new ArrayList<>();
        ArrayDeque<String> defaultOrderNames = new ArrayDeque<>();
        while (getAllMinionsNamesSet.next()) {
            defaultOrderNames.add(getAllMinionsNamesSet.getString("name"));
        }

        while (!defaultOrderNames.isEmpty()) {
            orderedNames.add(defaultOrderNames.pollFirst());
            if (!defaultOrderNames.isEmpty()) {
                orderedNames.add(defaultOrderNames.pollLast());
            }
        }

        for (String name : orderedNames) {
            System.out.println(name);
        }

    }
}
