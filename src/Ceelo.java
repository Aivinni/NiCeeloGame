import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ceelo implements ActionListener {
    // Class instance variables
    private Player player1;
    private Player player2;
    private Player player3;
    private Banker banker;
    private OutputWindow window;
    private boolean matchOver;
    private boolean bankerMatchVictory;
    private Player matchWinner;
    private boolean gameOver;
    private boolean bankerVictory;
    private Player winner;

    // menu instance methods
    private JTextField Player1NameInput;
    private JTextField Player2NameInput;
    private JTextField Player3NameInput;
    private JPanel Player1Input;
    private JPanel Player2Input;
    private JPanel Player3Input;
    private JLabel Player1InputValue;
    private JLabel Player2InputValue;
    private JLabel Player3InputValue;
    private JPanel finalRow;
    private String player1Name;
    private String player2Name;
    private String player3Name;
    private JButton confirmPlayer1 = new JButton("Confirm Player 1 Name");
    private JButton confirmPlayer2 = new JButton("Confirm Player 2 Name");
    private JButton confirmPlayer3 = new JButton("Confirm Player 3 Name");
    private boolean player1Confirmed = false;
    private boolean player2Confirmed = false;
    private boolean player3Confirmed = false;
    private JButton confirmDone = new JButton("Confirm all?");
    // Wager methods instance variables
    private int playerToWager = 1;
    private boolean makingWagers = false;

    public Ceelo() {
        this.window = new OutputWindow();
    }

    public void bankerMatchVictory() {
        bankerMatchVictory = true;
        matchOver = true;
    }
    public void matchWinner(Player winner) {
        matchWinner = winner;
        matchOver = true;
    }

    public void play() {
        menu();
    }
    public void menu() {
        window.addTextToWindow("Welcome to Cee-Lo!", Color.black);
        JLabel Player1InputNamePrompt = new JLabel("Player 1: enter your name: ");
        Player1NameInput = new JTextField(15);
        JPanel Player1 = new JPanel();
        Player1.add(Player1InputNamePrompt);
        Player1.add(Player1NameInput);

        JLabel Player2InputNamePrompt = new JLabel("Player 2: enter your name: ");
        Player2NameInput = new JTextField(15);
        JPanel Player2 = new JPanel();
        Player2.add(Player2InputNamePrompt);
        Player2.add(Player2NameInput);

        JLabel Player3InputNamePrompt = new JLabel("Player 3: enter your name: ");
        Player3NameInput = new JTextField(15);
        JPanel Player3 = new JPanel();
        Player3.add(Player3InputNamePrompt);
        Player3.add(Player3NameInput);

        JButton Player1Button = new JButton("Submit Player 1 Name");
        Player1Button.addActionListener(this);

        JButton Player2Button = new JButton("Submit Player 2 Name");
        Player2Button.addActionListener(this);

        JButton Player3Button = new JButton("Submit Player 3 Name");
        Player3Button.addActionListener(this);

        Player1Input = new JPanel();
        Player1InputValue = new JLabel("Player 1 Name");
        Player1Input.add(Player1InputValue);

        Player2Input = new JPanel();
        Player2InputValue = new JLabel("Player 2 Name");
        Player2Input.add(Player2InputValue);

        Player3Input = new JPanel();
        Player3InputValue = new JLabel("Player 3 Name");
        Player3Input.add(Player3InputValue);

        GridLayout layout = new GridLayout(4, 3);
        JPanel nameInput = new JPanel();
        nameInput.setLayout(layout);
        nameInput.add(Player1);
        nameInput.add(Player2);
        nameInput.add(Player3);
        nameInput.add(Player1Button);
        nameInput.add(Player2Button);
        nameInput.add(Player3Button);
        nameInput.add(Player1Input);
        nameInput.add(Player2Input);
        nameInput.add(Player3Input);

        JPanel filler1 = new JPanel();
        finalRow = new JPanel();
        JPanel filler2 = new JPanel();

        nameInput.add(filler1);
        nameInput.add(finalRow);
        nameInput.add(filler2);

        window.frame.add(nameInput, BorderLayout.NORTH);

        window.frame.setVisible(true);
    }

    Thread wagerThread = new Thread(this::makeWagers);
    Thread matchThread = new Thread(() -> {
        try {
            // Wait for wagerThread to finish
            wagerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Success");
    });

    public void playMatch() {
        matchThread.start();
        wagerThread.start();
    }

    Thread wait = new Thread(() -> {
        while (makingWagers) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    Thread getWager = new Thread(() -> {
        player1.getPlayerWager();
    });
    public void makeWagers() {
        makingWagers = true;
        window.addTextToWindow("Player 1 wager: " + player1.getWager() + "\n", Color.black);
        window.addTextToWindow("Player 2 wager: " + player2.getWager() + "\n", Color.black);
        window.addTextToWindow("Player 3 wager: " + player3.getWager() + "\n", Color.black);

        getWager.start();
        wait.start();
        try {
            // Wait for both getWager and wait threads to finish
            getWager.join();
            wait.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        JButton srcButton = (JButton) source;
        if (srcButton.getText().equals("Submit Player 1 Name")) {
            String enteredText = Player1NameInput.getText();
            if (!enteredText.isEmpty()) {
                Player1InputValue.setText(enteredText);
                confirmPlayer1.addActionListener(this);
                Player1Input.add(confirmPlayer1);
                player1Name = enteredText;
            } else {
                Player1InputValue.setText("Enter a name!");
            }
        } else if (srcButton.getText().equals("Submit Player 2 Name")) {
            String enteredText = Player2NameInput.getText();
            if (!enteredText.isEmpty()) {
                Player2InputValue.setText(enteredText);
                confirmPlayer2.addActionListener(this);
                Player2Input.add(confirmPlayer2);
                player2Name = enteredText;
            } else {
                Player2InputValue.setText("Enter a name!");
            }
        } else if (srcButton.getText().equals("Submit Player 3 Name")) {
            String enteredText = Player3NameInput.getText();
            if (!enteredText.isEmpty()) {
                Player3InputValue.setText(enteredText);
                confirmPlayer3.addActionListener(this);
                Player3Input.add(confirmPlayer3);
                player3Name = enteredText;
            } else {
                Player3InputValue.setText("Enter a name!");
            }
        } else if (srcButton == confirmPlayer1) {
            player1 = new Player(player1Name, 100, window, this);
            player1Confirmed = true;
            Player1InputValue.setText("Confirmed");
            if (player2Confirmed && player3Confirmed) {
                confirmDone.addActionListener(this);
                finalRow.add(confirmDone);
            }
        } else if (srcButton == confirmPlayer2) {
            player2 = new Player(player2Name, 100, window, this);
            player2Confirmed = true;
            Player2InputValue.setText("Confirmed");
            if (player1Confirmed && player3Confirmed) {
                confirmDone.addActionListener(this);
                finalRow.add(confirmDone);
            }
        } else if (srcButton == confirmPlayer3) {
            player3 = new Player(player3Name, 100, window, this);
            player3Confirmed = true;
            Player3InputValue.setText("Confirmed");
            if (player1Confirmed && player2Confirmed) {
                confirmDone.addActionListener(this);
                finalRow.add(confirmDone);
            }
        } else if (srcButton.getText().equals("Confirm all?")) {
            window.clear();
            playMatch();
        } else if (srcButton.getText().equals("Make Wager")) {
            playerToWager++;
            if (playerToWager == 2) {
                window.addTextToWindow("Player 1 wager: " + player1.getWager() + "\n", Color.black);
                window.addTextToWindow("Player 2 wager: " + player2.getWager() + "\n", Color.black);
                window.addTextToWindow("Player 3 wager: " + player3.getWager() + "\n", Color.black);
                player2.getPlayerWager();
            } else if (playerToWager == 3) {
                window.addTextToWindow("Player 1 wager: " + player1.getWager() + "\n", Color.black);
                window.addTextToWindow("Player 2 wager: " + player2.getWager() + "\n", Color.black);
                window.addTextToWindow("Player 3 wager: " + player3.getWager() + "\n", Color.black);
                player3.getPlayerWager();
            } else if (playerToWager == 4) {
                playerToWager = 1;
                makingWagers = false;
            }
        }
    }
}
