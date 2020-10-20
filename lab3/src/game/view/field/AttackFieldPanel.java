package game.view.field;

import game.model.Field;
import game.view.AttackModePanel;

import javax.swing.*;
import java.awt.*;

abstract public class AttackFieldPanel extends JPanel {
    protected JComponent[][] gField;
    private Field field;
    private AttackModePanel gui;
    private String ownerName;

    protected void setVisualParameters(JComponent component) {
        component.setPreferredSize(new Dimension(20, 20));
        component.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        component.setBackground(Color.GRAY);
    }

    public AttackFieldPanel(Field field, String ownerName) {
        this.field = field;
        this.ownerName = ownerName;
        gField = new JComponent[Field.FIELD_SIZE][Field.FIELD_SIZE];
        setLayout(new GridLayout(Field.FIELD_SIZE, Field.FIELD_SIZE));
    }

    public void informAttackGuiToChange() {
        gui.changeActivePlayer();
    }

    public void repaint(int row, int column, int color) {
        if (color == 0) {
            gField[row][column].setBackground(Color.RED);
        }
        if (color == 1) {
            gField[row][column].setBackground(Color.BLUE);
        }
        if (color == 2) {
            gField[row][column].setBackground(Color.GREEN);
        }
        repaint();
    }

    public void winAlert() {
        gui.winAlert();
    }

    public void setAttackMode(AttackModePanel attackMode) {
        this.gui = attackMode;
    }

    public void clear() {
        for (JComponent[] row : gField) {
            for (JComponent cell : row) {
                cell.setBackground(Color.GRAY);
            }
        }
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void unlockField() {
        field.unlock();
    }

    public void changeField(Field field) {
        this.field = field;
    }

    public void changeBorderColor(int color) {
        if (color == 0) {
            setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }
        if (color == 1) {
            setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }
}
