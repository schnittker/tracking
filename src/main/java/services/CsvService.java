package main.java.services;

import main.java.helper.PropertiesLoader;
import main.java.models.SchedulerModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import main.java.utils.TimeUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static main.java.utils.TimeUtils.computeHours;

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
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                     translations.getString("header_date"), translations.getString("header_project_name"),
                     translations.getString("header_start_time"), translations.getString("header_stop_time"),
                     translations.getString("header_hours")))) {

            for(SchedulerModel schedulerModel : schedulerModelList) {
                String curHours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime()) + ":" + TimeUtils.computeMinutes(schedulerModel.getStartTime(), schedulerModel.getStopTime());
                String date = TimeUtils.getFormattedDate(schedulerModel.getStartTime());
                String startTime = TimeUtils.getFormattedTime(schedulerModel.getStartTime());
                String stopTime = TimeUtils.getFormattedTime(schedulerModel.getStopTime());

                csvPrinter.printRecord(date, schedulerModel.getProjectName(), startTime, stopTime, curHours);
            }

            csvPrinter.println();

            Map<String, Long> projectsMap = getTotalHoursAsProjectsMap(schedulerModelList);
            for(Map.Entry projectEntry : projectsMap.entrySet()) {
                csvPrinter.printRecord(projectEntry.getKey(), projectEntry.getValue());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    private Map<String, Long> getTotalHoursAsProjectsMap(List<SchedulerModel> schedulerModelList) {
        Map<String, Long> projectsMap = new HashMap<String, Long>();

        for(SchedulerModel schedulerModel : schedulerModelList) {
            long currentHours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            String projectName = schedulerModel.getProjectName();

            if(projectsMap.containsKey(projectName)) {
                Long totalProjectHours = projectsMap.get(projectName);
                totalProjectHours = totalProjectHours.longValue() + currentHours;
                projectsMap.put(projectName, totalProjectHours.longValue());
            } else {
                projectsMap.put(projectName, currentHours);
            }
        }

        return projectsMap;
    }
}
