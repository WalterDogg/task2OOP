import java.awt.*;
import java.util.List;

public class Property {
    public int name;
    public int cost;
    public int x;


    public int y;
    public int weight;
    public int height;
    public Color color;
    public int gradeCount;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public void setNumOfOwnedPlayer(int numOfOwnedPlayer) {
        this.numOfOwnedPlayer = numOfOwnedPlayer;
    }

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }
    public int getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getNumOfOwnedPlayer() {
        return numOfOwnedPlayer;
    }

    public int numOfOwnedPlayer;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Property(int name, int cost,int x, int y, int weight, int height) {
        this.name = name;
        this.cost = cost;
        this.numOfOwnedPlayer = -1;
        this.x=x;
        this.y=y;
        this.weight=weight;
        this.height=height;
        this.color=Color.GRAY;
        this.gradeCount=0;
    }

}
