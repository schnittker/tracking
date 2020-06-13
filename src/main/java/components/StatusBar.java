package main.java.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * @author markus schnittker
 */
public class StatusBar {
    private static JLabel lblStatus;
    private static JProgressBar progressBar;

    private JPanel messagePanel;
    private JPanel mainPanel;

    public JPanel createStatusBar() {
        mainPanel = new JPanel();
        messagePanel = new JPanel();
        lblStatus = new JLabel();
        progressBar = new JProgressBar();

        messagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
        messagePanel.setPreferredSize(new Dimension(400, 30));
        messagePanel.add(lblStatus);
        lblStatus.setHorizontalTextPosition(SwingConstants.LEFT);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.WEST, messagePanel);
        mainPanel.add(BorderLayout.EAST, progressBar);

        return mainPanel;
    }

    public void setMessage(String msg) {
        lblStatus.setText(msg);
    }

    public void setProgressBar(int value, int min, int max) {
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setValue(value);
    }
}
