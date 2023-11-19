import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    public List<Property> objects;
    public int numOfPlayer;
    public int position;
    public int money;
    public int[] coordinate;
    public Color color;
    public int countOfJail;

    public Player(List<Property> objects,int numOfPlayer, int money,int[] coordinate,Color color) {
        this.objects = objects;
        this.numOfPlayer=numOfPlayer;
        this.money = money;
        this.countOfJail=0;
        this.coordinate=coordinate;
        this.color=color;
    }
    public int getCountOfJail() {
        return countOfJail;
    }
    public int[] getCoordinate() {
        return coordinate;
    }
    public int getPosition() {
        return position;
    }
    public int getNumOfPlayer() {
        return numOfPlayer;
    }
    public Color getColor() {
        return color;
    }
    public int getMoney() {
        return money;
    }
    public List<Property> getObjects() {
        return objects;
    }
    public void setCountOfJail(int countOfJail) {
        this.countOfJail = countOfJail;
    }
    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public void choice(String answer, Property prop){
        if(answer.equals("buy")){
            buyObject(prop);
        }
        if(answer.equals("sell")){
            sellObject(prop);
        }
        if(answer.equals("change")){
            changeObjects(prop);
        }
    }
    public void buyObject(Property prop){
        if (prop.getNumOfOwnedPlayer()==-1){
            prop.setNumOfOwnedPlayer(numOfPlayer);
            objects.add(prop);
            money-=prop.getCost();
        }else{

        }
    }
    public void sellObject (Property prop){
        if (prop.getNumOfOwnedPlayer()==numOfPlayer){
            money+=prop.getCost();
            prop.numOfOwnedPlayer=0;
            objects.remove(prop);
            prop.setNumOfOwnedPlayer(-1);
            prop.setColor(Color.GRAY);
        }
    }
    public void changeObjects (Property prop){

    }
    public void gradeObject(Property prop){
        if(prop.getGradeCount()<4){
            prop.setGradeCount(prop.getGradeCount()+1);
            prop.setCost(prop.getCost()*2);
        }else{
            JOptionPane.showMessageDialog(null,"Максимальная степень улучшения!");
        }

    }

}
