package stackcalc.commands;

import stackcalc.ExecutionContext;

import java.util.EmptyStackException;
import java.util.Stack;

final public class PrintCommand extends Command {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        if (!zeroArgumentsNumberCheck(notParsedArguments)) {
            throw new WrongNumberOfCommandArgumentsException("print");
        }
        Stack<Double> stack = executionContext.getStack();
        try {
            System.out.println(stack.peek());
        } catch (EmptyStackException e) {
            throw new EmptyStackOfCalculatorException("print");
        }
    }
}
