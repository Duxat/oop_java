package game.control.reactor;

import game.model.OneComputerTimer;
import game.model.player.computer.ComputerPlayer;

public class TwoMachinePlayersReactor implements AfterTurnReactor {
    private OneComputerTimer currTimer;
    private OneComputerTimer nextTimer;
    private boolean isShutDown = false;

    public TwoMachinePlayersReactor(ComputerPlayer player1, ComputerPlayer player2) {
        currTimer = new OneComputerTimer(player1, player2.getField());
        nextTimer = new OneComputerTimer(player2, player1.getField());
    }

    @Override
    public void react() {
        if (!isShutDown) {
            currTimer.start();

            OneComputerTimer temp = currTimer;
            currTimer = nextTimer;
            nextTimer = temp;
        }
    }

    @Override
    public void shutDown() {
        isShutDown = true;
        nextTimer.stop();
        currTimer.stop();
    }
}
