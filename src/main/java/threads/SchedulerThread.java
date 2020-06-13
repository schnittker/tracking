package main.java.threads;

import main.java.TrackingApplication;
import main.java.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author markus schnittker
 */
public class SchedulerThread extends Thread{
    private static final long SECONDS = 1;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 60;

    private final ResourceBundle translations;

    private Timer timer;
    private int projectsId;
    private String projectName;
    private LocalDateTime startTime;

    public SchedulerThread(int projectsId, String projectName, LocalDateTime startTime) {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        this.projectsId = projectsId;
        this.projectName = projectName;
        this.startTime = startTime;
    }

    public void run() {
        createStatusMessage();
    }

    public int getProjectsId() {
        return projectsId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Timer getTimer() {
        return timer;
    }

    private void createStatusMessage() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            private int value = 0;

            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                long hours = TimeUtils.computeHours(getStartTime(), now);
                String minutes = TimeUtils.computeMinutes(getStartTime(), now);
                TrackingApplication.statusBar.setMessage(projectName + " " + hours + ":" + minutes);

                TrackingApplication.statusBar.setProgressBar(value, MIN_VALUE, MAX_VALUE);

                value++;
                if(value == 60) {
                    value = 0;
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(SECONDS));
    }
}
