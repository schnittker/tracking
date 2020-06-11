package main.java.threads;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class SchedulerThread extends Thread{
    private final ResourceBundle translations;

    private final int projectsId;
    private final LocalDateTime startTime;

    public SchedulerThread(int projectsId, LocalDateTime startTime) {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        this.projectsId = projectsId;
        this.startTime = startTime;
    }

    public void run() {

    }

    public int getProjectsId() {
        return projectsId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
