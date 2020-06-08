package services;

import endpoints.SchedulerEndpoint;
import models.SchedulerModel;
import org.apache.commons.lang3.StringUtils;
import threads.SchedulerThread;
import utils.TimeUtils;

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

    private List<SchedulerThread> schedulerThreadList = new ArrayList<>();

    public SchedulerService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerEndpoint = new SchedulerEndpoint();
        csvService = new CsvService();
        exceptionService = new ExceptionService();
    }

    public void start(String projectName) {
        SchedulerThread schedulerThread = new SchedulerThread(projectName, LocalDateTime.now());
        schedulerThread.start();
        schedulerThreadList.add(schedulerThread);
    }

    public void stop(String projectName) {
        SchedulerThread schedulerThread = getSchedulerThreadByProjectName(projectName);
        SchedulerModel schedulerModel = createSchedulerModel(projectName, schedulerThread.getStartTime(), LocalDateTime.now());

        if(Objects.nonNull(schedulerThread) && Objects.nonNull(schedulerModel)) {
            schedulerEndpoint.insert(schedulerModel);
            schedulerThread.interrupt();
            System.out.println(translations.getString("stop_tracking") + " \"" + projectName + "\"");
        }
    }

    public void pause(String projectName) {
        SchedulerThread schedulerThread = getSchedulerThreadByProjectName(projectName);
        try {
            schedulerThread.wait();
        } catch (InterruptedException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    public void active() {
        for(SchedulerThread schedulerThread : schedulerThreadList) {
            System.out.println(TimeUtils.getFormattedTime(schedulerThread.getStartTime()) + ": " +
                    schedulerThread.getProjectName());
        }
    }

    public void export(String projectName, int month) {
        LocalDateTime startDateTime = TimeUtils.getFirstDateOfMonth(month);
        LocalDateTime stopDateTime = TimeUtils.getLastDateOfMonth(month);

        List<SchedulerModel> schedulerModelList;
        if(StringUtils.equals(projectName, "all")) {
            schedulerModelList = schedulerEndpoint.getByDateRange(startDateTime, stopDateTime);
        } else {
            schedulerModelList = schedulerEndpoint.getByProjectNameAndDateRange(projectName, startDateTime, stopDateTime);
        }

        csvService.exportAsFile(schedulerModelList);
    }

    private SchedulerThread getSchedulerThreadByProjectName(String projectName) {
        for(SchedulerThread schedulerThread : schedulerThreadList){
            if(StringUtils.equals(schedulerThread.getProjectName(),projectName)){
                return schedulerThread;
            }
        }

        return null;
    }

    private SchedulerModel createSchedulerModel(String projectName, LocalDateTime startTime, LocalDateTime stopTime) {
        if(StringUtils.isEmpty(projectName) || Objects.isNull(startTime) || Objects.isNull(stopTime)) {
            return null;
        }

        SchedulerModel schedulerModel = new SchedulerModel();
        schedulerModel.setProjectName(projectName);
        schedulerModel.setStartTime(startTime);
        schedulerModel.setStopTime(stopTime);

        return schedulerModel;
    }
}
