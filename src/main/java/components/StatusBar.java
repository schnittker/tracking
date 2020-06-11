package main.java.components;

import javax.swing.*;

/**
 * @author markus schnittker
 */
public class StatusBar {
    private JPanel statusPanel;
    private static JLabel lblStatus;

    public JPanel createStatusBar() {
        statusPanel = new JPanel();
        lblStatus = new JLabel();

        statusPanel.add(lblStatus);
        return statusPanel;
    }

    public void setMessage(String msg) {
        lblStatus.setText(msg);
    }
}
