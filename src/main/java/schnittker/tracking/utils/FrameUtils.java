package schnittker.tracking.utils;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author markus schnittker
 */
public class FrameUtils {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String OS = System.getProperty("os.name");

    public static void centerFrame(JFrame jFrame) {
        int x = (int) ((SCREEN_SIZE.getWidth() - jFrame.getWidth()) / 2);
        int y = (int) ((SCREEN_SIZE.getHeight() - jFrame.getHeight()) / 2);
        jFrame.setLocation(x, y);
    }

    public static void openURL(String url)
    {
        if(!StringUtils.contains("https://", url)) {
            url = "https://" + url;
        }

        Runtime rt = Runtime.getRuntime();
        try {
            if (FrameUtils.isWindows()) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();
            } else if (FrameUtils.isMac()) {
                String[] cmd = {"open", url};
                rt.exec(cmd).waitFor();
            } else if (FrameUtils.isUnix()) {
                String[] cmd = {"xdg-open", url};
                rt.exec(cmd).waitFor();
            } else {
                try {
                    throw new IllegalStateException();
                } catch (IllegalStateException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWindows()
    {
        return OS.contains("win");
    }

    public static boolean isMac()
    {
        return OS.contains("mac");
    }

    public static boolean isUnix()
    {
        return OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;
    }
}
