package stackcalc.commands;


public class WrongFormatOfCommandArgument extends CommandExecutionException {
    public WrongFormatOfCommandArgument(String commandName, String argument) {
        super("Wrong argument format of command " + commandName + ": " + argument);
    }
}
