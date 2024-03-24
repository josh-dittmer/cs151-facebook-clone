package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void insert(String table, String columns, String values) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeUpdate("INSERT INTO " + table + " (" + columns + ")" + " VALUES (" + values + ")");
    }

    public void update(String table, String column, String value, String updates) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeUpdate("UPDATE " + table + " SET " + updates + " WHERE " + column + "='" + value + "'");
    }

    public void delete(String table, String column, String value) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeUpdate("DELETE FROM " + table + " WHERE " + column + "='" + value + "'");
    }

}
