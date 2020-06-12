package main.java.components;

import main.java.TrackingApplication;
import main.java.services.SchedulerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ToolBar {
    private final SchedulerService schedulerService;
    private final ResourceBundle translations;

    private ExportDialog exportDialog;

    private JToolBar toolBar;
    private JButton btnTrackerStart;
    private ImageIcon iconTrackerStart;
    private JButton btnTrackerStop;
    private ImageIcon iconTrackerStop;
    private JButton btnExport;
    private ImageIcon iconExport;
    private JButton btnChart;
    private ImageIcon iconChart;
    private JButton btnExit;
    private ImageIcon iconExit;

    public ToolBar() {
        schedulerService = new SchedulerService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JToolBar createToolbar() {
        toolBar = new JToolBar();

        iconTrackerStart = new ImageIcon(getClass().getResource("/icons/play.png"));
        btnTrackerStart = new JButton(iconTrackerStart);
        btnTrackerStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer projectsId = TrackingApplication.projectList.getSelectedProject();
                if(Objects.nonNull(projectsId)) {
                    schedulerService.start(projectsId.intValue());
                    TrackingApplication.statusBar.setMessage(translations.getString("start_tracking"));
                } else {
                    TrackingApplication.statusBar.setMessage(translations.getString("no_selected_project"));
                }
            }
        });

        iconTrackerStop = new ImageIcon(getClass().getResource("/icons/stop.png"));
        btnTrackerStop = new JButton(iconTrackerStop);
        btnTrackerStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer projectsId = TrackingApplication.projectList.getSelectedProject();
                if(Objects.nonNull(projectsId)) {
                    schedulerService.stop(projectsId.intValue());
                    TrackingApplication.statusBar.setMessage(translations.getString("stop_tracking"));
                    TrackingApplication.tableView.getRefreshedDefaults();
                } else {
                    TrackingApplication.statusBar.setMessage(translations.getString("no_selected_project"));
                }
            }
        });

        iconExport = new ImageIcon(getClass().getResource("/icons/export.png"));
        btnExport = new JButton(iconExport);
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExportDialog().createDialog();
            }
        });

        iconChart = new ImageIcon(getClass().getResource("/icons/chart.png"));
        btnChart = new JButton(iconChart);
        btnChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChartDialog().createChart();
            }
        });

        iconExit = new ImageIcon(getClass().getResource("/icons/logout.png"));
        btnExit = new JButton(iconExit);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        toolBar.add(btnTrackerStart);
        toolBar.add(btnTrackerStop);
        toolBar.addSeparator();
        toolBar.add(btnExport);
        toolBar.add(btnChart);
        toolBar.addSeparator();
        toolBar.add(btnExit);

        return toolBar;
    }
}
