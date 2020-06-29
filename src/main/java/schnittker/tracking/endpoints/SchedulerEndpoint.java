package schnittker.tracking.endpoints;

import schnittker.tracking.helper.Database;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.utils.TimeUtils;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public class SchedulerEndpoint {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ResourceBundle translations;

    public SchedulerEndpoint() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public void insert(SchedulerModel schedulerModel) {
        insert(schedulerModel.getProjectsId().intValue(), schedulerModel.getStartTime(), schedulerModel.getStopTime(), schedulerModel.getTask());
    }

    public void insert(int projectsId, LocalDateTime startTime, LocalDateTime stopTime, String task) {
        try(Connection connection = Database.getConnection()) {
            String sql = "INSERT INTO scheduler (projects_id, start_time, stop_time, task) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectsId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(stopTime));
            preparedStatement.setString(4, task);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public List<SchedulerModel> getByDateRangeForExport(LocalDateTime from, LocalDateTime to) {
        List<SchedulerModel> schedulerModelList = new ArrayList<>();

        try(Connection connection = Database.getConnection()) {
            String sql = "SELECT * FROM scheduler INNER JOIN projects ON scheduler.projects_id = projects.id " +
                    "WHERE scheduler.start_time >= ? AND scheduler.stop_time <= ? ORDER BY scheduler.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                SchedulerModel schedulerModel = new SchedulerModel();
                schedulerModel.setProjectName(resultSet.getString("project_name"));
                schedulerModel.setTask(resultSet.getString("task"));
                schedulerModel.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                schedulerModel.setStopTime(resultSet.getTimestamp("stop_time").toLocalDateTime());

                schedulerModelList.add(schedulerModel);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }

        return schedulerModelList;
    }

    public DefaultTableModel getByDateRange(LocalDateTime from, LocalDateTime to) {
        String[] headline = getHeadline();
        DefaultTableModel defaultTableModel = new DefaultTableModel(headline, 0);

        try(Connection connection = Database.getConnection()) {
            String sql = "SELECT * FROM scheduler INNER JOIN projects ON scheduler.projects_id = projects.id " +
                    "WHERE scheduler.start_time >= ? AND scheduler.stop_time <= ? ORDER BY scheduler.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            addRows(defaultTableModel, resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new DefaultTableModel();
        }

        return defaultTableModel;
    }

    public DefaultTableModel getByProjectsIdAndDateRange(int projectsId, LocalDateTime from, LocalDateTime to) {
        String[] headline = getHeadline();
        DefaultTableModel defaultTableModel = new DefaultTableModel(headline, 0);

        try(Connection connection = Database.getConnection()) {
            String sql = "SELECT * FROM scheduler INNER JOIN projects ON scheduler.projects_id = projects.id " +
                    "WHERE scheduler.projects_id = ? AND scheduler.start_time >= ? AND scheduler.stop_time <= ? ORDER BY scheduler.id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectsId);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(from));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            addRows(defaultTableModel, resultSet);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new DefaultTableModel();
        }

        return defaultTableModel;
    }

    private String[] getHeadline() {
        return new String[] {translations.getString("header_project_name"), translations.getString("header_date"),
                translations.getString("header_task"), translations.getString("header_start_time"),
                translations.getString("header_stop_time"), translations.getString("header_hours")};
    }

    private void addRows(DefaultTableModel defaultTableModel, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
            LocalDateTime stopTime = resultSet.getTimestamp("stop_time").toLocalDateTime();
            String hours = TimeUtils.computeHours(startTime, stopTime) + ":" + TimeUtils.computeMinutes(startTime, stopTime);

            Object[] columns = {
                    resultSet.getString("project_name"),
                    TimeUtils.getFormattedDate(startTime),
                    resultSet.getString("task"),
                    TimeUtils.getFormattedTime(startTime),
                    TimeUtils.getFormattedTime(stopTime),
                    hours
            };

            defaultTableModel.addRow(columns);
        }
    }
}
