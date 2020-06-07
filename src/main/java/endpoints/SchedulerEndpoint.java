package endpoints;

import helper.Database;
import models.SchedulerModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class SchedulerEndpoint {
    private static final Logger LOGGER = Logger.getLogger(SchedulerEndpoint.class.getName());

    private final Connection connection;

    public SchedulerEndpoint() {
        connection = Database.getConnection();
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
            LOGGER.severe("Exception while trying to execute sql statement. " + e.getMessage());
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
