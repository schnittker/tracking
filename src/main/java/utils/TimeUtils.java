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

    /**
     * The submitted LocalDateTime object is formatted and returned as string object
     *
     * @param dateTime
     *          The LocalDateTime object to be formatted according to the format dd.MM.yyyy
     * @return
     *          Returns a String object with the formatted date
     */
    public static String getFormattedDate(LocalDateTime dateTime) {

        // Format the submitted LocalDateTime object to dd.MM.yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Returns a String object with the formatted date
        return dateTime.format(formatter);
    }

    /**
     * The submitted LocalDateTime object is formatted and returned as string object
     *
     * @param dateTime
     *          The LocalDateTime object to be formatted according to the format HH:mm
     * @return
     *          Returns a String object with the formatted time
     */
    public static String getFormattedTime(LocalDateTime dateTime) {

        // Format the submitted LocalDateTime object to HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Returns a String object with the formatted time
        return dateTime.format(formatter);
    }

    /**
     * This method calculates the difference between the two LocalDateTime objects
     * and returns the hours
     *
     * @param startTime
     *          The LocalDateTime Object which includes the start time
     * @param stopTime
     *          The LocalDateTime Object which includes the end time
     * @return
     *          Returns the hours between the start and the end time
     */
    public static long computeHours(LocalDateTime startTime, LocalDateTime stopTime) {

        // Returns the hours between the start and the end time
        return ChronoUnit.HOURS.between(startTime, stopTime);
    }

    /**
     * This method calculates the difference between the two LocalDateTime objects
     * and returns the minutes
     *
     * @param startTime
     *          The LocalDateTime Object which includes the start time
     * @param stopTime
     *          The LocalDateTime Object which includes the end time
     * @return
     *          Returns the minutes between the start and the end time
     */
    public static String computeMinutes(LocalDateTime startTime, LocalDateTime stopTime) {

        // Get the minutes between the start and the end time
        long minutes = ChronoUnit.MINUTES.between(startTime, stopTime);

        // Compute the hours
        long hours = minutes / 60;

        // Calculate the remaining minutes that are left when all full hours are deducted
        String result = String.valueOf(minutes - (hours * 60));

        // Transform the result
        if(result.length() == 1) {

            // If the remaining minutes are only single digits, then add a zero to the beginning
            result = 0 + result;
        }

        // Returns the minutes between the start and the end time
        return result;
    }

    /**
     *
     * @return Returns the current year
     */
    public static int getCurrentYear() {

        // Get the current date
        LocalDate localDate = LocalDate.now();

        // Returns the current year
        return localDate.getYear();
    }

    /**
     *
     * @return Returns the current month
     */
    public static Month getCurrentMonth() {

        // Get the current date
        LocalDate localDate = LocalDate.now();

        // Returns the current month
        return localDate.getMonth();
    }


    /**
     *
     * @param month The month to be calculated
     * @return Returns the first date of a month as LocalDateTime object
     */
    public static LocalDateTime getFirstDateOfMonth(int month) {

        // Create a new LocalTime object with the time 00:00:00
        LocalTime localTime = LocalTime.of(0,0,0);

        // Create a new LocalDate object with the first day and the current year
        LocalDate localDate = LocalDate.of(getCurrentYear(), Month.of(month), 1);

        // Returns the first date of a month as LocalDateTime object
        return LocalDateTime.of(localDate, localTime);
    }

    /**
     *
     * @param month The month to be calculated
     * @return Returns the last date of a month as LocalDateTime object
     */
    public static LocalDateTime getLastDateOfMonth(int month) {

        // Create a new LocalTime object with the time 23:59:59
        LocalTime localTime = LocalTime.of(23,59,59);

        // Get the first date of the month
        LocalDate firstDateOfMonth = LocalDate.of(getCurrentYear(), Month.of(month), 1);

        // Get the last date of the month
        LocalDate lastDateOfMonth = firstDateOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        // Returns the last date of a month as LocalDateTime object
        return LocalDateTime.of(lastDateOfMonth, localTime);
    }

    /**
     *
     * @param totalMinutes
     * @return
     */
    public static String getWorkingTimeAsString(long totalMinutes) {
        long hours = totalMinutes / 60;
        String minutes = String.valueOf(totalMinutes - (hours * 60));
        if(minutes.length() == 1) {
            minutes = 0 + minutes;
        }

        return hours + ":" + minutes;
    }

    /**
     *
     * @param schedulerModelList
     * @return
     */
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
