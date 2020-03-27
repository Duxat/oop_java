import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class CharToMorseTable extends MorseCodeTable {
    static private HashMap<String, String> charToMorseTable;

    public CharToMorseTable() throws IOException {
        charToMorseTable = new HashMap<>();
        Scanner scanner = new Scanner(new File("morse_code.txt"));
        while (scanner.hasNext()) {
            String character = scanner.next();
            String code = scanner.next();
            charToMorseTable.put(character, code);
        }
        scanner.close();
    }

    public String getCode(String code) {
        return charToMorseTable.get(code);
    }
}
