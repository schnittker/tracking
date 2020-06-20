package schnittker.tracking.endpoints;

import schnittker.tracking.helper.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public class ProjectsEndpoint {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void addNewProject(String projectName) {
        try(Connection connection = Database.getConnection()) {
            String sql = "INSERT INTO projects (project_name) VALUES(?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, projectName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public List<String> getForProjectTree() {
        List<String> projectList = new ArrayList<>();

        try(Connection connection = Database.getConnection()) {
            String sql = "SELECT project_name FROM projects ORDER BY id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                projectList.add(resultSet.getString(1));
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }

        return projectList;
    }

    public void removeById(Integer projectId) {
        try(Connection connection = Database.getConnection()) {
            String sql = "DELETE FROM projects WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public String getProjectNameById(int projectsId) {
        try(Connection connection = Database.getConnection()) {
            String sql = "SELECT project_name FROM projects WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectsId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return "";
        }

        return "";
    }
}
