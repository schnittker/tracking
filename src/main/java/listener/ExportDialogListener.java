package main.java.listener;

import main.java.components.dialogs.ExportDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportDialogListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new ExportDialog().createDialog();
    }
}
