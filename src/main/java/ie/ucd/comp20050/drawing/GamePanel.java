package ie.ucd.comp20050.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;


import ie.ucd.comp20050.*;
import ie.ucd.comp20050.entity.*;


import static ie.ucd.comp20050.MathUtils.pointsDistance;
import java.util.Scanner;

public class GamePanel extends JPanel implements KeyListener {
    int x[];
    int y[];
    int counter;
    Hexagon2[] hex2 = new Hexagon2[100];
    Arrow[] arr = new Arrow[100];
    Lazer zipzap = new Lazer(-10, -10, 10, 10);
    Lazer2[] zipzoop = new Lazer2[10];
    Border bored;
    Border bored2;
    int hexagonCounter=0, hexagonCounter2=0;
    int posPointer = 0;
    boolean zip=false;
    Random random = new Random();
    int lazer2Count =0;

    /* LEGACY VARS */
    double modifier;
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    int xConstant=SCREEN_HEIGHT/2;
    int yConstant=SCREEN_WIDTH/2;


    double hexagondistance ;
    final int atomnum = 5;

    TreeSet<Double> hexxs;
    TreeSet<Double> hexys;

    PlayerInteractions playerhandler;

    double atomRadius;
    double ringRadius;
    /* UPDATED VARS */

    /**
     * ArrayList storage for Atoms in the game.
     */
    private ArrayList<Atom> atoms;

    /**
     * Graphics tool. Set by paintComponent, allows methods to be called without issue from other classes.
     */
    Graphics graph;

    public GamePanel(Dimension windowSizeInput, double windowModifierInput){
        SCREEN_HEIGHT = (int) windowSizeInput.getHeight();
        SCREEN_WIDTH = (int) windowSizeInput.getHeight();
        modifier = windowModifierInput;
        ringRadius = (175 * modifier);
        atomRadius = ringRadius / 2;
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        for(int a=0;a<10;a++){
            zipzoop[a] = new Lazer2();
        }
        calculateGridInADifferentWay();
        calculateBorderInADifferentWay();
        calculateArrows();
         makeAtoms();

         playerhandler= new PlayerInteractions(this);

        //makeAtomsTest();
        //idk if this should be here
    }

    /**
     * Adds Atoms; atoms are not generated here.
     * This is a temporary method, and will be removed once GamePanel is merged into GameWindow.
     * @param input ArrayList<Atom>, list of Atoms to be drawn
     */
    public void setAtoms(ArrayList<Atom> input) {
        atoms = input;
    }




   private void makeAtomsTest()
    {
        atoms = new ArrayList<Atom>();
        for(int i = 0; i < atomnum; i++) {

            int pos0 = 28;
            int pos1 = 12;
            int pos2 = 21;
            int pos3 = 38;
            int pos4 = 37;
            switch(i)
            {
                case 0:
                    atoms.add(new Atom(
                            hex2[pos0].getMiddleX(),
                            hex2[pos0].getMiddleY(),
                            atomRadius,
                            ringRadius,
                            hexagondistance
                            ,pos0
                    ));
                case 1:
                    atoms.add(new Atom(
                            hex2[pos1].getMiddleX(),
                            hex2[pos1].getMiddleY(),
                            atomRadius,
                            ringRadius,
                            hexagondistance
                            ,pos1
                    ));
                case 2:
                    atoms.add(new Atom(
                            hex2[pos2].getMiddleX(),
                            hex2[pos2].getMiddleY(),
                            atomRadius,
                            ringRadius,
                            hexagondistance
                            ,pos2
                    ));
                case 3:
                    atoms.add(new Atom(
                            hex2[pos3].getMiddleX(),
                            hex2[pos3].getMiddleY(),
                            atomRadius,
                            ringRadius,
                            hexagondistance
                            ,pos3
                    ));
                case 4:
            }atoms.add(new Atom(
                    hex2[pos4].getMiddleX(),
                    hex2[pos4].getMiddleY(),
                    atomRadius,
                    ringRadius,
                    hexagondistance
                    ,pos4
            ));

        }
    }

    private void makeAtoms()
    {
        boolean already[] = new boolean[100];
        atoms = new ArrayList<Atom>();
        Random random = new Random();
       int hex = 0;
        for(int i = 0; i < atomnum; i++) {
            while (already[hex])
            {
                 hex = random.nextInt(hexagonCounter2);
            }
            already[hex] = true;

            atoms.add(new Atom(
                    hex2[hex].getMiddleX(),
                    hex2[hex].getMiddleY(),
                    atomRadius,
                    ringRadius,
                    hexagondistance
                    ,hex
            ));


        }


    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graph = g; // Updates stored Graphics
        drawBackground(atoms);

    }


    /*
    Generates the grid, and creates the count of hexagons.
    @TODO Refactor. 'Grid making' should be moved to main, switching an array for ArrayList. Eliminate hexcounter.

    Steven: This seems to always make 61 hexagons on my desktop, haven't yet tested on mac;
    is there ever a realistic scenario where this should be dynamic?
    Monopoly is always the same board size, I would imagine this game is much the same (I don't know it that well).
     */
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

        hexxs = new TreeSet<Double>();
        hexys = new TreeSet<Double>();
        for (Hexagon2 hex:hex2)
        {
            if (hex == null ) continue;
            hexxs.add(hex.getMiddleX());
            hexys.add(hex.getMiddleY());
        }
        hexagondistance  = Math.abs(hex2[1].getMiddleX() - hex2[0].getMiddleY());
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
     * Draws Atoms on the game board
     * @param list ArrayList<Atom>, input list of Atoms to draw
     */
    private void drawAtoms(ArrayList<Atom> list) {
        if(list == null) return; // Temp. necessary to alleviate bug on macOS
        for(Atom atom : list) {
            int posX = (int) hex2[atom.getHexagon()].getMiddleX();
            int posY = (int) hex2[atom.getHexagon()].getMiddleY();
            int size =(int) (175 * modifier);

            graph.fillOval(posX - size/2, posY - size/2, size, size);
            graph.drawOval(posX - size, posY - size, size*2, size*2);
            graph.setColor(Color.red);
            graph.setColor(Color.blue);
        }
    }





    /**
     * Method to handle collision detection with atoms
     */
    private void collisionDetection(){
        if (!zip) return;
        double tmp= 0;
        double bounce=0;

        for(int a=0;a<atomnum;a++){
            tmp = atoms.get(a).collide(zipzap,hexxs,hexys);
            if (tmp != 0 && tmp != Atom.DOUBLE && tmp != Atom.EXIT_180 && tmp != Atom.EXIT_ABSORB)
                bounce = tmp;
            else if (tmp == Atom.DOUBLE)
                bounce *= 2;
            else if (tmp == Atom.EXIT_ABSORB) {
                //to avoid overlapping radii
                for (;a< atomnum;a++)
                    atoms.get(a).setCollideTrue();
                zip = false; break;
            }
            else if (tmp == Atom.EXIT_180) //this overpowers everything excpet for absorb at edge
            {
                bounce = 180;
                for (;a< atomnum;a++)
                {
                   if (atoms.get(a).collide(zipzap,hexxs,hexys)==Atom.EXIT_ABSORB) {
                       zip = false;
                   }
                }
                break;
           }
        }
        if (bounce == Atom.ABSORB) zip = false;
        //if it has collided and not at edge centre
        double x = MathUtils.closestValue(zipzap.getX(),hexxs);
        double y = MathUtils.closestValue(zipzap.getY(),hexys);
        if (MathUtils.squ(x - zipzap.getX()) + MathUtils.squ(y -zipzap.getY()) > MathUtils.squ( hexagondistance))
        {
           System.out.println("passed");
            zip = false;
        }
        else if (bounce != 0 && tmp != Atom.EXIT_180) {
                zipzap.setX(x);
                zipzap.setY(y);
        }

        zipzap.setCollideStatus(Lazer.collideState.never);
        zipzap.addBounce(bounce);
    }

    /*
    private void collisionDetection(ArrayList<Atom> list){

        int bounce=0;
        if(list == null) return; // Temp. necessary to alleviate bug on macOS
        for(Atom atom : list) {
            int posX = (int) hex2[atom.getHexagon()].getMiddleX();
            int posY = (int) hex2[atom.getHexagon()].getMiddleY();
            int dist = pointsDistance((int) zipzap.getMidX(), (int) zipzap.getMidY(), posX, posY);

            if(dist < 50) zip = false;
            if(dist < 100) {
                if(zipzap.getDirection() == 180); // does nothing?
                else if(zipzap.getMidY() > posY) bounce = -60;
                else if(zipzap.getMidY() < posY) bounce = 60;
            }
            zipzap.changeDirection(zipzap.getDirection() + bounce);
        }
    }
*/
    private void drawBackground(ArrayList<Atom> atoms){
        collisionDetection();
        graph.setColor(Color.blue);

        for(int a=0;a<bored2.getCountPoints();a++){
            if(posPointer == a){
                graph.setColor(Color.yellow);
            }
            graph.drawLine(arr[a].getLineX()[0], arr[a].getLineY()[0], arr[a].getLineX()[1], arr[a].getLineY()[1]);
            graph.drawString(Integer.toString(a), arr[a].getLineX()[1], arr[a].getLineY()[1]);
            if(posPointer ==a){
                graph.setColor(Color.blue);
            }
        }

        drawAtoms(atoms);

        graph.drawOval((int)zipzap.getMidX()-5, (int)zipzap.getMidY()-5, 10, 10);
        graph.setColor(Color.red);

        //for(int a=0;a<lazer2Count;a++){
        //    if(zipzoop[a].x[0]!=0 && zipzoop[a].x[1]!=0){
        //g.drawLine(zipzoop[a].x[0], zipzoop[a].y1, zipzoop[a].x2, zipzoop[a].y2);
        //g.drawPolyline(zipzoop[a].getX(), zipzoop[a].getY(), zipzoop[a].getNum());
        //    }
        //}

        graph.drawString("Player 1: Press a and d to move. Press and release w to shoot.", 25, 25);
        graph.drawString("Number of lazers left:" + Integer.toString(10-lazer2Count), 25, 50);

        graph.setColor(Color.pink);
        for(int a=0;a<hexagonCounter2;a++){
            graph.drawPolygon(hex2[a].getXInteger(), hex2[a].getYInteger(), hex2[a].getNumberOfPoints());
            graph.drawString(Integer.toString(a), (int)hex2[a].getMiddleX(), (int)hex2[a].getMiddleY());
        }
        graph.setColor(Color.RED);
        graph.drawPolygon(bored2.getX(), bored2.getY(), bored2.getCountPoints());

    }

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
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> posPointer--;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> posPointer++;
        }

        /* Resets pointer position if it moves out of bounds */
        if(posPointer < 0) posPointer = 53;
        else if(posPointer > 53) posPointer = 0;
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
            case KeyEvent.VK_G -> {playerhandler.endTurn(lazer2Count); return;}
            default -> { return; }
        }

        /* Spawns ray */
        zipzap.set(arr[posPointer].getLineX()[0], arr[posPointer].getLineY()[0], arr[posPointer].getAngle());
        zip = true;
        lazer2Count++;
        System.out.println("lazer2Count: " + lazer2Count);

       //reset atom collisions
        for (Atom atom:atoms)
        {
           atom.resetCollisionStatus();
        }

    }

    @Override
    public void keyTyped(KeyEvent e){ }



    public void resetGame()
    {
        lazer2Count = 0;
        atoms.clear();
        makeAtoms();
        zipzap = new Lazer(-10, -10, 10, 10);
    }

    public ArrayList<Atom> getAtoms() { return atoms; }

    public int getAtomNum() { return atomnum; }
    public int getHexagonNum() {return hexagonCounter2;}
}
