package game.model.player;

import game.model.Field;
import game.model.Fleet;
import game.view.field.PlacingShipsPanel;

abstract public class Player {
    protected Field field = new Field();
    protected static Fleet fleet = new Fleet();
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public abstract PlacingShipsPanel placeShips();
}
