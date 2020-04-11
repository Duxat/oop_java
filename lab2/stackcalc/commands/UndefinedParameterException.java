package stackcalc.commands;


public class UndefinedParameterException extends CommandExecutionException {
    public UndefinedParameterException(String commandName, String parameter) {
        super("While executing command " + commandName + "undefined parameter was used: " + parameter);
    }
}
