package main.java.listener;

import main.java.TrackingApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProjectListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        TrackingApplication.projectList.addNewProject();
    }
}
