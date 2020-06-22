package schnittker.tracking.components;

import schnittker.tracking.services.SchedulerService;
import schnittker.tracking.utils.TimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * @author markus schnittker
 */
public class TableView {
    private final SchedulerService schedulerService;

    private JTable schedulerTable;
    private DefaultTableModel defaultModel;

    public TableView() {
        schedulerService = new SchedulerService();
    }

    public JTable createTable() {
        defaultModel = getDefaultModel();
        schedulerTable = new JTable(defaultModel);
        schedulerTable.setShowGrid(true);
        schedulerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        schedulerTable.setFillsViewportHeight(true);
        schedulerTable.setGridColor(Color.BLACK);

        return schedulerTable;
    }

    public void getTableByProjectsId(Integer projectsId) {
        Month month = TimeUtils.getCurrentMonth();
        LocalDateTime firstDateTimeOfMonth = TimeUtils.getFirstDateOfMonth(month.getValue());
        LocalDateTime lastDateTimeOfMonth = TimeUtils.getLastDateOfMonth(month.getValue());
        DefaultTableModel byProjectsIdAndDateRange = schedulerService.getByProjectsIdAndDateRange(projectsId.intValue(),
                firstDateTimeOfMonth, lastDateTimeOfMonth);

        schedulerTable.setModel(byProjectsIdAndDateRange);
        refresh();
    }

    public void getTableForDaily() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        LocalTime startTime = LocalTime.of(0,0,0);
        LocalTime stopTime = LocalTime.of(23, 59, 59);

        LocalDateTime yesterdayMorning = LocalDateTime.of(yesterday, startTime);
        LocalDateTime yesterdayEvening = LocalDateTime.of(yesterday, stopTime);
        DefaultTableModel byDateRange = schedulerService.getByDateRange(yesterdayMorning, yesterdayEvening);

        schedulerTable.setModel(byDateRange);
        refresh();
    }

    public void getRefreshedDefaults() {
        defaultModel = getDefaultModel();
        schedulerTable.setModel(defaultModel);
        refresh();
    }

    private DefaultTableModel getDefaultModel() {
        Month month = TimeUtils.getCurrentMonth();
        LocalDateTime firstDateTimeOfMonth = TimeUtils.getFirstDateOfMonth(month.getValue());
        LocalDateTime lastDateTimeOfMonth = TimeUtils.getLastDateOfMonth(month.getValue());
        DefaultTableModel byDateRange = schedulerService.getByDateRange(firstDateTimeOfMonth, lastDateTimeOfMonth);

        return byDateRange;
    }

    private void refresh () {
        defaultModel.fireTableStructureChanged();
        defaultModel.fireTableDataChanged();
        schedulerTable.repaint();
    }
}
