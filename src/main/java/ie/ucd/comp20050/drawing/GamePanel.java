package ie.ucd.comp20050.drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import ie.ucd.comp20050.MathUtils;
import ie.ucd.comp20050.entity.Atom;
import ie.ucd.comp20050.entity.LaserRay;
import ie.ucd.comp20050.entity.Player;
import ie.ucd.comp20050.component.Arrow;
import ie.ucd.comp20050.component.Border;
import ie.ucd.comp20050.component.Hexagon;

public class GamePanel extends JPanel implements KeyListener {
    Hexagon[] hexagons = new Hexagon[100];
    Arrow[] arr = new Arrow[100];
    LaserRay laser = new LaserRay(-10, -10, 10, 10);
    Border border;
    int hexagonCounter =0;
    int posPointer = 0;
    boolean zip=false;
    Random random = new Random();
    int laserCount =0;
    Character setterInput;
    String toDisplay3="Enter Atom guess 1 (and press Enter): ";
    String currGuess ="";
    String holderString="";
    int guessCounter=0;
    ArrayList<Integer> starting = new ArrayList<Integer>();
    ArrayList<Integer> ending = new ArrayList<Integer>();
    boolean guessing=false;
    ArrayList<Integer> enterAndExit = new ArrayList<Integer>();
    boolean round=false;

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

    Renderer drawer;
    /* UPDATED VARS */

    /**
     * ArrayList storage for Atoms in the game.
     */
    private ArrayList<Atom> atoms;

    /**
     * Graphics tool. Set by paintComponent, allows methods to be called without issue from other classes.
     */
    Graphics graph;

   public static boolean test = false;


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
        if(test){
            makeAtomsTest();
        }else{
            makeAtoms();
        }

        guessed = new boolean[61];
        drawer = new Renderer(this);

    }


    /**
     * makes 5 atoms placed randomly
     */
     private void makeAtoms()
     {  
         boolean already[] = new boolean[100];
         atoms = new ArrayList<Atom>();
        int hex = random.nextInt(hexagonCounter);
         for(int i = 0; i < atomnum; i++) {
             while (already[hex])
             {
                  hex = random.nextInt(hexagonCounter);
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
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graph = g; // Updates stored Graphics
        drawer.setGraph(g);
        gameLoop();
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
        hexagons[hexagonCounter] = new Hexagon(xConstant, yConstant, modifier, 60);
        hexagonCounter++;
        int angle=180;
        int flip=1;
        int flip2=flip;
        int layer2=1;


        for(int a=0;a<6;a++){
            hexagons[hexagonCounter] = new Hexagon(hexagons[0].getX()[a], hexagons[0].getY()[a], modifier, angle);
            hexagonCounter++;
            angle=angle+60;
            angle=angle%360;
        }
        layer2++;

        for(int c=0;c<3;c++){
            for(int b=0;b<6;b++){
                for(int a=0;a<layer2;a++){
                    if(b+a==5+layer2-1){
                        hexagons[hexagonCounter] = new Hexagon(hexagons[flip2].getX()[b], hexagons[flip2].getY()[b], modifier, angle);
                    }else{
                        hexagons[hexagonCounter] = new Hexagon(hexagons[flip+a].getX()[b], hexagons[flip+a].getY()[b], modifier, angle);
                    }
                    hexagonCounter++;
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



    public int borderDetection(int a){
        int a2;
        if(a==53){
            a2=0;
        }else{
            a2=a+1;
        }
        double x1 = border.getX()[a];
        double x2 = border.getX()[a2];
        double y1 = border.getY()[a];
        double y2 = border.getY()[a2];
        double px = laser.getPosX();//laser.getMidX();
        double py = laser.getPosY();//laser.getMidY();
        double len1= MathUtils.pointsDistance(x1, y1, px, py);
        double len2=MathUtils.pointsDistance(x2, y2, px, py);
        double len3=MathUtils.pointsDistance(x1, y1, x2, y2);
        if((len1+len2-len3) < 25){
            return a;
        }
        return 100;
    }

    /**
     * Method to handle collision detection
     */
    private void collisionDetection() {
        if (!zip) return;

        atomCollision();

        borderCollision();
    }

    /**
     * Collision detection with atoms
     */
  void atomCollision()
  {

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
                  atoms.get(a).setCollideStatus(true);
              bounce = Atom.ABSORB; break;
          }
          else if (tmp == Atom.EXIT_180) //this overpowers everything except for absorb at edge
          {
              bounce = 180;
              for (;a< atomnum;a++)
              {
                  if (atoms.get(a).collide(laser,hexxs,hexys)==Atom.EXIT_ABSORB) {
                      bounce = Atom.ABSORB;
                  }
              }
              break;
          }
      }
      if (bounce == Atom.ABSORB){
          ending.add(100);
          zip = false;
      }
      //if it has collided and not at edge centre
      if (bounce != 0 && tmp != Atom.EXIT_180) {
          centreLaser();
      }
      laser.setCollideStatus(LaserRay.CollideState.never);
      laser.addBounce(bounce);

  }

    /**
     * Collision detection with border
     */
  void borderCollision()
  {
      for(int a=0;a<54;a++){
          int result=borderDetection(a);
          if(result!=100){
              enterAndExit.add(result);
              if(enterAndExit.size()>2){
                  round=true;
                  zip = false;
              }else{
                  round=false;
              }
          }
      }
  }

  void centreLaser()
  {

      double x = MathUtils.closestValue(laser.getPosX(),hexxs);
      double y = MathUtils.closestValue(laser.getPosY(),hexys);
      laser.setPosX(x);
      laser.setPosY(y);
  }

    private boolean guessAtoms(int answer) {
        for(int a=0; a<atomnum; a++){
            if(atoms.get(a).getHexagon() == answer) return true;
        }
        return false;
    }


    /**
     * Primarily handles drawing of game itself, along with other game logic.
     */
    private void gameLoop() {
        if(playerCounter!=2) {
            if(round){
                round=false;
                ending.add(enterAndExit.get(2));
                enterAndExit.clear();
            }
            collisionDetection();
            if(!test) {
                drawer.drawEntities();
            }

            graph.setColor(Color.red); // this is colouring text

            if(guessing) {
                getGuesses();
            }
            else {
                drawPlayerGameplayInfo();
            }
            drawer.drawBackGround();
        }
        else {
            displayEndScreen();
        }
    }

    void displayEndScreen() {
        graph.setFont(new Font("TimesRoman", Font.PLAIN, (int)(100*modifier))); 
        int lineHeight = graph.getFontMetrics().getHeight();
        graph.setColor(Color.RED);
        //game over box
        graph.drawRect((int)(100*modifier), (int)(100*modifier), (int)(SCREEN_WIDTH-(200*modifier)), (int)(SCREEN_HEIGHT*0.3));
        int x=(int)(100*modifier);
        int y=(int)(150*modifier)+(int)(SCREEN_HEIGHT*0.3);
        graph.drawRect(x, y,(int)(SCREEN_WIDTH-(200*modifier)), (int)(SCREEN_HEIGHT*0.5));
        graph.drawString("GAME OVER", (int)(SCREEN_WIDTH/2-(300*modifier)), (int)((100*modifier)+(SCREEN_HEIGHT*0.3)/2));
        int textPosition;
        if (player[1].getScore() == player[0].getScore())
        {
            textPosition=(int)(SCREEN_WIDTH/2-(400*modifier));
            graph.drawString("Draw!" + player[0].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 1 score = " + player[0].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight* 2);
            graph.drawString("Player 2 score = " + player[1].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight* 3);
        }
        else if(player[0].getScore()<player[1].getScore()) {
            textPosition=(int)(SCREEN_WIDTH/2-(600*modifier));
            graph.drawString("Player 1 wins, score = " + player[0].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 2 loses, score = " + player[1].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight*2);
        }
        else {
            textPosition=(int)(SCREEN_WIDTH/2-(600*modifier));
            graph.drawString("Player 2 wins, score = " + player[1].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight);
            graph.drawString("Player 1 loses, score = " + player[0].getScore(), textPosition, SCREEN_HEIGHT/2 + lineHeight*2);
        }

        graph.drawString("Press g to exit game", textPosition, SCREEN_HEIGHT/2 + lineHeight*5);

        if(setterInput!=null){
            if(setterInput=='g'){
                System.exit(0);
            }
        }
    }

    void drawPlayerGameplayInfo(){
        String startingPositions = "Atom starting & ending positions:\n";
        graph.drawString("Player " + (playerCounter +1) +  ": Press a and d to move. Press and release w to shoot,press and release g to guess", (int)(50*modifier), (int)(50*modifier));
        graph.drawString("lasers shot: " + laserCount, (int)(25*modifier), (int)(100*modifier));
        int why=(int)(1500*modifier);
        int lineHeight = graph.getFontMetrics().getHeight();
        graph.drawString(startingPositions, (int)(1400*modifier), why);
        for(int a=ending.size() - 1;a >= 0;a--){
            if(ending.get(a)==100){
                graph.drawString("starting: " + starting.get(a).toString() + "    ending: absorbed", (int)(1400*modifier), why+=lineHeight);
            }else{
                graph.drawString("starting: " + starting.get(a).toString() + "    ending: " + ending.get(a), (int)(1400*modifier), why+=lineHeight);
            }
        }
        setterInput=null;
    }




    void getGuesses(){
        int holder;
        if(setterInput!=null){
            if((int)setterInput==10){
                try{
                    holder=Integer.parseInt(currGuess);
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
                currGuess ="";
            }else{
                toDisplay3=toDisplay3.concat(String.valueOf(setterInput));
                currGuess = currGuess.concat(String.valueOf(setterInput));
            }
        }

        graph.drawString(toDisplay3, (int)(50*modifier), (int)(50*modifier));
        graph.drawString(holderString, (int)(50*modifier), (int)(80*modifier));
        setterInput=null;
        if(guessCounter==5){
            guessed = new boolean[61];
            guessing=false;
            playerCounter++;
            laserCount=0;
            holderString = "";
            guessCounter=0;
            toDisplay3="Enter Atom guess 1 (and press Enter): ";
            if (test) makeAtomsTest();
            else makeAtoms();
            for(int a=starting.size();a>0;a--){
                starting.remove(a-1);
            }
            ending.clear();
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
        if(!guessing && !zip){
            switch(event.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_SPACE -> spawnRay();
                case KeyEvent.VK_G ->{ guessing = true; return;}
                default -> { return; }
            }


    }


    }

    /**
     * spawns a ray at the current position pointer
     */
    public void spawnRay()
    {

        laser.set(arr[posPointer].getLineX()[0], arr[posPointer].getLineY()[0], arr[posPointer].getAngle());
        starting.add(posPointer);
        enterAndExit.clear();
        zip = true;
        player[playerCounter].incrementScore();
        laserCount++;
        for (Atom atom:atoms)
            atom.setCollideStatus(false);
    }

    @Override
    public void keyTyped(KeyEvent e){ 
        setterInput = e.getKeyChar();
    }

    ArrayList<Atom> getAtoms() {return atoms;}
    LaserRay getLaser() {return laser;}

    //for testing mainly
    public ArrayList<Integer> getEndings()
    {
       return ending;
    }

    public int getPlayerScore(int p)
    {
        assert(p < 3 && p > 0);
       return player[p - 1].getScore();
    }

    public void testLaserShoot(int pos)
    {
       posPointer = pos;
       spawnRay();
    }
    static private int[]positions;
    static public void setTestInput(int a,int b,int c,int d,int e)
    {
        positions = new int[]{a,b,c,d,e};
    }
    private void makeAtomsTest()
    {
        atoms = new ArrayList<Atom>();
        for(int i = 0; i < atomnum; i++) {
            atoms.add(new Atom(
                    hexagons[positions[i]].getMiddleX(),
                    hexagons[positions[i]].getMiddleY(),
                    atomRadius,
                    ringRadius,
                    hexagondistance
                    ,positions[i]
            ));
        }
    }


}
