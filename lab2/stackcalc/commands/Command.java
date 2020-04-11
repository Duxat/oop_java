package stackcalc.commands;

import stackcalc.ExecutionContext;

public abstract class Command {
    public abstract void execute(ExecutionContext executionContext, String notParsedArguments);

    protected String[] parseArguments(String notParsedArguments) {
        if (notParsedArguments == null || notParsedArguments.length() == 0 || notParsedArguments.matches("\\s+")) {
            return new String[0];
        }
        return (notParsedArguments.trim()).split("\\s+", 0);
    }

    protected boolean zeroArgumentsNumberCheck(String notParsedArguments) {
        return parseArguments(notParsedArguments).length == 0;
    }
}
