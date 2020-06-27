package schnittker.tracking.listener;

import schnittker.tracking.utils.FrameUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPageListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FrameUtils.openURL("https://github.com/schnittker/tracking");
    }
}
