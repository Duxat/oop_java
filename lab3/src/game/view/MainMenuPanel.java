package game.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(GUI gui) {
        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel basePanel = new JPanel(new GridLayout(0, 1, 0, 5) );
        basePanel.setOpaque(false);

        JButton start = new JButton("START");
        JButton score = new JButton("SCORE");
        JButton exit = new JButton("EXIT");
        basePanel.add(start);
        basePanel.add(score);
        basePanel.add(exit);

        add(basePanel);

        exit.addActionListener(e -> gui.dispose());
        score.addActionListener(e -> gui.showScoreTable());
        start.addActionListener(e -> gui.selectPlayersMods());
    }
}
