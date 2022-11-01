import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class DeleteVillain {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int villainID = Integer.parseInt(scanner.nextLine());

        Connection connection = Utils.getSQLConnection();

        PreparedStatement selectVillain = connection.prepareStatement("" +
                "SELECT name FROM villains WHERE id = ?");

        selectVillain.setInt(1, villainID);
        ResultSet villainSet = selectVillain.executeQuery();

        if(!villainSet.next()) {
            System.out.println("No such villain was found");
            connection.close();
            return;
        }

        String villainName = villainSet.getString("name");
        connection.setAutoCommit(false);

        List<Integer> minionsIDs = new ArrayList<>();
        PreparedStatement selectAllVillainMinions = connection.prepareStatement(
                "SELECT COUNT(DISTINCT minion_id) AS `count` FROM minions_villains WHERE villain_id = ?");
        selectAllVillainMinions.setInt(1, villainID);
        ResultSet minionsSet = selectAllVillainMinions.executeQuery();
        minionsSet.next();
        int countMinionsDeleted = minionsSet.getInt("count");

        while(minionsSet.next()) {
            minionsIDs.add(minionsSet.getInt("minion_id"));
        }

        try {

            //transaction
            PreparedStatement deleteMinionsVillains = connection.prepareStatement("" +
                    "DELETE FROM minions_villains WHERE villain_id = ?");

            deleteMinionsVillains.setInt(1, villainID);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement(
                    "DELETE FROM villains WHERE id =?");
            deleteVillain.setInt(1, villainID);
            deleteVillain.executeUpdate();


            connection.commit();
            System.out.println(villainName + " was deleted");
            System.out.println(countMinionsDeleted + " minions deleted");

        } catch (SQLException e){
            connection.rollback();
        };
    }
}
