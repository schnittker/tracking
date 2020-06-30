package schnittker.tracking.threads;

import schnittker.tracking.TrackingApplication;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.services.SchedulerService;
import schnittker.tracking.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
    private static final long MINUTES = 1;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 60;

    private final SchedulerService schedulerService;
    private final ResourceBundle translations;

    private Timer statusTimer;
    private Timer countdownTimer;
    private int projectsId;
    private String projectName;
    private LocalDateTime startTime;

    public SchedulerThread(int projectsId, String projectName, LocalDateTime startTime) {
        schedulerService = new SchedulerService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        this.projectsId = projectsId;
        this.projectName = projectName;
        this.startTime = startTime;
    }

    public void run() {
        createStatusMessage();
        createCountdown();
    }

    public int getProjectsId() {
        return projectsId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Timer getStatusTimer() {
        return statusTimer;
    }

    public Timer getCountdownTimer() {
        return countdownTimer;
    }

    private void createStatusMessage() {
        statusTimer = new Timer();
        statusTimer.schedule(new TimerTask() {
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

    private void createCountdown() {
        countdownTimer = new Timer();
        final int workingTime = getWorkingTime();
        countdownTimer.schedule(new TimerTask() {
            private int minutes = 0;

            @Override
            public void run() {
                if(workingTime > 0) {
                    minutes += workingTime;
                }

                int hours = 0;
                if(minutes > 60) {
                    hours = minutes / 60;
                    minutes = minutes - (hours * 60);
                }

                String minuteString = String.valueOf(minutes);
                if(minuteString.length() < 2) {
                    minuteString = "0" + minuteString;
                }

                TrackingApplication.statusBar.setCountdown("    " + translations.getString("status_bar.working_time.today") + " " + hours + ":" + minuteString);

                minutes++;
            }
        }, 0, TimeUnit.MINUTES.toMillis(MINUTES));
    }

    private int getWorkingTime() {
        LocalDate today = LocalDate.now();
        LocalTime startTime = LocalTime.of(0,0,1);
        LocalTime stopTime = LocalTime.of(23,59,59);
        LocalDateTime firstDateTime = LocalDateTime.of(today, startTime);
        LocalDateTime lastDateTime = LocalDateTime.of(today, stopTime);
        final List<SchedulerModel> schedulerModelList = schedulerService.getByDateRangeAsSchedulerModelList(firstDateTime, lastDateTime);

        int minutes = 0;
        for(SchedulerModel schedulerModel : schedulerModelList) {
            minutes += ChronoUnit.MINUTES.between(schedulerModel.getStartTime(), schedulerModel.getStopTime());
        }

        return minutes;
    }
}
