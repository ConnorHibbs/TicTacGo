package hibbscm;

public class Driver {

    private int currentPlayer;
    private UI ui;
    private Settings settings;
    public enum DurationMethod {TURNS, SCORE}

    public Driver() {
        UI ui = new UI(this);
        ui.setVisible(true);
        this.ui = ui;
        this.settings = ui.getSettings();
        startGame();
    }

    public void startGame() {
        currentPlayer = 1;
        ui.startGame();
    }

    public void advanceTurn() {
        currentPlayer = (currentPlayer + 1) % settings.numPlayers + 1;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
    }

    static class Settings {
        int numPlayers;
        DurationMethod durationMethod;
        int numTurns;
        int maxScore;
    }
}
