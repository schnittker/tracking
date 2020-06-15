package schnittker.tracking.listener;

import schnittker.tracking.components.dialogs.ChartDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartDialogListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ChartDialog().createChart();
    }
}
