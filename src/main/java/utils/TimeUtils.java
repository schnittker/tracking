package main.java.utils;

import main.java.models.SchedulerModel;

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

/**
 * @author markus schnittker
 */
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

    public static String computeMinutes(LocalDateTime startTime, LocalDateTime stopTime) {
        long minutes = ChronoUnit.MINUTES.between(startTime, stopTime);
        long hours = minutes / 60;

        String result = String.valueOf(minutes - (hours * 60));
        if(result.length() == 1) {
            result = 0 + result;
        }
        return result;
    }

    public static int getCurrentYear() {
        LocalDate localDate = LocalDate.now();
        return localDate.getYear();
    }

    public static Month getCurrentMonth() {
        LocalDate localDate = LocalDate.now();
        return localDate.getMonth();
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

    public static String getWorkingTimeAsString(long totalMinutes) {
        long hours = totalMinutes / 60;
        String minutes = String.valueOf(totalMinutes - (hours * 60));
        if(minutes.length() == 1) {
            minutes = 0 + minutes;
        }

        return hours + ":" + minutes;
    }

    public static Map<String, Long> getTotalMinutesByProjectAsMap(List<SchedulerModel> schedulerModelList) {
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
