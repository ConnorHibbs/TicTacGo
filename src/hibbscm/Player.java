package hibbscm;

import java.awt.*;

public class Player {

    private String name;
    private int playerNum;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.playerNum = number;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return playerNum;
    }

    public Color getColor() {
        return color;
    }
}
