package schnittker.tracking.api;

import com.google.gson.Gson;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.services.ProjectService;
import schnittker.tracking.services.SchedulerService;
import schnittker.tracking.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * http://localhost:4567/
 *
 * @author markus schnittker
 */
public class Routes {
    private final SchedulerService schedulerService;
    private final ProjectService projectService;

    public Routes() {
        schedulerService = new SchedulerService();
        projectService = new ProjectService();
    }

    public void bind() {

        // Get all projects
        get("/projects", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(projectService.getProjectsAsList(), ArrayList.class);
        });

        // Get scheduler
        get("/scheduler", (request, response) -> {
            response.type("application/json");
            final int currentMonth = TimeUtils.getCurrentMonth().getValue();
            List<SchedulerModel> schedulerModelList = schedulerService.getSchedulerModelList(currentMonth);
            return new Gson().toJson(schedulerModelList, ArrayList.class);
        });

        // Get scheduler by project name
        get("/scheduler/:project", (request, response) -> {
            response.type("application/json");
            // todo: implements method
            return new Gson().toJson("", ArrayList.class);
        });

        // Add scheduler
        post("/scheduler/add", (request, response) -> {
            // todo: implements method
            return "";
        });

        // Remove scheduler
        delete("/scheduler/delete/:id", (request, response) -> {
            // todo: implements method
            return "";
        });
    }
}
