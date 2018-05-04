package hibbscm;

import java.util.HashMap;
import java.util.Map;

public class Board {


    private Map<Coordinate, Space> boardMap;

    /** The current occupied space on the board */
    private Space currentSpace;


    /** Can do any actions on dummy space, and nobody can see */
    private Space dummySpace = new Space(this, 0, 0);

    private UI ui;

    public Board(UI ui) {
        this.ui = ui;
        boardMap = new HashMap<>();
    }

    public Space newSpace(int x, int y) {
        Space space = new Space(this, x, y);
        boardMap.put(new Coordinate(x, y), space);

        return space;
    }

    public Space getSpace(int x, int y) {
        return getSpace(new Coordinate(x, y));
    }

    public Space getSpace(Coordinate coordinate) {
        Space space = boardMap.get(coordinate);
        return space != null ? space : dummySpace;
    }

    public Space getCurrentSpace() {
        return currentSpace;
    }

    public void setCurrentSpace(Space space) {
        this.currentSpace = space;
    }

}
