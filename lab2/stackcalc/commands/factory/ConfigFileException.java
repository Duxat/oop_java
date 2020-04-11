package stackcalc.commands.factory;

import stackcalc.StackCalculatorException;

public class ConfigFileException extends StackCalculatorException {
    public ConfigFileException() {
        super("Exception during loading configuration file(commands.txt)");
    }
}
