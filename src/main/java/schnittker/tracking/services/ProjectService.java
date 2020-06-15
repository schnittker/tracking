package schnittker.tracking.services;

import schnittker.tracking.endpoints.ProjectsEndpoint;

import java.util.List;

public class ProjectService {
    private final ProjectsEndpoint projectsEndpoint;

    public ProjectService() {
        projectsEndpoint = new ProjectsEndpoint();
    }

    public String getProjectNameById(int projectsId) {
        return projectsEndpoint.getProjectNameById(projectsId);
    }

    public List<String> getForProjectTree() {
        return projectsEndpoint.getForProjectTree();
    }

    public void addNewProject(String projectName) {
        projectsEndpoint.addNewProject(projectName);
    }

    public void removeProjectById(Integer selectedProject) {
        projectsEndpoint.removeById(selectedProject);
    }
}
