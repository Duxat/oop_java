package stackcalc.commands.factory;

import stackcalc.StackCalculatorException;

public class WrongCommandNameException extends StackCalculatorException {
    public WrongCommandNameException(String commandName) {
        super("No such command name in configuration file: " + commandName);
    }
}
