package threads;

import utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class SchedulerThread extends Thread{
    private final ResourceBundle translations;

    private final String projectName;
    private final LocalDateTime startTime;

    public SchedulerThread(String projectName, LocalDateTime startTime) {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        this.projectName = projectName;
        this.startTime = startTime;
    }

    public void run() {
        System.out.println(translations.getString("start_tracking") + " \"" + projectName + "\"");
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
