import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame extends JFrame {
    static final int FORM_WIDTH = 1920;
    static final int FORM_HEIGHT = 1080;
    private JPanel mainPanel;
    private int count=0;
    private JButton start;
    private JTextField textField2;

    private JTextArea textArea2;
    private JButton operation;

    private JPanel panel2;
    private JPanel jPanel;
    private JPanel leftPanel;
    private JPanel upperPanel;

    private JTextArea textArea1;

    private final List<Property> props = new ArrayList<>();
    public List<Integer> losers = new ArrayList<>();
    public Point pointStart = new Point(0,0);
    public List<Player> players = new ArrayList<>();
    public int pos02maxX=pointStart.x + 10+800;
    public int pos01maxY=pointStart.y + 10+700;
    public int pos23maxY=pointStart.y+45+700;
    public int pos13maxX=pointStart.x+55+800;


    Frame() {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(FORM_WIDTH, FORM_HEIGHT));
        setSize(FORM_WIDTH, FORM_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setContentPane(mainPanel);
        setVisible(true);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(460, 1080));
        mainPanel.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(textArea1, BorderLayout.WEST);
        textArea1.setFont(new Font("Dialog", Font.PLAIN, 10));
        textArea1.setEditable(false);

        Panel jPanel = new Panel();
        mainPanel.add(jPanel, BorderLayout.CENTER);

        JPanel upperPanel= new JPanel();
        upperPanel.setPreferredSize(new Dimension(460, 1080));
        mainPanel.add(upperPanel, BorderLayout.EAST);
        upperPanel.add(textField2);
        upperPanel.add(start);
        textField2.setFont(new Font("Dialog", Font.PLAIN, 10));
        textField2.setEditable(false);

        JPanel panel2= new JPanel();
        panel2.setPreferredSize(new Dimension(460, 1080));
        upperPanel.add(panel2, BorderLayout.SOUTH);
        panel2.add(operation,BorderLayout.NORTH);
        panel2.add(textArea2, BorderLayout.SOUTH);
        textArea2.setFont(new Font("Dialog", Font.PLAIN, 10));
        textArea2.setEditable(false);

        Dice dice = new Dice(6);

        Game game = new Game(players,dice,props);
        int k=22;
        for(int i=0;i<40;i++){
            Property prop = null;
            if (i<=9){
                prop= new Property(i,100,pointStart.x+80*i,(pointStart.y+10),70,60);
            }
            if(i>=10&&i<=20){
                prop= new Property(i,100,pointStart.x+800,(pointStart.y+10)+70*(i-10),70,60);
            }
            if(i>=21&&i<=30){
                prop= new Property(i,100,pointStart.x+80*Math.abs(i-30),pointStart.y+710,70,60);
            }
            if(i>=31){
                prop= new Property(i,100,pointStart.x+10,(pointStart.y+10)+70*(Math.abs(i-k)),70,60);
                k+=2;
            }
            props.add(prop);
        }

        List<Property> objects0 = new ArrayList<>();
        Player player0 = new Player(objects0,0,1000, new int[]{pointStart.x + 10, pointStart.y + 10, 25, 25}, Color.red);
        players.add(player0);

        List<Property> objects1 = new ArrayList<>();
        Player player1 = new Player(objects1,1,1000, new int[]{pointStart.x+55,pointStart.y+10,25,25},Color.blue);
        players.add(player1);

        List<Property> objects2 = new ArrayList<>();
        Player player2 = new Player(objects2,2,1000, new int[]{pointStart.x+10,pointStart.y+45,25,25},Color.green);
        players.add(player2);

        List<Property> objects3 = new ArrayList<>();
        Player player3 = new Player(objects3,3,1000, new int[]{pointStart.x+55,pointStart.y+45,25,25},Color.yellow);
        players.add(player3);
        operation.setEnabled(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textField2.setText("Играет игрок: "+ count);
                int numOfFirstDice =dice.createNumber();
                int numOfSecondDice =dice.createNumber();
                int diceNumber = numOfFirstDice + numOfSecondDice;
                Player player = players.get(count);
                int pos=player.getPosition();
                if(player.getCountOfJail()==0){
                    game.dropDice(count,textArea2, diceNumber);
                    panel2.setVisible(true);
                    switch (count) {
                        case 0 -> changeCoordOfPlayer(player, pos, diceNumber, pos02maxX, pos01maxY);
                        case 1 -> changeCoordOfPlayer(player, pos, diceNumber, pos13maxX, pos01maxY);
                        case 2 -> changeCoordOfPlayer(player, pos, diceNumber, pos02maxX, pos23maxY);
                        case 3 -> changeCoordOfPlayer(player, pos, diceNumber, pos13maxX, pos23maxY);
                    }
                }else{
                    textField2.setText("Ход игрока " + count+", находится в тюрьме");
                }
                start.setEnabled(false);
                operation.setEnabled(true);
            }
        });


        operation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.play(count, textArea2);
                List<Property> propsPlayers0=player0.getObjects();
                List<Integer> propPlay0 = createListOfPropsPlayer(propsPlayers0);

                List<Property> propsPlayers1=player1.getObjects();
                List<Integer> propPlay1 = createListOfPropsPlayer(propsPlayers1);

                List<Property> propsPlayers2=player2.getObjects();
                List<Integer> propPlay2 = createListOfPropsPlayer(propsPlayers2);

                List<Property> propsPlayers3=player3.getObjects();
                List<Integer> propPlay3 = createListOfPropsPlayer(propsPlayers3);


                textArea1.setText("Игрок: "+0+ Arrays.toString(propPlay0.toArray())+", "+player0.getMoney()+"\n"+"Игрок: "+1+ Arrays.toString(propPlay1.toArray())+", "+player1.getMoney()+"\n"+"Игрок: "+2+ Arrays.toString(propPlay2.toArray())+", "+player2.getMoney()+"\n"+"Игрок: "+3+ Arrays.toString(propPlay3.toArray())+", "+player3.getMoney());
                if(game.checkLose(players.get(count))){
                    losers.add(count);
                    JOptionPane.showMessageDialog(null,"Игрок "+count +" проиграл");
                }
                if(props.get(players.get(count).getPosition()).getNumOfOwnedPlayer()==count){
                    props.get(players.get(count).getPosition()).setColor(players.get(count).getColor());
                }

                count++;
                if(count==4){
                    count=0;
                }
                while (losers.contains(count)){
                    if(count==4){
                        count=0;
                    }
                    count++;
                }
                if(game.checkWin()){
                    for (Player player : players) {
                        if (player.getMoney()>0){
                            JOptionPane.showMessageDialog(null,"Игрок "+player.getNumOfPlayer()+" Выиграл");
                        }
                    }
                    start.setEnabled(false);
                    operation.setEnabled(false);
                }
                start.setEnabled(true);
                operation.setEnabled(false);
            }

        });

    }

    class Panel extends JPanel {
        Panel() {
            setPreferredSize(new Dimension(900, 900));

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i=0; i<=9;i++){
                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x+80*i,pointStart.y,80,80);

                g.setColor(props.get(i).getColor().darker());
                g.fillRect(props.get(i).getX(),props.get(i).getY(),props.get(i).getWeight(),props.get(i).getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(String.valueOf(i),props.get(i).getX()+10,props.get(i).getY()+30);

            }
            for (int i=0; i<=10;i++){
                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x+790,pointStart.y+70*i,90,80);
                g.setColor(props.get(i+10).getColor().darker());
                g.fillRect(props.get(i+10).getX(),props.get(i+10).getY(),props.get(i+10).getWeight(),props.get(i+10).getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(String.valueOf(i+10),props.get(i+10).getX()+10,props.get(i+10).getY()+30);

            }
            int k1=38;

            for (int i=1; i<=9;i++){

                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x,pointStart.y+70*i,90,90);
                g.setColor(props.get(i+k1).getColor().darker());
                g.fillRect(pointStart.x+10,(pointStart.y+10)+70*i,70,60);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(String.valueOf(i+k1),props.get(i+k1).getX()+10,props.get(i+k1).getY()+30);

                k1-=2;
            }
            int k=12;

            for (int i=9; i>=0;i--){

                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x+80*i,pointStart.y+700,80,80);
                g.setColor(props.get(i+k).getColor().darker());
                g.fillRect(props.get(i+k).getX(),props.get(i+k).getY(),props.get(i+k).getWeight(),props.get(i+k).getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(String.valueOf(i+k),props.get(i+k).getX()+10,props.get(i+k).getY()+30);
                k+=2;
            }
            g.setColor(Color.RED);
            g.fillOval(players.get(0).getCoordinate()[0],players.get(0).getCoordinate()[1],players.get(0).getCoordinate()[2],players.get(0).getCoordinate()[3]);

            g.setColor(Color.BLUE);
            g.fillOval(players.get(1).getCoordinate()[0],players.get(1).getCoordinate()[1],players.get(1).getCoordinate()[2],players.get(1).getCoordinate()[3]);

            g.setColor(Color.GREEN);
            g.fillOval(players.get(2).getCoordinate()[0],players.get(2).getCoordinate()[1],players.get(2).getCoordinate()[2],players.get(2).getCoordinate()[3]);

            g.setColor(Color.YELLOW);
            g.fillOval(players.get(3).getCoordinate()[0],players.get(3).getCoordinate()[1],players.get(3).getCoordinate()[2],players.get(3).getCoordinate()[3]);
            repaint();
        }

    }

    public List<Integer> createListOfPropsPlayer(List<Property> propsPlayers0){
        List<Integer> propPlay0 = new ArrayList<>();
        for (Property property : propsPlayers0) {
            propPlay0.add(property.getName());
        }
        return propPlay0;
    }

    public void changeCoordOfPlayer(Player player, int pos, int diceNumber, int posMaxX, int posMaxY){
        int[] coord;
        if(player.getPosition()<11&&pos>=0){
            coord = new int[]{player.getCoordinate()[0] + (diceNumber * 80)-10, player.getCoordinate()[1] , player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()<11&&pos>=28){
            coord = new int[]{posMaxX-820+(player.getPosition()*80)+10, posMaxY-700, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()>=11 &&player.getPosition()<21 && pos<11){
            coord = new int[]{posMaxX-10, player.getCoordinate()[1]  + ((player.getPosition()-10)*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()>=11 &&player.getPosition()<21 && pos>=11){
            coord = new int[]{player.getCoordinate()[0], player.getCoordinate()[1]  + (diceNumber*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()>=21&& player.getPosition()<31&& pos<21){
            coord = new int[]{player.getCoordinate()[0] - ((player.getPosition()-20)*80), posMaxY, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=21&& player.getPosition()<30&& pos>=21){
            coord = new int[]{player.getCoordinate()[0] - (diceNumber * 80), player.getCoordinate()[1], player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()==30){
            coord=new int[]{posMaxX,posMaxY-700, player.getCoordinate()[2],player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()>=31&& player.getPosition()<=39&& pos<30){
            coord = new int[]{posMaxX-800, player.getCoordinate()[1]  - ((player.getPosition()-30)*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=31&& player.getPosition()<=39&& pos>=31){
            coord = new int[]{posMaxX-800,  player.getCoordinate()[1]  - (diceNumber*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
    }
}