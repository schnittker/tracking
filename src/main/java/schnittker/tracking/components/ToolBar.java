package schnittker.tracking.components;

import schnittker.tracking.listener.ChartDialogListener;
import schnittker.tracking.listener.DailyListener;
import schnittker.tracking.listener.ExitListener;
import schnittker.tracking.listener.ExportDialogListener;
import schnittker.tracking.listener.TrackerStartListener;
import schnittker.tracking.listener.TrackerStopListener;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ToolBar {
    private final ResourceBundle translations;

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

    public ToolBar() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JToolBar createToolbar() {
        toolBar = new JToolBar();

        iconTrackerStart = new ImageIcon(getClass().getResource("/icons/play.png"));
        btnTrackerStart = new JButton(iconTrackerStart);
        btnTrackerStart.addActionListener(new TrackerStartListener());
        btnTrackerStart.setToolTipText(translations.getString("tool_bar.start"));

        iconTrackerStop = new ImageIcon(getClass().getResource("/icons/stop.png"));
        btnTrackerStop = new JButton(iconTrackerStop);
        btnTrackerStop.addActionListener(new TrackerStopListener());
        btnTrackerStop.setToolTipText(translations.getString("tool_bar.stop"));

        iconExport = new ImageIcon(getClass().getResource("/icons/export.png"));
        btnExport = new JButton(iconExport);
        btnExport.addActionListener(new ExportDialogListener());
        btnExport.setToolTipText(translations.getString("tool_bar.export"));

        iconChart = new ImageIcon(getClass().getResource("/icons/chart.png"));
        btnChart = new JButton(iconChart);
        btnChart.addActionListener(new ChartDialogListener());
        btnChart.setToolTipText(translations.getString("tool_bar.chart"));

        iconDaily = new ImageIcon(getClass().getResource("/icons/daily.png"));
        btnDaily = new JButton(iconDaily);
        btnDaily.addActionListener(new DailyListener());
        btnDaily.setToolTipText(translations.getString("tool_bar.daily"));

        iconExit = new ImageIcon(getClass().getResource("/icons/logout.png"));
        btnExit = new JButton(iconExit);
        btnExit.addActionListener(new ExitListener());
        btnExit.setToolTipText(translations.getString("tool_bar.exit"));

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
