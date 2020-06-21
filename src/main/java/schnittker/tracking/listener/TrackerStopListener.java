package schnittker.tracking.listener;

import schnittker.tracking.TrackingApplication;
import schnittker.tracking.services.SchedulerService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrackerStopListener implements ActionListener {
    private final SchedulerService schedulerService;
    private final ResourceBundle translations;

    public TrackerStopListener() {
        schedulerService = new SchedulerService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer projectsId = TrackingApplication.projectList.getSelectedProjectPosition();
        if(Objects.nonNull(projectsId)) {
            schedulerService.stop(projectsId.intValue());
            TrackingApplication.statusBar.setMessage(translations.getString("stop_tracking"));
            TrackingApplication.statusBar.setProgressBar(0,0,100);
            TrackingApplication.tableView.getRefreshedDefaults();
        } else {
            TrackingApplication.statusBar.setMessage(translations.getString("no_selected_project"));
        }
    }
}
