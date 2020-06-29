package schnittker.tracking.components;

import schnittker.tracking.listener.AddProjectListener;
import schnittker.tracking.listener.DailyListener;
import schnittker.tracking.listener.ExitListener;
import schnittker.tracking.listener.ExportDialogListener;
import schnittker.tracking.listener.InfoListener;
import schnittker.tracking.listener.RemoveProjectListener;
import schnittker.tracking.listener.ShowPageListener;
import schnittker.tracking.listener.TrackerStartListener;
import schnittker.tracking.listener.TrackerStopListener;

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
    private JMenu schedulerMenu;
    private JMenu helpMenu;
    private JMenuItem exitItem;
    private JMenuItem addProjectItem;
    private JMenuItem removeProjectItem;
    private JMenuItem startTrackingItem;
    private JMenuItem stopTrackingItem;
    private JMenuItem dailyItem;
    private JMenuItem exportItem;
    private JMenuItem showPageItem;
    private JMenuItem infoItem;

    public MenuBar() {
        translation = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JMenuBar createMenu() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu(translation.getString("menu_bar.menu.file"));
        projectMenu = new JMenu(translation.getString("menu_bar.menu.project"));
        schedulerMenu = new JMenu(translation.getString("menu_bar.menu.scheduler"));
        helpMenu = new JMenu(translation.getString("menu_bar.menu.help"));

        exitItem = new JMenuItem(translation.getString("menu_bar.menu.file.exit"));
        addProjectItem = new JMenuItem(translation.getString("menu_bar.menu.project.add_project"));
        removeProjectItem = new JMenuItem(translation.getString("menu_bar.menu.project.remove_project"));
        startTrackingItem = new JMenuItem(translation.getString("menu_bar.menu.scheduler.start"));
        stopTrackingItem = new JMenuItem(translation.getString("menu_bar.menu.scheduler.stop"));
        dailyItem = new JMenuItem(translation.getString("menu_bar.menu.scheduler.daily"));
        exportItem = new JMenuItem(translation.getString("menu_bar.menu.scheduler.export"));
        showPageItem = new JMenuItem(translation.getString("menu_bar.menu.help.show_page"));
        infoItem = new JMenuItem("?");

        fileMenu.add(exitItem);
        projectMenu.add(addProjectItem);
        projectMenu.add(removeProjectItem);
        schedulerMenu.add(startTrackingItem);
        schedulerMenu.add(stopTrackingItem);
        schedulerMenu.addSeparator();
        schedulerMenu.add(dailyItem);
        schedulerMenu.addSeparator();
        schedulerMenu.add(exportItem);
        helpMenu.add(showPageItem);
        helpMenu.add(infoItem);

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);
        menuBar.add(schedulerMenu);
        menuBar.add(helpMenu);

        addProjectItem.addActionListener(new AddProjectListener());
        removeProjectItem.addActionListener(new RemoveProjectListener());
        startTrackingItem.addActionListener(new TrackerStartListener());
        stopTrackingItem.addActionListener(new TrackerStopListener());
        dailyItem.addActionListener(new DailyListener());
        exportItem.addActionListener(new ExportDialogListener());
        exitItem.addActionListener(new ExitListener());
        showPageItem.addActionListener(new ShowPageListener());
        infoItem.addActionListener(new InfoListener());

        return menuBar;
    }
}
