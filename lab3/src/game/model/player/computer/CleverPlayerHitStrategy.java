package game.model.player.computer;

import game.model.Field;
import game.model.HitResult;

import java.util.Random;

public class CleverPlayerHitStrategy {
    private Field field;

    public CleverPlayerHitStrategy(Field field) {
        this.field = field;
    }

    private boolean isAttackMode = false;

    public HitResult hit() {
        HitResult result;
        if (isAttackMode) {
            result = finishOff();
            if (result == HitResult.KILL) {
                isAttackMode = false;
            }
        }
        else {
            result = lookFor();
            if (result == HitResult.SUCCESS) {
                isAttackMode = true;
            }
        }
        return result;
    }

    private int hitOrientation = -1;
    private static final int[][] orientation = {{-1, 0}, {0, 1}, {0, -1}, {1, 0}};
    private int currRow;
    private int currColumn;

    private HitResult finishOff() {
        HitResult result;
        int row = 0;
        int column = 0;
        if (hitOrientation == -1) {
            int i = 0;
            for (; i < orientation.length; i++) {
                row = currRow + orientation[i][0];
                column = currColumn + orientation[i][1];
                if (row >= 0 && column >= 0 && row <= Field.FIELD_SIZE - 1 &&
                        column <= Field.FIELD_SIZE - 1 && field.isUnknown(row, column)) {
                    break;
                }
            }
            result = field.hit(row, column);
            if (result == HitResult.SUCCESS) {
                hitOrientation = i;
            }
        }
        else {
            row = currRow + orientation[hitOrientation][0];
            column = currColumn + orientation[hitOrientation][1];
            if (!(row >= 0 && column >= 0 && row <= Field.FIELD_SIZE - 1 &&
                    column <= Field.FIELD_SIZE - 1 && field.isUnknown(row, column))) {
                hitOrientation = orientation.length - (hitOrientation + 1);
                do {
                    row += orientation[hitOrientation][0];
                    column += orientation[hitOrientation][1];
                } while(!field.isUnknown(row, column));
            }
            result = field.hit(row, column);
            if (result == HitResult.KILL) {
                hitOrientation = -1;
            }
        }
        if (result == HitResult.SUCCESS) {
            currRow = row;
            currColumn = column;
        }
        return result;
    }

    private int status = 0;
    private Random random = new Random();

    private HitResult lookFor() {
        HitResult result = HitResult.NO_CHANGE;
        if (status < 2) {
            result = hitDiagonal();
        }
        else if (status == 2) {
            int randomRow = random.nextInt(Field.FIELD_SIZE);
            int randomColumn = random.nextInt(Field.FIELD_SIZE);
            result = field.hit(randomRow, randomColumn);
        }
        return result;
    }

    private int[][] diagonalStartPoint;
    private int[] diagonalSize;
    private boolean[] isDiagonalDiscovered;
    private int killedDiagonals = 0;

    private HitResult hitDiagonal() {
        if (diagonalStartPoint == null) {
            diagonalStartPoint = createDiagonalStartPoint(3, 4);
            diagonalSize = createDiagonalCells(diagonalStartPoint);
            isDiagonalDiscovered = new boolean[diagonalSize.length];
        }

        int randomDiagonal;
        do {
            randomDiagonal = random.nextInt(diagonalStartPoint.length);
        } while (isDiagonalDiscovered[randomDiagonal]);

        int randomCell = random.nextInt(diagonalSize[randomDiagonal]);

        currRow = diagonalStartPoint[randomDiagonal][0] - randomCell;
        currColumn = diagonalStartPoint[randomDiagonal][1] + randomCell;
        HitResult result = field.hit(diagonalStartPoint[randomDiagonal][0] - randomCell,
                diagonalStartPoint[randomDiagonal][1] + randomCell);

        for (int i = 0; i < diagonalSize[randomDiagonal]; i++) {
            if (field.isUnknown(diagonalStartPoint[randomDiagonal][0] - i,
                    diagonalStartPoint[randomDiagonal][1] + i)) {
                return result;
            }
        }

        isDiagonalDiscovered[randomDiagonal] = true;
        killedDiagonals++;
        if (killedDiagonals == diagonalStartPoint.length) {
            status++;
            killedDiagonals = 0;
            if (status == 1) {
                diagonalStartPoint = createDiagonalStartPoint(1, 4);
                diagonalSize = createDiagonalCells(diagonalStartPoint);
                isDiagonalDiscovered = new boolean[diagonalSize.length];
            }
        }

        return result;
    }

    private int[][] createDiagonalStartPoint(int begin, int step) {
        final int size = (2 * Field.FIELD_SIZE - 1 - begin + step - 1) / step;
        int[][] diagonalStartPoint = new int[size][2];
        for (int i = 0; i < diagonalStartPoint.length; i++) {
            int currIndex = i * step + begin;
            if (currIndex >= Field.FIELD_SIZE) {
                diagonalStartPoint[i][0] = Field.FIELD_SIZE - 1;
                diagonalStartPoint[i][1] = currIndex % Field.FIELD_SIZE + 1;
            }
            else {
                diagonalStartPoint[i][0] = currIndex;
                diagonalStartPoint[i][1] = 0;
            }
        }
        return diagonalStartPoint;
    }

    private int[] createDiagonalCells(int[][] diagonalStartPoint) {
        int[] diagonalCells = new int[diagonalStartPoint.length];
        for (int i = 0; i < diagonalCells.length; i++) {
            if (diagonalStartPoint[i][0] == Field.FIELD_SIZE - 1) {
                diagonalCells[i] = Field.FIELD_SIZE - diagonalStartPoint[i][1];
            }
            else {
                diagonalCells[i] = diagonalStartPoint[i][0] + 1;
            }
        }
        return diagonalCells;
    }
}
