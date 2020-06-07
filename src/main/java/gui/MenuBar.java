package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuBar {
    private final ResourceBundle translation;

    public MenuBar() {
        translation = ResourceBundle.getBundle("i18n.Gui", Locale.getDefault());
    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(translation.getString("menu_bar.menu.file"));
        JMenu helpMenu = new JMenu(translation.getString("menu_bar.menu.help"));
        JMenuItem exitItem = new JMenuItem(translation.getString("menu_bar.menu.file.exit"));

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
