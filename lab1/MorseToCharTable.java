import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class MorseToCharTable extends MorseCodeTable {
    static private HashMap<String, String> morseToCharTable;

    public MorseToCharTable() throws IOException {
        morseToCharTable = new HashMap<>();
        Scanner scanner = new Scanner(new File("morse_code.txt"));
        while (scanner.hasNext()) {
            String character = scanner.next();
            String code = scanner.next();
            morseToCharTable.put(code, character);
        }
        scanner.close();
    }

    public String getCode(String code) {
        return morseToCharTable.get(code);
    }
}
