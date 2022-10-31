import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownCasingWithLecturer {
    private static final String GET_ALL_TOWNS = "SELECT t.name FROM minions_db.towns AS t WHERE t.country = ?";
    private static final String UPPER_ALL_TOWNS = "UPDATE minions_db.towns SET name = UPPER(name) WHERE country = ?";
    private static final String COLUMN_NAME = "name";
    private static final String NO_TOWNS_CHANGED_FORMAT = "No town names were affected.";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String countryName = scanner.nextLine();

        Connection connection = Utils.getSQLConnection();

        PreparedStatement updateTownsNames = connection.prepareStatement(UPPER_ALL_TOWNS);

        updateTownsNames.setString(1, countryName);
        int updatedTownsCount = updateTownsNames.executeUpdate();

        if (updatedTownsCount == 0) {
            System.out.println(NO_TOWNS_CHANGED_FORMAT);
            connection.close();
            return;
        }

        System.out.println(updatedTownsCount + " towns names were affected.");
        PreparedStatement selectAllTowns = connection.prepareStatement(GET_ALL_TOWNS);
        selectAllTowns.setString(1, countryName);
        ResultSet allTowns = selectAllTowns.executeQuery();
        List<String> updatedTownsList = new ArrayList<>();
        while(allTowns.next()) {
            updatedTownsList.add(allTowns.getString(COLUMN_NAME));
        }

        System.out.println(updatedTownsList);
        connection.close();
    }
}
