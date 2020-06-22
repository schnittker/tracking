package schnittker.tracking.listener;

import schnittker.tracking.TrackingApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DailyListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        TrackingApplication.tableView.getTableForDaily();
    }
}
