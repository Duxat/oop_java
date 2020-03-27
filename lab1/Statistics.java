import java.util.HashSet;
import java.util.Objects;

public class Statistics {
    public static class Pair {
        private char value;
        private int count = 1;

        private Pair(char value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair pair = (Pair) o;
            if (value == pair.value) {
                pair.count++;
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        char getValue() {
            return value;
        }

        int getCount() {
            return count;
        }
    }

    private HashSet<Pair> hashSet = new HashSet<>();

    public Pair[] getStatistics(String text) {
        for (int i = 0; i < text.length(); i++) {
            char currTextChar = text.charAt(i);
            Pair pair;
            if (Character.isWhitespace(currTextChar)) {
                pair = new Pair(' ');
            }
            else {
                pair = new Pair(currTextChar);
            }
            hashSet.add(pair);
        }
        Pair[] resultStat = new Pair[hashSet.size()];
        return hashSet.toArray(resultStat);
    }
}
