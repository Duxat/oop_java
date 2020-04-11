package stackcalc;

public class RequestCommand {
    final private String commandName;
    final private String notParsedArguments;

    public RequestCommand(String commandName, String notParsedArguments) {
        this.commandName = commandName;
        this.notParsedArguments = notParsedArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandNotParsedArguments() {
        return notParsedArguments;
    }
}
