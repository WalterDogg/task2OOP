import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

    private final List<Property> props = new ArrayList<>();
    public List<Integer> losers = new ArrayList<>();
    public Point pointStart = new Point(500,50);
    public List<Player> players = new ArrayList<>();
    public int pos02maxX=pointStart.x + 10+800;
    public int pos01maxY=pointStart.y + 10+700;
    public int pos23maxY=pointStart.y+45+700;
    public int pos13maxX=pointStart.x+55+800;



    Frame() {
        Panel jPanel = new Panel();
        setSize(FORM_WIDTH, FORM_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(jPanel);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(100, 300));
//
//            add(textField2);
//            JPanel panel2= new JPanel();
//            add(panel2);
//            panel2.add(operation);
//            panel2.add(textArea2);
//            panel2.setVisible(false);
//            textField2.setText("0");
            add(start);


        Dice dice = new Dice(6);

        Game game = new Game(players,dice,props);
        int k=22;
        for(int i=0;i<40;i++){
            Property prop = null;
            if (i<=9){
                prop= new Property(i,100,-1,pointStart.x+80*i,(pointStart.y+10),70,60, Color.GRAY);
            }
            if(i>=10&&i<=20){
                prop= new Property(i,100,-1,pointStart.x+800,(pointStart.y+10)+70*(i-10),70,60, Color.GRAY);
            }
            if(i>=21&&i<=30){
                prop= new Property(i,100,-1,pointStart.x+80*Math.abs(i-30),pointStart.y+710,70,60, Color.GRAY);
            }
            if(i>=31){
                prop= new Property(i,100,-1,pointStart.x+10,(pointStart.y+10)+70*(Math.abs(i-k)),70,60, Color.GRAY);
                k+=2;
            }
            props.add(prop);
        }
        List<Property> objects0 = new ArrayList<>();
        Player player0 = new Player(objects0,0,1000,0, new int[]{pointStart.x + 10, pointStart.y + 10, 25, 25}, Color.red);
        players.add(player0);

        List<Property> objects1 = new ArrayList<>();
        Player player1 = new Player(objects1,1,1000,0, new int[]{pointStart.x+55,pointStart.y+10,25,25},Color.blue);
        players.add(player1);

        List<Property> objects2 = new ArrayList<>();
        Player player2 = new Player(objects2,2,1000,0, new int[]{pointStart.x+10,pointStart.y+45,25,25},Color.green);
        players.add(player2);

        List<Property> objects3 = new ArrayList<>();
        Player player3 = new Player(objects3,3,1000,0, new int[]{pointStart.x+55,pointStart.y+45,25,25},Color.yellow);
        players.add(player3);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField2.setText("Играет игрок: "+ count);
                int diceNumber = dice.createNumber() + dice.createNumber();
                Player player = players.get(count);
                int pos=player.getPosition();
                game.dropDice(count,textArea2, diceNumber);
                panel2.setVisible(true);
                Property prop = props.get(player.getPosition());
                if (count==0){
                    changeCoordOfPlayer(player,pos,diceNumber,pos02maxX,pos01maxY);
                }
                if (count==1){
                    changeCoordOfPlayer(player,pos,diceNumber,pos13maxX,pos01maxY);
                }
                if (count==2){
                    changeCoordOfPlayer(player,pos,diceNumber,pos02maxX,pos23maxY);
                }
                if (count==3){
                    changeCoordOfPlayer(player,pos,diceNumber,pos13maxX,pos23maxY);
                }

            }
        });


        operation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.play(count, textArea2);
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
                }

            }

        });

    }

    class Panel extends JPanel {
        Panel() {
            setPreferredSize(new Dimension(800, 800));

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int name = 0;
            Point pointOLength = new Point(150,150);

            for (int i=0; i<=9;i++){
                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x+80*i,pointStart.y,80,80);

                g.setColor(props.get(i).getColor().darker());
                g.fillRect(props.get(i).getX(),props.get(i).getY(),props.get(i).getWeight(),props.get(i).getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(Integer.toString(i),props.get(i).getX()+10,props.get(i).getY()+30);
            }
            for (int i=0; i<=10;i++){
                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x+790,pointStart.y+70*i,90,80);
                g.setColor(props.get(i+10).getColor().darker());
                g.fillRect(props.get(i+10).getX(),props.get(i+10).getY(),props.get(i+10).getWeight(),props.get(i+10).getHeight());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(Integer.toString(i+10),props.get(i+10).getX()+10,props.get(i+10).getY()+30);

            }
            int k1=38;

            for (int i=1; i<=9;i++){

                g.setColor(Color.BLACK);
                g.fillRect(pointStart.x,pointStart.y+70*i,90,90);
                g.setColor(props.get(i+k1).getColor().darker());
                g.fillRect(pointStart.x+10,(pointStart.y+10)+70*i,70,60);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Dialog", Font.PLAIN, 30));
                g.drawString(Integer.toString(i+k1),props.get(i+k1).getX()+10,props.get(i+k1).getY()+30);

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
                g.drawString(Integer.toString(i+k),props.get(i+k).getX()+10,props.get(i+k).getY()+30);
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
    public void changeCoordOfPlayer(Player player, int pos, int diceNumber, int posMaxX, int posMaxY){
        int[] coord;
        if(player.getPosition()<11&&pos>=0){
            coord = new int[]{player.getCoordinate()[0] + (diceNumber * 80), player.getCoordinate()[1] , player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()<11&&pos>=28){
            coord = new int[]{posMaxX-800+(player.getPosition()*80)+10, posMaxY-700, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=11 &&player.getPosition()<21 && pos<11){
            coord = new int[]{posMaxX, player.getCoordinate()[1]  + ((player.getPosition()-10)*70)+10, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
        if(player.getPosition()>=11 &&player.getPosition()<21 && pos>=11){
            coord = new int[]{player.getCoordinate()[0], player.getCoordinate()[1]  + (diceNumber*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=21&& player.getPosition()<31&& pos<21){
            coord = new int[]{player.getCoordinate()[0] - ((player.getPosition()-20)*80)+10, posMaxY, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=21&& player.getPosition()<31&& pos>=21){
            coord = new int[]{player.getCoordinate()[0] - (diceNumber * 80), player.getCoordinate()[1], player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=31&& player.getPosition()<=39&& pos<31){
            coord = new int[]{posMaxX-800, player.getCoordinate()[1]  - ((player.getPosition()-30)*70)+10, player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }

        if(player.getPosition()>=31&& player.getPosition()<=39&& pos>=31){
            coord = new int[]{posMaxX-800,  player.getCoordinate()[1]  - (diceNumber*70), player.getCoordinate()[2], player.getCoordinate()[3]};
            player.setCoordinate(coord);
        }
    }
}