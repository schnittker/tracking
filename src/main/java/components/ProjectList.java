package main.java.components;

import main.java.TrackingApplication;
import main.java.endpoints.ProjectsEndpoint;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ProjectList {
    private final ProjectsEndpoint projectsEndpoint;
    private final ResourceBundle translations;

    private JTree projectTree;

    private Integer selectedProject;

    public ProjectList() {
        projectsEndpoint = new ProjectsEndpoint();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JTree createTree() {
        List<String> projectList = projectsEndpoint.getForProjectTree();
        DefaultMutableTreeNode root = processHierarchy(projectList);
        projectTree = new JTree(root);

        projectTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                TreePath treePath = projectTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                if (treePath != null) {
                    selectedProject = projectTree.getRowForPath(treePath);
                    TrackingApplication.tableView.getTableByProjectsId(selectedProject);
                } else {
                    TrackingApplication.tableView.getRefreshedDefaults();
                }
            }
        });

        return projectTree;
    }

    public Integer getSelectedProject() {
        return selectedProject;
    }

    private DefaultMutableTreeNode processHierarchy(List<String> projectList) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(translations.getString("projects"));
        DefaultMutableTreeNode child;

        for (String projectName : projectList) {
            child = new DefaultMutableTreeNode(projectName);
            node.add(child);
        }

        return (node);
    }
}
