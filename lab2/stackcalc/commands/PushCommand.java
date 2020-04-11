package stackcalc.commands;

import stackcalc.ExecutionContext;

import java.util.HashMap;
import java.util.Stack;

final public class PushCommand extends Command {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        String[] arguments = parseArguments(notParsedArguments);
        if (arguments.length != 1) {
            throw new WrongNumberOfCommandArgumentsException("push", arguments.length);
        }
        HashMap<String, Double> parameters = executionContext.getParameters();
        Stack<Double> stack = executionContext.getStack();
        Double value = parameters.get(arguments[0]);
        if (value == null) {
            try {
                value = Double.parseDouble(arguments[0]);
            } catch (NumberFormatException e) {
                throw new UndefinedParameterException("push", arguments[0]);
            }
        }
        stack.push(value);
    }
}
