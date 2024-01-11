import java.awt.Color;

public class Player {
    private String name;
    private int chips;
    private int score;
    private int wager;
    private OutputWindow window;

    public Player(String name, int chips, OutputWindow window) {
        this.name = name;
        this.chips = chips;
        this.window = window;
    }

    public void setWager(int wager) {
        this.wager = wager;
    }
    public String getName() {
        return name;
    }
    public int getChips() {
        return chips;
    }
    public int getScore() {
        return score;
    }
    public int getWager() {
        return wager;
    }

    public void turn() {
        window.addTextToWindow(this.name + " to wager", Color.black);

    }
}
