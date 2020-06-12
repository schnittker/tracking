package main.java.components;

import main.java.TrackingApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class MenuBar {
    private final ResourceBundle translation;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu projectMenu;
    private JMenu helpMenu;
    private JMenuItem exitItem;
    private JMenuItem addProjectItem;
    private JMenuItem removeProjectItem;
    private JMenuItem infoItem;

    public MenuBar() {
        translation = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JMenuBar createMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu(translation.getString("menu_bar.menu.file"));
        projectMenu = new JMenu(translation.getString("menu_bar.menu.project"));
        helpMenu = new JMenu(translation.getString("menu_bar.menu.help"));
        exitItem = new JMenuItem(translation.getString("menu_bar.menu.file.exit"));
        addProjectItem = new JMenuItem(translation.getString("menu_bar.menu.project.add_project"));
        removeProjectItem = new JMenuItem(translation.getString("menu_bar.menu.project.remove_project"));
        infoItem = new JMenuItem("?");

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);
        menuBar.add(helpMenu);

        fileMenu.add(exitItem);

        projectMenu.add(addProjectItem);
        projectMenu.add(removeProjectItem);

        helpMenu.add(infoItem);

        //exitItem.setMnemonic(KeyEvent.VK_Q);

        addProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrackingApplication.projectList.addNewProject();
            }
        });

        removeProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrackingApplication.projectList.removeProject();
            }
        });

        exitItem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        infoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "https://github.com/schnittker", "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return menuBar;
    }
}
