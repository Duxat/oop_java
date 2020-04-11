package stackcalc.commands;

import stackcalc.ExecutionContext;

import java.util.HashMap;

final public class DefineCommand extends Command {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        String[] arguments = parseArguments(notParsedArguments);
        if (arguments.length != 2) {
            throw new WrongNumberOfCommandArgumentsException("define", arguments.length);
        }
        HashMap<String, Double> parameters = executionContext.getParameters();
        double value;
        String key = arguments[0];
        try {
            value = Double.parseDouble(arguments[1]);
        } catch (NumberFormatException e) {
            throw new WrongFormatOfCommandArgument("define", arguments[1]);
        }
        parameters.put(key, value);
    }
}
