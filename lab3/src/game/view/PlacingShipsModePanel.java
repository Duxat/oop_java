package game.view;

import game.control.*;
import game.view.field.PlacingShipsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlacingShipsModePanel extends JPanel {
    private String[] players = {"FIRST PLAYER", "SECOND PLAYER"};
    private String message = "Not all ships are placed!";

    private ArrayList<PlayerField> panelList = new ArrayList<PlayerField>();
    private PlayerField[] panels;

    private GUI gui;
    private Controller controller;
    private boolean areShipsSet;

    private JButton next = new JButton("NEXT");
    private JButton prev = new JButton("PREV");
    private JLabel currLabel = new JLabel();
    private GridBagConstraints constraints = new GridBagConstraints();

    static class PlayerField {
        private PlacingShipsPanel panel;
        private int index;

        public PlayerField(PlacingShipsPanel panel, int index) {
            this.panel = panel;
            this.index = index;
        }

        public PlacingShipsPanel getPanel() {
            return panel;
        }

        public int getIndex() {
            return index;
        }
    }

    public PlacingShipsModePanel(GUI gui, Controller controller) {
        this.controller = controller;
        this.gui = gui;

        setLayout(new GridBagLayout());

        next.setPreferredSize(new Dimension(150, 50));
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areShipsSet = false;
                for (PlayerField panel: panelList) {
                    remove(panel.getPanel());
                }
                ActionListener[] listeners = next.getActionListeners();
                for (ActionListener listener: listeners) {
                    next.removeActionListener(listener);
                }
                panelList.clear();
                gui.selectPlayersMods();
            }
        });

        JPanel panel = new JPanel(new GridLayout(1, 2, 50, 0));
        panel.add(prev);
        panel.add(next);

        add(currLabel, constraints);
        constraints.insets.top = 40;
        constraints.gridy = 2;
        add(panel, constraints);
        constraints.gridy = 1;
    }

    public void add(PlacingShipsPanel panel, int index) {
        if (index >= 0 && index < players.length && panel != null) {
            panelList.add(new PlayerField(panel, index));
        }
    }

    private void showFieldPanel(int index) {
        currLabel.setText(players[panels[index].getIndex()]);
        add(panels[index].getPanel(), constraints);

        repaint();
        revalidate();

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areShipsSet) {
                    areShipsSet = false;
                    remove(panels[index].getPanel());
                    next.removeActionListener(this);
                    if (index == panels.length - 1) {
                        panelList.clear();
                        controller.play();
                    }
                    else {
                        showFieldPanel(index + 1);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(PlacingShipsModePanel.this, message);
                }
            }
        });
    }

    public void placeShips() {
        if (panelList.size() != 0) {
            panels = panelList.toArray(new PlayerField[0]);
            showFieldPanel(0);
        }
    }

    public void allShipsAreSet() {
        areShipsSet = true;
    }
}
