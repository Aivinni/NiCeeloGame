public class Die {
    private int roll;

    public Die() {}

    public int rollDie() {
        roll = (int) ((Math.random() * 6) + 1);
        return roll;
    }

    public int getRoll() {
        return roll;
    }
}
