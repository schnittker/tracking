package main.java;

import main.java.components.MenuBar;
import main.java.components.ProjectList;
import main.java.components.StatusBar;
import main.java.components.TableView;
import main.java.components.ToolBar;
import main.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author markus schnitter
 */
public class TrackingApplication {
    private static final Dimension MAIN_SIZE = new Dimension(800, 500);

    public static MenuBar menuBar;
    public static ToolBar toolBar;
    public static ProjectList projectList;
    public static TableView tableView;
    public static StatusBar statusBar;

    private JFrame frame;
    private JSplitPane mainSplitPane;
    private JPanel mainPanel;
    private JScrollPane projectsScrollPane;
    private JScrollPane tableScrollPane;

    public TrackingApplication() {
        menuBar = new MenuBar();
        toolBar = new ToolBar();
        projectList = new ProjectList();
        tableView = new TableView();
        statusBar =  new StatusBar();
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrackingApplication().createGui();
            }
        });
    }

    private void createGui() {
        frame = new JFrame("Time Tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar.createMenu());

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(MAIN_SIZE);
        mainPanel.setLayout(new BorderLayout(10, 10));

        projectsScrollPane = new JScrollPane(projectList.createTree());
        tableScrollPane = new JScrollPane(tableView.createTable());

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(projectsScrollPane);
        mainSplitPane.setRightComponent(tableScrollPane);

        mainSplitPane.getLeftComponent().setMinimumSize(new Dimension(200, 500));

        mainPanel.add(toolBar.createToolbar(),BorderLayout.NORTH);
        mainPanel.add(mainSplitPane);
        mainPanel.add(statusBar.createStatusBar(), BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        FrameUtils.centerFrame(frame);
    }
}
