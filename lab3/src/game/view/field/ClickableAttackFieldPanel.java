package game.view.field;

import game.control.listener.HitListener;
import game.model.Field;

import javax.swing.*;

public class ClickableAttackFieldPanel extends AttackFieldPanel {
    private HitListener[][] listeners = new HitListener[gField.length][gField[0].length];

    public ClickableAttackFieldPanel(Field field, String ownerName) {
        super(field, ownerName);

        for (int i = 0; i < gField.length; i++) {
            for (int j = 0; j < gField[i].length; j++) {
                gField[i][j] = new JButton();
                setVisualParameters(gField[i][j]);
                listeners[i][j] = new HitListener(i, j, field);
                ((JButton)gField[i][j]).addActionListener(listeners[i][j]);
                add(gField[i][j]);
            }
        }
    }

    @Override
    public void setEnabled(boolean status) {
        for (JComponent[] buttons : gField) {
            for (JComponent button : buttons) {
                button.setEnabled(status);
            }
        }
    }

    public void changeField(Field field) {
        super.changeField(field);
        for (int i = 0; i < listeners.length; i++) {
            for (int j = 0; j < listeners[i].length; j++) {
                listeners[i][i].changeField(field);
            }
        }
    }
}
