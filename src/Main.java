import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        OutputWindow window = new OutputWindow();
//        try {
//            Thread.sleep(5000);
//        } catch (Exception ignored) { }
        window.addTextToWindow("Hello World", Color.BLACK);
        Player p1 = new Player("Aivin", 100, window);
        window.playerWager(p1);
    }
}
