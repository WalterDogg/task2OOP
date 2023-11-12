import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    public List<Player> players;
    public Dice dice;
    public List<Property> objectsToBuy = new ArrayList<>();

    public Game(List<Player> players, Dice dice,List<Property> objectsToBuy) {
        this.players = players;
        this.dice = dice;
        this.objectsToBuy=objectsToBuy;
    }
    public void dropDice(int numPlayer, JTextArea field, int diceNumber){
        Player player = players.get(numPlayer);
        field.setText("Ход игрока " + numPlayer);
        int position = player.getPosition();

        if (position + diceNumber >= 40) {
            player.setPosition(position + diceNumber - 40);
        } else {
            player.setPosition(position + diceNumber);
        }
        field.setText(field.getText()+"\n"+"Выпало: " + diceNumber+"\n"+"Идите на " + player.getPosition()+" "+"\n");
    }
    public void play(int numPlayer, JTextArea field) {
        Player player = players.get(numPlayer);
        Scanner scanner = new Scanner(System.in);
        int position = player.getPosition();

        Property prop = objectsToBuy.get(position);

        switch (prop.getName()) {
            case 0 -> {
                player.setMoney(player.getMoney() + 100);
                field.setText(field.getText() + "Вы попали стартовую клетку, вам начислено 100 ");
                return;
            }
            case 10 -> {
                field.setText(field.getText() + "Вы посетили тюрьму, но не сели ");
                if (player.getCountOfJail() > 0) {
                    Object[] options = {"dice", "buyback"};
                    int result = JOptionPane.showOptionDialog(null, "Кинуть кубик или выкупиться? (dice, buyback) ","Что хотите?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (result == 0) {
                        jail(player, field);
                    } else {
                        player.setMoney(player.getMoney() - 100);
                        player.setCountOfJail(0);
                    }
                    if (player.getCountOfJail() == 4) {
                        player.setCountOfJail(0);
                    }
                }
                return;
            }
            case 20 -> {
                Object[] options = {"yes", "no"};
                int result = JOptionPane.showOptionDialog(null, "Вы попали в казино, хотите сыграть? (yes, no) ", "Что хотите?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    field.setText(field.getText() + "Выберете 2 цифры ");
                    int numCas1 = scanner.nextInt();
                    int numCas2 = scanner.nextInt();
                    casino(player, numCas1, numCas2);
                } else {
                    return;
                }
            }
            case 30 -> {
                player.setCountOfJail(1);
                field.setText(field.getText() + "Вы попали в тюрьму! ");
                player.setPosition(10);
                return;
            }
        }




        if (prop.getNumOfOwnedPlayer() != -1 && prop.getNumOfOwnedPlayer() != numPlayer) {
            player.setMoney(player.getMoney() - prop.getCost());
            players.get(prop.getNumOfOwnedPlayer()).setMoney(players.get(prop.getNumOfOwnedPlayer()).getMoney()+prop.getCost());
            field.setText(field.getText()+"Вы попали на чужую клетку! "+"\n");
        }
        if (prop.getNumOfOwnedPlayer() == numPlayer) {
            Object[] options = {"grade", "no"};
            int result = JOptionPane.showOptionDialog(null, "Хотите улучшить постройку? ","Что хотите?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                player.grade(prop);
            }
        }
        if (prop.getNumOfOwnedPlayer() == -1 && prop.getName() != 0 && prop.getName() != 10 && prop.getName() != 20 && prop.getName() != 30) {
            Object[] options = {"buy", "sell"};
            int result = JOptionPane.showOptionDialog(null, "Вы хотите купить или продать ","Что хотите?",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                player.choice("buy", prop);
            } else {
                player.choice("sell", prop);
                }
        }


        field.setText(field.getText()+"Ход закончен");
    }

    public void jail(Player player, JTextArea field) {
        int dice1 = dice.createNumber();
        int dice2 = dice.createNumber();
        field.setText(field.getText()+"Выпало: "+dice1+" "+dice2+"\n");
        player.setCountOfJail(player.getCountOfJail() + 1);
        if (dice1 == dice2) {
            player.setCountOfJail(0);
            field.setText(field.getText()+"Вы освободились из тюрьмы "+"\n");
        }else {
            field.setText(field.getText()+"Вы остались в тюрьме, осталось "+ (4-player.getCountOfJail())+" ходов"+"\n");
        }
    }

    public void casino(Player player, int numCas1, int numCas2) {
        int diceNum = dice.createNumber();
        if (diceNum == numCas1 || diceNum == numCas2) {
            player.setMoney(player.getMoney() + 100);
        } else {
            player.setMoney(player.getMoney() - 100);
        }
    }

    public boolean checkLose(Player player) {
       if(player.getMoney() <= 0){
           List<Property> prop = player.getObjects();
           for (Property property : prop) {
               property.setColor(Color.GRAY);
               property.setNumOfOwnedPlayer(-1);
           }
           return true;
       }else {
           return false;
       }
    }

    public boolean checkWin() {
        int count = 0;
        for (Player player : players) {
            if (player.getMoney() <= 0) {
                count++;
            }
        }
        return count == 3;
    }
}
