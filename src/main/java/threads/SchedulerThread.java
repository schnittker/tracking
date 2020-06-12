package main.java.threads;

import main.java.TrackingApplication;
import main.java.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author markus schnittker
 */
public class SchedulerThread extends Thread{
    private static final long SECONDS = 1000;

    private final ResourceBundle translations;

    private final int projectsId;
    private final LocalDateTime startTime;
    private Timer timer;

    public SchedulerThread(int projectsId, LocalDateTime startTime) {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        this.projectsId = projectsId;
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

            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                long hours = TimeUtils.computeHours(getStartTime(), now);
                String minutes = TimeUtils.computeMinutes(getStartTime(), now);
                TrackingApplication.statusBar.setMessage(hours + ":" + minutes);
            }
        }, 0, SECONDS);
    }
}
