package services;

import helper.PropertiesLoader;
import models.SchedulerModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import utils.TimeUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class CsvService {
    private static final Logger LOGGER = Logger.getLogger(SchedulerService.class.getName());

    public CsvService() {
    }

    public void exportAsFile(List<SchedulerModel> schedulerModelList) {
        Properties properties = PropertiesLoader.loadProperties("application.properties");
        String csvOutputPath = properties.getProperty("csv_output_path");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            int stepSize = schedulerModelList.size() / 10;
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
}
