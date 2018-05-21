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

        JPanel content = new JPanel(new BorderLayout());


        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(INITIAL_RADIUS * 2 + 1, INITIAL_RADIUS * 2 + 1));

        // Build up the board
        Util.doInRadius(INITIAL_RADIUS, (x, y) -> {
            Space space = board.newSpace(x, y);
            space.addActionListener(onSpaceClick);
            boardPanel.add(space);
        });

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));

        // TODO add the score to the score panel

        content.add(boardPanel, BorderLayout.CENTER);
        content.add(scorePanel, BorderLayout.EAST);

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
        Color backgroundColor = Util.fade(Settings.getInstance().getPlayerColor(1));
        center.forAllNeighbors((s -> s.turnOn(backgroundColor)));
        center.occupy(-1);
    }

    public void requestSettings() {
        Settings settings = Settings.getInstance();

        settings.numPlayers = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players?"));

        int durationMethod = JOptionPane.showOptionDialog(this, "How are you determining the length of the game?",
                "Duration Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Turn Based", "Score Based", "Infinite"}, "Infinite");

        if (durationMethod == 0) { // turn based
            settings.durationMethod = Settings.DurationMethod.TURNS;
            settings.numTurns = Integer.parseInt(JOptionPane.showInputDialog(this, "How many turns?"));
        } else if (durationMethod == 1) { // score based
            settings.durationMethod = Settings.DurationMethod.SCORE;
            settings.maxScore = Integer.parseInt(JOptionPane.showInputDialog(this, "What score to play until?"));
        } else if (durationMethod == 2) { // infinite
            settings.durationMethod = Settings.DurationMethod.INFINITE;
        }
    }

    private ActionListener onSpaceClick = (event) -> {
        // This action is only for spaces. Check if source is space, and then cast
        if(!(event.getSource() instanceof Space)) return;
        Space space = (Space)event.getSource();

        // first, occupy the space with the current player
        space.occupy(driver.getCurrentPlayer());

        // calculate any points scored
        int points = space.getPoints();
        if(points > 0) {
            System.out.println("Player " + driver.getCurrentPlayer() + " got " + points + " points");
        }

        // turn off the previous spaces
        board.getCurrentSpace().forAllNeighbors(s -> s.turnOff());

        // then set the board up for the new player and advance the turn
        board.setCurrentSpace(space);
        driver.advanceTurn();
        Color enabledColor = Util.fade(Settings.getInstance().getPlayerColor(driver.getCurrentPlayer()));
        space.forAllNeighbors(s -> s.turnOn(enabledColor));
    };
}
