import java.util.Random;

public class Dice {
    public int number;
    public Random  random = new Random();
    public Dice(int number) {
        this.number = number;
    }
    public int createNumber (){
        return (random.nextInt(number)+1);
    }
}
