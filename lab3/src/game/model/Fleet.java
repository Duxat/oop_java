package game.model;

public class Fleet {
    private int[][] fleet = {{4, 1}, {3, 2}, {2, 3}, {1, 4}};

    public int[][] getFleet() {
        return fleet;
    }

    public int getFleetSize() {
        int size = 0;
        for (int[] shipType : fleet) {
            size += shipType[1];
        }
        return size;
    }
}
