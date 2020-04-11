package stackcalc.parser;

import org.apache.log4j.Logger;
import stackcalc.RequestCommand;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class StackCalculatorParser {
    private static final Logger log = Logger.getLogger(StackCalculatorParser.class);

    public static ArrayList<RequestCommand> parseFile(InputStream file) {
        Scanner scanner = new Scanner(file);
        log.debug("Файл загружен в сканнер");
        ArrayList<RequestCommand> commands = new ArrayList<>();
        while (scanner.hasNext()) {
            String commandName = scanner.next();
            String notParsedArguments = scanner.nextLine();
            RequestCommand command = new RequestCommand(commandName, notParsedArguments);
            commands.add(command);
            log.debug("Сформирована новая RequestCommand: " + commandName + " " + notParsedArguments);
        }
        return commands;
    }
}
