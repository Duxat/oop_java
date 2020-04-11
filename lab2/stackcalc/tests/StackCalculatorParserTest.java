package stackcalc.tests;


import org.junit.*;
import org.junit.rules.TemporaryFolder;
import stackcalc.RequestCommand;
import stackcalc.parser.StackCalculatorParser;

import java.io.*;
import java.util.ArrayList;

public class StackCalculatorParserTest {
    private InputStream testStream;
    private String[][] expectedCommandStrings;

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void createTestStream() throws IOException {
        File tempFile = folder.newFile();
        FileWriter writer = new FileWriter(tempFile);
        writer.write("    " + expectedCommandStrings[0][0] + expectedCommandStrings[0][1] + '\n');
        writer.write("   \n");
        writer.write("\t\t\t" + expectedCommandStrings[1][0] + expectedCommandStrings[1][1] + '\n');
        writer.write(expectedCommandStrings[2][0] + expectedCommandStrings[2][1]);
        testStream = new FileInputStream(tempFile);
        writer.close();
    }

    @Before
    public void fillExpectedCommandStrings() {
        expectedCommandStrings = new String[][]{
                {"123", "   \t234 324  44\t  "},
                {"SQRT", "   sdfd  df df a dff"},
                {"+", "   "}};
    }

    @Test
    public void fileParsing() {
        ArrayList<RequestCommand> actualCommands = StackCalculatorParser.parseFile(testStream);
        RequestCommand[] actualCommandsArray = new RequestCommand[actualCommands.size()];
        actualCommands.toArray(actualCommandsArray);
        Assert.assertEquals(3, actualCommandsArray.length);
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(expectedCommandStrings[i][0], actualCommandsArray[i].getCommandName());
            Assert.assertEquals(expectedCommandStrings[i][1], actualCommandsArray[i].getCommandNotParsedArguments());
        }
    }

    @After
    public void closeTestStream() throws IOException {
        testStream.close();
    }
}
