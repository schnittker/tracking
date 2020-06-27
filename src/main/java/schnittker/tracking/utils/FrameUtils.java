package schnittker.tracking.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @author markus schnittker
 */
public class FrameUtils {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static void centerFrame(JFrame jFrame) {
        int x = (int) ((SCREEN_SIZE.getWidth() - jFrame.getWidth()) / 2);
        int y = (int) ((SCREEN_SIZE.getHeight() - jFrame.getHeight()) / 2);
        jFrame.setLocation(x, y);
    }
}
