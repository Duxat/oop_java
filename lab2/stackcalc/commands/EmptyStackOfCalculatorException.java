package stackcalc.commands;

public class EmptyStackOfCalculatorException extends CommandExecutionException {
    public EmptyStackOfCalculatorException(String commandName) {
        super("Encountered empty stack while executing command " + commandName);
    }
}
