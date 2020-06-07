package gui;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private final MenuBar menuBar;

    public Gui() {
        menuBar = new MenuBar();
    }

    public void createGui() {
        JFrame jFrame = new JFrame("Time Tracking");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 400);
        jFrame.getContentPane().add(BorderLayout.NORTH, menuBar.createMenu());
        jFrame.setVisible(true);
    }
}
