package stackcalc.commands;

import stackcalc.ExecutionContext;

import java.util.EmptyStackException;
import java.util.Stack;

final public class PopCommand extends Command {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        if (!zeroArgumentsNumberCheck(notParsedArguments)) {
            throw new WrongNumberOfCommandArgumentsException("pop");
        }
        Stack<Double> stack = executionContext.getStack();
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyStackOfCalculatorException("pop");
        }
    }
}
