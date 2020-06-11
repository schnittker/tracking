package main.java.models;

import java.time.LocalDateTime;

public class SchedulerModel {
    private int id;
    private Integer projectsId;
    private String projectName;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProjectsId() {
        return projectsId;
    }

    public void setProjectsId(Integer projectsId) {
        this.projectsId = projectsId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }
}
