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
    boolean setter=true;
    Character setterInput;
    String toDisplay="Player 2 enter position of atom 1 (and press Enter): ";
    String toDisplay3="Enter Atom guess 1 (and press Enter): ";
    String toDisplay2="";
    String holderString="";
    int setterCounter=0;
    int guessCounter=0;
    ArrayList<Integer> starting = new ArrayList<Integer>();
    ArrayList<Integer> ending = new ArrayList<Integer>();
    boolean userGenerated=true;
    boolean guessing=false;
    int gameOver=0;

    /* LEGACY VARS */
    double modifier;
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    int xConstant=SCREEN_HEIGHT/2;
    int yConstant=SCREEN_WIDTH/2;

    boolean [] guessed;
    double hexagondistance ;
    final int atomnum = 5;

    TreeSet<Double> hexxs;
    TreeSet<Double> hexys;

    PlayerInteractions playerhandler;

    Player[] player = new Player[2];

    int playerCounter=0;

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
        player[0] = new Player(0);
        player[1] = new Player(0);
        calculateGridInADifferentWay();
        calculateBorderInADifferentWay();
        calculateArrows();
        if(userGenerated){
            makeAtomsSetterInput();
        }else{
            makeAtoms();
        }

        guessed = new boolean[61];

    }

    /**
     * Adds Atoms; atoms are not generated here.
     * This is a temporary method, and will be removed once GamePanel is merged into GameWindow.
     * @param input ArrayList<Atom>, list of Atoms to be drawn
     */
    public void setAtoms(ArrayList<Atom> input) {
        atoms = input;
    }



    private void makeAtomsSetterInput(){
        //Scanner myObj = new Scanner(System.in);

        atoms = new ArrayList<Atom>();
        
        //Integer input = myObj.nextInt();
        for(int a=0;a<atomnum;a++){
        atoms.add(new Atom(
                hex2[0].getMiddleX(),
                hex2[0].getMiddleY(),
                atomRadius,
                ringRadius,
                hexagondistance
                 ,0
             ));
        }
    }

    private void changeAtoms(int hexagon, int counter){
        atoms.get(setterCounter).change(
            hex2[hexagon].getMiddleX(),
            hex2[hexagon].getMiddleY(),
            hexagon);
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
        setter=false;
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
            zip = false;
        }
        else if (bounce != 0 && tmp != Atom.EXIT_180) {
                zipzap.setX(x);
                zipzap.setY(y);
        }

        zipzap.setCollideStatus(Lazer.collideState.never);
        zipzap.addBounce(bounce);
    }

    private boolean guessAtoms(int answer){
        for(int a=0;a<atomnum;a++){

            if(atoms.get(a).getHexagon() == answer){
                return true;
            }
        }   
    
        return false;
    }

    private void moveAtoms(){
        for(int i = 0; i < atomnum; i++){
            atoms.get(i).change(100, 100, 0);
        }
    }

    private boolean atomNotAlrThere(int x)
    {
        for (Atom a:atoms) if (a.getHexagon() == x) return false;
        return true;
    }



    private void drawBackground(ArrayList<Atom> atoms){
        if(gameOver!=2){

        int holder=0;
        //System.out.println("player " + playerCounter);
        collisionDetection();
        graph.setColor(Color.blue);

        for(int a=0;a<bored2.getCountPoints();a++){
            if(posPointer == a && setter==false){
                graph.setColor(Color.yellow);
            }
            graph.drawLine(arr[a].getLineX()[0], arr[a].getLineY()[0], arr[a].getLineX()[1], arr[a].getLineY()[1]);
            graph.drawString(Integer.toString(a), arr[a].getLineX()[1], arr[a].getLineY()[1]);
            if(posPointer == a && setter==false){
                graph.setColor(Color.blue);
            }
        }

        if(setter==true) {
            drawAtoms(atoms);


            graph.drawOval((int) zipzap.getMidX() - 5, (int) zipzap.getMidY() - 5, 10, 10);
            graph.setColor(Color.red);
        }
        //for(int a=0;a<lazer2Count;a++){
        //    if(zipzoop[a].x[0]!=0 && zipzoop[a].x[1]!=0){
        //g.drawLine(zipzoop[a].x[0], zipzoop[a].y1, zipzoop[a].x2, zipzoop[a].y2);
        //g.drawPolyline(zipzoop[a].getX(), zipzoop[a].getY(), zipzoop[a].getNum());
        //    }
        //}
        if(lazer2Count>=10){
            guessing=true;
        }
        if(setter==false && guessing==false){
            String startingPositions = "Atom starting & ending positions:\n";
            graph.drawString("Player 1: Press a and d to move. Press and release w to shoot.", (int)(50*modifier), (int)(50*modifier));
            graph.drawString("Number of lazers left:" + Integer.toString(10-lazer2Count), (int)(25*modifier), (int)(100*modifier));
            int why=(int)(1500*modifier);
            int lineHeight = graph.getFontMetrics().getHeight();
            graph.drawString(startingPositions, (int)(1400*modifier), why);
            for(int a=0;a<starting.size();a++){
                graph.drawString("starting: " + starting.get(a).toString() + "    ending: " + "todo", (int)(1400*modifier), why+=lineHeight);
            }
            setterInput=null;
        }else if(setter==true && guessing==false){
            if(setterInput!=null){   
                if((int)setterInput==10){
                    holderString = "";
                    try{
                        holder=Integer.parseInt(toDisplay2);
                    }catch(Exception E){
                        holderString="invalid input!";
                        holder=100;
                    }
                    //System.out.println(holder);
                    if(holder<=60 && atomNotAlrThere(holder)){
                        changeAtoms(holder, setterCounter);
                        setterCounter++;
                    }
                    else holderString="invalid input!";

                    toDisplay="Player " + (((playerCounter+1)%2)+1) + " enter position of atom " + setterCounter + " (and press Enter): ";
                    toDisplay2="";
                }else if((int)setterInput==8){
                    StringBuffer sb = new StringBuffer(toDisplay);
                    StringBuffer sp = new StringBuffer(toDisplay2);
                    if(sp.length()>0){
                        sp.deleteCharAt(sp.length()-1);
                    }
                    if(sb.length()>51){
                        sb.deleteCharAt(sb.length()-1);
                    }
                    toDisplay=sb.toString();
                    toDisplay2=sp.toString();
                }else{
                    toDisplay=toDisplay.concat(String.valueOf(setterInput));
                    toDisplay2=toDisplay2.concat(String.valueOf(setterInput));
                } 
            }
            graph.drawString(toDisplay, (int)(50*modifier), (int)(50*modifier));
            graph.drawString(holderString, (int)(50*modifier), (int)(80*modifier));
            setterInput=null;
            if(setterCounter==5){
                setter=false;
            }
        }else if(guessing==true){
            if(setterInput!=null){
                if((int)setterInput==10){
                    try{
                        holder=Integer.parseInt(toDisplay2);
                    }catch(Exception E){
                        holderString="invalid input!";
                        holder=100;
                    }
                    //System.out.println(holder);
                    if(holder<=60 && !guessed[holder]){
                        guessed[holder] = true;
                        if(guessAtoms(holder)){
                            holderString="correct!";
                            player[playerCounter].incrementScore();
                            System.out.println("Player score: " + player[playerCounter].getScore());
                        }else{
                            holderString="wrong";
                        }
                        guessCounter++;
                    }
                    toDisplay3="Enter Atom guess " + guessCounter + " (and press Enter): ";
                    toDisplay2="";
                }else{
                    toDisplay3=toDisplay3.concat(String.valueOf(setterInput));
                    toDisplay2=toDisplay2.concat(String.valueOf(setterInput));
                } 
            }

            graph.drawString(toDisplay3, (int)(50*modifier), (int)(50*modifier));
            graph.drawString(holderString, (int)(50*modifier), (int)(80*modifier));
            setterInput=null;
            if(guessCounter==5){
                guessed = new boolean[61];
                gameOver++;
                guessing=false;
                setter=true;
                playerCounter=1;
                lazer2Count=0;
                setterCounter=0;
                guessCounter=0;
                toDisplay="Player 1 enter position of atom 1 (and press Enter): ";
                for(int a=starting.size();a>0;a--){
                    starting.remove(a-1);
                }
                moveAtoms();
            }
        }

        graph.setColor(Color.pink);
        for(int a=0;a<hexagonCounter2;a++){
            graph.drawPolygon(hex2[a].getXInteger(), hex2[a].getYInteger(), hex2[a].getNumberOfPoints());
            graph.drawString(Integer.toString(a), (int)hex2[a].getMiddleX(), (int)hex2[a].getMiddleY());
        }
        graph.setColor(Color.RED);
        graph.drawPolygon(bored2.getX(), bored2.getY(), bored2.getCountPoints());
    }else{
        int lineHeight = graph.getFontMetrics().getHeight();
        graph.setColor(Color.RED);
        graph.drawString("GAME OVER", SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        if(player[0].getScore()>player[1].getScore()){
            graph.drawString("Player 1 wins, score = " + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 2 loses, score = " + player[1].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight*2);
        }else{
            graph.drawString("Player 2 wins, score = " + player[1].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 1 loses, score = " + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight*2);
        }
    }

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
        if(setter==false && guessing==false){
            switch(event.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> System.out.println("Ray Spawned");
                case KeyEvent.VK_G -> {playerhandler.endTurn(lazer2Count); return;}
                default -> { return; }
            }

        /* Spawns ray */
        zipzap.set(arr[posPointer].getLineX()[0], arr[posPointer].getLineY()[0], arr[posPointer].getAngle());
        starting.add(posPointer);
        ending.add(0);
        zip = true;
        lazer2Count++;
        System.out.println("lazer2Count: " + lazer2Count);
    }

       //reset atom collisions
        for (Atom atom:atoms)
        {
           atom.resetCollisionStatus();
        }

    }

    @Override
    public void keyTyped(KeyEvent e){ 
        setterInput = e.getKeyChar();
        System.out.println((int)setterInput);
    }


    public void resetGame()
    {
        lazer2Count = 0;
        atoms.clear();
        makeAtomsSetterInput();
        zipzap = new Lazer(-10, -10, 10, 10);
    }

    public ArrayList<Atom> getAtoms() { return atoms; }

    public int getAtomNum() { return atomnum; }
    public int getHexagonNum() {return hexagonCounter2;}
}
