package stackcalc.commands.arithmetics;

import stackcalc.ExecutionContext;
import stackcalc.commands.EmptyStackOfCalculatorException;
import stackcalc.commands.WrongNumberOfCommandArgumentsException;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class BinaryArithmeticOperationCommand extends ArithmeticOperationCommand {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        if (!zeroArgumentsNumberCheck(notParsedArguments)) {
            throw new WrongNumberOfCommandArgumentsException(getCommandName());
        }
        Stack<Double> stack = executionContext.getStack();
        Double operand1, operand2;
        try {
            operand2 = stack.pop();
            operand1 = stack.pop();
        } catch (EmptyStackException e) {
            throw new EmptyStackOfCalculatorException(getCommandName());
        }
        Double result = operation(new Double[]{operand1, operand2});
        stack.push(result);
    }
}
