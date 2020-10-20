package game.view;

import game.control.reactor.AfterTurnReactor;
import game.view.field.AttackFieldPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackModePanel extends JPanel {
    private GUI gui;
    private ScoreTablePanel scoreTable;

    private AttackFieldPanel currPanel;
    private AttackFieldPanel nextPanel;

    private String shootState = " SHOOTS";
    private String winState = " WINS!";

    private AfterTurnReactor reactor;

    private JLabel label = new JLabel();
    private JButton finish = new JButton("FINISH");
    private JPanel twoFields = new JPanel(new GridLayout(1, 0, 25, 0));
    private GridBagConstraints constraints = new GridBagConstraints();

    Timer timer;

    private ActionListener timerListener1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            label.setText(currPanel.getOwnerName() + shootState);

            currPanel.changeBorderColor(0);
            nextPanel.changeBorderColor(1);

            revalidate();
            repaint();

            if (reactor != null) {
                reactor.react();
            }
        }
    };

    private ActionListener timerListener2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currPanel.unlockField();
            label.setText(currPanel.getOwnerName() + winState);

            ((Timer) e.getSource()).removeActionListener(this);
            ((Timer) e.getSource()).removeActionListener(timerListener1);
        }
    };

    private ActionListener listener1 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(gui, "Are you sure?",
                    "Finish is pressed", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (reactor != null) {
                    reactor.shutDown();
                }
                gui.run();
            }
        }
    };

    private ActionListener listener2 = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ((JButton)e.getSource()).removeActionListener(this);
            ((JButton)e.getSource()).addActionListener(listener1);
            gui.run();
        }
    };


    public AttackModePanel(GUI gui, ScoreTablePanel scoreTable) {
        this.gui = gui;
        this.scoreTable = scoreTable;

        setLayout(new GridBagLayout());

        finish.addActionListener(listener1);

        add(label, constraints);
        constraints.gridy = 1;
        constraints.insets.top = 40;
        add(twoFields, constraints);
        constraints.gridy = 2;
        add(finish, constraints);
    }


    public void prepare(AttackFieldPanel player1, AttackFieldPanel player2, AfterTurnReactor reactor) {
        this.reactor = reactor;

        currPanel = player1;
        nextPanel = player2;

        nextPanel.changeBorderColor(1);
        currPanel.setEnabled(false);

        label.setText(currPanel.getOwnerName() + shootState);

        twoFields.removeAll();
        twoFields.add(player1);
        twoFields.add(player2);

        timer = new Timer(500, timerListener1);
        timer.setRepeats(false);

        if (reactor != null) {
            reactor.react();
        }
    }

    public void changeActivePlayer() {
        currPanel.setEnabled(true);
        nextPanel.setEnabled(false);

        AttackFieldPanel tempPanel = currPanel;
        currPanel = nextPanel;
        nextPanel = tempPanel;

        timer.start();
    }

    public void winAlert() {
        nextPanel.setEnabled(false);

        finish.removeActionListener(listener1);
        finish.addActionListener(listener2);

        scoreTable.update(currPanel.getOwnerName());

        timer.removeActionListener(timerListener1);
        timer.addActionListener(timerListener2);
        timer.start();
    }
}
