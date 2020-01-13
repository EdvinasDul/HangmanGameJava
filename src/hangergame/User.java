
package hangergame;

public class User {
    private String username;
    private int id;
    private int score;
    private int lives = 6;
    
    public User(){

    }
    
    public String getName() { return username; }
    
    public int getId() { return id; }
    
    public int getScore() { return score; }
    
    public void setName(String username) { this.username = username; }
    
    public void setScore(int score) { this.score = score; }
    
    public void setId(int id) { this.id = id; }
    
    public int getLives() { return lives; }
    
    public void reduceLive() { lives--; }
    
}
