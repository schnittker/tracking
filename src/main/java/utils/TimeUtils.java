package utils;

import models.SchedulerModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeUtils {
    public static String getFormattedDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dateTime.format(formatter);
    }

    public static String getFormattedTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    public static long computeHours(LocalDateTime startTime, LocalDateTime stopTime) {
        return ChronoUnit.HOURS.between(startTime, stopTime);
    }

    public static long computeTotalHours(List<SchedulerModel> schedulerModelList) {
        long totalHours = 0;
        for(SchedulerModel schedulerModel : schedulerModelList) {
            long hours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            totalHours += hours;
        }

        return totalHours;
    }

    public static Map<String, Long> getTotalHoursAsProjectsMap(List<SchedulerModel> schedulerModelList) {
        Map<String, Long> projectsMap = new HashMap<>();

        for(SchedulerModel schedulerModel : schedulerModelList) {
            long currentHours = computeHours(schedulerModel.getStartTime(), schedulerModel.getStopTime());
            String projectName = schedulerModel.getProjectName();

            if(projectsMap.containsKey(projectName)) {
                long totalProjectHours = projectsMap.get(projectName);
                totalProjectHours += currentHours;
                projectsMap.put(projectName, totalProjectHours);
            } else {
                projectsMap.put(projectName, currentHours);
            }
        }

        return projectsMap;
    }
}
