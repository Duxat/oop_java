package game.model;

import game.control.reactor.AfterTurnReactor;
import game.control.reactor.ManualAndMachinePlayersReactor;
import game.control.reactor.TwoMachinePlayersReactor;
import game.model.player.*;
import game.model.player.computer.CleverPlayer;
import game.model.player.computer.ComputerPlayer;
import game.model.player.computer.RandomPlayer;
import game.view.GUI;
import game.view.field.AttackFieldPanel;
import game.view.field.ClickableAttackFieldPanel;
import game.view.field.NonClickableAttackFieldPanel;

public class Battleship {
    private GUI gui;
    private Player player1 = null;
    private Player player2 = null;
    private String playerName1 = null;
    private String playerName2 = null;

    private PlayerType playerMod1;
    private PlayerType playerMod2;

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    private Player createPlayer(PlayerType playerMod, String playerName) {
        switch (playerMod) {
            case MANUAL:
                return new ManualPlayer(playerName);
            case CLEVER:
                return new CleverPlayer(playerName);
            case RANDOM:
                return new RandomPlayer(playerName);
            default:
                return null;
        }
    }

    public void createPlayers(PlayerType playerMod1, String playerName1,
                              PlayerType playerMod2, String playerName2) {
        player1 = createPlayer(playerMod1, playerName1);
        this.playerMod1 = playerMod1;
        this.playerName1 = playerName1;
        player2 = createPlayer(playerMod2, playerName2);
        this.playerMod2 = playerMod2;
        this.playerName2 = playerName2;
    }

    public void placeShips() {
        gui.placeShips(player1.placeShips(), player2.placeShips());
    }

    public void prepareGUIForAttack() {
        Field field1 = player1.getField();
        Field field2 = player2.getField();
        AttackFieldPanel panel1;
        AttackFieldPanel panel2;
        AfterTurnReactor reactor;

        if (playerMod1 != PlayerType.MANUAL && playerMod2 != PlayerType.MANUAL) {
            panel1 = new NonClickableAttackFieldPanel(field1, playerName1);
            panel2 = new NonClickableAttackFieldPanel(field2, playerName2);
            reactor = new TwoMachinePlayersReactor((ComputerPlayer) player1, (ComputerPlayer) player2);
        }
        else if (playerMod1 == PlayerType.MANUAL && playerMod2 == PlayerType.MANUAL) {
            panel1 = new ClickableAttackFieldPanel(field1, playerName1);
            panel2 = new ClickableAttackFieldPanel(field2, playerName2);
            reactor = null;
        }
        else if (playerMod1 == PlayerType.MANUAL) {
            panel1 = new NonClickableAttackFieldPanel(field1, playerName1);
            panel2 = new ClickableAttackFieldPanel(field2, playerName2);
            reactor = new ManualAndMachinePlayersReactor((ComputerPlayer)player2, field1,true);
        }
        else {
            panel1 = new ClickableAttackFieldPanel(field1, playerName1);
            panel2 = new NonClickableAttackFieldPanel(field1, playerName2);
            reactor = new ManualAndMachinePlayersReactor((ComputerPlayer)player1, field2,false);
        }

        field1.setAttackPanel(panel1);
        field2.setAttackPanel(panel2);
        gui.attack(panel1, panel2, reactor);
    }
}
