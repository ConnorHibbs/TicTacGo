package hibbscm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        setBackground(playerColors.get(player));
    }

    public int getPoints() {
        // TODO check every direction
        // TODO start counter for points at 1 (for self)
        // TODO keep going in that direction while the space is yours
        // TODO add up points, subtract 3

        System.out.println("Checking (" + (x) + ", " + (y) + ") for points...");

        // assume 0 points scored on any given turn
        int totalPoints = 0;

        // The four cardinal direction pairs to check for points in ( \ | / - )
        int[][][] directions3 = {{NW, SE}, {N, S}, {NE, SW}, {E, W}};

        for(int cardinal = 0; cardinal < 4; cardinal++) {
            // Start points off with 1 (for the space just occupied)
            int cardinalPoints = 1;

            // check in the two possible directions for each cardinal pair, and add on points for those encountered
            for(int direction = 0; direction < 2; direction++) {
                int xOffset = directions3[cardinal][direction][0];
                int yOffset = directions3[cardinal][direction][1];

                Space neighbor = board.getSpace(x + xOffset, y + yOffset);
                System.out.println("\tChecking (" + (x + xOffset) + ", " + (y + yOffset) + ")...");

                while(neighbor.occupied == occupied) {
                    System.out.println("Found match at (" + (x + xOffset) + ", " + (y + yOffset) + ")");
                    cardinalPoints++;
                    neighbor = board.getSpace(neighbor.x + xOffset, neighbor.y + yOffset);
                }
            }

            // if they have at least 3 in a row, give them a point for every set of 3
            // this is equal to the total in that row - 2
            if(cardinalPoints >= 3) {
                totalPoints += cardinalPoints - 2;
            }

            System.out.println("Total matches for this cardinal: " + cardinalPoints);
        }

        return totalPoints;
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

    public void forAllNeighbors(SpaceFunction action) {
        for(int y = -1; y <= 1; y++) {
            for(int x = -1; x <= 1; x++) {
                Space space = board.getSpace(this.x + x, this.y + y);
                action.runOn(space);
            }
        }
    }


    interface SpaceFunction {
        void runOn(Space space);
    }
}
