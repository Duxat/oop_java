package stackcalc.tests;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stackcalc.ExecutionContext;
import stackcalc.commands.*;
import stackcalc.commands.arithmetics.AddCommand;
import stackcalc.commands.factory.DefaultCommandFactory;

@RunWith(Theories.class)
public class CommandsExceptionsTest {
    private ExecutionContext context = new ExecutionContext();

    @DataPoints
    static public Object[][] commands = new Object[][]{
            {"+", "3", WrongNumberOfCommandArgumentsException.class},
            {"+", "", EmptyStackOfCalculatorException.class},
            {"-", "3", WrongNumberOfCommandArgumentsException.class},
            {"-", "", EmptyStackOfCalculatorException.class},
            {"*", "3", WrongNumberOfCommandArgumentsException.class},
            {"*", "", EmptyStackOfCalculatorException.class},
            {"/", "3", WrongNumberOfCommandArgumentsException.class},
            {"/", "", EmptyStackOfCalculatorException.class},
            {"SQRT", "3", WrongNumberOfCommandArgumentsException.class},
            {"SQRT", "", EmptyStackOfCalculatorException.class},
            {"POP", "sdfs", WrongNumberOfCommandArgumentsException.class},
            {"POP", "", EmptyStackOfCalculatorException.class},
            {"PUSH", "sdf sdf", WrongNumberOfCommandArgumentsException.class},
            {"PUSH", "a", UndefinedParameterException.class},
            {"PRINT", "sdf", WrongNumberOfCommandArgumentsException.class},
            {"DEFINE", "2 2 2", WrongNumberOfCommandArgumentsException.class},
            {"DEFINE", "a d", WrongFormatOfCommandArgument.class}
    };

    @Theory
    @Test
    public void commandException(Object... testData) {
        Command command = DefaultCommandFactory.getCommand((String) testData[0]);
        String arg = (String) testData[1];
        Class<CommandExecutionException> e = (Class<CommandExecutionException>) testData[2];
        Assertions.assertThrows(e, () -> {command.execute(context, arg);});
    }
}