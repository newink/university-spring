package com.smarterama.university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
    public static void createDatabase(Connection connection) throws Exception {
        String dropQuery = "DROP TABLE IF EXISTS lessons CASCADE;" +
                "DROP TABLE IF EXISTS disciplines CASCADE;" +
                "DROP TABLE IF EXISTS rooms CASCADE;" +
                "DROP TABLE IF EXISTS lecturers CASCADE;" +
                "DROP TABLE IF EXISTS students CASCADE;" +
                "DROP TABLE IF EXISTS groups CASCADE;" +
                "DROP TABLE IF EXISTS lecturers_disciplines CASCADE;";
        String tablesQuery =
                "CREATE TABLE IF NOT EXISTS groups (" +
                        "id SERIAL PRIMARY KEY," +
                        "group_number INTEGER NOT NULL" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS disciplines (" +
                        "id SERIAL PRIMARY KEY," +
                        "test_type VARCHAR(40) NOT NULL," +
                        "name VARCHAR(40) NOT NULL" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS rooms (" +
                        "id SERIAL PRIMARY KEY," +
                        "room_number INTEGER NOT NULL," +
                        "capacity INTEGER NOT NULL" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS lecturers (" +
                        "id SERIAL PRIMARY KEY," +
                        "first_name VARCHAR(40) NOT NULL," +
                        "last_name VARCHAR(40) NOT NULL," +
                        "email VARCHAR(40) NOT NULL," +
                        "degree VARCHAR(40) NOT NULL" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS students (" +
                        "id SERIAL PRIMARY KEY," +
                        "address VARCHAR(40) NOT NULL," +
                        "course INTEGER NOT NULL," +
                        "first_name VARCHAR(40) NOT NULL," +
                        "last_name VARCHAR(40) NOT NULL," +
                        "subsidized BOOLEAN NOT NULL," +
                        "group_id INTEGER REFERENCES groups(id)" +
                        ");" +
                        "CREATE TABLE lessons (" +
                        "id SERIAL PRIMARY KEY," +
                        "group_id INTEGER REFERENCES groups(id)," +
                        "lecturer_id INTEGER REFERENCES lecturers(id)," +
                        "discipline_id INTEGER REFERENCES disciplines(id)," +
                        "room_id INTEGER REFERENCES rooms(id)," +
                        "start_time TIMESTAMP NOT NULL," +
                        "finish_time TIMESTAMP NOT NULL" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS lecturers_disciplines (" +
                        "lecturer_id INTEGER REFERENCES lecturers(id) ON DELETE CASCADE," +
                        "discipline_id INTEGER REFERENCES disciplines(id) ON DELETE CASCADE," +
                        "PRIMARY KEY (lecturer_id, discipline_id)" +
                        ");";
        PreparedStatement statement = connection.prepareStatement(dropQuery + tablesQuery);
        statement.execute();
        statement.close();
    }

    public static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/testdb", "postgres", "postgres");
    }
}
