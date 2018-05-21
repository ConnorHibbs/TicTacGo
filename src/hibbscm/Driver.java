package hibbscm;

public class Driver {

    private int currentPlayer;
    private UI ui;

    public Driver() {
        UI ui = new UI(this);
        ui.setVisible(true);
        this.ui = ui;
        startGame();
    }

    public void startGame() {
        currentPlayer = 1;
        ui.requestSettings();
        ui.startGame();
    }

    public void advanceTurn() {
        currentPlayer++;
        if(currentPlayer > Settings.getInstance().numPlayers) currentPlayer = 1;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void main(String[] args) {

//        new GameManager().sendPlay();
        Driver driver = new Driver();
    }
}
