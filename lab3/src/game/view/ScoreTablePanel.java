package game.view;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class ScoreTablePanel extends JPanel {
    private final int LINES_MAX_NUMBER = 10;

    private GUI gui;

    private JLabel headline = new JLabel("SCORE TABLE");
    private JLabel[] lines = new JLabel[LINES_MAX_NUMBER];
    private JButton menu = new JButton("MENU");
    private JButton reset = new JButton("RESET");
    private JPanel linesPanel = new JPanel(new GridLayout(LINES_MAX_NUMBER, 0, 0, 10));

    private InputStream scoreFile = null;
    private PrintWriter writer;
    private HashSet<Pair> hashSet = new HashSet<>();
    private ArrayList<Pair> arrayList = new ArrayList<>();

    public class Pair {
        private String playerName;
        private int wins;

        public Pair(String playerName, int wins) {
            this.playerName = playerName;
            this.wins = wins;
        }

        public Pair(String playerName) {
            this(playerName, 1);
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
            if (playerName.equals(pair.playerName)) {
                pair.wins++;
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerName);
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getWins() {
            return wins;
        }
    }


    public ScoreTablePanel(GUI gui) {
        this.gui = gui;

        setLayout(new GridBagLayout());

        menu.addActionListener(e -> gui.run());
        reset.addActionListener(e -> reset());

        headline.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.add(menu);
        panel.add(reset);

        GridBagConstraints constraints = new GridBagConstraints();
        add(headline, constraints);
        constraints.gridy = 1;
        constraints.insets.top = 40;
        add(linesPanel, constraints);
        constraints.gridy = 2;
        add(panel, constraints);

        for (int i = 0; i < LINES_MAX_NUMBER; i++) {
            lines[i] = new JLabel((i + 1) + ". ...............");
            linesPanel.add(lines[i]);
        }

        try {
            scoreFile =  new FileInputStream("score.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(scoreFile);
        while (scanner.hasNext()) {
            int wins = Integer.parseInt(scanner.next());
            String playerName = scanner.nextLine();
            hashSet.add(new Pair(playerName.trim(), wins));
        }
    }

    public void prepare() {
        arrayList.clear();
        arrayList.addAll(hashSet);
        arrayList.sort((o1, o2) -> Integer.compare(o2.getWins(), o1.getWins()));

        int i = 0;
        for (Pair pair: arrayList) {
            lines[i].setText((i + 1) + ". " + pair.getPlayerName() + " " + pair.getWins());
            i++;
            if (i == LINES_MAX_NUMBER - 1) {
                break;
            }
        }
    }

    public void update(String playerName) {
        Pair pair = new Pair(playerName);
        hashSet.add(pair);
    }

    private void reset() {
        hashSet.clear();
        for (int i = 0; i < lines.length; i++) {
            lines[i].setText((i + 1) + ". ...............");
        }
    }

    public void dump() {
        try {
            if (hashSet.size() != 0) {
                writer = new PrintWriter("score.txt");
                writer.print("");
                for (Pair pair : hashSet) {
                    writer.print(pair.getWins());
                    writer.print(" ");
                    writer.print(pair.getPlayerName());
                    writer.print('\n');
                }
                writer.close();
                scoreFile.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
