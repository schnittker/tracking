package schnittker.tracking.services;

import schnittker.tracking.endpoints.SchedulerEndpoint;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.threads.SchedulerThread;
import schnittker.tracking.utils.TimeUtils;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
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

    public void start(String projectName) {
        SchedulerThread schedulerThread = new SchedulerThread(projectName, LocalDateTime.now());
        schedulerThread.start();
        schedulerThreadList.add(schedulerThread);
        System.out.println(schedulerThreadList);
    }

    public void stop(int projectsId) {
        SchedulerThread schedulerThread = getSchedulerThreadByProjectsId(projectsId);
        if(Objects.nonNull(schedulerThread)) {
            SchedulerModel schedulerModel = createSchedulerModel(projectsId, schedulerThread.getStartTime(), LocalDateTime.now());
            schedulerEndpoint.insert(schedulerModel);
            schedulerThread.getTimer().cancel();
            schedulerThread.interrupt();
        }
    }

    public void export(int month) {
        LocalDateTime startDateTime = TimeUtils.getFirstDateOfMonth(month);
        LocalDateTime stopDateTime = TimeUtils.getLastDateOfMonth(month);
        List<SchedulerModel> schedulerModelList = schedulerEndpoint.getByDateRangeForExport(startDateTime, stopDateTime);
        csvService.exportAsFile(schedulerModelList);
    }

    public List<SchedulerModel> getSchedulerModelList(int month) {
        LocalDateTime startDateTime = TimeUtils.getFirstDateOfMonth(month);
        LocalDateTime stopDateTime = TimeUtils.getLastDateOfMonth(month);

        return schedulerEndpoint.getByDateRangeForExport(startDateTime, stopDateTime);
    }

    public DefaultTableModel getByProjectsIdAndDateRange(int projectsId, LocalDateTime firstDateTimeOfMonth, LocalDateTime lastDateTimeOfMonth) {
        return schedulerEndpoint.getByProjectsIdAndDateRange(projectsId, firstDateTimeOfMonth, lastDateTimeOfMonth);
    }

    public DefaultTableModel getByDateRange(LocalDateTime firstDateTimeOfMonth, LocalDateTime lastDateTimeOfMonth) {
        return schedulerEndpoint.getByDateRange(firstDateTimeOfMonth, lastDateTimeOfMonth);
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

    private SchedulerModel createSchedulerModel(Integer projectsId, LocalDateTime startTime, LocalDateTime stopTime) {
        if(Objects.isNull(projectsId) || Objects.isNull(startTime) || Objects.isNull(stopTime)) {
            return null;
        }

        SchedulerModel schedulerModel = new SchedulerModel();
        schedulerModel.setProjectsId(projectsId);
        schedulerModel.setStartTime(startTime);
        schedulerModel.setStopTime(stopTime);

        return schedulerModel;
    }
}
