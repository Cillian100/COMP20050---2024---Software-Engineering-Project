package ie.ucd.comp20050;

import ie.ucd.comp20050.drawing.GamePanel;
import ie.ucd.comp20050.entity.Atom;

import java.util.ArrayList;
import java.util.Scanner;


public class PlayerInteractions {
    int player = 0;
    ArrayList<Integer> playerScores;

    GamePanel gamePanel;

    public PlayerInteractions(GamePanel g)
    {
        gamePanel = g;
        playerScores = new ArrayList<Integer>();
    }

    public void endTurn(int lasercount)
    {
        int score = 0;
        System.out.println(lasercount);
        score += lasercount;

        score += guessAtoms();
        playerScores.add(score);
        if (player == 0) switchPlayer();
        else endGame();
    }

    int guessAtoms()
    {

        Scanner scan = new Scanner(System.in);
        int score = 0;
        boolean[] prevguess = new boolean[100];
        int i= 0;
        while (i < gamePanel.getAtomNum())
        {
            System.out.println("Enter a hexagon number (between 0 and " + gamePanel.getHexagonNum());
            while (!scan.hasNextInt()) scan.next();
            int guess = scan.nextInt();
            if (guess <0 || guess >= gamePanel.getHexagonNum() || prevguess[guess])
            {
                System.out.println("number is not good");
                continue;
            }
            prevguess[guess] = true;
            i++;
            //shoul;d prbbaly be a variable
            int tmp = 5;
            for (Atom a: gamePanel.getAtoms())
            {
                if (a.getHexagon() == guess) tmp = 0;
            }
            score += tmp;
        }
        return score;
    }

    void endGame()
    {

        System.out.println("End of game. Result is: ");
        System.out.println("Player A: " + playerScores.get(0) + " Player B: " + playerScores.get(1));
        if (playerScores.get(0).equals(playerScores.get(1)))
        {
            System.out.println("Draw!");
            return;
        }

        char p = playerScores.get(0) < playerScores.get(1)? 'A':'B';
        System.out.println("Player " + p + " won!");
    }

    void switchPlayer()
    {
        player++;

        gamePanel.resetGame();

        System.out.println("Player B's turn to experiment");
    }
}
