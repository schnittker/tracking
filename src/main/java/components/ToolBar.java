package main.java.components;

import main.java.TrackingApplication;
import main.java.services.SchedulerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToolBar {
    private final SchedulerService schedulerService;
    private final ResourceBundle translations;

    private JToolBar toolBar;
    private JButton btnTrackerStart;
    private Icon iconTrackerStart;
    private JButton btnTrackerStop;
    private Icon iconTrackerStop;
    private JButton btnExit;
    private Icon iconExit;

    public ToolBar() {
        schedulerService = new SchedulerService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JToolBar createToolbar() {
        toolBar = new JToolBar();

        iconTrackerStart = new ImageIcon("");
        btnTrackerStart = new JButton("start");
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

        iconTrackerStop = new ImageIcon("");
        btnTrackerStop = new JButton("stop");
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

        iconExit = new ImageIcon("");
        btnExit = new JButton("exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        toolBar.addSeparator();
        toolBar.add(btnTrackerStart);
        toolBar.add(btnTrackerStop);
        toolBar.addSeparator();
        toolBar.add(btnExit);

        return toolBar;
    }
}
