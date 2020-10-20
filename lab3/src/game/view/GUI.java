package game.view;

import game.control.Controller;
import game.control.reactor.AfterTurnReactor;
import game.view.field.AttackFieldPanel;
import game.view.field.PlacingShipsPanel;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 600;

    private Controller controller;

    private MainMenuPanel mainMenu;
    private SelectionMenuPanel selectionMenu;
    private ScoreTablePanel scoreTable;
    private PlacingShipsModePanel placingShipsMode;
    private AttackModePanel attackMode;

    private Container container;
    
    public GUI(Controller controller) {
        this();
        this.controller = controller;
        this.mainMenu = new MainMenuPanel(this);
        this.selectionMenu = new SelectionMenuPanel(this, controller);
        this.scoreTable = new ScoreTablePanel(this);
        this.placingShipsMode = new PlacingShipsModePanel(this, controller);
        this.attackMode = new AttackModePanel(this, scoreTable);
        container = getContentPane();
        setVisible(true);
    }

    public GUI() {
        super("battleship");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JPanel(new BorderLayout()) {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("waves.jpg"));
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
                }
        });
    }

    public void run() {
        container.removeAll();
        container.add(mainMenu);
        revalidate();
        repaint();
    }

    public void selectPlayersMods() {
        container.removeAll();
        container.add(selectionMenu);
        revalidate();
        repaint();
    }

    public void showScoreTable() {
        container.removeAll();
        scoreTable.prepare();
        container.add(scoreTable);
        revalidate();
        repaint();
    }
    
    public void placeShips(PlacingShipsPanel fieldPanel1, PlacingShipsPanel fieldPanel2) {
        if (fieldPanel1 != null || fieldPanel2 != null) {
            if (fieldPanel1 != null) {
                fieldPanel1.setPlacingShipsMode(placingShipsMode);
            }
            if (fieldPanel2 != null) {
                fieldPanel2.setPlacingShipsMode(placingShipsMode);
            }

            placingShipsMode.add(fieldPanel1, 0);
            placingShipsMode.add(fieldPanel2, 1);

            container.removeAll();
            container.add(placingShipsMode);

            revalidate();
            repaint();

            placingShipsMode.placeShips();
        }
        else {
            controller.play();
        }
    }

    public void attack(AttackFieldPanel panel1, AttackFieldPanel panel2, AfterTurnReactor reactor) {
        panel1.setAttackMode(attackMode);
        panel2.setAttackMode(attackMode);

        attackMode.prepare(panel1, panel2, reactor);

        container.removeAll();
        container.add(attackMode);

        revalidate();
        repaint();
    }

    @Override
    public void dispose() {
        scoreTable.dump();
        super.dispose();
    }
}
