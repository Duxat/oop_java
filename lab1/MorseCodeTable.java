public abstract class MorseCodeTable {
    static private String wordsDelimiter = "/";
    static private String letterDelimiter = " ";

    public static String getLetterDelimiter() {
        return letterDelimiter;
    }

    public static String getWordsDelimiter() {
        return wordsDelimiter;
    }

    public abstract String getCode(String code);
}
