package game.control;

import game.model.Battleship;
import game.model.player.PlayerType;

public class Controller {
    private Battleship battleship;

    public Controller(Battleship battleship) {
        this.battleship = battleship;
    }

    public void start(PlayerType playerType1, String playerName1, PlayerType playerType2, String playerName2) {
        battleship.createPlayers(playerType1, playerName1, playerType2, playerName2);
        battleship.placeShips();
    }

    public void play() {
        battleship.prepareGUIForAttack();
    }
}
