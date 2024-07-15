/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_java_database.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStorage implements DataStorage {

    private Connection connection;

    public DatabaseStorage(String databasePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            createNotesTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createNotesTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS notes (note TEXT)");
        }
    }

    @Override
    public void writeData(String note) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO notes (note) VALUES (?)")) {
            statement.setString(1, note);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readData() {
        List<String> notes = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT note FROM notes ORDER BY note DESC")) {
            while (resultSet.next()) {
                notes.add(resultSet.getString("note"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public void deleteData(String note) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM notes WHERE note = ?")) {
            statement.setString(1, note);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
