import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player implements ActionListener  {
    // Class instance variables
    private String playerName;
    private int chips;
    private int score;
    private int playerWager;
    private OutputWindow window;
    private Ceelo ceelo;

    // turn instance variables
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Die die3 = new Die();

    // playerWager instance variables
    private JLabel name;
    private JTextField entryField;
    private JButton send;
    private JButton clear;
    private JLabel inputted;
    private JPanel inputField;
    private JPanel inputPanel;
    private JPanel combined;
    private JButton makeWager = new JButton("Make Wager");
    private int wager;

    public Player(String name, int chips, OutputWindow window, Ceelo ceelo) {
        this.playerName = name;
        this.chips = chips;
        this.window = window;
        this.ceelo = ceelo;
    }

    public String getName() {
        return playerName;
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
    public int getWager() {
        return playerWager;
    }

    public void getPlayerWager() {
        window.addTextToWindow(this.playerName + " to wager", Color.black);
        playerWager(window);
    }
    public void playerWager(OutputWindow window) {
        name = new JLabel(this.playerName + ": Enter your wager");
        entryField = new JTextField(15);

        // Submit button
        send = new JButton("Submit");
        send.addActionListener(this);

        inputted = new JLabel("Your Wager");

        // Field to input values
        inputField = new JPanel();
        inputField.add(name);
        inputField.add(entryField);
        inputField.add(send);

        // Panel to display inputted values
        inputPanel = new JPanel();
        inputPanel.add(inputted);

        combined = new JPanel();
        combined.add(inputField);
        combined.add(inputPanel);
        GridLayout layout = new GridLayout(2, 1);
        combined.setLayout(layout);
        window.frame.add(combined, BorderLayout.NORTH);

        window.frame.setVisible(true);
    }
    public int[] turn(Ceelo game) {
        boolean loop = true;
        int[] rolls = new int[3];
        while (loop) {
            int roll1 = die1.rollDie();
            int roll2 = die2.rollDie();
            int roll3 = die3.rollDie();
            rolls = new int[]{roll1, roll2, roll3};
            loop = false;
            if (roll1 == roll2 && roll2 == roll3) {
                score =  Integer.MAX_VALUE;
            } else if (roll1 == roll2) {
                score = roll3;
            } else if (roll2 == roll3) {
                score = roll1;
            } else if (roll1 == roll3) {
                score = roll2;
            } else if (roll1 + roll2 + roll3 == 15) {
                // checks if rolls are 4, 5, 6, only way to get 15 if all numbers are not equal, which is guaranteed because of above code
                score = Integer.MAX_VALUE;
            } else if (roll1 + roll2 + roll3 == 6) {
                // checks if rolls are 1, 2, 3, only way to get 6 if all numbers are not equal, which is guaranteed because of above code
                score = 0;
            } else {
                loop = true;
            }
        }
        return rolls;
    }
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        JButton srcButton = (JButton) source;
        if (srcButton == send) {
            String enteredText = entryField.getText();
            try {
                wager = Integer.parseInt(enteredText);
                if (wager > chips) {
                   inputted.setText("You do not have that many chips!");
                   wager = 0;
                } else {
                    inputted.setText("Wager: " + enteredText);
                    makeWager.addActionListener(ceelo);
                    makeWager.addActionListener(this);
                    inputPanel.add(makeWager);
                }
            } catch (Exception e) {
                inputted.setText("Enter an integer!");
            }
        } else if (srcButton == makeWager) {
            playerWager = wager;
            window.clear();
        }
    }
}
