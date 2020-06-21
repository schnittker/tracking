package schnittker.tracking.listener;

import schnittker.tracking.TrackingApplication;
import schnittker.tracking.services.SchedulerService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrackerStartListener implements ActionListener {
    private final SchedulerService schedulerService;
    private final ResourceBundle translations;

    public TrackerStartListener() {
        schedulerService = new SchedulerService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String projectName = TrackingApplication.projectList.getSelectedProjectName();
        if(Objects.nonNull(projectName)) {
            schedulerService.start(projectName);
            TrackingApplication.statusBar.setMessage(translations.getString("start_tracking"));
        } else {
            TrackingApplication.statusBar.setMessage(translations.getString("no_selected_project"));
        }
    }
}
