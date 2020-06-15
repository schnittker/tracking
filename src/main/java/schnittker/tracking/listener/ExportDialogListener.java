package schnittker.tracking.listener;

import schnittker.tracking.components.dialogs.ExportDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportDialogListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ExportDialog().createDialog();
    }
}
