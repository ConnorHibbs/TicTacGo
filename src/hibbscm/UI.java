package hibbscm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UI extends JFrame {

    private enum Direction {NW, N, NE, E, SE, S, SW, W}

    /** The initial size of the board, also as big as the screen will show */
    private final int INITIAL_RADIUS = 10;

    private Board board;
    private Driver driver;

    public UI(Driver driver) {
        super("Tic Tac Go");
        this.driver = driver;
        board = new Board(this);

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(WIDTH, HEIGHT));

        // Build up the board
        Util.doInRadius(INITIAL_RADIUS, (x, y) -> {
            Space space = board.newSpace(x, y);
            space.addActionListener(onSpaceClick);
            content.add(space);
        });

        add(content);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    Direction getOppositeDirection(Direction d) {
        Direction[] directions = Direction.values();
        return directions[(d.ordinal() + 4) % 8];
    }

    public void startGame() {
        Space center = board.getSpace(0, 0);
        board.setCurrentSpace(center);
        center.forAllNeighbors((s -> s.setEnabled(true)));
        center.occupy(-1);
    }

    public void getSettings() {
        Settings settings = Settings.getInstance();

        settings.numPlayers = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players?"));

        int durationMethod = JOptionPane.showOptionDialog(this, "How are you determining the length of the game?",
                "Duration Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Turn Based", "Score Based", "Infinite"}, "Infinite");

        if (durationMethod == 0) { // turn based
            settings.durationMethod = Driver.DurationMethod.TURNS;
            settings.numTurns = Integer.parseInt(JOptionPane.showInputDialog(this, "How many turns?"));
        } else if (durationMethod == 1) { // score based
            settings.durationMethod = Driver.DurationMethod.SCORE;
            settings.maxScore = Integer.parseInt(JOptionPane.showInputDialog(this, "What score to play until?"));
        } else if (durationMethod == 2) { // infinite
            settings.durationMethod = Driver.DurationMethod.INFINITE;
        }
    }

    boolean gameFinished() {
        // TODO return if the game is finished or not,
        // TODO based on the driving method and the settings
        return false;
    }

    private ActionListener onSpaceClick = (event) -> {
        // This action is only for spaces. Check if source is space, and then cast
        if(!(event.getSource() instanceof Space)) return;
        Space space = (Space)event.getSource();

        // first, occupy the space with the current player
        space.occupy(driver.getCurrentPlayer());

        // calculate any points scored
        int points = space.getPoints();
        System.out.println("Points: " + points);

        // then set the board up for the new player and advance the turn
        Color enabledColor = Util.fade(Settings.getInstance().getPlayerColor(driver.getCurrentPlayer()));
        board.getCurrentSpace().forAllNeighbors(s -> s.turnOff());
        space.forAllNeighbors(s -> s.turnOn(enabledColor));
        board.setCurrentSpace(space);
        driver.advanceTurn();
    };
}
