package schnittker.tracking.listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExitListener implements ActionListener {
    private final ResourceBundle translations;

    public ExitListener() {
        translations = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int dialogResult = JOptionPane.showConfirmDialog (null, translations.getString("dialog.exit.message"),
                translations.getString("dialog.exit.headline"), JOptionPane.YES_NO_OPTION);

        if(dialogResult == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
}
