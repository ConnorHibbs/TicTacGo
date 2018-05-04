package hibbscm;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Space extends JButton {

    // The offset directions
    final int[] NW = {-1,  1}, N = {0,  1}, NE = {1,  1};
    final int[] W  = {-1,  0}, /*  self  */  E = {1,  0};
    final int[] SW = {-1, -1}, S = {0, -1}, SE = {1, -1};

    /** The player occupying this space, or 0 for empty */
    private int occupied = 0;

    private Board board;

    private int x, y;

    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;

        setEnabled(false);
        setBackground(Color.WHITE);
        setMinimumSize(new Dimension(20, 20));
        setMaximumSize(new Dimension(40, 40));
    }

    public void occupy(int player) {
        setEnabled(false);
        this.occupied = player;
        setBackground(Settings.getInstance().getPlayerColor(player));
    }

    public int getPoints() {
        // assume 0 points scored on any given turn
        int totalPoints = 0;

        // The four cardinal direction pairs to check for points in ( \ | / - )
        int[][][] directions3 = {{NW, SE}, {N, S}, {NE, SW}, {E, W}};

        Set<Space> scoredSpaces = new HashSet<>();

        // for each possible direction to score in
        for(int cardinal = 0; cardinal < 4; cardinal++) {
            // Start points off with 1 (for the space just occupied)
            int cardinalPoints = 1;
            Set<Space> cardinalSpaces = new HashSet<>();

            // check in the two possible directions for each cardinal pair, and add on points for those encountered
            for(int direction = 0; direction < 2; direction++) {
                int xOffset = directions3[cardinal][direction][0];
                int yOffset = directions3[cardinal][direction][1];

                Space neighbor = board.getSpace(x + xOffset, y + yOffset);

                // keep searching as long as matches exist in that direction
                while(neighbor.occupied == occupied) {
                    cardinalSpaces.add(neighbor); // add this piece as scored
                    cardinalPoints++; // add points
                    neighbor = board.getSpace(neighbor.x + xOffset, neighbor.y + yOffset);
                }
            }

            // if they have at least 3 in a row, give them a point for every set of 3
            // this is equal to the total in that row - 2
            if(cardinalPoints >= 3) {
                totalPoints += cardinalPoints - 2;

                // if they scored points, add those spaces to the set to clear at the end
                scoredSpaces.addAll(cardinalSpaces);
            }
        }

        // if they scored, clear the pieces used to score
        if(totalPoints > 0) {
            clear(); // clear itself
            scoredSpaces.forEach(Space::clear); // clear all scored spaces
        }

        return totalPoints;
    }

    public void clear() {
        this.occupied = 0;
        turnOff();
    }

    public void turnOn(Color backgroundColor) {
        if(occupied == 0) {
            super.setEnabled(true);
            setBackground(backgroundColor);
        }
    }

    public void turnOff() {
        if(occupied == 0) {
            super.setEnabled(false);
            setBackground(Color.WHITE);
        }
    }

    public int isOccupied() {
        return this.occupied;
    }

    public void forAllNeighbors(SpaceFunction action) {
        // don't use the doInRadius from Util, to exclude the center (self)
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                if(x == 0 && y == 0) continue;
                action.runOn(board.getSpace(this.x + x, this.y + y));
            }
        }
    }


    interface SpaceFunction {
        void runOn(Space space);
    }
}
