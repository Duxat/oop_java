package game.view.field;

import game.model.Field;

import javax.swing.*;

public class NonClickableAttackFieldPanel extends AttackFieldPanel {
    public NonClickableAttackFieldPanel(Field field, String ownerName) {
        super(field, ownerName);

        for (int i = 0; i < gField.length; i++) {
            for (int j = 0; j < gField[i].length; j++) {
                gField[i][j] = new JLabel();
                setVisualParameters(gField[i][j]);
                gField[i][j].setOpaque(true);
                add(gField[i][j]);
            }
        }
    }

}

