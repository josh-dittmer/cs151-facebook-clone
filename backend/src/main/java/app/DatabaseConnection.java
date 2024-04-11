package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
        String query = "SELECT * FROM " + table + " WHERE ";

        for (String value : values) {
            query += column + "='" + value + "' OR ";
        }
        query += "FALSE;";

        return statement.executeQuery(query);
    }

    public ResultSet lookup(String table, String selection, Map<String, String> criteria) throws SQLException {
        Statement statement = this.conn.createStatement();
        String query = "SELECT " +  selection + " FROM " + table + " WHERE ";

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            query += entry.getKey() + "='" + entry.getValue() + "' AND ";
        }
        query += "TRUE;";

        return statement.executeQuery(query);
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

        String query = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")";
        statement.executeUpdate(query);
    }

    /*public void update(String table, String column, String value, String updates) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeUpdate("UPDATE " + table + " SET " + updates + " WHERE " + column + "='" + value + "'");
    }*/

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

        String query = "UPDATE " + table + " SET " + updateStr + " WHERE " + column + "='" + value + "'";
        statement.executeUpdate(query);
    }

    /*public void delete(String table, String column, String value) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeUpdate("DELETE FROM " + table + " WHERE " + column + "='" + value + "'");
    }*/

    public void delete(String table, Map<String, String> criteria) throws SQLException {
        Statement statement = this.conn.createStatement();
        String query = "DELETE FROM " +  table + " WHERE ";

        for (Map.Entry<String, String> entry : criteria.entrySet()) {
            query += entry.getKey() + "='" + entry.getValue() + "' AND ";
        }
        query += "TRUE;";

        statement.executeUpdate(query);
    }

}
