package game.control.listener;

import game.model.Field;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HitListener implements ActionListener {
    private int row;
    private int column;
    private Field field;

    public HitListener(int row, int column, Field field) {
        this.row = row;
        this.column = column;
        this.field = field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        field.hit(row, column);
    }

    public void changeField(Field field) {
        this.field = field;
    }
}

