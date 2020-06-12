package main.java.services;

import main.java.endpoints.SchedulerEndpoint;
import main.java.models.SchedulerModel;
import main.java.threads.SchedulerThread;
import main.java.utils.TimeUtils;

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
    private final ExceptionService exceptionService;
    private final ProjectService projectService;

    private List<SchedulerThread> schedulerThreadList = new ArrayList<>();

    public SchedulerService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerEndpoint = new SchedulerEndpoint();
        csvService = new CsvService();
        exceptionService = new ExceptionService();
        projectService = new ProjectService();
    }

    public void start(int projectsId) {
        String projectName = projectService.getProjectNameById(projectsId);
        SchedulerThread schedulerThread = new SchedulerThread(projectsId, projectName, LocalDateTime.now());
        schedulerThread.start();
        schedulerThreadList.add(schedulerThread);
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
