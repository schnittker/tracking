import services.ExceptionService;
import services.SchedulerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author markus schnittker
 */
public class Console {
    private final ResourceBundle translations;
    private final SchedulerService schedulerService;
    private final ExceptionService exceptionService;

    public Console() {
        translations = ResourceBundle.getBundle("i18n.Console", Locale.getDefault());
        schedulerService = new SchedulerService();
        exceptionService = new ExceptionService();
    }

    public void createConsole() {

        System.out.println("tracking 0.1 - type help to display all commands");

        while(true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String userInput = bufferedReader.readLine();
                if (userInput.equals("exit")) {
                    System.exit(0);
                }

                handleUserInput(userInput);
            }catch (IOException e) {
                exceptionService.logging(this.getClass().getName(), e.getMessage());
            }
        }
    }

    private void handleUserInput(String userInput) {
        if(userInput.isEmpty()) {
            return;
        }

        List<String> argumentsList = Arrays.asList(userInput.split("\\s"));

        String projectName = "";
        int period = 1;
        if(argumentsList.size() == 2) {
            projectName = argumentsList.get(1);
        } else if(argumentsList.size() == 3) {
            projectName = argumentsList.get(1);
            period = Integer.parseInt(argumentsList.get(2));
        }

        switch (argumentsList.get(0)) {
            case "start":
                schedulerService.start(projectName);
                break;
            case "stop":
                schedulerService.stop(projectName);
                break;
            case "export":
                schedulerService.export(projectName, period);
            default:
                System.out.println(translations.getString("invalid_user_input"));
                break;
        }
    }
}
