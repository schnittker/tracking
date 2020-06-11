package main.java.components;

import main.java.endpoints.SchedulerEndpoint;
import main.java.utils.TimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class TableView {
    private final SchedulerEndpoint schedulerEndpoint;

    private JTable schedulerTable;

    public TableView() {
        schedulerEndpoint = new SchedulerEndpoint();
    }

    public JTable createTable() {
        DefaultTableModel defaultModel = getDefaultModel();

        schedulerTable = new JTable(defaultModel);
        schedulerTable.setShowGrid(true);
        schedulerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        schedulerTable.setFillsViewportHeight(true);
        schedulerTable.setGridColor(Color.BLACK);
        return schedulerTable;
    }

    private DefaultTableModel getDefaultModel() {
        Month month = TimeUtils.getCurrentMonth();

        LocalDateTime firstDateTimeOfMonth = TimeUtils.getFirstDateOfMonth(month.getValue());
        LocalDateTime lastDateTimeOfMonth = TimeUtils.getLastDateOfMonth(month.getValue());

        DefaultTableModel byDateRange = schedulerEndpoint.getByDateRange(
                firstDateTimeOfMonth,
                lastDateTimeOfMonth
        );

        return byDateRange;
    }
}
