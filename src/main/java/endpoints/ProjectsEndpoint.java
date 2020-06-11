package main.java.endpoints;

import main.java.helper.Database;
import main.java.services.ExceptionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectsEndpoint {
    private final Connection connection;
    private final ExceptionService exceptionService;

    public ProjectsEndpoint() {
        connection = Database.getConnection();
        exceptionService = new ExceptionService();
    }

    public void insert(String projectName) {
        try {
            String sql = "INSERT INTO projects (project_name) VALUES(?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    public List<String> getForProjectTree() {
        List<String> projectList = new ArrayList<>();

        try {
            String sql = "SELECT project_name FROM projects ORDER BY id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                projectList.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }

        return projectList;
    }
}