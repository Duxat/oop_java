package stackcalc.commands;

public class WrongNumberOfCommandArgumentsException extends CommandExecutionException {
    public WrongNumberOfCommandArgumentsException(String commandName, int argumentsNumber) {
        super("Wrong arguments number of command " + commandName + ": " + argumentsNumber);
    }

    public WrongNumberOfCommandArgumentsException(String commandName) {
        super("Wrong arguments number of command " + commandName);
    }
}
