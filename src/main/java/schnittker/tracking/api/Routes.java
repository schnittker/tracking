package schnittker.tracking.api;

import com.google.gson.Gson;
import schnittker.tracking.api.response.StandardResponse;
import schnittker.tracking.enums.StatusResponse;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.services.ProjectService;
import schnittker.tracking.services.SchedulerService;

import static spark.Spark.*;

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
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS,new Gson()
                            .toJson(projectService.getProjectsAsList())));
        });

        // Get scheduler
        get("/scheduler", (request, response) -> {
            response.type("application/json");
           // return new Gson().toJson(
             //       new StandardResponse(StatusResponse.SUCCESS,new Gson()
               //             .toJsonTree(schedulerService.getByDateRange())));
            return "";
        });

        // Get scheduler by project name
        get("/scheduler/:project", (request, response) -> {
            response.type("application/json");
            //return new Gson().toJson(
                   // new StandardResponse(StatusResponse.SUCCESS,new Gson()
                         //   .toJsonTree(schedulerService.getByProjectsNameAndDateRange(request.params(":project"), LocalDateTime.now(), LocalDateTime.now()))));
            return "";
        });

        // Add scheduler
        post("/scheduler/add", (request, response) -> {
            response.type("application/json");
            SchedulerModel schedulerModel = new Gson().fromJson(request.body(), SchedulerModel.class);
            // schedulerService.add(schedulerModel);

            return new Gson()
                    .toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        // Remove scheduler
        delete("/scheduler/delete/:id", (request, response) -> {
            response.type("application/json");
            // schedulerService.delete(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, ""));
        });
    }
}
