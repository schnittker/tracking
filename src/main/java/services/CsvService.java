package main.java.services;

import main.java.helper.PropertiesLoader;
import main.java.models.SchedulerModel;
import main.java.utils.TimeUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static main.java.utils.TimeUtils.computeHours;

/**
 * @author markus schnittker
 */

public class CsvService {

    private final ResourceBundle translations;
    private final ExceptionService exceptionService;

    public CsvService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        exceptionService = new ExceptionService();
    }

    public void exportAsFile(List<SchedulerModel> schedulerModelList) {
        Properties properties = PropertiesLoader.loadProperties("application.properties");
        String csvOutputPath = Objects.requireNonNull(properties).getProperty("csv_output_path");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            writerHeader(csvPrinter);
            writeContent(schedulerModelList, csvPrinter);
            writeFooter(schedulerModelList, csvPrinter);
            csvPrinter.flush();
        } catch (IOException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    private void writerHeader(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord(
                translations.getString("header_date"), translations.getString("header_project_name"),
                translations.getString("header_start_time"), translations.getString("header_stop_time"),
                translations.getString("header_hours"));
    }

    private void writeContent(List<SchedulerModel> schedulerModelList, CSVPrinter csvPrinter) throws IOException {
        for(SchedulerModel schedulerModel : schedulerModelList) {
            String curHours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime()) + ":" + TimeUtils.computeMinutes(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            String date = TimeUtils.getFormattedDate(schedulerModel.getStartTime());
            String startTime = TimeUtils.getFormattedTime(schedulerModel.getStartTime());
            String stopTime = TimeUtils.getFormattedTime(schedulerModel.getStopTime());

            csvPrinter.printRecord(date, schedulerModel.getProjectName(), startTime, stopTime, curHours);
        }
    }

    private void writeFooter(List<SchedulerModel> schedulerModelList, CSVPrinter csvPrinter) throws IOException {
        Map<String, Long> projectsMap = getTotalMinutesByProjectAsMap(schedulerModelList);
        csvPrinter.println();
        for(Map.Entry<String, Long> projectEntry : projectsMap.entrySet()) {
            String totalTime = TimeUtils.getWorkingTimeAsString(projectEntry.getValue());
            csvPrinter.printRecord(projectEntry.getKey(), totalTime);
        }
    }

    private Map<String, Long> getTotalMinutesByProjectAsMap(List<SchedulerModel> schedulerModelList) {
        Map<String, Long> projectsMap = new HashMap<String, Long>();

        for(SchedulerModel schedulerModel : schedulerModelList) {
            long currentMinutes = ChronoUnit.MINUTES.between(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            String projectName = schedulerModel.getProjectName();

            if(projectsMap.containsKey(projectName)) {
                Long totalProjectMinutes = projectsMap.get(projectName);
                totalProjectMinutes = totalProjectMinutes.longValue() + currentMinutes;
                projectsMap.put(projectName, totalProjectMinutes);
            } else {
                projectsMap.put(projectName, currentMinutes);
            }
        }

        return projectsMap;
    }
}
