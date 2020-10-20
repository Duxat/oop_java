package game.view.field;

import game.control.listener.FieldPanelListener;
import game.model.Field;
import game.model.Fleet;
import game.view.PlacingShipsModePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlacingShipsPanel extends JPanel {
    private PlacingShipsModePanel placingShipsMode;
    private Field field;
    private JButton[][] fieldUnits;
    private JRadioButton selectedShip = null;
    private HashMap<JRadioButton, Ship> lines = new HashMap<>();
    private Color[] colors;
    
    static class Ship {
        private JLabel size;
        private JToggleButton orientation;
        private int prevX = -1;
        private int prevY = -1;

        public Ship(JLabel size, JToggleButton orientation) {
            this.size = size;
            this.orientation = orientation;
        }

        public JLabel getSize() {
            return size;
        }

        public JToggleButton getOrientation() {
            return orientation;
        }

        public int getPrevX() {
            return prevX;
        }

        public int getPrevY() {
            return prevY;
        }

        public void setPrevCoords(int x, int y) {
            prevX = x;
            prevY = y;
        }
    }

    public int getSelectedSize() {
        if (selectedShip == null) {
            return -1;
        }
        return Integer.parseInt(lines.get(selectedShip).getSize().getText());
    }

    public int getSelectedOrientation() {
        if (selectedShip == null) {
            return -1;
        }

        JToggleButton selected = lines.get(selectedShip).getOrientation();

        if (selected.getText().equals("G")) {
            return 0;
        }
        else {
            return 1;
        }
    }

    public int getSelectedPrevX() {
        return lines.get(selectedShip).getPrevX();
    }

    public int getSelectedPrevY() {
        return lines.get(selectedShip).getPrevY();
    }

    public void setSelectedPrevCoords(int x, int y) {
        lines.get(selectedShip).setPrevCoords(x, y);
    }

    private Color[] getColors(int size) {
        ArrayList<Color> colors = new ArrayList<>();
        int r, b, g;
        final int MAX_COLOR = 255;
        int step = (6 * MAX_COLOR + size - 1) / size;
        for (r = 0; r < MAX_COLOR; r += step) colors.add(new Color(r,MAX_COLOR,0));
        for (g = MAX_COLOR - r % MAX_COLOR; g > 0; g -= step) colors.add(new Color(MAX_COLOR, g,0));
        for (b = -g; b < MAX_COLOR; b += step) colors.add(new Color(MAX_COLOR,0, b));
        for (r = MAX_COLOR - b % MAX_COLOR; r > 0; r -= step) colors.add(new Color(r, 0,MAX_COLOR));
        for (g = -r; g < MAX_COLOR; g += step) colors.add(new Color(0,g, MAX_COLOR));
        for (b = MAX_COLOR - g % MAX_COLOR; b > 0; b -= step) colors.add(new Color(0, MAX_COLOR,b));
        return colors.toArray(new Color[0]);
    }

    public PlacingShipsPanel(Field field, Fleet fleet) {
        this.field = field;

        colors = getColors(fleet.getFleetSize());
        
        int[][] innerFleet = fleet.getFleet();
        ButtonGroup group = new ButtonGroup();
        JPanel fleetPanel = new JPanel(new GridLayout(0, 3, 0, 5));
        for (int i = 0, k = 0; i < innerFleet.length; i++) {
            for (int j = 0; j < innerFleet[i][1]; j++, k++) {
                JRadioButton chooseButton = new JRadioButton();
                chooseButton.setBackground(colors[k]);
                chooseButton.setHorizontalAlignment(SwingConstants.CENTER);
                group.add(chooseButton);
                fleetPanel.add(chooseButton);

                JLabel size = new JLabel(Integer.toString(innerFleet[i][0]));
                size.setHorizontalAlignment(SwingConstants.CENTER);
                fleetPanel.add(size);

                JToggleButton orientation = new JToggleButton("G");
                fleetPanel.add(orientation);

                orientation.addActionListener(e -> {
                    JToggleButton source = (JToggleButton) e.getSource();
                    if (source.isSelected()) {
                        orientation.setText("V");
                    }
                    else {
                        orientation.setText("G");
                    }
                });

                chooseButton.addActionListener(e -> selectedShip = (JRadioButton)e.getSource());

                lines.put(chooseButton, new Ship(size, orientation));
            }
        }

        JPanel fieldBase = new JPanel(new GridLayout(10, 10));
        fieldUnits = new JButton[Field.FIELD_SIZE][Field.FIELD_SIZE];
        for (int i = 0; i < fieldUnits.length; i++) {
            for (int j = 0; j < fieldUnits[i].length; j++) {
                fieldUnits[i][j] = new JButton();
                fieldUnits[i][j].setPreferredSize(new Dimension(20, 20));
                fieldUnits[i][j].setBackground(Color.WHITE);
                fieldUnits[i][j].addActionListener(new FieldPanelListener(field, i, j, this));
                fieldBase.add(fieldUnits[i][j]);
            }
        }

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        add(fieldBase, constraints);
        constraints.insets.left = 40;
        add(fleetPanel, constraints);
    }

    public void repaint(int row, int column, int color) {
        if (color == 1) {
            fieldUnits[row][column].setBackground(selectedShip.getBackground());
        }
        if (color == 0) {
            fieldUnits[row][column].setBackground(Color.WHITE);
        }
    }

    public void setPlacingShipsMode(PlacingShipsModePanel placingShipsMode) {
        this.placingShipsMode = placingShipsMode;
    }

    public void allShipsAreSet() {
        if (placingShipsMode != null) {
            placingShipsMode.allShipsAreSet();
        }
    }

    public void clear() {
        selectedShip.setSelected(false);
        selectedShip = null;

        for (Map.Entry<JRadioButton, Ship> pair: lines.entrySet()) {
            Ship ship = pair.getValue();
            ship.getOrientation().setSelected(false);
            ship.setPrevCoords(-1, -1);
        }

        for (JButton[] fieldUnit : fieldUnits) {
            for (JButton jButton : fieldUnit) {
                jButton.setBackground(Color.WHITE);
            }
        }
    }
}
