package game.view;

import game.model.player.PlayerType;
import game.control.*;


import javax.swing.*;
import java.awt.*;

public class SelectionMenuPanel extends JPanel {
    public SelectionMenuPanel(GUI gui, Controller controller) {
        JPanel box1 = new JPanel(new GridLayout(0, 2, 50, 10));

        String[] mods = {"RANDOM", "CLEVER", "MANUAL"};
        JToggleButton[] firstPlayerMods = new JToggleButton[mods.length];
        JToggleButton[] secondPlayerMods = new JToggleButton[mods.length];
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();

        JLabel label1 = new JLabel("PLAYER 1");
        JLabel label2 = new JLabel("PLAYER 2");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.RED);
        label2.setForeground(Color.RED);
        label1.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        label2.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        box1.add(label1);
        box1.add(label2);

        TextField textField1 = new TextField("FIRST PLAYER", 15);
        TextField textField2 = new TextField("SECOND PLAYER", 15);
        box1.add(textField1);
        box1.add(textField2);

        for (int i = 0; i < mods.length; i++) {
            firstPlayerMods[i] = new JToggleButton(mods[i]);
            group1.add(firstPlayerMods[i]);
            box1.add(firstPlayerMods[i]);

            secondPlayerMods[i] = new JToggleButton(mods[i]);
            group2.add(secondPlayerMods[i]);
            box1.add(secondPlayerMods[i]);
        }

        JPanel box2 = new JPanel(new GridLayout(1, 0, 70, 0));

        JButton play = new JButton("PLAY");
        JButton menu = new JButton("MENU");
        play.setPreferredSize(new Dimension(100, 50));
        menu.setPreferredSize(new Dimension(100, 50));

        box2.add(menu);
        box2.add(play);

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        add(box1, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets.top = 50;
        add(box2, constraints);

        menu.addActionListener(e -> gui.run());

        play.addActionListener(e -> {
            JToggleButton chosenMod1 = null;
            JToggleButton chosenMod2 = null;
            for(JToggleButton b : firstPlayerMods) {
                if (b.isSelected()) {
                    chosenMod1 = b;
                    break;
                }
            }
            for(JToggleButton b : secondPlayerMods) {
                if (b.isSelected()) {
                    chosenMod2 = b;
                    break;
                }
            }
            String playerName1 = textField1.getText();
            String playerName2 = textField2.getText();
            if (chosenMod1 != null && playerName1.length() != 0 && chosenMod2 != null && playerName2.length() != 0) {
                controller.start(PlayerType.valueOf(chosenMod1.getText()), playerName1,
                        PlayerType.valueOf(chosenMod2.getText()), playerName2);
            }
            else {
                JOptionPane.showMessageDialog(SelectionMenuPanel.this, "Not all game parameters are set!");
            }
        });
    }
}
