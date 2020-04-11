package stackcalc.commands.factory;

import stackcalc.commands.Command;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class DefaultCommandFactory {
    final private static Properties commandProperties = new Properties();
    final private static HashMap<String, Command> cashedCommands = new HashMap<>();
    final private static String configFileName = "commands.txt";

    static {
        try {
            commandProperties.load(DefaultCommandFactory.class.getResourceAsStream(configFileName));
        } catch (IOException e) {
            throw new ConfigFileException();
        }
    }

    public static Command getCommand(String commandName) {
        Command command = cashedCommands.get(commandName);
        if (command == null) {
            String className = (String) commandProperties.get(commandName);
            if (className == null) {
                throw new WrongCommandNameException(commandName);
            }
            try {
                Class<?> commandClass = Class.forName(className);
                command = (Command) commandClass.getConstructor().newInstance();
                cashedCommands.put(commandName, command);
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException |
                    NoSuchMethodException | InvocationTargetException e) {
                throw new CommandInstantiationException(commandName);
            }
        }
        return command;
    }
}
