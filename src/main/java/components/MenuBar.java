package main.java.components;

import main.java.listener.AddProjectListener;
import main.java.listener.ExitListener;
import main.java.listener.InfoListener;
import main.java.listener.RemoveProjectListener;

import javax.swing.*;
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

        fileMenu.add(exitItem);
        projectMenu.add(addProjectItem);
        projectMenu.add(removeProjectItem);
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);
        menuBar.add(helpMenu);

        addProjectItem.addActionListener(new AddProjectListener());
        removeProjectItem.addActionListener(new RemoveProjectListener());
        exitItem.addActionListener(new ExitListener());
        infoItem.addActionListener(new InfoListener());

        return menuBar;
    }
}
