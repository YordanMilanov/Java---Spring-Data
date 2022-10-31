import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {

    private static final String GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID = "select m.name, m.age from minions as m " +
            "join minions_villains mv on m.id = mv.minion_id " +
            "where mv.villain_id = ?;";
    private static final String VILLAIN_FORMAT = "Villain: %s%n";
    private static final String MINION_FORMAT = "%d. %s %d%n";
    private static final String GET_VILLAIN_NAME = "select v.name from villains as v " +
            "where v.id = ?;";

    private static final String NO_VILLAIN_ID_FOUND_FORMAT = "No villain with ID %d exists in the database.";
    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final int villainID = new Scanner(System.in).nextInt();

       final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME);
       villainStatement.setInt(1, villainID);

       final ResultSet villainResult = villainStatement.executeQuery();


        if (!villainResult.next()) {
            System.out.printf(NO_VILLAIN_ID_FOUND_FORMAT, villainID);
            connection.close();
            return;
        }

       final String villainName = villainResult.getString(Constants.COLUMN_LABEL_NAME);

        System.out.printf(VILLAIN_FORMAT, villainName);

        final PreparedStatement minions = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE_BY_VILLAIN_ID);
        minions.setInt(1, villainID);

        ResultSet minionsResult = minions.executeQuery();

        for (int i = 1; minionsResult.next(); i++) {
            final String minionName = minionsResult.getString(Constants.COLUMN_LABEL_NAME);
            final int minionAge = minionsResult.getInt(Constants.COLUMN_LABEL_AGE);
        System.out.printf(MINION_FORMAT,i, minionName, minionAge);
        }

        connection.close();
    }

}
