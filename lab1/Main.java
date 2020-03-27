import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class Main {
    static public void writeStatisticsInFile(FileWriter statFile, String text) throws IOException {
        Statistics statistics = new Statistics();
        Statistics.Pair[] statisticsInPairs = statistics.getStatistics(text);
        for (Statistics.Pair p: statisticsInPairs) {
            char value = p.getValue();
            if (value == ' ') {
                statFile.write("Whitespace");
            }
            else {
                statFile.write(p.getValue());
            }
            statFile.write(' ');
            statFile.write(Integer.toString(p.getCount()));
            statFile.write('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriter outFile = null;
        FileWriter statFile = null;
        try {
            if (args.length != 2) {
                throw new RuntimeException("Invalid number of command line arguments (must be 2): "  + args.length);
            }
            String text = Files.readString(Path.of(args[1]));
            TextConverter coder;
            if (args[0].equals("encode")) {
                coder = new MorseCodeEncoder();
            }
            else if (args[0].equals("decode")) {
                coder = new MorseCodeDecoder();
            }
            else {
                throw new RuntimeException("Invalid option (must be decode/encode): " + args[0]);
            }
            String convertedText = coder.convert(text);
            outFile = new FileWriter("out.txt");
            outFile.write(convertedText);

            statFile = new FileWriter("stat.txt");
            if (args[0].equals("encode")) {
                writeStatisticsInFile(statFile, text);
            }
            else {
                writeStatisticsInFile(statFile, convertedText);
            }
        }
        catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
        catch (Throwable t) {
            System.err.println(t.getLocalizedMessage());
        }
        finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
                if (statFile != null) {
                    statFile.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
