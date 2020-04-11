package stackcalc.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stackcalc.RequestCommand;
import stackcalc.StackCalculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class StackCalculatorTest {
    private ArrayList<RequestCommand> requestCommands = new ArrayList<>();
    private String expectedOutString = "4.0\r\n8.9\r\n";
    private PrintStream oldOut = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private String[][] requestCommandsString = {
            {"DEFINE", "var 8.9"},
            {"PUSH", "7"},
            {"DEFINE", "a 9"},
            {"PUSH", "a"},
            {"+", ""},
            {"SQRT", ""},
            {"PRINT", ""},
            {"PUSH", "var"},
            {"PRINT", ""}
    };

    @Before
    public void setRequestCommands() {
        for(String[] pair: requestCommandsString) {
            requestCommands.add(new RequestCommand(pair[0], pair[1]));
        }
    }

    @Before
    public void setOutStream() {
        out.reset();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void stackCalculatorWorkWithNoParser() {
        StackCalculator stackCalculator = new StackCalculator();
        stackCalculator.calculate(requestCommands);
        Assert.assertEquals(expectedOutString, out.toString());
    }

    @After
    public void clearContext() {
        System.setOut(oldOut);
        requestCommands.clear();
    }
}
