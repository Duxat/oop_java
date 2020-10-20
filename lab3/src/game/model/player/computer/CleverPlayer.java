package game.model.player.computer;

import game.model.Field;
import game.model.HitResult;
import game.view.field.PlacingShipsPanel;

import java.io.*;
import java.util.Random;

public class CleverPlayer extends ComputerPlayer {

    public CleverPlayer(String name) {
        super(name);
    }

    @Override
    public PlacingShipsPanel placeShips() {
        int[] preferredFleet = new int[4];
        int[][] innerFleet = fleet.getFleet();
        for (int[] shipType : innerFleet) {
            if (shipType[0] <= 4 && shipType[0] >= 0) {
                preferredFleet[shipType[0] - 1] = shipType[1];
            } else {
                placeNonClassicalFleetShips();
                return null;
            }
        }

        if (preferredFleet[1] == 3 && preferredFleet[2] == 2 && preferredFleet[3] == 1) {
            placeClassicalFleetShips();
        }
        else {
            placeNonClassicalFleetShips();
        }
        return null;
    }

    private void placeClassicalFleetShips() {
        RandomAccessFile reader;
        int linesNumber;
        final int lineSize = 4 * 6 + 2;
        byte[] line = new byte[lineSize - 2];
        Random random = new Random();

        try {
            reader = new RandomAccessFile("ships.txt", "r");
            linesNumber = (int)reader.length() / lineSize;
            int randomLine = random.nextInt(linesNumber);
            reader.seek(randomLine * lineSize);
            reader.read(line);
            for (int i = 0; i < line.length; i++) {
                line[i] -= '0';
            }
        }
        catch (IOException e) {
            placeNonClassicalFleetShips();
        }

        for (int i = 0; i < line.length; i += 4) {
            field.placeShip(line[i + 1], line[i + 2], line[i], line[i + 3]);
        }

        int[][] innerField = fleet.getFleet();
        int oneCellShipsNumber = 0;
        for (int[] shipType : innerField) {
            if (shipType[0] == 1) {
                oneCellShipsNumber = shipType[1];
            }
        }

        int randomRow;
        int randomColumn;
        for (int i = 0; i < oneCellShipsNumber; i++) {
            do {
                randomRow = random.nextInt(Field.FIELD_SIZE);
                randomColumn = random.nextInt(Field.FIELD_SIZE);
            } while(!field.placeShip(randomRow, randomColumn, 1, 0));
        }
    }

    private void placeNonClassicalFleetShips() {
        int[][] innerFleet = fleet.getFleet();
        Random random = new Random();
        int randomOrientation;
        int randomRow;
        int randomColumn;
        for (int i = 0; i < innerFleet.length; i++) {
            for (int j = 0; j < innerFleet[i][1]; j++) {
                if (innerFleet[i][0] == 1) {
                    do {
                        randomRow = 1 + random.nextInt(Field.FIELD_SIZE - 2);
                        randomColumn = 1 + random.nextInt(Field.FIELD_SIZE - 2);
                    } while (!field.placeShip(randomRow, randomColumn, 1, 0));
                }
                else {
                    do {
                        randomOrientation = random.nextInt(2);
                        if (randomOrientation == 0) {
                            randomRow = random.nextInt(2);
                            if (randomRow == 1) {
                                randomRow = Field.FIELD_SIZE - 1;
                            }
                            randomColumn = random.nextInt(Field.FIELD_SIZE);
                        } else {
                            randomColumn = random.nextInt(2);
                            if (randomColumn == 1) {
                                randomColumn = Field.FIELD_SIZE - 1;
                            }
                            randomRow = random.nextInt(Field.FIELD_SIZE);
                        }
                    } while (!field.placeShip(randomRow, randomColumn, innerFleet[i][0], randomOrientation));
                }
            }
        }
    }

    private CleverPlayerHitStrategy strategy;
    @Override
    public HitResult hit(Field enemyField) {
        if (strategy == null) {
            strategy = new CleverPlayerHitStrategy(enemyField);
        }
        return strategy.hit();
    }
}
