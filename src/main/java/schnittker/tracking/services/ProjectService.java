package schnittker.tracking.services;

import schnittker.tracking.endpoints.ProjectsEndpoint;

import java.util.List;

public class ProjectService {
    private final ProjectsEndpoint projectsEndpoint;

    public ProjectService() {
        projectsEndpoint = new ProjectsEndpoint();
    }

    public List<String> getForProjectTree() {
        return projectsEndpoint.getListForProjectTree();
    }

    public void addNewProject(String projectName) {
        projectsEndpoint.addNewProject(projectName);
    }

    public void removeProjectByName(String projectName) {
        projectsEndpoint.removeByName(projectName);
    }
}
