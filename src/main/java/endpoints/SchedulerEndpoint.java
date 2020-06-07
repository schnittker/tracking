package endpoints;

import helper.Database;
import models.SchedulerModel;
import services.ExceptionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class SchedulerEndpoint {
    private final Connection connection;
    private final ExceptionService exceptionService;

    public SchedulerEndpoint() {
        connection = Database.getConnection();
        exceptionService = new ExceptionService();
    }

    public void insert(SchedulerModel schedulerModel) {
        insert(schedulerModel.getProjectName(), schedulerModel.getStartTime(), schedulerModel.getStopTime());
    }

    public void insert(String projectName, LocalDateTime startTime, LocalDateTime stopTime) {
        try {
            String sql = "INSERT INTO scheduler (project_name, start_time, stop_time) VALUES('" + projectName + "'," +
                    Timestamp.valueOf(startTime) + "," + Timestamp.valueOf(stopTime) + ")";

            connection.createStatement().executeUpdate(sql);
        } catch (SQLException | NullPointerException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    public void delete() {

    }

    public List<SchedulerModel> getByProjectName(String projectName) {
        return Collections.emptyList();
    }

    public List<SchedulerModel> getByDate(LocalDateTime dateTime) {
        return Collections.emptyList();
    }

    public List<SchedulerModel> getByProjectNameAndDate(String projectName, LocalDateTime dateTime) {
        return Collections.emptyList();
    }

    public List<SchedulerModel> getByProjectNameAndDateRange(String projectName, LocalDateTime from, LocalDateTime to) {
        return Collections.emptyList();
    }
}
