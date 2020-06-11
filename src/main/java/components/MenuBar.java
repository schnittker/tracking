package main.java.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuBar {
    private final ResourceBundle translation;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem exitItem;

    public MenuBar() {
        translation = ResourceBundle.getBundle("i18n.Gui", Locale.getDefault());
    }

    public JMenuBar createMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu(translation.getString("menu_bar.menu.file"));
        helpMenu = new JMenu(translation.getString("menu_bar.menu.help"));
        exitItem = new JMenuItem(translation.getString("menu_bar.menu.file.exit"));

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        fileMenu.add(exitItem);

        //exitItem.setMnemonic(KeyEvent.VK_Q);

        exitItem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return menuBar;
    }
}
