import java.io.IOException;

public class MorseCodeDecoder implements TextConverter {
    private static MorseToCharTable table;

    public MorseCodeDecoder() throws IOException {
        table = new MorseToCharTable();
    }

    @Override
    public String convert(String text) {
        String wordsDelimiter = table.getWordsDelimiter();
        String lettersDelimiter = table.getLetterDelimiter();
        StringBuilder convertedText = new StringBuilder();
        String[] words = text.split(wordsDelimiter, -1);
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() != 0) {
                for (String letter : words[i].split(lettersDelimiter, 0)) {
                    String character = table.getCode(letter);
                    if (character != null) {
                        convertedText.append(character);
                    } else if (letter.length() != 0) {
                        System.err.println("Unknown morse code : " + letter);
                    }
                }
            }
            if (i < words.length - 1) {
                convertedText.append(" ");
            }
        }
        return convertedText.toString();
    }
}
