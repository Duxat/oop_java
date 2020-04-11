package stackcalc;

import org.apache.log4j.Logger;
import stackcalc.commands.Command;
import stackcalc.commands.CommandExecutionException;
import stackcalc.commands.factory.DefaultCommandFactory;

import java.util.ArrayList;

public class StackCalculator {
    private static final Logger log = Logger.getLogger(StackCalculator.class);
    private ExecutionContext executionContext = new ExecutionContext();

    public void calculate(ArrayList<RequestCommand> requestCommands) {
        executionContext.clear();
        for (RequestCommand requestCommand : requestCommands) {
            Command currentCommand;
            try {
                currentCommand = DefaultCommandFactory.getCommand(requestCommand.getCommandName());
                log.info("Объект команды '" + requestCommand.getCommandName() + "' получен");
                currentCommand.execute(executionContext, requestCommand.getCommandNotParsedArguments());
                log.info("Команда '" + requestCommand.getCommandName() + "' выполнена c аргументами '" +
                        requestCommand.getCommandNotParsedArguments() + "'");
            } catch (StackCalculatorException e) {
                log.warn(e.getMessage(), e);
                System.err.println(e.getMessage());
            }
        }
    }
}
