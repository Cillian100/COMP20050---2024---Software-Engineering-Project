package ie.ucd.comp20050;

public class Player{
    private int score;

    public Player(int apple){
        score=apple;
    }

    public int getScore(){
        return score;
    }

    public void incrementScore(){
        score++;
    }

    public void add5ToScore()
    {
        score += 5;
    }


}
