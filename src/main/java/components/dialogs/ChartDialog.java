package main.java.components.dialogs;

import main.java.models.SchedulerModel;
import main.java.services.SchedulerService;
import main.java.utils.FrameUtils;
import main.java.utils.TimeUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ChartDialog {
    private final ResourceBundle translations;
    private final SchedulerService schedulerService;

    private ChartFrame frame;
    private DefaultPieDataset pieDataset;
    private JFreeChart chart;

    public ChartDialog() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerService = new SchedulerService();
    }

    public void createChart() {
        pieDataset = new DefaultPieDataset();

        int curMonth = TimeUtils.getCurrentMonth().getValue();
        List<SchedulerModel> schedulerModelList = schedulerService.getSchedulerModelList(curMonth);
        Map<String, Long> totalMinutesByProjectAsMap = TimeUtils.getTotalMinutesByProjectAsMap(schedulerModelList);
        for(Map.Entry<String, Long> project : totalMinutesByProjectAsMap.entrySet()) {
            String projectName = project.getKey();
            Long totalWorkingTimeInMinutes = project.getValue();

            pieDataset.setValue(projectName, totalWorkingTimeInMinutes);
        }

        chart = ChartFactory.createPieChart(translations.getString("project_chart.headline"), pieDataset, true,false, false);

        frame = new ChartFrame("", chart);
        frame.setSize(400, 400);
        frame.setVisible(true);

        FrameUtils.centerFrame(frame);
    }
}
