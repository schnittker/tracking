package main.java.endpoints;

import main.java.helper.Database;
import main.java.models.SchedulerModel;
import main.java.services.ExceptionService;
import main.java.utils.TimeUtils;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class SchedulerEndpoint {
    private final Connection connection;
    private final ExceptionService exceptionService;
    private final ResourceBundle translations;

    public SchedulerEndpoint() {
        connection = Database.getConnection();
        exceptionService = new ExceptionService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public void insert(SchedulerModel schedulerModel) {
        insert(schedulerModel.getProjectsId().intValue(), schedulerModel.getStartTime(), schedulerModel.getStopTime());
    }

    public void insert(int projectsId, LocalDateTime startTime, LocalDateTime stopTime) {
        try {
            String sql = "INSERT INTO scheduler (projects_id, start_time, stop_time) VALUES(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectsId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(stopTime));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    public DefaultTableModel getByDateRange(LocalDateTime from, LocalDateTime to) {
        String[] headline = {translations.getString("header_project_name"), translations.getString("header_date"),
                translations.getString("header_start_time"), translations.getString("header_stop_time"),
                translations.getString("header_hours")};
        DefaultTableModel defaultTableModel = new DefaultTableModel(headline, 0);

        try {
            String sql = "SELECT * FROM scheduler INNER JOIN projects ON scheduler.projects_id = projects.id " +
                    "WHERE scheduler.start_time >= ? AND scheduler.stop_time <= ? ORDER BY scheduler.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime stopTime = resultSet.getTimestamp("stop_time").toLocalDateTime();
                String hours = TimeUtils.computeHours(startTime, stopTime) + ":" + TimeUtils.computeMinutes(startTime, stopTime);

                Object[] columns = {
                        resultSet.getString("project_name"),
                        TimeUtils.getFormattedDate(startTime),
                        TimeUtils.getFormattedTime(startTime),
                        TimeUtils.getFormattedTime(stopTime),
                        hours
                };

                defaultTableModel.addRow(columns);
            }

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }

        return defaultTableModel;
    }

    public DefaultTableModel getByProjectsIdAndDateRange(int projectsId, LocalDateTime from, LocalDateTime to) {
        String[] headline = {translations.getString("header_project_name"), translations.getString("header_date"),
                translations.getString("header_start_time"), translations.getString("header_stop_time"),
                translations.getString("header_hours")};
        DefaultTableModel defaultTableModel = new DefaultTableModel(headline, 0);

        try {
            String sql = "SELECT * FROM scheduler INNER JOIN projects ON scheduler.projects_id = projects.id " +
                    "WHERE scheduler.projects_id = ? AND scheduler.start_time >= ? AND scheduler.stop_time <= ? ORDER BY scheduler.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectsId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime stopTime = resultSet.getTimestamp("stop_time").toLocalDateTime();
                String hours = TimeUtils.computeHours(startTime, stopTime) + ":" + TimeUtils.computeMinutes(startTime, stopTime);

                Object[] columns = {
                        resultSet.getString("project_name"),
                        TimeUtils.getFormattedDate(startTime),
                        TimeUtils.getFormattedTime(startTime),
                        TimeUtils.getFormattedTime(stopTime),
                        hours
                };

                defaultTableModel.addRow(columns);
            }

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }

        return defaultTableModel;
    }
}
