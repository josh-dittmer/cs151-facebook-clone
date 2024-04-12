package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return this.conn;
    }

    public ResultSet lookup(String table, String column, String value) throws SQLException {
        Statement statement = this.conn.createStatement();
        return statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + value + "';");
    }

    public ResultSet lookupAll(String table, String column, Set<String> values) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT * FROM " + table + " WHERE ";

        for (String value : values) {
            sql += column + "='" + value + "' OR ";
        }
        sql += "FALSE;";

        return statement.executeQuery(sql);
    }

    public ResultSet contains(String table, Map<String, String> criteria) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT * FROM " + table + " WHERE ";

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            sql += entry.getKey() + " LIKE '%" + entry.getValue() + "%' OR ";
        }
        sql += "FALSE;";

        return statement.executeQuery(sql);
    }

    public ResultSet lookup(String table, String selection, Map<String, String> criteria) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "SELECT " +  selection + " FROM " + table + " WHERE ";

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            sql += entry.getKey() + "='" + entry.getValue() + "' AND ";
        }
        sql += "TRUE;";

        return statement.executeQuery(sql);
    }

    public void insert(String table, Map<String, String> data) throws SQLException {
        Statement statement = this.conn.createStatement();

        boolean first = true;

        String columns = "";
        String values = "";

        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) {
                columns += ", ";
                values += ", ";
            } else {
                first = false;
            }

            columns += entry.getKey();
            values += "'" + entry.getValue() + "'";
        }

        String sql = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")";
        statement.executeUpdate(sql);
    }

    public void update(String table, String column, String value, Map<String, String> updates) throws SQLException {
        Statement statement = this.conn.createStatement();

        boolean first = true;

        String updateStr = "";

        for (Map.Entry<String, String> entry : updates.entrySet()) {
            if (!first) {
                updateStr += ", ";
            } else {
                first = false;
            }

            updateStr += entry.getKey() + "=" + entry.getValue();
        }

        String sql = "UPDATE " + table + " SET " + updateStr + " WHERE " + column + "='" + value + "'";
        statement.executeUpdate(sql);
    }

    public void delete(String table, Map<String, String> criteria) throws SQLException {
        Statement statement = this.conn.createStatement();
        String sql = "DELETE FROM " +  table + " WHERE ";

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            sql += entry.getKey() + "='" + entry.getValue() + "' AND ";
        }
        sql += "TRUE;";

        statement.executeUpdate(sql);
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

        String sql = createUsers + createPosts + createLikes + createComments + createSessions;

        Statement statement = this.conn.createStatement();
        statement.execute(sql);
    }
}
