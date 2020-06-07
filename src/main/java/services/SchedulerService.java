package services;

import endpoints.SchedulerEndpoint;
import models.SchedulerModel;
import org.apache.commons.lang3.StringUtils;
import threads.SchedulerThread;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
@SuppressWarnings("all")
public class SchedulerService {
    private static final Logger LOGGER = Logger.getLogger(SchedulerService.class.getName());

    private final ResourceBundle translations;
    private final SchedulerEndpoint schedulerEndpoint;
    private final CsvService csvService;

    private List<SchedulerThread> schedulerThreadList = new ArrayList<>();

    public SchedulerService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerEndpoint = new SchedulerEndpoint();
        csvService = new CsvService();
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
        }
    }

    public void pause(String projectName) {

    }

    public void active() {

    }

    public void export(String projectName, String period) {
        List<SchedulerModel> schedulerModelList = new ArrayList<>();

        // todo: get from table

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
