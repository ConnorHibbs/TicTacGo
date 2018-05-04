package hibbscm;

public class Driver {

    private int currentPlayer;
    private UI ui;
    public enum DurationMethod {TURNS, SCORE, INFINITE}

    public Driver() {
        UI ui = new UI(this);
        ui.setVisible(true);
        this.ui = ui;
        startGame();
    }

    public void startGame() {
        currentPlayer = 1;
        ui.startGame();
    }

    public void advanceTurn() {
        System.out.print("Changing the turn from " + currentPlayer);
        currentPlayer++;
        if(currentPlayer > Settings.getInstance().numPlayers) currentPlayer = 1;
        System.out.println(" to " + currentPlayer);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
    }
}
