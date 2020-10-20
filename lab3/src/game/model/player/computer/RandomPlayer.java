package game.model.player.computer;

import game.model.Field;
import game.model.HitResult;
import game.view.field.PlacingShipsPanel;

import java.util.Random;

public class RandomPlayer extends ComputerPlayer {
    private Random random = new Random();

    public RandomPlayer(String name) {
        super(name);
    }

    @Override
    public PlacingShipsPanel placeShips() {
        int randomRow;
        int randomColumn;
        int randomOrientation;
        int[][] innerFleet = fleet.getFleet();
        for (int i = 0; i < innerFleet.length; i++) {
            for (int j = 0; j < innerFleet[i][1]; j++) {
                do {
                    randomRow = random.nextInt(Field.FIELD_SIZE);
                    randomColumn = random.nextInt(Field.FIELD_SIZE);
                    randomOrientation = random.nextInt(2);
                } while (!field.placeShip(randomRow, randomColumn,
                        innerFleet[i][0], randomOrientation));
            }
        }
        return null;
    }

    @Override
    public HitResult hit(Field enemyField) {
        int randomRow = random.nextInt(Field.FIELD_SIZE);
        int randomColumn = random.nextInt(Field.FIELD_SIZE);
        return enemyField.hit(randomRow, randomColumn);
    }
}
