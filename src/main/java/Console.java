import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public class Console {
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    private static final Logger LOGGER = Logger.getLogger(Console.class.getName());

    public void createConsole() {
        Scheduler scheduler = new Scheduler();

        System.out.println("tracking 0.1 - type help to display all commands");

        while(true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String userInput = bufferedReader.readLine();
                if (userInput.equals("exit")) {
                    System.exit(0);
                }

                handleUserInput(scheduler, userInput);
            }catch (IOException e) {
                LOGGER.severe("Exception while trying parse user input. " + e.getMessage());
            }
        }
    }

    private static void handleUserInput(Scheduler scheduler, String userInput) {
        if(userInput.isEmpty()) {
            return;
        }

        List<String> argumentsList = Arrays.asList(userInput.split("\\s"));

        String projectName = "";
        String period = "";
        if(argumentsList.size() == 2) {
            projectName = argumentsList.get(1);
        } else if(argumentsList.size() == 3) {
            projectName = argumentsList.get(1);
            period = argumentsList.get(2);
        }

        switch (argumentsList.get(0)) {
            case "start":
                scheduler.start(projectName);
                break;
            case "stop":
                scheduler.stop(projectName);
                break;
            case "export":
                scheduler.export(projectName, period);
            default:
                System.out.println(MESSAGES.getString("invalid_user_input"));
                break;
        }
    }
}
