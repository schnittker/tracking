package schnittker.tracking.services;

import schnittker.tracking.endpoints.SchedulerEndpoint;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.threads.SchedulerThread;
import schnittker.tracking.utils.TimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
@SuppressWarnings("all")
public class SchedulerService {
    private final ResourceBundle translations;
    private final SchedulerEndpoint schedulerEndpoint;
    private final CsvService csvService;
    private final ProjectService projectService;

    private static List<SchedulerThread> schedulerThreadList = new ArrayList<>();

    public SchedulerService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerEndpoint = new SchedulerEndpoint();
        csvService = new CsvService();
        projectService = new ProjectService();
    }

    public void start(int projectsId) {
        String projectName = projectService.getProjectNameById(projectsId);
        SchedulerThread schedulerThread = new SchedulerThread(projectsId, projectName, LocalDateTime.now());
        schedulerThread.start();
        schedulerThreadList.add(schedulerThread);
        System.out.println(schedulerThreadList);
    }

    public void stop(int projectsId) {
        SchedulerThread schedulerThread = getSchedulerThreadByProjectsId(projectsId);
        if(Objects.nonNull(schedulerThread)) {
            String taskDescription = JOptionPane.showInputDialog(null,
                    translations.getString("project_stop.description"),
                    translations.getString("project_stop.headline"),
                    JOptionPane.PLAIN_MESSAGE);

            SchedulerModel schedulerModel = createSchedulerModel(projectsId, schedulerThread.getStartTime(),
                    LocalDateTime.now(), taskDescription);
            schedulerEndpoint.insert(schedulerModel);
            schedulerThread.getStatusTimer().cancel();
            schedulerThread.getCountdownTimer().cancel();
            schedulerThread.interrupt();
        }
    }

    public void export(int month) {
        LocalDateTime startDateTime = TimeUtils.getFirstDateOfMonth(month);
        LocalDateTime stopDateTime = TimeUtils.getLastDateOfMonth(month);
        List<SchedulerModel> schedulerModelList = schedulerEndpoint.getByDateRangeAsSchedulerModelList(startDateTime, stopDateTime);
        csvService.exportAsFile(schedulerModelList);
    }

    public List<SchedulerModel> getSchedulerModelList(int month) {
        LocalDateTime startDateTime = TimeUtils.getFirstDateOfMonth(month);
        LocalDateTime stopDateTime = TimeUtils.getLastDateOfMonth(month);

        return schedulerEndpoint.getByDateRangeAsSchedulerModelList(startDateTime, stopDateTime);
    }

    public DefaultTableModel getByProjectsIdAndDateRange(int projectsId, LocalDateTime firstDateTime, LocalDateTime lastDateTime) {
        return schedulerEndpoint.getByProjectsIdAndDateRangeAsDefaultTableModel(projectsId, firstDateTime, lastDateTime);
    }

    public DefaultTableModel getByDateRangeAsDefaultTableModel(LocalDateTime firstDateTime, LocalDateTime lastDateTime) {
        return schedulerEndpoint.getByDateRangeAsDefaultTableModel(firstDateTime, lastDateTime);
    }

    public List<SchedulerModel> getByDateRangeAsSchedulerModelList(LocalDateTime firstDateTime, LocalDateTime lastDateTime) {
        return schedulerEndpoint.getByDateRangeAsSchedulerModelList(firstDateTime, lastDateTime);
    }

    public int getWorkingTime() {
        LocalDate today = LocalDate.now();
        LocalTime startTime = LocalTime.of(0,0,1);
        LocalTime stopTime = LocalTime.of(23,59,59);
        LocalDateTime firstDateTime = LocalDateTime.of(today, startTime);
        LocalDateTime lastDateTime = LocalDateTime.of(today, stopTime);
        final List<SchedulerModel> schedulerModelList = getByDateRangeAsSchedulerModelList(firstDateTime, lastDateTime);

        int minutes = 0;
        for(SchedulerModel schedulerModel : schedulerModelList) {
            minutes += ChronoUnit.MINUTES.between(schedulerModel.getStartTime(), schedulerModel.getStopTime());
        }

        return minutes;
    }

    private SchedulerThread getSchedulerThreadByProjectsId(int projectsId) {
        SchedulerThread curSchedulerThread = null;
        for(SchedulerThread schedulerThread : schedulerThreadList){
            if(Objects.equals(schedulerThread.getProjectsId(), projectsId)){
                curSchedulerThread = schedulerThread;
            }
        }

        if(Objects.nonNull(curSchedulerThread)) {
            schedulerThreadList.remove(curSchedulerThread);
        }

        return curSchedulerThread;
    }

    private SchedulerModel createSchedulerModel(Integer projectsId, LocalDateTime startTime, LocalDateTime stopTime,
                                                String taskDescription) {
        if(Objects.isNull(projectsId) || Objects.isNull(startTime) || Objects.isNull(stopTime)) {
            return null;
        }

        SchedulerModel schedulerModel = new SchedulerModel();
        schedulerModel.setProjectsId(projectsId);
        schedulerModel.setStartTime(startTime);
        schedulerModel.setStopTime(stopTime);
        schedulerModel.setTask(taskDescription);

        return schedulerModel;
    }
}
