package game.model.player;

import game.view.field.PlacingShipsPanel;

public class ManualPlayer extends Player {

    public ManualPlayer(String name) {
        super(name);
    }

    @Override
    public PlacingShipsPanel placeShips() {
        PlacingShipsPanel placingShipsPanel = field.getFieldPanel();
        if (placingShipsPanel == null) {
            placingShipsPanel = new PlacingShipsPanel(field, fleet);
            field.setFieldPanel(placingShipsPanel, fleet.getFleetSize());
        }
        else {
            placingShipsPanel.clear();
        }
        return placingShipsPanel;
    }
}
