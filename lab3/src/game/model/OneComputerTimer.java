package game.model;

import game.model.player.computer.ComputerPlayer;

import javax.swing.*;

public class OneComputerTimer extends Timer {
    public OneComputerTimer(ComputerPlayer player, Field enemyField) {
        super(500, e -> {
            HitResult result = HitResult.NO_CHANGE;

            while (result == HitResult.NO_CHANGE) {
                result = player.hit(enemyField);
            }

            if (result == HitResult.WIN || result == HitResult.FAIL) {
                ((Timer)e.getSource()).stop();
            }
        });
    }
}
