package MainDirectory.Classes;

import java.sql.*;

/**
 * This Class is for MYSQL Connection
 * This is a singleton connection class
 * All SQL methods are inside this class
 */
public class Connector {
    private static Connection connection = null;
    private static boolean isFound; // a boolean for validation

    private Connector() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String URL = "jdbc:mysql://localhost:3306/election";
        String USERNAME = "bhavan";
        String PASSWORD = "123";
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            new Connector();
        }
        return connection;
    }

    /*
     * This method executes a SQL query (returns nothing).
     * for queries that begin with 'INSERT','UPDATE','DELETE'
     * we must use executeUpdate, for others it's executeQuery.
     * if data is found with 'SELECT' type queries isFound = true
     * if there is no data, isFound will remain false
     */
    public static void sqlQuery(String query) throws SQLException {
        Connection connection = Connector.getConnection();
        Statement stmt = connection.createStatement();
        isFound = false;

        if (query.startsWith("SELECT")) {
            ResultSet resultSet = stmt.executeQuery(query);
            isFound = resultSet.next();
        } else if (query.startsWith("LOAD")) {
            stmt.executeQuery(query);
        } else {
            stmt.executeUpdate(query);
        }
    }

    // This method executes a SQL query (returns ResultSet)
    public static ResultSet newSqlQuery(String query) throws SQLException {
        Connection connection = Connector.getConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    // This method checks username & password (returns boolean)
    public static boolean validateAdmin(String username,
                                        String password) throws SQLException {
        String query = String.format("SELECT * FROM `admin` WHERE username = '%s'" +
                " AND password = '%s' ", username, password);
        sqlQuery(query);
        return isFound;
    }

    // This method checks voter_id (returns boolean)
    public static boolean validateVoter(int voter_id) throws SQLException {
        String query = ("SELECT * FROM `voter` WHERE voter_id = " + voter_id);
        sqlQuery(query);
        return isFound;
    }

    // This method inserts a voter to the database
    public static void addVoter(int voter_id, String first_name,
                                String last_name, String address,
                                String gender, String dob) throws SQLException {
        String query = String.format("INSERT INTO `voter` " +
                "(voter_id, first_name, last_name, address, gender, dob) " +
                "VALUES ('%d','%s','%s','%s','%s','%s')",
                voter_id, first_name, last_name, address, gender, dob);
        sqlQuery(query);
    }

    // This method inserts a candidate to the database
    // This updates both candidate & result tables
    public static void addCandidate(int id, String name,
                                    String party) throws SQLException {
        String query = String.format("INSERT INTO `candidate` " +
                "(id,name,party) VALUES ('%d','%s','%s')",
                id, name, party);
        sqlQuery(query);
        String query2 = String.format("INSERT INTO `result` " +
                "(id,votes) VALUES ('%d','%d')", id, 0);
        sqlQuery(query2);
    }

    // This method updates a voter in the database
    public static void editVoter(int voter_id, String first_name,
                                 String last_name, String address,
                                 String gender, String dob) throws SQLException {
        String query = String.format("UPDATE `voter` SET  first_name = '%s' , " +
                "last_name = '%s', address = '%s' , gender = '%s', dob = '%s' " +
                "WHERE voter_id = '%d'",
                first_name, last_name, address, gender, dob, voter_id);
        sqlQuery(query);
    }

    // This method updates a candidate in the database
    public static void editCandidate(int id, String name,
                                     String party) throws SQLException {
        String query = String.format("UPDATE `candidate` SET  name = '%s', " +
                "party = '%s' WHERE id = '%d'", name, party, id);
        sqlQuery(query);
    }

    // This method deletes a voter in the database
    public static void deleteVoter(int voter_id) throws SQLException {
        String query = ("DELETE FROM `voter` WHERE voter_id = " + voter_id);
        sqlQuery(query);
    }

    // This method deletes a voter in the database
    // This deletes in both candidate & result tables
    public static void deleteCandidate(int id) throws SQLException {
        String query1 = ("DELETE FROM `result` WHERE  id = " + id);
        sqlQuery(query1);
        String query2 = ("DELETE FROM `candidate` WHERE  id = " + id);
        sqlQuery(query2);
    }

    // This method imports a csv file to database using file path
    public static void importVoters(String path) throws SQLException {
        String query = String.format("LOAD DATA INFILE '%s' INTO " +
                "TABLE `voter` FIELDS TERMINATED BY ',' " +
                "ENCLOSED BY '%c' LINES TERMINATED BY '\\n' " +
                "IGNORE 1 ROWS", path, '"');
        sqlQuery(query);
    }

    // This method add votes for candidates by id
    public static void addVote(int id) throws SQLException {
        String query = ("UPDATE `result` SET votes = votes + 1 " +
                "WHERE id = " + id);
        sqlQuery(query);
    }

    // This method reset votes = 0 when program ends
    public static void resetVotes() throws SQLException {
        String query = ("UPDATE result SET votes = 0");
        sqlQuery(query);
    }

    // This method is to check candidates & voters != 0
    public static boolean dataExists() throws SQLException {
        String query1 = ("SELECT * FROM `voter`");
        String query2 = ("SELECT * FROM `candidate`");
        ResultSet voter = newSqlQuery(query1);
        ResultSet candidate = newSqlQuery(query2);
        isFound = voter.next() && candidate.next();
        return isFound;
    }

    // This method is to find winner of the election
    public static String findWinner() throws SQLException {
        String winner = "";
        int maxVotes = 0;

        // Get maximum votes and assign it to maxVotes
        String query = "SELECT MAX(votes) AS max_votes FROM result";
        ResultSet set = newSqlQuery(query);
        if (set.next()) {
            maxVotes = set.getInt("max_votes");
        }

        // Get candidate with maximum votes
        String query2 = "SELECT candidate.name AS name FROM candidate " +
                "JOIN result ON candidate.id = result.id" +
                " WHERE result.votes = " + maxVotes;
        ResultSet set2 = newSqlQuery(query2);
        if (set2.next()) {
            winner = set2.getString("name");
        }

        return winner;
    }
}