package stackcalc.commands.arithmetics;

final public class MulCommand extends BinaryArithmeticOperationCommand {
    @Override
    protected Double operation(Double[] operands) {
        return operands[0] * operands[1];
    }

    @Override
    protected String getCommandName() {
        return "*";
    }
}
