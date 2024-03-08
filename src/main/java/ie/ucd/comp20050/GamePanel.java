package ie.ucd.comp20050;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.Arrays;
import java.util.Random;
import ie.ucd.comp20050.entity.Atom;


class GamePanel extends JPanel implements ActionListener, KeyListener{
    Timer timer;
    QuickMaff maff = new QuickMaff();
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    int xConstant=SCREEN_HEIGHT/2;
    int yConstant=SCREEN_WIDTH/2;
    double modifier;
    int x[];
    int y[];
    int counter;
    Hexagon2[] hex2 = new Hexagon2[100];
    Arrow[] arr = new Arrow[100];
    Atom[] atoms = new Atom[100];
    AtomCircle[] atcir = new AtomCircle[100];
    Lazer zipzap = new Lazer(-10, -10, 0, 0);
    Lazer2[] zipzoop = new Lazer2[10];
    Border bored;
    Border bored2;
    int hexagonCounter=0, hexagonCounter2=0;
    int green=0;
    boolean zip=false;
    Random random = new Random();
    int lazer2Count =0;

    GamePanel(){
        SCREEN_HEIGHT = this.screenSize.height-80;
        SCREEN_WIDTH = this.screenSize.height-80;
        modifier = maff.getModifier(SCREEN_HEIGHT, SCREEN_WIDTH);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        for(int a=0;a<10;a++){
            zipzoop[a] = new Lazer2();
        }
        calculateGridInADifferentWay();
        calculateBorderInADifferentWay();
        calculateArrows();
        calculateAtoms();
        startGame();
    }

    public void startGame(){
        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawBackground(g);
    }

    private void calculateGridInADifferentWay(){
        yConstant=(int)(SCREEN_WIDTH/2 - 50 * modifier);
        xConstant=(int)(SCREEN_WIDTH/2 - 87 * modifier);
        hex2[hexagonCounter2] = new Hexagon2(xConstant, yConstant, modifier, 60);
        hexagonCounter2++;
        int angle=180;
        int flip=1;
        int flip2=flip;
        int layer2=1;

        for(int a=0;a<6;a++){
            hex2[hexagonCounter2] = new Hexagon2(hex2[0].getX()[a], hex2[0].getY()[a], modifier, angle);
            hexagonCounter2++;
            angle=angle+60;
            angle=angle%360;
        }
        layer2++;

        System.out.println(flip);
        for(int c=0;c<3;c++){
            for(int b=0;b<6;b++){
                for(int a=0;a<layer2;a++){
                    if(b+a==5+layer2-1){
                        hex2[hexagonCounter2] = new Hexagon2(hex2[flip2].getX()[b], hex2[flip2].getY()[b], modifier, angle);
                    }else{
                        hex2[hexagonCounter2] = new Hexagon2(hex2[flip+a].getX()[b], hex2[flip+a].getY()[b], modifier, angle);
                    }
                    hexagonCounter2++;
                }
                angle=angle+60;
                angle=angle%360;
                flip=flip+1+c;
            }
            layer2++;
            flip2=flip;
        }
    }

    private void calculateBorderInADifferentWay(){
        int x[] = new int[100];
        int y[] = new int[100];
        int counter=37;                             //hard coding
        int numberOfPoints=0;

        for(int b=0;b<6;b++){
            for(int a=0;a<5;a++){
                if(counter==61){
                    counter=37;
                }
                x[numberOfPoints]=(int)hex2[counter].x[0+b];
                y[numberOfPoints]=(int)hex2[counter].y[0+b];
                numberOfPoints++;
                x[numberOfPoints]=(int)hex2[counter].x[(1+b) % 6];
                y[numberOfPoints]=(int)hex2[counter].y[(1+b) % 6];
                numberOfPoints++;
                counter++;
            }
            counter--;
            numberOfPoints--;
        }   

        bored2 = new Border(x, y, numberOfPoints);
    }



    private void calculateArrows(){
        for(int a=0;a<bored2.numberOfPoints;a++){
            if(a!=bored2.numberOfPoints-1){
                arr[a] = new Arrow((int)bored2.x[a], (int)bored2.y[a], (int)bored2.x[a+1], (int)bored2.y[a+1], modifier);
            }else{
                arr[a] = new Arrow((int)bored2.x[a], (int)bored2.y[a], (int)bored2.x[0], (int)bored2.y[0], modifier);
            }
        }
    }

    /**
     * Method to calculate where atoms should be spawned
     */ //@TODO Refactor
    private void calculateAtoms(){
        int max = hexagonCounter2;
        for(int a=0;a<5;a++){
            int randomRandom = random.nextInt(max);
            int sizing = (int)(175 * modifier);
            atoms[a] = new Atom((int)hex2[randomRandom].getMiddleX(), (int)hex2[randomRandom].getMiddleY(), sizing, sizing);                  //HARD CODING
            atcir[a] = new AtomCircle((int)hex2[randomRandom].getMiddleX(), (int)hex2[randomRandom].getMiddleY(), sizing*2, sizing*2);
        }
    }

    /**
     * Method to handle collision detection with atoms
     */ //@TODO Refactor
    private void collisionDetection(){
        int bounce=0;
        for(int a=0;a<5;a++){
            if(maff.distanceBetweenTwoPoints((int)zipzap.getMidX(), (int)zipzap.getMidY(), (int)atoms[a].getX(), (int)atoms[a].getY()) < 50){    //HARD CODING
                zip=false;
            }

            if(maff.distanceBetweenTwoPoints((int)zipzap.getMidX(), (int)zipzap.getMidY(), (int)atcir[a].getX(), (int)atcir[a].getY()) < 100){
                if(zipzap.getDirection()==180){
                    if(zipzap.getMidY() >= atcir[a].getY() - 10 && zipzap.getMidY() <= atcir[a].getY() + 10){
                    }else if(zipzap.getMidY() > atcir[a].getY()){
                        bounce=-60;
                    }else if(zipzap.getMidY() < atcir[a].getY()){
                        bounce=60;
                    }
                }
                zipzap.changeDirection(zipzap.getDirection() + bounce);
                //LAZER 2 enable
                //zipzoop[lazer2Count-1].set((int)zipzap.getMidX(), (int)zipzap.getMidY());
                //zip=false;
            }
        }
    }

    private void drawBackground(Graphics g){
        collisionDetection();
        g.setColor(Color.blue);

        for(int a=0;a<bored2.numberOfPoints;a++){
             if(green==a){
                g.setColor(Color.yellow);
             }
             g.drawLine(arr[a].lineX[0], arr[a].lineY[0], arr[a].lineX[1], arr[a].lineY[1]);
             g.drawString(Integer.toString(a), arr[a].lineX[1], arr[a].lineY[1]);
             if(green==a){
                 g.setColor(Color.blue);
             }
         }

        for(int a=0;a<5;a++){
            g.fillOval((int)atoms[a].getX() - atoms[a].getHeight()/2, (int)atoms[a].getY() - atoms[a].getHeight()/2, atoms[a].getHeight(), atoms[a].getWidth());
            g.drawOval((int)atcir[a].getX() - atcir[a].getHeight()/2, (int)atcir[a].getY() - atcir[a].getHeight()/2, atcir[a].getHeight(), atcir[a].getWidth());
            g.setColor(Color.red);
            g.drawString(Integer.toString(a), (int)atoms[a].getX() - atoms[a].getHeight()/2, (int)atoms[a].getY() - atoms[a].getHeight()/2);
            g.setColor(Color.blue);
        }
        g.drawOval((int)zipzap.getMidX()-5, (int)zipzap.getMidY()-5, 10, 10);
        g.setColor(Color.red);
        
        //for(int a=0;a<lazer2Count;a++){
        //    if(zipzoop[a].x[0]!=0 && zipzoop[a].x[1]!=0){
                //g.drawLine(zipzoop[a].x[0], zipzoop[a].y1, zipzoop[a].x2, zipzoop[a].y2);
                //g.drawPolyline(zipzoop[a].getX(), zipzoop[a].getY(), zipzoop[a].getNum());
        //    }
        //}

        g.drawString("Player 1: Press a and d to move. Press and release w to shoot.", 25, 25);
        g.drawString("Number of lazers left:" + Integer.toString(10-lazer2Count), 25, 50);
        
        g.setColor(Color.pink);
        for(int a=0;a<hexagonCounter2;a++){
            g.drawPolygon(hex2[a].getXInteger(), hex2[a].getYInteger(), hex2[a].getNumberOfPoints());
            g.drawString(Integer.toString(a), (int)hex2[a].getMiddleX(), (int)hex2[a].getMiddleY());
        }
        g.setColor(Color.RED);
        g.drawPolygon(bored2.x, bored2.y, bored2.numberOfPoints);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
        if(zip==true){
            zipzap.move();
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
    }
    @Override
    public void keyReleased(KeyEvent e){
        char keyChar = e.getKeyChar();
        if(keyChar=='w'){
            System.out.println(keyChar);
            zipzap.set(arr[green].lineX[0], arr[green].lineY[0], arr[green].angle);
            //zipzoop[lazer2Count].set((int)arr[green].lineX[0], (int)arr[green].lineY[0]);
            zip=true;
            lazer2Count++;
            System.out.println(lazer2Count);
        }

    }
    @Override
    public void keyTyped(KeyEvent e){
        char keyChar = e.getKeyChar();
        if(keyChar=='a'){
            green--;
        }
        if(keyChar=='d'){
            green++;
        }
        green=green%54;
        if (green < 0) {
            green += 54;
        }
    }
}
