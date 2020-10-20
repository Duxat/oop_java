package game.model.player.computer;

import game.model.Field;
import game.model.HitResult;
import game.model.player.Player;

abstract public class ComputerPlayer extends Player {
    public ComputerPlayer(String name) {
        super(name);
    }

    abstract public HitResult hit(Field enemyField);
}
