import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AddMinion {

    private static final String GET_VILLAIN_BY_NAME = "select v.name from villains as v where v.name = ?;";
    private static final String GET_TOWN_BY_NAME = "select t.id from towns as t where t.name = ?;";
    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    private static final String INSERT_INTO_TOWNS = "insert into towns(name) values (?);";

    private static final String INSERT_VILLAIN_BY_NAME = "insert into villains(name, evilness_factor) values (?, ?);";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";

    private static final String GET_LAST_MINION = "select m.id from minions as m order by m.id desc limit 1";
    private static final String INSERT_INTO_MINIONS = "insert into minions(name, age, town_id) values(?, ?, ?);";
    private static final String INSERT_INTO_MINIONS_VILLAINS = "insert into minions_villains(minion_id, villain_id) values(?, ?);";

    private static final String RESULT_FORMAT = "Successfully added %s to be minion of %s%n";

    public static void main(String[] args) throws SQLException {
       final Connection connection = Utils.getSQLConnection();

        final Scanner scanner = new Scanner(System.in);

        final String[] minionInformation = scanner.nextLine().split(" ");
       final String minionName = minionInformation[1];
       final int minionAge = Integer.parseInt(minionInformation[2]);
       final String minionTown = minionInformation[3];

       final String villainName = scanner.nextLine().split(" ")[1];



        final int townId = getId(connection,
                List.of(minionTown),
                GET_TOWN_BY_NAME,
                INSERT_INTO_TOWNS,
                TOWN_ADDED_FORMAT);

        final int villainId = getId(connection,
                List.of(villainName, EVILNESS_FACTOR),
                GET_VILLAIN_BY_NAME,
                INSERT_VILLAIN_BY_NAME,
                VILLAIN_ADDED_FORMAT);

        PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_INTO_MINIONS);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townId);

        insertMinionStatement.executeUpdate();

       final PreparedStatement lastMinion = connection.prepareStatement(GET_LAST_MINION);
       final ResultSet lastMinionResultSet = lastMinion.executeQuery();
       lastMinionResultSet.next();

       int lastMinionId = lastMinionResultSet.getInt(Constants.COLUMN_LABEL_ID);

       final PreparedStatement insertIntoMinionsVillains = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
       insertIntoMinionsVillains.setInt(1, lastMinionId);
       insertIntoMinionsVillains.setInt(2, villainId);

       insertIntoMinionsVillains.executeUpdate();

        System.out.printf(RESULT_FORMAT, minionName, villainName);

        connection.close();
    }

    private static int getId(Connection connection,
                             List<String> arguments,
                             String selectQuery,
                             String insertQuery,
                             String printFormat) throws SQLException {

        String name = arguments.get(0);

        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, name);

        final ResultSet resultSet = selectStatement.executeQuery();
        resultSet.next();

        if (!resultSet.next()) {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            for (int index = 1; index <= arguments.size(); index++) {
                insertStatement.setString(index, arguments.get(index - 1));
            }
            insertStatement.executeUpdate();

            final ResultSet newResultSet = selectStatement.executeQuery();
            newResultSet.next();

            System.out.printf(printFormat, name);

            return newResultSet.getInt(Constants.COLUMN_LABEL_ID);
        }

       return resultSet.getInt(Constants.COLUMN_LABEL_ID);
    }
}
//not Working

