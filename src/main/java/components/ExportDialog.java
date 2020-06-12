package main.java.components;

import main.java.TrackingApplication;
import main.java.services.SchedulerService;
import main.java.utils.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ExportDialog {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private final ResourceBundle translations;
    private final SchedulerService schedulerService;

    private JDialog mainFrame;
    private JComboBox comboBoxMonth;
    private JButton btnOk;

    private Integer selectedMonth;

    public ExportDialog() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
        schedulerService = new SchedulerService();
    }

    public void createDialog() {
        mainFrame = new JDialog();
        mainFrame.setTitle(translations.getString("dialog.select_month.headline"));

        Integer[] monthArr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        Integer curMonth = TimeUtils.getCurrentMonth().getValue();
        comboBoxMonth = new JComboBox(monthArr);
        comboBoxMonth.setMaximumSize(new Dimension(60,30));
        comboBoxMonth.setSelectedItem(curMonth);
        comboBoxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonth = (Integer)comboBoxMonth.getSelectedItem();
            }
        });

        btnOk = new JButton(translations.getString("dialog.select_month.btn"));
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonth = (Integer) comboBoxMonth.getSelectedItem();
                if(Objects.nonNull(selectedMonth)) {
                    schedulerService.export(selectedMonth.intValue());
                    TrackingApplication.statusBar.setMessage(translations.getString("export_data"));
                    mainFrame.setVisible(false);
                }
            }
        });

        mainFrame.setLayout(new FlowLayout());
        mainFrame.add(new JLabel(translations.getString("dialog.select_month.description")));
        mainFrame.add(comboBoxMonth);
        mainFrame.add(btnOk);

        mainFrame.setVisible(true);
        mainFrame.pack();

        int x = (int) ((SCREEN_SIZE.getWidth() - mainFrame.getWidth()) / 2);
        int y = (int) ((SCREEN_SIZE.getHeight() - mainFrame.getHeight()) / 2);
        mainFrame.setLocation(x, y);
    }
}
