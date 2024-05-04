package app;

import org.h2.command.Prepared;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return this.conn;
    }

//WIP Prepared statment for sql
//    public ResultSet lookup(String table, String column, String value) throws SQLException {
//        String query = "SELECT * FROM " + table + " WHERE " + column + "=?";
//        PreparedStatement prep = conn.prepareStatement(query);
//        prep.setString(1, value);
//        return prep.executeQuery();
//    }

    public ResultSet lookup(String table, String column, String value) throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE " + column + "= ?;";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, value);

        /*Statement statement = this.conn.createStatement();
        return statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + value + "';");*/
        return statement.executeQuery();
    }

    public ResultSet lookupAll(String table, String column, Set<String> values) throws SQLException {
        //Statement statement = this.conn.createStatement();
        String sql = String.format("SELECT * FROM " + table + " WHERE " + column + " IN (%s);",
                values.stream().map(v -> "?").collect(Collectors.joining(", ")));

        /*String sql = "SELECT * FROM " + table + " WHERE ";

        for (String value : values) {
            //sql += column + "='" + value + "' OR ";
            sql += column + "= ? OR ";
        }
        sql += "FALSE;";*/

        PreparedStatement statement = conn.prepareStatement(sql);
        int valueCount = 0;
        for (String value : values) {
            statement.setString(++valueCount, value);
        }

        return statement.executeQuery();
    }

    public ResultSet contains(String table, Map<String, String> criteria) throws SQLException {
        //Statement statement = this.conn.createStatement();
        String sql = "SELECT * FROM " + table + " WHERE ";

        ArrayList<String> values = new ArrayList<String>();

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            //sql += entry.getKey() + " LIKE '%" + entry.getValue() + "%' OR ";
            sql += entry.getKey() + " LIKE ? OR ";
            values.add(entry.getValue());
        }
        sql += "FALSE;";

        PreparedStatement statement = conn.prepareStatement(sql);
        int valueCount = 0;
        for (String value : values) {
            value = value
                    .replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");

            statement.setString(++valueCount, "%" + value + "%");
        }

        return statement.executeQuery();
    }

    public ResultSet lookup(String table, String selection, Map<String, String> criteria) throws SQLException {
        //Statement statement = this.conn.createStatement();
        String sql = "SELECT " +  selection + " FROM " + table + " WHERE ";

        ArrayList<String> values = new ArrayList<String>();

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            //sql += entry.getKey() + "='" + entry.getValue() + "' AND ";
            sql += entry.getKey() + "= ? AND ";
            values.add(entry.getValue());
        }
        sql += "TRUE;";

        PreparedStatement statement = conn.prepareStatement(sql);
        int valueCount = 0;
        for (String value : values) {
            statement.setString(++valueCount, value);
        }

        return statement.executeQuery();
    }

    public void insert(String table, Map<String, String> data) throws SQLException {
        //Statement statement = this.conn.createStatement();

        boolean first = true;

        String columns = "";
        String values = "";

        ArrayList<String> valuesList = new ArrayList<String>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) {
                columns += ", ";
                values += ", ";
            } else {
                first = false;
            }

            columns += entry.getKey();
            values += "?";
            valuesList.add(entry.getValue());
        }

        String sql = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ");";
        PreparedStatement statement = conn.prepareStatement(sql);
        int valueCount = 0;
        for (String value : valuesList) {
            statement.setString(++valueCount, value);
        }

        statement.executeUpdate();
    }

    public void update(String table, String column, String value, Map<String, String> updates) throws SQLException {
        //Statement statement = this.conn.createStatement();

        boolean first = true;

        String updateStr = "";

        ArrayList<String> updateList = new ArrayList<String>();

        for (Map.Entry<String, String> entry : updates.entrySet()) {
            if (!first) {
                updateStr += ", ";
            } else {
                first = false;
            }

            updateStr += entry.getKey() + "= ?";
            updateList.add(entry.getValue());
        }

        String sql = "UPDATE " + table + " SET " + updateStr + " WHERE " + column + "= ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        int updateCount = 0;
        for (String update : updateList) {
            statement.setString(++updateCount, update);
        }

        statement.setString(++updateCount, value);
        System.out.println(sql);

        statement.executeUpdate();
    }

    public void uncheckedUpdate(String table, String column, String value, Map<String, String> updates) throws SQLException {
        String sql = "UPDATE " + table + " SET ";

        boolean first = true;

        String updateStr = "";

        for (Map.Entry<String, String> entry : updates.entrySet()) {
            if (!first) {
                updateStr += ", ";
            } else {
                first = false;
            }

            updateStr += entry.getKey() + "= " + entry.getValue();
        }

        sql += updateStr + " WHERE " + column + "= ?;";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, value);

        statement.executeUpdate();
    }

    public void updateTimestamp(String table, String column, String value) throws SQLException {
        String sql = "UPDATE " + table + " SET TIMESTAMP=CURRENT_TIMESTAMP() WHERE " + column + " = ?;";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, value);

        statement.executeUpdate();
    }

    public void delete(String table, Map<String, String> criteria) throws SQLException {
        //Statement statement = this.conn.createStatement();
        String sql = "DELETE FROM " +  table + " WHERE ";

        ArrayList<String> values = new ArrayList<String>();

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            //sql += entry.getKey() + "='" + entry.getValue() + "' AND ";
            sql += entry.getKey() + "= ? AND ";
            values.add(entry.getValue());
        }
        sql += "TRUE;";

        PreparedStatement statement = conn.prepareStatement(sql);
        int valueCount = 0;
        for (String value : values) {
            statement.setString(++valueCount, value);
        }

        statement.executeUpdate();
    }

    public int numTables() throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT COUNT(*)\n" +
                "  FROM INFORMATION_SCHEMA.TABLES\n" +
                " WHERE TABLE_TYPE = 'BASE TABLE'\n" +
                "   AND TABLE_SCHEMA = SCHEMA()";

        ResultSet res =  statement.executeQuery(sql);
        res.next();

        return res.getInt(1);
    }

    public void createDb() throws SQLException {
        String createUsers = "CREATE TABLE USERS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    USERNAME VARCHAR(64) NOT NULL,\n" +
                "    PASSWORD TEXT NOT NULL,\n" +
                "    DISPLAY_NAME VARCHAR(64) NOT NULL,\n" +
                "    BIO TEXT NOT NULL,\n" +
                "    NUM_FOLLOWERS INTEGER NOT NULL DEFAULT(0),\n" +
                "    NUM_FOLLOWING INTEGER NOT NULL DEFAULT(0),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String createPosts = "CREATE TABLE POSTS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    POST_ID VARCHAR(64) NOT NULL,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    HAS_IMAGE BOOLEAN NOT NULL,\n" +
                "    TEXT TEXT NOT NULL," +
                "    NUM_LIKES INTEGER NOT NULL DEFAULT(0),\n" +
                "    NUM_COMMENTS INTEGER NOT NULL DEFAULT(0),\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP()),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String createLikes = "CREATE TABLE LIKES (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    POST_ID VARCHAR(64) NOT NULL,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP())\n" +
                ");\n";

        String createComments = "CREATE TABLE COMMENTS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    COMMENT_ID VARCHAR(64) NOT NULL,\n" +
                "    POST_ID VARCHAR(64) NOT NULL,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    TEXT TEXT NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP())\n" +
                ");";

        String createSessions = "CREATE TABLE SESSIONS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    TOKEN VARCHAR(64) NOT NULL,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP()),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String createFollows = "CREATE TABLE FOLLOWS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    SRC_USER_ID VARCHAR(64) NOT NULL,\n" +
                "    DEST_USER_ID VARCHAR(64) NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP()),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String createUploads = "CREATE TABLE UPLOADS (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    RESOURCE_ID VARCHAR(64) NOT NULL,\n" +
                "    USER_ID VARCHAR(64) NOT NULL,\n" +
                "    ASSOCIATED_ID VARCHAR(64) NOT NULL,\n" +
                "    IS_REMOTE_RESOURCE BOOLEAN NOT NULL,\n" +
                "    RESOURCE_LOCATION TEXT NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP()),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String createMessages = "CREATE TABLE MESSAGES (\n" +
                "    ID INT NOT NULL AUTO_INCREMENT,\n" +
                "    MESSAGE_ID VARCHAR(64) NOT NULL,\n" +
                "    SENDER_ID VARCHAR(64) NOT NULL,\n" +
                "    RECEIVER_ID VARCHAR(64) NOT NULL,\n" +
                "    MESSAGE_TEXT TEXT NOT NULL,\n" +
                "    TIMESTAMP DATETIME NOT NULL DEFAULT(CURRENT_TIMESTAMP()),\n" +
                "    PRIMARY KEY (ID)\n" +
                ");\n";

        String sql = createUsers + createPosts + createLikes + createComments + createSessions + createFollows +createUploads + createMessages;
        //String sql = createComments;

        Statement statement = this.conn.createStatement();
        statement.execute(sql);
    }
}
