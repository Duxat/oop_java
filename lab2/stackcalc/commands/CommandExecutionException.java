package stackcalc.commands;

import stackcalc.StackCalculatorException;

public class CommandExecutionException extends StackCalculatorException {
    public CommandExecutionException(String message) {
        super(message);
    }
}
