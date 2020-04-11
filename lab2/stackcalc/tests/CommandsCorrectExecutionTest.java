package stackcalc.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import stackcalc.ExecutionContext;
import stackcalc.commands.*;
import stackcalc.commands.arithmetics.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

import static java.lang.Double.NaN;

@RunWith(Theories.class)
public class CommandsCorrectExecutionTest {
    private ExecutionContext actualContext = new ExecutionContext();
    private Stack<Double> expectedStack = new Stack<>();
    private HashMap<String, Double> expectedParameters = new HashMap<>();
    private static Double[] startStackCondition = new Double[] {2.4 , 5.6};
    private static String[] startKeys = new String[] {"b", "d"};
    private static Double[] startValues = new Double[] {2.3, -1.7};
    private PrintStream oldOut = System.out;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void fillStack() {
        Stack<Double> stack = actualContext.getStack();
        for (int i = startStackCondition.length - 1; i >= 0; i--) {
            stack.push(startStackCondition[i]);
            expectedStack.push(startStackCondition[i]);
        }
    }

    @Before
    public void fillParameters() {
        HashMap<String, Double> parameters = actualContext.getParameters();
        for (int i = 0; i < startKeys.length; i++) {
            expectedParameters.put(startKeys[i], startValues[i]);
            parameters.put(startKeys[i], startValues[i]);
        }
    }

    @Before
    public void setOutStream() {
        out.reset();
        System.setOut(new PrintStream(out));
    }

    @Test
    @Theory
    public void correctCommandExecution(Object... testData) {
        Stack<Double> actualStack = actualContext.getStack();
        HashMap<String, Double> actualParameters = actualContext.getParameters();
        Command command = (Command) testData[0];
        command.execute(actualContext, (String) testData[1]);
        if (testData[2] != null) {
            Object[] stackChange  = (Object[]) testData[2];
            for (int i = 0; i < (int)stackChange[0]; i++) {
                expectedStack.pop();
            }
            if (!Double.isNaN((Double) stackChange[1])) {
                expectedStack.push((Double) stackChange[1]);
            }
        }
        if (testData[4] != null) {
            Object[] parametersChange = (Object[]) testData[4];
            expectedParameters.put((String)parametersChange[0], (Double) parametersChange[1]);
        }
        Assert.assertEquals(expectedStack, actualStack);
        Assert.assertEquals(out.toString(), testData[3]);
        Assert.assertEquals(expectedParameters, actualParameters);
    }

    @After
    public void clearContext() {
        System.setOut(oldOut);
        actualContext.clear();
        expectedParameters.clear();
        expectedStack.clear();
    }

    @DataPoints
    static public Object[][] commands = new Object[][] {
            {new AddCommand(), "", new Object[]{ 2, startStackCondition[1] + startStackCondition[0] }, "", null},
            {new SubCommand(), "", new Object[]{ 2, startStackCondition[1] - startStackCondition[0] }, "", null},
            {new MulCommand(), "", new Object[]{ 2, startStackCondition[1] * startStackCondition[0] }, "", null},
            {new DivCommand(), "", new Object[]{ 2, startStackCondition[1] / startStackCondition[0] }, "", null},
            {new SqrtCommand(), "", new Object[]{ 1, Math.sqrt(startStackCondition[0]) }, "", null},
            {new PushCommand(), "23", new Object[]{ 0, 23.0 }, "", null},
            {new PushCommand(), "b", new Object[]{ 0, 2.3 }, "", null},
            {new PopCommand(), "", new Object[]{ 1, NaN }, "", null},
            {new CommentCommand(),"ys  dfysdfdf", null, "", null},
            {new PrintCommand(), "", null, startStackCondition[0] + "\r\n", null},
            {new DefineCommand(), "a 4", null, "", new Object[]{"a", 4.0}}
    };
}
