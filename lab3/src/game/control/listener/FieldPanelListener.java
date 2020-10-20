package game.control.listener;

import game.model.Field;
import game.view.field.PlacingShipsPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldPanelListener implements ActionListener {
    private Field field;
    private int x;
    private int y;
    private PlacingShipsPanel placingShipsPanel;

    public FieldPanelListener(Field field, int x, int y, PlacingShipsPanel placingShipsPanel) {
        this.field = field;
        this.x = x;
        this.y = y;
        this.placingShipsPanel = placingShipsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int size = placingShipsPanel.getSelectedSize();
        if (size == -1) {
            return;
        }
        int orientation = placingShipsPanel.getSelectedOrientation();
        int prevX = placingShipsPanel.getSelectedPrevX();
        int prevY = placingShipsPanel.getSelectedPrevY();

        if (prevX == -1 || prevY == -1) {
            if (field.placeShip(x, y, size, orientation)) {
                placingShipsPanel.setSelectedPrevCoords(x, y);
            }
        }
        else {
            if(field.replace(prevX, prevY, x, y, orientation)) {
                placingShipsPanel.setSelectedPrevCoords(x, y);
            }
        }
    }
}
