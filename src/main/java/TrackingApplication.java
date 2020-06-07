import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author markus schnitter
 */
public class TrackingApplication {
    public static void main(String... args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        options.addOption("g", "gui", false, "Starts the application with a gui");

        try {
            CommandLine cmd = parser.parse( options, args);
            if(cmd.hasOption("gui")) {
                new Gui().createGui();
            } else {
                new Console().createConsole();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
