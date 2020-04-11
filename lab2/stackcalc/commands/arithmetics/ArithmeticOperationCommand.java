package stackcalc.commands.arithmetics;

import stackcalc.commands.Command;

public abstract class ArithmeticOperationCommand extends Command {
    abstract protected Double operation(Double[] operands);

    abstract protected String getCommandName();
}
