package schnittker.tracking.services;

import schnittker.tracking.TrackingApplication;
import schnittker.tracking.helper.PropertiesLoader;
import schnittker.tracking.models.SchedulerModel;
import schnittker.tracking.utils.TimeUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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

import static schnittker.tracking.utils.TimeUtils.computeHours;

/**
 * @author markus schnittker
 */

public class CsvService {
    private final ResourceBundle translations;
    private final ExceptionLoggerService exceptionLoggerService;

    public CsvService() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        exceptionLoggerService = new ExceptionLoggerService();
    }

    public void exportAsFile(List<SchedulerModel> schedulerModelList) {
        Properties properties = new PropertiesLoader().loadProperties("application.properties");
        String csvOutputPath = Objects.requireNonNull(properties).getProperty("csv_output_path");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvOutputPath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            writerHeader(csvPrinter);
            writeContent(schedulerModelList, csvPrinter);
            writeFooter(schedulerModelList, csvPrinter);
            csvPrinter.flush();
        } catch (IOException e) {
            exceptionLoggerService.logging(this.getClass().getName(), e.getMessage());
        }
    }

    private void writerHeader(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord(
                translations.getString("header_date"), translations.getString("header_project_name"),
                translations.getString("header_start_time"), translations.getString("header_stop_time"),
                translations.getString("header_hours"));
    }

    private void writeContent(List<SchedulerModel> schedulerModelList, CSVPrinter csvPrinter) throws IOException {
        int minValue = 0;
        int maxValue = schedulerModelList.size();
        int currentValue = 0;

        for(SchedulerModel schedulerModel : schedulerModelList) {
            String curHours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime()) + ":" + TimeUtils.computeMinutes(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            String date = TimeUtils.getFormattedDate(schedulerModel.getStartTime());
            String startTime = TimeUtils.getFormattedTime(schedulerModel.getStartTime());
            String stopTime = TimeUtils.getFormattedTime(schedulerModel.getStopTime());

            csvPrinter.printRecord(date, schedulerModel.getProjectName(), startTime, stopTime, curHours);

            ++currentValue;
            TrackingApplication.statusBar.setProgressBar(currentValue, minValue, maxValue);
        }
    }

    private void writeFooter(List<SchedulerModel> schedulerModelList, CSVPrinter csvPrinter) throws IOException {
        Map<String, Long> projectsMap = TimeUtils.getTotalMinutesByProjectAsMap(schedulerModelList);
        csvPrinter.println();
        for(Map.Entry<String, Long> projectEntry : projectsMap.entrySet()) {
            String totalTime = TimeUtils.getWorkingTimeAsString(projectEntry.getValue());
            csvPrinter.printRecord(projectEntry.getKey(), totalTime);
        }
    }
}
