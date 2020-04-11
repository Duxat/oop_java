package stackcalc.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import stackcalc.commands.*;
import stackcalc.commands.arithmetics.*;
import stackcalc.commands.factory.DefaultCommandFactory;
import stackcalc.commands.factory.WrongCommandNameException;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(Theories.class)
public class FactoryCommandTest {
    @DataPoints
    static public Object[][] commands = new Object[][]{
            {"+", AddCommand.class},
            {"-", SubCommand.class},
            {"*", MulCommand.class},
            {"/", DivCommand.class},
            {"SQRT", SqrtCommand.class},
            {"POP", PopCommand.class},
            {"PUSH", PushCommand.class},
            {"PRINT", PrintCommand.class},
            {"#", CommentCommand.class},
            {"DEFINE", DefineCommand.class}
    };

    @Theory
    @Test
    public void commandException(Object... testData) {
        Command actualCommand = DefaultCommandFactory.getCommand((String) testData[0]);
        Assert.assertThat(actualCommand, instanceOf((Class<?>) testData[1]));
    }

    @Test
    public void commandFactoryException() {
        Assertions.assertThrows(WrongCommandNameException.class,
                () -> DefaultCommandFactory.getCommand("adfad"));
    }
}
