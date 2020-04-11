import org.apache.log4j.Level;
import stackcalc.RequestCommand;
import stackcalc.StackCalculator;
import stackcalc.parser.StackCalculatorParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        InputStream file = null;
        if (args.length == 0) {
            file = System.in;
            log.info("В качестве входного файла для парсера калькулятора используется System.in");
        } else if (args.length == 1) {
            try {
                file = new FileInputStream(args[0]);
                log.info("В качестве входного файла для парсера калькулятора используется файл " + args[0]);
            } catch (IOException e) {
                log.error("Невозможно открыть файл " + args[0], e);
                System.err.println(e.getLocalizedMessage() + " cant be open.");
                return;
            }
        } else {
            log.error("Неверное количество аргументов командной строки");
            System.err.println("Wrong number of console arguments.");
            return;
        }

        ArrayList<RequestCommand> requestCommands = StackCalculatorParser.parseFile(file);
        log.info("Входной файл успешно распарсен");
        StackCalculator stackCalculator = new StackCalculator();
        stackCalculator.calculate(requestCommands);
        log.info("Калькулятор успешно выполнил работу");
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Входной файл не может быть закрыт", e);
        }
    }
}
