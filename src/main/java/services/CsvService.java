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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

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
                     translations.getString("csv_header_date"), translations.getString("csv_header_project_name"),
                     translations.getString("csv_header_start_time"), translations.getString("csv_header_stop_time"),
                     translations.getString("csv_header_hours")))) {

            for(SchedulerModel schedulerModel : schedulerModelList) {
                long curHours = TimeUtils.computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime());
                String date = TimeUtils.getFormattedDate(schedulerModel.getStartTime());
                String startTime = TimeUtils.getFormattedTime(schedulerModel.getStartTime());
                String stopTime = TimeUtils.getFormattedTime(schedulerModel.getStopTime());

                csvPrinter.printRecord(date, schedulerModel.getProjectsId(), startTime, stopTime, Long.valueOf(curHours));
            }

            csvPrinter.println();

            Map<Integer, Long> projectsMap = TimeUtils.getTotalHoursAsProjectsMap(schedulerModelList);
            for(Map.Entry projectEntry : projectsMap.entrySet()) {
                csvPrinter.printRecord(projectEntry.getKey(), projectEntry.getValue());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            exceptionService.logging(this.getClass().getName(), e.getMessage());
        }
    }
}
