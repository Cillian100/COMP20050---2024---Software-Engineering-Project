package ie.ucd.comp20050.drawing;

import java.awt.*;
import java.util.ArrayList;

public class TextHandler {
    GamePanel gp;

    String toDisplay3="Enter Atom guess 1 (and press Enter): ";
    boolean [] guessed;
    String currGuess ="";
    String holderString="";
    int guessCounter=0;
    Graphics graph;
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    double modifier;
    enum state  {info,guessing,end};
    TextHandler(GamePanel g)
    {
        gp = g;
        modifier = gp.modifier;
        SCREEN_HEIGHT = gp.SCREEN_HEIGHT;
        SCREEN_WIDTH = gp.SCREEN_WIDTH;
        guessed = new boolean[61];

    }


    /**
     *  draws text on screen depending on current game state
     * @param s state of game
     */
    void drawText(state s)
    {
        graph = gp.graph;
        switch(s)
        {
            case info: drawPlayerGameplayInfo(); break;
            case guessing: getGuesses(); break;
            case end: displayEndScreen(); break;
        }


    }

    /**
     * displays endgame screen
     */
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
        textPosition=(int)(SCREEN_WIDTH/2-(400*modifier));
        String endText = "Player 1 wins";
        if (gp.getPlayerScore(2) == gp.getPlayerScore(1))   endText = "Draw!";
        else if (gp.getPlayerScore(1) > gp.getPlayerScore(2)) endText = "Player 2 wins";

            graph.drawString(endText, textPosition, SCREEN_HEIGHT/2 + lineHeight);


            graph.drawString("Player 1 score = " + gp.getPlayerScore(1), textPosition, SCREEN_HEIGHT/2 + lineHeight*2);
            graph.drawString("Player 2 score = " + gp.getPlayerScore(2), textPosition, SCREEN_HEIGHT/2 + lineHeight*3);


            graph.drawString("Press g to exit game", textPosition, SCREEN_HEIGHT/2 + lineHeight*5);

        if(gp.getTextInput()!=null){
            if(gp.getTextInput()=='g'){
                System.exit(0);
            }
        }
    }

    /**
     * draws player info during game including laser beginning and endings
     */

    void drawPlayerGameplayInfo(){

        ArrayList<Integer> starting = gp.getStartings();
        ArrayList<Integer> ending = gp.getEndings();
        graph.setColor(Color.RED);
        String startingPositions = "Atom starting & ending positions:\n";
        graph.drawString("Player " + (gp.playerCounter +1) +  ": Press a and d to move. Press and release w to shoot,press and release g to guess", (int)(50*modifier), (int)(50*modifier));
        graph.drawString("lasers shot: " + gp.laserCount, (int)(25*modifier), (int)(100*modifier));
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
        gp.setTextInput(null);
    }

    /**
     * gets player guesses
     */
    void getGuesses(){
        graph.setColor(Color.red);
        int holder;
        if(gp.getTextInput()!=null){
            if((int)gp.getTextInput()==10){
                try{
                    holder=Integer.parseInt(currGuess);
                }catch(Exception E){
                    holder=100;
                }

                if(holder >= 0 &&holder<=60 && !guessed[holder]){
                    guessed[holder] = true;
                    if(guessAtoms(holder)){
                        holderString="correct!";
                    }else{
                        holderString="wrong";
                        gp.getPlayer().add5ToScore();
                    }
                    guessCounter++;
                }
                else
                {
                    if (holder == 100) holderString="invalid input!";
                    else if (holder > 60) holderString = "Guess number too big!";
                    else if (holder < 0)holderString = "Guess number too small!";
                    else if (guessed[holder]) holderString = "You already guessed that";
                }
                toDisplay3="Enter Atom guess " + (guessCounter + 1) + " (and press Enter): ";
                currGuess ="";
            }else{
                toDisplay3=toDisplay3.concat(String.valueOf(gp.getTextInput()));
                currGuess = currGuess.concat(String.valueOf(gp.getTextInput()));
            }
        }

        gp.setTextInput(null);
        if (guessCounter + 1 < 6) {
            graph.drawString(toDisplay3, (int) (50 * modifier), (int) (50 * modifier));
            graph.drawString(holderString, (int) (50 * modifier), (int) (80 * modifier));
        }
        if(guessCounter==5){
            holderString = "";
            guessed = new boolean[61];
            gp.reset();
            gp.setTextInput(null);
            guessCounter=0;
            toDisplay3="Enter Atom guess 1 (and press Enter): ";

        }
    }

    /**
     * helper for guess atoms
     * @param answer
     * @return returns true if atom guessed was in right place
     */
    private boolean guessAtoms(int answer) {
        for(int a=0; a<gp.atomnum; a++){
            if(gp.getAtoms().get(a).getHexagon() == answer) return true;
        }
        return false;
    }


}
