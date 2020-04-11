package stackcalc.commands.factory;

import stackcalc.StackCalculatorException;

public class CommandInstantiationException extends StackCalculatorException {
    public CommandInstantiationException(String commandName) {
        super("Command object with such name cant be created: " + commandName);
    }
}
