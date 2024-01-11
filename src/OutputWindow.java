import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class OutputWindow implements ActionListener{
    // OutputWindow variables
    private JFrame frame;
    private JTextPane textPane;
    private StyledDocument doc;
    private Style style;

    // playerWager instance variables
    private JLabel name;
    private JTextField entryField;
    private JButton send;
    private JButton clear;
    private JLabel inputted;
    private JPanel inputField;
    private JPanel inputPanel;
    private JPanel combined;
    private int wager;
    private boolean makeWager;

    // Makes a window
    public OutputWindow() {
        frame = new JFrame("Cee-lo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1470, 956);
        frame.setLocation(500, 500);
        textPane = new JTextPane();
        textPane.setEditable(false);
        doc = textPane.getStyledDocument();
        style = doc.addStyle("my style", null);
        StyleConstants.setFontSize(style, 36);
        frame.add(textPane);
        frame.setVisible(true);
    }
    // Adds text
    public void addTextToWindow(String text, Color color) {
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style); }
        catch (Exception ignored) { }
    }
    public void playerWager(Player player) {
        name = new JLabel(player.getName() + ": Enter your wager");
        entryField = new JTextField(15);

        // Submit button
        send = new JButton("Submit");
        send.addActionListener(this);

        // Clear button
        clear = new JButton("Clear");
        clear.addActionListener(this);

        inputted = new JLabel("Wager");

        // Field to input values
        inputField = new JPanel();
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
        frame.add(combined, BorderLayout.NORTH);

        frame.setVisible(true);
    }
    public void clear() {
        textPane.setText("");
    }
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        JButton srcButton = (JButton) source;
        if (srcButton.getText().equals("Submit")) {
            String enteredText = entryField.getText();
            try {
                wager = Integer.parseInt(enteredText);
                inputted.setText(" Wager: " + enteredText);
                JButton makeWager = new JButton("Make Wager");
                makeWager.addActionListener(this);
                inputPanel.add(makeWager);
            } catch (Exception e) {
                inputted.setText("Enter an integer!");
            }
        } else if (srcButton.getText().equals("Clear")) {
            entryField.setText("");
            inputted.setText("");
        } else if (srcButton.getText().equals("Make Wager")) {
            inputted.setText("Test success");
            makeWager = true;
        }
    }
}