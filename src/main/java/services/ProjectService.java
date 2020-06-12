package main.java.services;

import main.java.endpoints.ProjectsEndpoint;

public class ProjectService {
    private ProjectsEndpoint projectsEndpoint;

    public ProjectService() {
        projectsEndpoint = new ProjectsEndpoint();
    }

    public String getProjectNameById(int projectsId) {
        return projectsEndpoint.getProjectNameById(projectsId);
    }
}
