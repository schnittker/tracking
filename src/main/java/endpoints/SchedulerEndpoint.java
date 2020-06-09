package endpoints;

import helper.Database;
import helper.database.QueryBuilder;
import models.SchedulerModel;
import services.ExceptionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
            // INSERT INTO scheduler (project_name, start_time, stop_time) VALUES(?,?,?)
            String sql = new QueryBuilder().insert().table("scheduler")
                    .columns(Arrays.asList("project_name", "start_time", "stop_time"))
                    .values(Arrays.asList("?", "?", "?")).toSql();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(stopTime));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    public List<SchedulerModel> getByDateRange(LocalDateTime from, LocalDateTime to) {
        List<SchedulerModel> schedulerModelList = new ArrayList<>();

        try {
            // SELECT * FROM scheduler WHERE start_time >= ? AND stop_time <= ? ORDER BY id
            String sql = new QueryBuilder().select().all().from("scheduler").where()
                    .column("start_time").gt().eq().column("?").and()
                    .column("stop_time").lt().eq().column("?").orderBy("id").toSql();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                SchedulerModel schedulerModel = new SchedulerModel();
                schedulerModel.setId(resultSet.getInt("id"));
                schedulerModel.setProjectName(resultSet.getString("project_name"));
                schedulerModel.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                schedulerModel.setStopTime(resultSet.getTimestamp("stop_time").toLocalDateTime());
                schedulerModelList.add(schedulerModel);
            }

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }

        return schedulerModelList;
    }

    public List<SchedulerModel> getByProjectNameAndDateRange(String projectName, LocalDateTime from, LocalDateTime to) {
        List<SchedulerModel> schedulerModelList = new ArrayList<>();

        try {
            // SELECT * FROM scheduler WHERE project_name = ? AND start_time >= ? AND stop_time <= ? ORDER BY id"
            String sql = new QueryBuilder().select().all().from("scheduler").where()
                    .column("project_name").eq().column("?").and()
                    .column("start_time").gt().eq().column("?").and()
                    .column("stop_time").lt().eq().column("?")
                    .orderBy("id").toSql();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                SchedulerModel schedulerModel = new SchedulerModel();
                schedulerModel.setId(resultSet.getInt("id"));
                schedulerModel.setProjectName(resultSet.getString("project_name"));
                schedulerModel.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                schedulerModel.setStopTime(resultSet.getTimestamp("stop_time").toLocalDateTime());
                schedulerModelList.add(schedulerModel);
            }

        } catch (SQLException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }

        return schedulerModelList;
    }
}
