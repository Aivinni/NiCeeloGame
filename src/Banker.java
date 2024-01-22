import java.awt.Color;

public class Banker {
    // Class instance variables
    private OutputWindow window;
    private int chips;
    private int score;
    // turn instance variables
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Die die3 = new Die();

    public Banker(OutputWindow window) {
        this.window = window;
        chips = 1000;
    }
    public void setChips(int newChips) {
        chips = newChips;
    }
    public int getChips() {
        return chips;
    }
    public int getScore() {
        return score;
    }

    public int[] turn(Ceelo game) {
        boolean loop = true;
        int[] rolls = new int[3];
        while (loop) {
            int roll1 = die1.rollDie();
            int roll2 = die2.rollDie();
            int roll3 = die3.rollDie();
//            int roll1 = 4;
//            int roll2 = 5;
//            int roll3 = 6;
            rolls = new int[]{roll1, roll2, roll3};
            loop = false;
            if (roll1 == roll2 && roll2 == roll3) {
                game.bankerMatchVictory();
                score = Integer.MAX_VALUE;
            } else if (roll1 == roll2) {
                score = roll3;
            } else if (roll2 == roll3) {
                score = roll1;
            } else if (roll1 == roll3) {
                score = roll2;
            } else if (roll1 + roll2 + roll3 == 15) {
                // checks if rolls are 4, 5, 6, which is the only way to get 15 if all numbers are not equal, which is guaranteed because of above code
                game.bankerMatchVictory();
                score = Integer.MAX_VALUE;
            } else if (roll1 + roll2 + roll3 == 6) {
                // checks if rolls are 1, 2, 3, which is the only way to get 6 if all numbers are not equal, which is guaranteed because of above code
                score = 0;
            } else {
                loop = true;
            }
        }
        return rolls;
    }
}
