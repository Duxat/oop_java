import game.control.*;
import game.model.Battleship;
import game.view.GUI;

public class Main {
    public static void main(String[] args) {
        Battleship battleship = new Battleship();
        Controller controller = new Controller(battleship);
        GUI gui = new GUI(controller);
        battleship.setGui(gui);
        gui.run();
    }
}
