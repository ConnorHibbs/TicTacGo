package hibbscm;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UI extends JFrame {

    private Map<Integer, Color> playerColors = new HashMap<>();

    private final int WIDTH = 21, HEIGHT = 21;
    private Space[][] board = new Space[HEIGHT][WIDTH];

    /** The current occupied space on the board */
    private Space currentSpace;

    /** Can do any actions on dummy space, and nobody can see */
    private Space dummySpace = new Space(-1, -1);

    private Driver driver;

    public UI(Driver driver) {
        super("Tic Tac Go");
        this.driver = driver;

        playerColors.put(0, Color.WHITE);
        playerColors.put(-1, Color.BLACK);
        playerColors.put(1, Color.RED);
        playerColors.put(2, Color.BLUE);
        playerColors.put(3, Color.GREEN);

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(WIDTH, HEIGHT));

        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                Space space = new Space(x, y);
                space.addActionListener((event) -> {
                    // first, occupy the space with the current player
                    space.occupy(getCurrentPlayer());

                    // then advance the turn
                    driver.advanceTurn();

                    // then set the board up for the new player
                    disableSpacesAround(this.currentSpace.coordinate);
                    this.currentSpace = space;
                    enableSpacesAround(this.currentSpace.coordinate);

                });

                board[x][y] = space;
                content.add(space);
            }
        }

        add(content);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public Space getSpace(int x, int y) {
        if(0 <= x && x < WIDTH && 0 <= y && y < HEIGHT) {
            return board[x][y];
        } else {
            return dummySpace;
        }
    }

    public Space getSpace(Coordinate coordinate) {
        return getSpace(coordinate.x, coordinate.y);
    }

    public void startGame() {
        Coordinate center = new Coordinate(WIDTH / 2, HEIGHT / 2);
        this.currentSpace = getSpace(center);
        enableSpacesAround(center);
        this.currentSpace.occupy(-1);
    }

    public void disableSpacesAround(Coordinate coordinate) {
        int RADIUS = 2;

        for(int x = -RADIUS; x <= RADIUS; x++) {
            for(int y = -RADIUS; y <= RADIUS; y++) {
                getSpace(coordinate.x + x, coordinate.y + y).setEnabled(false);
            }
        }
    }

    public void enableSpacesAround(Coordinate coordinate) {
        int RADIUS = 1;

        for(int x = -RADIUS; x <= RADIUS; x++) {
            for(int y = -RADIUS; y <= RADIUS; y++) {
                System.out.println("Trying to enable x: " + (coordinate.x + x) + ", y: " + (coordinate.y + y));
                getSpace(coordinate.x + x, coordinate.y + y).setEnabled(true);
            }
        }
    }

    public int getCurrentPlayer() {
        return driver.getCurrentPlayer();
    }

    public Driver.Settings getSettings() {
        Driver.Settings settings = new Driver.Settings();

        settings.numPlayers = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players?"));

        int durationMethod = JOptionPane.showOptionDialog(this, "How are you determining the length of the game?",
                "Duration Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Turn Based", "Score Based"}, "Score Based");

        if (durationMethod == 1) { // turn based
            settings.durationMethod = Driver.DurationMethod.TURNS;
            settings.numTurns = Integer.parseInt(JOptionPane.showInputDialog(this, "How many turns?"));
        } else if (durationMethod == 2) { // score based
            settings.durationMethod = Driver.DurationMethod.SCORE;
            settings.maxScore = Integer.parseInt(JOptionPane.showInputDialog(this, "What score to play until?"));
        }

        return settings;
    }

    private class Space extends JButton {

        /** The player occupying this space, or 0 for empty */
        private int occupied = 0;
        private Coordinate coordinate;

        public Space(int x, int y) {
            this.coordinate = new Coordinate(x, y);
            setEnabled(false);
            setBackground(Color.WHITE);
            setMinimumSize(new Dimension(20, 20));
            setMaximumSize(new Dimension(40, 40));
        }

        public void occupy(int player) {
            setEnabled(false);
            this.occupied = player;
            setBackground(playerColors.get(player));
        }

        public void clear() {
            this.occupied = 0;
            setBackground(Color.WHITE);
        }

        @Override
        public void setEnabled(boolean enabled) {
            // only enable of disable if it is not occupied
            if(occupied == 0) {
                super.setEnabled(enabled);
                setBackground(enabled ? getEnabledColor() : Color.WHITE);
            }
        }

        Color getEnabledColor() {
            Color c = playerColors.get(getCurrentPlayer());
            int saturation = 64;
            int boldness = 255 - saturation;
            int r = Math.min(c.getRed() + boldness, 255);
            int g = Math.min(c.getGreen() + boldness, 255);
            int b = Math.min(c.getBlue() + boldness, 255);
            return new Color(r, g, b);
        }

        public int isOccupied() {
            return this.occupied;
        }


    }

    private class Coordinate {
        int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    boolean gameFinished() {
        // TODO return if the game is finished or not,
        // TODO based on the driving method and the settings
        return false;
    }
}
