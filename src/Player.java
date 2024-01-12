import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player implements ActionListener  {
    private String playerName;
    private int chips;
    private int score;
    private int playerWager;
    private OutputWindow window;

    // turn instance variables
    private Die die1 = new Die();
    private Die die2 = new Die();
    private Die die3 = new Die();
    private int roll1;
    private int roll2;
    private int roll3;

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

    public Player(String name, int chips, OutputWindow window) {
        this.playerName = name;
        this.chips = chips;
        this.window = window;
    }

    public String getName() {
        return playerName;
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

    public void turn(Ceelo game) {
        window.addTextToWindow(this.name + " to wager", Color.black);
        this.playerWager(window);
        window.clear();
        roll1 = die1.rollDie();
        roll2 = die2.rollDie();
        roll3 = die3.rollDie();
        if (roll1 == roll2 && roll2 == roll3) {

        }
    }

    public void playerWager(OutputWindow window) {
        name = new JLabel(this.playerName + ": Enter your wager");
        entryField = new JTextField(15);

        // Submit button
        send = new JButton("Submit");
        send.addActionListener(this);

        // Clear button
        clear = new JButton("Clear");
        clear.addActionListener(this);

        inputted = new JLabel("Your Wager");

        // Field to input values
        inputField = new JPanel();
        inputField.add(name);
        inputField.add(name);
        inputField.add(entryField);
        inputField.add(send);
        inputField.add(clear);

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

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        JButton srcButton = (JButton) source;
        if (srcButton.getText().equals("Submit")) {
            String enteredText = entryField.getText();
            try {
                wager = Integer.parseInt(enteredText);
                if (wager > chips) {
                   inputted.setText("You do not have that many chips!");
                   wager = 0;
                } else {
                    inputted.setText("Wager: " + enteredText);
                    makeWager.addActionListener(this);
                    inputPanel.add(makeWager);
                }
            } catch (Exception e) {
                inputted.setText("Enter an integer!");
            }
        } else if (srcButton.getText().equals("Clear")) {
            entryField.setText("");
            inputted.setText("");
            inputPanel.remove(makeWager);
        } else if (srcButton.getText().equals("Make Wager")) {
            playerWager = wager;
        }
    }
}
