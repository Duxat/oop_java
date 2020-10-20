package game.model;

import game.view.field.AttackFieldPanel;
import game.view.field.PlacingShipsPanel;

import java.util.HashMap;
import java.util.Stack;

public class Field {
    final public static int FIELD_SIZE = 10;
    final private int[][] field = new int[FIELD_SIZE][FIELD_SIZE];

    private PlacingShipsPanel placingShipsPanel;
    private AttackFieldPanel attackPanel;

    private HashMap<Integer, Ship> ships = new HashMap<Integer, Ship>();
    private Stack<Integer> deletedColors = new Stack<>();

    private int necessaryShipCount = 0;

    static class Ship {
        private int currSize;
        private int originalSize;
        private int orientation;
        private int row;
        private int column;
        public Ship(int size, int orientation, int row, int column) {
            this.currSize = size;
            this.originalSize = size;
            this.orientation = orientation;
            this.row = row;
            this.column = column;
        }

        public void hit() {
            currSize--;
        }

        public boolean isAlive() {
            return currSize != 0;
        }

        public int getOriginalSize() {
            return originalSize;
        }

        public int getOrientation() {
            return orientation;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    public void setFieldPanel(PlacingShipsPanel placingShipsPanel, int necessaryShipCount) {
        this.placingShipsPanel = placingShipsPanel;
        this.necessaryShipCount = necessaryShipCount;
    }

    public void setAttackPanel(AttackFieldPanel attackPanel) {
        this.attackPanel = attackPanel;
    }

    public boolean replace(int prevX, int prevY, int x, int y, int orientation) {
        int color = field[prevX][prevY];
        if (color < 1) {
            return false;
        }

        Ship ship = ships.get(color);
        int size = ship.getOriginalSize();
        deleteShip(prevX, prevY);
        if (placeShip(x, y, size, orientation)) {
            return true;
        }
        else {
            placeShip(ship.getRow(), ship.getColumn(), size, ship.getOrientation());
            return false;
        }
    }

    public HitResult hit(int x, int y) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE) {
            return HitResult.ERROR;
        }
        if (field[x][y] == 0) {
            field[x][y] = -1;
            if (attackPanel != null) {
                attackPanel.repaint(x, y, 1);
                attackPanel.informAttackGuiToChange();
            }
            return HitResult.FAIL;
        }
        if (field[x][y] > 0) {
            int color = field[x][y];
            Ship ship = ships.get(color);
            ship.hit();
            if (attackPanel != null) {
                attackPanel.repaint(x, y, 0);
            }
            field[x][y] = -2;

            if (!ship.isAlive()) {
                int row = ship.getRow();
                int column = ship.getColumn();
                int orientation = ship.getOrientation();
                int size = ship.getOriginalSize();

                ships.remove(color);

                int x1 = row;
                int y1 = column;
                if (orientation == 1) {
                    x1 += size - 1;
                }
                else {
                    y1 += size - 1;
                }

                for (int i = row == 0 ? row : row - 1; i <= (x1 == FIELD_SIZE - 1 ? FIELD_SIZE - 1 : x1 + 1); i++) {
                    for (int j = column == 0 ? column : column - 1; j <= (y1 == FIELD_SIZE - 1 ? FIELD_SIZE - 1 : y1 + 1); j++) {
                        if (field[i][j] == 0) {
                            field[i][j] = -1;
                            if (attackPanel != null) {
                                attackPanel.repaint(i, j, 1);
                            }
                        }
                    }
                }

                if (ships.size() == 0) {
                    if (attackPanel != null) {
                        attackPanel.winAlert();
                    }
                    return HitResult.WIN;
                }
                return HitResult.KILL;
            }
            return HitResult.SUCCESS;
        }
        return HitResult.NO_CHANGE;
    }

    public boolean isUnknown(int x, int y) {
        return field[x][y] >= 0 ;
    }

    public boolean placeShip(int x, int y, int size, int orientation) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE) {
            return false;
        }
        if (size < 1) {
            return false;
        }
        if (orientation != 0 && orientation != 1) {
            return false;
        }

        int x1 = x;
        int y1 = y;
        if (orientation == 1) {
            x1 += size - 1;
        }
        else {
            y1 += size - 1;
        }
        if (x1 >= FIELD_SIZE || y1 >= FIELD_SIZE) {
            return false;
        }

        for (int i = x == 0 ? x : x - 1; i <= (x1 == FIELD_SIZE - 1 ? FIELD_SIZE - 1 : x1 + 1); i++) {
            for (int j = y == 0 ? y : y - 1; j <= (y1 == FIELD_SIZE - 1 ? FIELD_SIZE - 1 : y1 + 1); j++) {
                if (field[i][j] != 0) {
                    return false;
                }
            }
        }

        int color;
        if (!deletedColors.empty()) {
            color = deletedColors.pop();
        }
        else {
            color = ships.size() + 1;
        }
        ships.put(color, new Ship(size, orientation, x, y));

        if (orientation == 0) {
            for (int i = y; i <= y1; i++) {
                field[x][i] = color;
                if (placingShipsPanel != null) {
                    placingShipsPanel.repaint(x, i, 1);
                }
            }
        }
        else  {
            for (int i = x; i <= x1; i++) {
                field[i][y] = color;
                if (placingShipsPanel != null) {
                    placingShipsPanel.repaint(i, y, 1);
                }
            }
        }

        if (placingShipsPanel != null && ships.size() == necessaryShipCount) {
            placingShipsPanel.allShipsAreSet();
        }

        return true;
    }

    public void deleteShip(int x, int y) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE) {
            return;
        }
        if (field[x][y] == 0) {
            return;
        }

        int color = field[x][y];
        Ship ship = ships.get(color);
        ships.remove(color);
        deletedColors.push(color);

        int orientation = ship.getOrientation();
        int column = ship.getColumn();
        int row = ship.getRow();
        int size = ship.getOriginalSize();

        if (orientation == 0) {
            for (int i = column; i < column + size; i++) {
                field[x][i] = 0;
                if (placingShipsPanel != null) {
                    placingShipsPanel.repaint(x, i, 0);
                }
            }
        }
        else {
            for (int i = row; i < row + size; i++) {
                field[i][y] = 0;
                if (placingShipsPanel != null) {
                    placingShipsPanel.repaint(i, y, 0);
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(field[i][j]);
                System.out.print(" ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public int getShipsCount() {
        return ships.size();
    }

    public PlacingShipsPanel getFieldPanel() {
        return placingShipsPanel;
    }

    public void unlock() {
        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                if (field[i][j] > 0) {
                    attackPanel.repaint(i, j, 2);
                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                field[i][j] = 0;
            }
        }
    }
}
