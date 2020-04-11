package stackcalc.commands.arithmetics;

import stackcalc.ExecutionContext;
import stackcalc.commands.EmptyStackOfCalculatorException;
import stackcalc.commands.WrongNumberOfCommandArgumentsException;

import java.util.Stack;

public abstract class UnaryArithmeticOperationCommand extends ArithmeticOperationCommand {
    @Override
    public void execute(ExecutionContext executionContext, String notParsedArguments) {
        if (!zeroArgumentsNumberCheck(notParsedArguments)) {
            throw new WrongNumberOfCommandArgumentsException(getCommandName());
        }
        Stack<Double> stack = executionContext.getStack();
        Double operand;
        if (!stack.empty()) {
            operand = stack.pop();
        } else {
            throw new EmptyStackOfCalculatorException(getCommandName());
        }
        Double result = operation(new Double[]{operand});
        stack.push(result);
    }
}
