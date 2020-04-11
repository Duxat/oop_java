package stackcalc.commands.arithmetics;

final public class SqrtCommand extends UnaryArithmeticOperationCommand {
    @Override
    protected Double operation(Double[] operands) {
        return Math.sqrt(operands[0]);
    }

    @Override
    protected String getCommandName() {
        return "sqrt";
    }
}
