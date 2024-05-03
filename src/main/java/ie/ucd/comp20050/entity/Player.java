package ie.ucd.comp20050.entity;

public class Player{

    /**
     * Score of player
     */
    private int score;

    /**
     * Constructor
     * @param scoreInput int, score player starts at
     */
    public Player(int scoreInput) {
        score=scoreInput;
    }

    /**
     * Method to get the player's current score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Method to increase the player's score by one
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Method to increase the player's score by five
     */
    public void add5ToScore() {
        score += 5;
    }


}
