import java.io.IOException;
import java.util.HashSet;

public class MorseCodeEncoder implements TextConverter {
    private static CharToMorseTable table;

    public MorseCodeEncoder() throws IOException {
        table = new CharToMorseTable();
    }

    @Override
    public String convert(String text) {
        String wordsDelimiter = MorseCodeTable.getWordsDelimiter();
        String lettersDelimiter = MorseCodeTable.getLetterDelimiter();

        HashSet <Character> unknownChars = new HashSet<>();
        StringBuilder convertedText = new StringBuilder();
        boolean isPrevCharWordsDelimiter = true;
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isWhitespace(currentChar)) {
                convertedText.append(wordsDelimiter);
                isPrevCharWordsDelimiter = true;
            }
            else {
                if (!isPrevCharWordsDelimiter) {
                    convertedText.append(lettersDelimiter);
                }
                isPrevCharWordsDelimiter = false;
                if (Character.isAlphabetic(currentChar)) {
                    currentChar = Character.toLowerCase(currentChar);
                }
                String code = table.getCode(Character.toString(currentChar));
                if (code != null) {
                    convertedText.append(code);
                }
                else {
                    if (unknownChars.add(currentChar)) {
                        System.err.println("Unknown char: " + currentChar);
                    }
                }
            }
        }
        return convertedText.toString();
    }
}
