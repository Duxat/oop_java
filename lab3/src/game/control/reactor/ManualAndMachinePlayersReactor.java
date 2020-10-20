package game.control.reactor;

import game.model.Field;
import game.model.OneComputerTimer;
import game.model.player.computer.ComputerPlayer;

public class ManualAndMachinePlayersReactor implements AfterTurnReactor {
    private OneComputerTimer timer;
    private boolean currTurn;
    private boolean isShutDown = false;

    public ManualAndMachinePlayersReactor(ComputerPlayer player, Field field, boolean currTurn) {
        this.timer = new OneComputerTimer(player, field);
        this.currTurn = currTurn;
    }

    @Override
    public void react() {
        if (!isShutDown) {
            currTurn = !currTurn;
            if (currTurn) {
                timer.start();
            }
        }
    }

    @Override
    public void shutDown() {
        isShutDown = true;
        timer.stop();
    }
}
