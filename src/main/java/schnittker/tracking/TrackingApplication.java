package schnittker.tracking;

import com.alee.laf.WebLookAndFeel;
import com.alee.skin.dark.WebDarkSkin;
import schnittker.tracking.api.Routes;
import schnittker.tracking.components.MenuBar;
import schnittker.tracking.components.ProjectList;
import schnittker.tracking.components.StatusBar;
import schnittker.tracking.components.TableView;
import schnittker.tracking.components.ToolBar;
import schnittker.tracking.utils.FrameUtils;

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
                setLookAndFeel();
                new TrackingApplication().createGui();
            }
        });

        // Bind the api routes
        new Routes().bind();
    }

    private static void setLookAndFeel() {
        try {
            WebLookAndFeel.install (WebDarkSkin.class);
            UIManager.setLookAndFeel (new WebLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
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
