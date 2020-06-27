package schnittker.tracking.components;

import schnittker.tracking.listener.ChartDialogListener;
import schnittker.tracking.listener.DailyListener;
import schnittker.tracking.listener.ExitListener;
import schnittker.tracking.listener.ExportDialogListener;
import schnittker.tracking.listener.TrackerStartListener;
import schnittker.tracking.listener.TrackerStopListener;

import javax.swing.*;

/**
 * @author markus schnittker
 */
public class ToolBar {
    private JToolBar toolBar;
    private JButton btnTrackerStart;
    private ImageIcon iconTrackerStart;
    private JButton btnTrackerStop;
    private ImageIcon iconTrackerStop;
    private JButton btnExport;
    private ImageIcon iconExport;
    private JButton btnChart;
    private ImageIcon iconChart;
    private JButton btnDaily;
    private ImageIcon iconDaily;
    private JButton btnExit;
    private ImageIcon iconExit;

    public JToolBar createToolbar() {
        toolBar = new JToolBar();

        iconTrackerStart = new ImageIcon(getClass().getResource("/icons/play.png"));
        btnTrackerStart = new JButton(iconTrackerStart);
        btnTrackerStart.addActionListener(new TrackerStartListener());

        iconTrackerStop = new ImageIcon(getClass().getResource("/icons/stop.png"));
        btnTrackerStop = new JButton(iconTrackerStop);
        btnTrackerStop.addActionListener(new TrackerStopListener());

        iconExport = new ImageIcon(getClass().getResource("/icons/export.png"));
        btnExport = new JButton(iconExport);
        btnExport.addActionListener(new ExportDialogListener());

        iconChart = new ImageIcon(getClass().getResource("/icons/chart.png"));
        btnChart = new JButton(iconChart);
        btnChart.addActionListener(new ChartDialogListener());

        iconDaily = new ImageIcon(getClass().getResource("/icons/daily.png"));
        btnDaily = new JButton(iconDaily);
        btnDaily.addActionListener(new DailyListener());

        iconExit = new ImageIcon(getClass().getResource("/icons/logout.png"));
        btnExit = new JButton(iconExit);
        btnExit.addActionListener(new ExitListener());

        toolBar.add(btnTrackerStart);
        toolBar.add(btnTrackerStop);
        toolBar.addSeparator();
        toolBar.add(btnDaily);
        toolBar.addSeparator();
        toolBar.add(btnExport);
        toolBar.add(btnChart);
        toolBar.addSeparator();
        toolBar.add(btnExit);

        return toolBar;
    }
}
