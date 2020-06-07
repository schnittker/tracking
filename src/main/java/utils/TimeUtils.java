package utils;

import models.SchedulerModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
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

    public static int getCurrentYear() {
        LocalDate localDate = LocalDate.now();
        return localDate.getYear();
    }

    public static LocalDateTime getFirstDateOfMonth(int month) {
        LocalTime localTime = LocalTime.of(0,0,0);
        LocalDate localDate = LocalDate.of(getCurrentYear(), Month.of(month), 1);
        return LocalDateTime.of(localDate, localTime);
    }

    public static LocalDateTime getLastDateOfMonth(int month) {
        LocalTime localTime = LocalTime.of(23,59,59);
        LocalDate firstDateOfMonth = LocalDate.of(getCurrentYear(), Month.of(month), 1);
        LocalDate lastDateOfMonth = firstDateOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(lastDateOfMonth, localTime);
    }
}
