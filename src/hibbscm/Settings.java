package hibbscm;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Settings settings = new Settings();
    public enum DurationMethod {TURNS, SCORE, INFINITE}

    int numPlayers;
    DurationMethod durationMethod;
    int numTurns;
    int maxScore;
    String name;
    Color playerColor;

    /** Map of colors for each player */
    private Map<Integer, Color> playerColors = new HashMap<>();

    private Settings() {
        playerColors.put(0, Color.WHITE);
        playerColors.put(-1, Color.BLACK);
        playerColors.put(1, Color.RED);
        playerColors.put(2, Color.BLUE);
        playerColors.put(3, Color.GREEN);
    }

    public Color getPlayerColor(int playerNum) {
        return playerColors.get(playerNum);
    }

    public static Settings getInstance() {
        return settings;
    }
}
