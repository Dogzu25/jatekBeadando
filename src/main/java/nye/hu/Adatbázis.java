package nye.hu;
import java.sql.*;

public class Adatbázis {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/schema"; // Az adatbázis URL-je
    private static final String USER = "root"; // Az adatbázis felhasználó neve
    private static final String PASS = "password"; // Az adatbázis jelszó

    // Kapcsolódás az adatbázishoz
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Új high score mentése
    public static void saveHighScore(String playerName, int wins) {
        String query = "INSERT INTO score (player_name, wins) VALUES (?, ?) ON DUPLICATE KEY UPDATE wins = wins + 1";

        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, wins);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // High score-ok kiíratása
    public static void displayHighScores() {
        String query = "SELECT player_name, wins FROM score ORDER BY wins DESC LIMIT 5";

        try (Connection connection = connect(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("High Scores:");
            while (resultSet.next()) {
                String playerName = resultSet.getString("player_name");
                int wins = resultSet.getInt("wins");
                System.out.println(playerName + ": " + wins + " wins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
