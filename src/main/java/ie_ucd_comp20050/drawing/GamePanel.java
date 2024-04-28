package ie.ucd.comp20050.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import ie.ucd.comp20050.*;
import ie.ucd.comp20050.entity.*;

public class GamePanel extends JPanel implements KeyListener {
    int x[];
    int y[];
    int counter;
    Hexagon[] hexagons = new Hexagon[100];
    Arrow[] arr = new Arrow[100];
    LaserRay laser = new LaserRay(-10, -10, 10, 10);
    Border border;
    int hexagonCounter=0, hexagonCounter2=0;
    int posPointer = 0;
    boolean zip=false;
    Random random = new Random();
    int laserCount =0;
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
        player[0] = new Player(0);
        player[1] = new Player(0);
        calculateGrid();
        border = new Border(hexagons);
        calculateArrows();
        if(userGenerated){
            //makeAtomsSetterInput();
            makeAtoms();
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
                hexagons[0].getMiddleX(),
                hexagons[0].getMiddleY(),
                atomRadius,
                ringRadius,
                hexagondistance
                 ,0
             ));
        }
    }

    private void changeAtoms(int hexagon, int counter){
        atoms.get(setterCounter).change(
            hexagons[hexagon].getMiddleX(),
            hexagons[hexagon].getMiddleY(),
            hexagon);
    }

     private void makeAtoms()
     {  
         boolean already[] = new boolean[100];
         atoms = new ArrayList<Atom>();
//         Random random = new Random();
        int hex = 0;
         for(int i = 0; i < atomnum; i++) {
             while (already[hex])
             {
                  hex = random.nextInt(hexagonCounter2);
             }
             already[hex] = true;
             atoms.add(new Atom(
                     hexagons[hex].getMiddleX(),
                     hexagons[hex].getMiddleY(),
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
    private void calculateGrid(){
        yConstant=(int)(SCREEN_WIDTH/2 - 50 * modifier);
        xConstant=(int)(SCREEN_WIDTH/2 - 87 * modifier);
        hexagons[hexagonCounter2] = new Hexagon(xConstant, yConstant, modifier, 60);
        hexagonCounter2++;
        int angle=180;
        int flip=1;
        int flip2=flip;
        int layer2=1;


        for(int a=0;a<6;a++){
            hexagons[hexagonCounter2] = new Hexagon(hexagons[0].getX()[a], hexagons[0].getY()[a], modifier, angle);
            hexagonCounter2++;
            angle=angle+60;
            angle=angle%360;
        }
        layer2++;

        for(int c=0;c<3;c++){
            for(int b=0;b<6;b++){
                for(int a=0;a<layer2;a++){
                    if(b+a==5+layer2-1){
                        hexagons[hexagonCounter2] = new Hexagon(hexagons[flip2].getX()[b], hexagons[flip2].getY()[b], modifier, angle);
                    }else{
                        hexagons[hexagonCounter2] = new Hexagon(hexagons[flip+a].getX()[b], hexagons[flip+a].getY()[b], modifier, angle);
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
        for (Hexagon hex:hexagons)
        {
            if (hex == null ) continue;
            hexxs.add(hex.getMiddleX());
            hexys.add(hex.getMiddleY());
        }
        hexagondistance  = Math.abs(hexagons[1].getMiddleX() - hexagons[0].getMiddleY());
    }

    private void calculateArrows(){
        for(int a = 0; a< border.getCountPoints(); a++){
            if(a!= border.getCountPoints()-1){
                arr[a] = new Arrow((int) border.getX()[a], (int) border.getY()[a], (int) border.getX()[a+1], (int) border.getY()[a+1], modifier);
            }else{
                arr[a] = new Arrow((int) border.getX()[a], (int) border.getY()[a], (int) border.getX()[0], (int) border.getY()[0], modifier);
            }
        }
    }

    /**
     * Draws Atoms on the game board
     * @param list ArrayList<Atom>, input list of Atoms to draw
     */
    private void drawAtoms(ArrayList<Atom> list) {
        if(list == null) return; // Temp. necessary to alleviate bug on macOS @TODO Better explanation
        graph.setColor(Color.blue);
        for(Atom atom : list) {
            int posX = (int) hexagons[atom.getHexagon()].getMiddleX();
            int posY = (int) hexagons[atom.getHexagon()].getMiddleY();
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
    private void collisionDetection() {
        if (!zip) return;
        double tmp= 0;
        double bounce=0;

        for(int a=0;a<atomnum;a++){
            tmp = atoms.get(a).collide(laser,hexxs,hexys);
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
                   if (atoms.get(a).collide(laser,hexxs,hexys)==Atom.EXIT_ABSORB) {
                       zip = false;
                   }
                }
                break;
           }
        }
        if (bounce == Atom.ABSORB) zip = false;
        //if it has collided and not at edge centre
        double x = MathUtils.closestValue(laser.getPosX(),hexxs);
        double y = MathUtils.closestValue(laser.getPosY(),hexys);
        if (MathUtils.squ(x - laser.getPosX()) + MathUtils.squ(y - laser.getPosY()) > MathUtils.squ( hexagondistance))
        {
            zip = false;
        }
        else if (bounce != 0 && tmp != Atom.EXIT_180) {
                laser.setPosX(x);
                laser.setPosY(y);
        }

        laser.setCollideStatus(LaserRay.CollideState.never);
        laser.addBounce(bounce);
    }

    private boolean guessAtoms(int answer) {
        for(int a=0; a<atomnum; a++){
            if(atoms.get(a).getHexagon() == answer) return true;
        }
        return false;
    }

    private void moveAtoms() {
        for(int i = 0; i < atomnum; i++)
            atoms.get(i).change(100, 100, 0);
    }

    private boolean atomNotAlrThere(int x) {
        for (Atom a : atoms)
            if (a.getHexagon() == x) return false;
        return true;
    }

    /* Temporary method! Using it to test position of laser */
    private void detectBorder() {
        System.out.println(laser.getPosX() + " " + laser.getPosY() + " " + laser.getDirection());
    }

    /**
     * Primarily handles drawing of game itself, along with other game logic.
     * @param atoms ArrayList<Atom>, atoms to be drawn ~ used
     */
    private void drawBackground(ArrayList<Atom> atoms) {
        if(gameOver!=2) {

            collisionDetection();
            if(setter) {
                drawRay(laser);
            }

            drawAtoms(atoms);

            graph.setColor(Color.red); // Unsure what this is colouring!

            if(guessing) {
                getGuesses();
            }
            else {
                drawPlayerGameplayInfo();
            }
            drawHexagons();
            drawBorder();
        }
        else {
            displayEndScreen();
        }
    }

    void displayEndScreen() {

        int lineHeight = graph.getFontMetrics().getHeight();
        graph.setColor(Color.RED);
        graph.drawString("GAME OVER", SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        if (player[1].getScore() == player[0].getScore())
        {
            graph.drawString("Draw!" + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 1 score = " + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight* 2);
            graph.drawString("Player 2 score = " + player[1].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight* 3);
        }
        else if(player[0].getScore()<player[1].getScore()) {
            graph.drawString("Player 1 wins, score = " + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 2 loses, score = " + player[1].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight*2);
        }
        else {
            graph.drawString("Player 2 wins, score = " + player[1].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 1 loses, score = " + player[0].getScore(), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + lineHeight*2);
        }
    }

    void drawPlayerGameplayInfo()
    {

        String startingPositions = "Atom starting & ending positions:\n";
        graph.drawString("Player " + (playerCounter +1) +  ": Press a and d to move. Press and release w to shoot,press and release g to guess", (int)(50*modifier), (int)(50*modifier));
        graph.drawString("lasers shot: " + laserCount, (int)(25*modifier), (int)(100*modifier));
        int why=(int)(1500*modifier);
        int lineHeight = graph.getFontMetrics().getHeight();
        graph.drawString(startingPositions, (int)(1400*modifier), why);
        for(int a=starting.size() - 1;a >= 0;a--)
            graph.drawString("starting: " + starting.get(a).toString() + "    ending: " + "todo", (int)(1400*modifier), why+=lineHeight);
        setterInput=null;
    }


    /**
     * Draws laser ray on board
     */
    private void drawRay(LaserRay ray) {
        graph.setColor(Color.YELLOW);
        graph.drawOval((int)ray.getMidX()-5, (int)ray.getMidY()-5, 10, 10);
    }

    void getGuesses()
    {


int holder;
        if(setterInput!=null){

            if((int)setterInput==10){
                try{
                    holder=Integer.parseInt(toDisplay2);
                }catch(Exception E){
                    holderString="invalid input!";
                    holder=100;
                }

                if(holder<=60 && !guessed[holder]){
                    guessed[holder] = true;
                    if(guessAtoms(holder)){
                        holderString="correct!";
                    }else{
                        holderString="wrong";
                        player[playerCounter].add5ToScore();
                    }
                    guessCounter++;
                }
                toDisplay3="Enter Atom guess " + (guessCounter + 1) + " (and press Enter): ";
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
            playerCounter=1;
            laserCount=0;
            guessCounter=0;
            toDisplay3="Enter Atom guess 1 (and press Enter): ";
            makeAtoms();
            for(int a=starting.size();a>0;a--){
                starting.remove(a-1);
            }
        }
    }

    /**
     * Draws hexagons on the board
     * @implNote Must be called before border drawing
     * @see #drawBorder()
     */
    private void drawHexagons() {
        graph.setColor(Color.pink);
        for(int a=0;a<hexagonCounter2;a++){
            graph.drawPolygon(hexagons[a].getXInteger(), hexagons[a].getYInteger(), hexagons[a].getNumberOfPoints());
            graph.drawString(Integer.toString(a), (int)hexagons[a].getMiddleX(), (int)hexagons[a].getMiddleY());
        }
    }

    /**
     * Draws the border, and its index, around the game board hexagon
     * @implNote Must be called after hexagon drawing, else border will be painted over
     * @see #drawHexagons()
     */
    private void drawBorder() {
        /* Border lines */
        graph.setColor(Color.RED);
        graph.drawPolygon(border.getX(), border.getY(), border.getCountPoints());

        /* Border indexes */
        for(int i = 0; i < border.getCountPoints(); i++) {
            graph.setColor(Color.BLUE);
            if(posPointer == i && !setter) graph.setColor(Color.YELLOW); // Colours player pointer yellow

            graph.drawLine(arr[i].getLineX()[0], arr[i].getLineY()[0], arr[i].getLineX()[1], arr[i].getLineY()[1]);
            graph.drawString(Integer.toString(i), arr[i].getLineX()[1], arr[i].getLineY()[1]);
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
    public void keyPressed(KeyEvent event) {
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
    public void keyReleased(KeyEvent event) {
        /* Ensures user pressed W or space */
        if(!setter && !guessing){
            switch(event.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> System.out.println("Ray Spawned");
//                case KeyEvent.VK_G -> {playerhandler.endTurn(laserCount); return;}
                case KeyEvent.VK_G ->{ guessing = true; return;}
                default -> { return; }
            }

        /* Spawns ray */
        laser.set(arr[posPointer].getLineX()[0], arr[posPointer].getLineY()[0], arr[posPointer].getAngle());
        starting.add(posPointer);

        ending.add(0);
        zip = true;
        player[playerCounter].incrementScore();
        laserCount++;
    }

       //reset atom collisions
        for (Atom atom:atoms)
            atom.resetCollisionStatus();

    }

    @Override
    public void keyTyped(KeyEvent e){ 
        setterInput = e.getKeyChar();
    }

    public void resetGame()
    {
        laserCount = 0;
        atoms.clear();
        makeAtomsSetterInput();
        laser = new LaserRay(-10, -10, 10, 10);
    }

    public ArrayList<Atom> getAtoms() { return atoms; }

    public int getAtomNum() { return atomnum; }
    public int getHexagonNum() {return hexagonCounter2;}
}
