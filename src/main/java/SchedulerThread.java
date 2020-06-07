import java.time.LocalDateTime;

public class SchedulerThread extends Thread{
    private final String projectName;
    private final LocalDateTime startTime;

    public SchedulerThread(String projectName, LocalDateTime startTime) {
        this.projectName = projectName;
        this.startTime = startTime;
    }

    public void run() {
        System.out.println("start " + projectName + " at " + startTime);
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
