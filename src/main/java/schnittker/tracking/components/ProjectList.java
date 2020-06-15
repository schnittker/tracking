package schnittker.tracking.components;

import schnittker.tracking.TrackingApplication;
import schnittker.tracking.services.ProjectService;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class ProjectList {
    private final ProjectService projectService;
    private final ResourceBundle translations;

    private JTree projectTree;
    private DefaultMutableTreeNode root;

    private Integer selectedProject;

    public ProjectList() {
        projectService = new ProjectService();
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public JTree createTree() {
        List<String> projectList = projectService.getForProjectTree();
        root = processHierarchy(projectList);
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

    public void addNewProject() {
        String projectName = JOptionPane.showInputDialog(null,translations.getString("project_name"),
                translations.getString("new_project"), JOptionPane.PLAIN_MESSAGE);

        projectService.addNewProject(projectName);
    }

    public void removeProject() {
        if(Objects.isNull(selectedProject)) {
            return;
        }

        int reply = JOptionPane.showConfirmDialog(null, translations.getString("delete_sure"),
                translations.getString("delete_project"), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            projectService.removeProjectById(selectedProject);
        }
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
