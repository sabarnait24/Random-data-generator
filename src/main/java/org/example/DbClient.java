package org.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class DbClient {

    private String url;
    private String user;
    private String password;

    public DbClient(DBConfig dbConfig){
        this.url = dbConfig.getUrl();
        this.user = dbConfig.getUsername();
        this.password = dbConfig.getPassword();
    }

    public void dbConnection(){

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL!");
            } else {
                System.out.println("Connection failed.");
            }
        } catch (SQLException e) {
            log.error("connection problem , error thrown ", e);

        }
    }
    public void insertData(List<Map<String, Object>> records, String tableName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            for (Map<String, Object> record : records) {
                StringBuilder columns = new StringBuilder();
                StringBuilder placeholders = new StringBuilder();

                // Loop through the record and build column names and placeholders
                for (String key : record.keySet()) {
                    columns.append(key).append(",");
                    placeholders.append("?").append(",");
                }

                // Create the insert SQL query
                String query = String.format("INSERT INTO %s (%s) VALUES (%s)",
                        tableName,
                        columns.substring(0, columns.length() - 1),
                        placeholders.substring(0, placeholders.length() - 1));

                // Use the connection to prepare and execute the query
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    int index = 1;
                    // Loop through the record and set the values to the placeholders
                    for (String key : record.keySet()) {
                        stmt.setObject(index++, record.get(key));
                    }
                    stmt.executeUpdate(); // Execute the insert statement
                }
            }
        } catch (SQLException e) {
            log.error("Insert data failed, error thrown ", e);
            throw e;
        }
    }

}
