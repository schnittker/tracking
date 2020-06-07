import endpoints.SchedulerEndpoint;
import helper.PropertiesLoader;
import models.SchedulerModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import utils.TimeUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
@SuppressWarnings("all")
public class Scheduler {
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    private static final Logger LOGGER = Logger.getLogger(Scheduler.class.getName());

    private SchedulerEndpoint schedulerEndpoint;
    private Properties properties;

    private List<SchedulerThread> schedulerThreadList = new ArrayList<>();

    public Scheduler() {
        schedulerEndpoint = new SchedulerEndpoint();
        properties = PropertiesLoader.loadProperties("application.properties");
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

        exportAsCsvFile(schedulerModelList);
    }

    private void exportAsCsvFile(List<SchedulerModel> schedulerModelList) {
        String csvOutputPath = properties.getProperty("csv_output_path");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            int stepSize = schedulerThreadList.size() / 10;
            System.out.print("[");

            for(SchedulerModel schedulerModel : schedulerModelList) {
                long curHours = TimeUtils.computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime());
                String date = TimeUtils.getFormattedDate(schedulerModel.getStartTime());
                String startTime = TimeUtils.getFormattedTime(schedulerModel.getStartTime());
                String stopTime = TimeUtils.getFormattedTime(schedulerModel.getStopTime());

                csvPrinter.printRecord(date, schedulerModel.getProjectName(), startTime, stopTime, curHours);

                for(int progressIndex = 0; progressIndex <= stepSize; progressIndex++) {
                    System.out.print("=");
                }
            }

            csvPrinter.println();

            Map<String, Long> projectsMap = TimeUtils.getTotalHoursAsProjectsMap(schedulerModelList);
            for(Map.Entry projectEntry : projectsMap.entrySet()) {
                csvPrinter.printRecord(projectEntry.getKey(), projectEntry.getValue());
            }

            System.out.print("] 100%");
            csvPrinter.flush();
        } catch (IOException e) {
            LOGGER.severe("Exception while trying to export as csv file. " + e.getMessage());
        }
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
