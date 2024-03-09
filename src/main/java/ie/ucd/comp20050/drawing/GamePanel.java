package ie.ucd.comp20050.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import ie.ucd.comp20050.Arrow;
import ie.ucd.comp20050.Border;
import ie.ucd.comp20050.Hexagon2;
import ie.ucd.comp20050.entity.*;

import static ie.ucd.comp20050.MathUtils.pointsDistance;


public class GamePanel extends JPanel implements KeyListener {
    Timer timer;
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

    /* LEGACY VARS */
    double modifier;
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    int xConstant=SCREEN_HEIGHT/2;
    int yConstant=SCREEN_WIDTH/2;

    public GamePanel(Dimension windowSizeInput, double windowModifierInput){
        SCREEN_HEIGHT = (int) windowSizeInput.getHeight();
        SCREEN_WIDTH = (int) windowSizeInput.getHeight();
        modifier = windowModifierInput;
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
        //startGame();
    }

//    /**
//     * Synchronous game clock. Redraws board every 50 ms
//     */
//    public void startGame(){
//        timer = new Timer(50, this);
//        timer.start();
//    }

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
                x[numberOfPoints]=(int)hex2[counter].getX()[0+b];
                y[numberOfPoints]=(int)hex2[counter].getY()[0+b];
                numberOfPoints++;
                x[numberOfPoints]=(int)hex2[counter].getX()[(1+b) % 6];
                y[numberOfPoints]=(int)hex2[counter].getY()[(1+b) % 6];
                numberOfPoints++;
                counter++;
            }
            counter--;
            numberOfPoints--;
        }   

        bored2 = new Border(x, y, numberOfPoints);
    }



    private void calculateArrows(){
        for(int a=0;a<bored2.getCountPoints();a++){
            if(a!=bored2.getCountPoints()-1){
                arr[a] = new Arrow((int)bored2.getX()[a], (int)bored2.getY()[a], (int)bored2.getX()[a+1], (int)bored2.getY()[a+1], modifier);
            }else{
                arr[a] = new Arrow((int)bored2.getX()[a], (int)bored2.getY()[a], (int)bored2.getX()[0], (int)bored2.getY()[0], modifier);
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
            if(pointsDistance((int)zipzap.getMidX(), (int)zipzap.getMidY(), (int)atoms[a].getX(), (int)atoms[a].getY()) < 50){    //HARD CODING
                zip=false;
            }

            if(pointsDistance((int)zipzap.getMidX(), (int)zipzap.getMidY(), (int)atcir[a].getPosX(), (int)atcir[a].getPosY()) < 100){
                if(zipzap.getDirection()==180){
                    if(zipzap.getMidY() >= atcir[a].getPosY() - 10 && zipzap.getMidY() <= atcir[a].getPosY() + 10){
                    }else if(zipzap.getMidY() > atcir[a].getPosY()){
                        bounce=-60;
                    }else if(zipzap.getMidY() < atcir[a].getPosY()){
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

        for(int a=0;a<bored2.getCountPoints();a++){
             if(green==a){
                g.setColor(Color.yellow);
             }
             g.drawLine(arr[a].getLineX()[0], arr[a].getLineY()[0], arr[a].getLineX()[1], arr[a].getLineY()[1]);
             g.drawString(Integer.toString(a), arr[a].getLineX()[1], arr[a].getLineY()[1]);
             if(green==a){
                 g.setColor(Color.blue);
             }
         }

        for(int a=0;a<5;a++){
            g.fillOval((int)atoms[a].getX() - atoms[a].getHeight()/2, (int)atoms[a].getY() - atoms[a].getHeight()/2, atoms[a].getHeight(), atoms[a].getWidth());
            g.drawOval((int)atcir[a].getPosX() - atcir[a].getHeight()/2, (int)atcir[a].getPosY() - atcir[a].getHeight()/2, atcir[a].getHeight(), atcir[a].getWidth());
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
        g.drawPolygon(bored2.getX(), bored2.getY(), bored2.getCountPoints());

    }

//    @Override
//    public void actionPerformed(ActionEvent e){
//        repaint();
//        if(zip==true){
//            zipzap.move();
//        }
//    }

    /**
     * Handles user input for moving pointer position.
     * User can use 'A' or 'Left arrow' to move counter-clockwise around grid.
     * User can use 'D' or 'Right arrow' to move clockwise around grid.
     * User position wraps around at indexes 0 and 53.
     * @param event event to be processed
     */
    @Override
    public void keyPressed(KeyEvent event){
        /* Ensures user pressed a movement key */
        switch(event.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> green--;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> green++;
        }

        /* Resets pointer position if it moves out of bounds */
        if(green < 0) green = 53;
        else if(green > 53) green = 0;
    }

    /**
     * Handles user input for ray spawning.
     * User can use 'W' or 'Space' to spawn a ray at his current pointer position.
     * @param event event to be processed
     */
    @Override
    public void keyReleased(KeyEvent event){
        /* Ensures user pressed W or space */
        switch(event.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_SPACE -> System.out.println("Ray Spawned");
            default -> { return; }
        }

        /* Spawns ray */
        zipzap.set(arr[green].getLineX()[0], arr[green].getLineY()[0], arr[green].getAngle());
        zip = true;
        lazer2Count++;
        System.out.println("lazer2Count: " + lazer2Count);
    }

    @Override
    public void keyTyped(KeyEvent e){ }

}
