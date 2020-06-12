package main.java.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * @author markus schnittker
 */
public class StatusBar {
    private static JLabel lblStatus;

    private JPanel statusPanel;

    public JPanel createStatusBar() {
        statusPanel = new JPanel();
        lblStatus = new JLabel();

        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setPreferredSize(new Dimension(900, 30));
        lblStatus.setHorizontalTextPosition(SwingConstants.LEFT);
        statusPanel.add(lblStatus);

        return statusPanel;
    }

    public void setMessage(String msg) {
        lblStatus.setText(msg);
    }
}
