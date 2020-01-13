package hangergame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Database class
 * @author Edvinas Dulskas
 */
public class Functions {
    private final String host = "jdbc:mysql://localhost/hanger_game?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String user = "root";         // database username
    private final String pass = "";             // database password

    // ------------------  Connection to the database and query execution -----------------
    public void theQuery(String query){
        Connection con = null;
        Statement stmt = null;
        try{
            con = DriverManager.getConnection(host, user, pass);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
           // JOptionPane.showMessageDialog(null, "Query Executed");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    // ---------------------------  QUERIES ----------------------------
    public void insertUser(String username, String password){
        String query = "insert into user (username, password) values ('"+username+"', '"+password+"')";
        
        theQuery(query);
    }
    
    public User getUser(String username, String password){
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM `user` WHERE username = '" +username+ "' AND password = '" +password+ "'";
        User temp = new User();
        
        try{
            con = DriverManager.getConnection(host, user, pass);
            stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                temp.setId(result.getInt("id"));
                temp.setName(result.getString("username")); 
                temp.setScore(result.getInt("score"));
                
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }    
        
        return temp;
    }
    
    public List getWords(){
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM `word`";
        List temp = new ArrayList();
        
        try{
            con = DriverManager.getConnection(host, user, pass);
            stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                Word ww = new Word();
                ww.setWord(result.getString("word"));
                ww.setDescription(result.getString("description"));
                ww.setId(result.getInt("id"));
                temp.add(ww);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }    
        
        for(int i = 0; i < temp.size(); i++){
            Word w = (Word)temp.get(i);
            System.out.println(w.getWord());
        }
        
        return temp;
    }
    
    public void saveScore(int score, int userId){
        String query = "UPDATE user SET score = '"+score+"' where id = '"+userId+"'";
        
        theQuery(query);
    }
    
    // ------------------------   VALIDATIONS ---------------------------
    // Check if username contains only of letters
    public boolean validateUsername(String username){
        return username.matches("[a-zA-Z]+");
    }
    
    // Check if password contains only of letters and numbers
    public boolean validatePassword(String password){
        return password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[aA-zZ0-9]+$");
    }

    // Check if user exists (only with username)
    public boolean checkUser(String username){
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM `user` WHERE username = '" +username+ "'";
        boolean equal = false;
        
        try{
            con = DriverManager.getConnection(host, user, pass);
            stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                if(username.equals(result.getString("username")))
                    equal = true;
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }        

       return equal;
    }
    
    // Check if user exists (username and password)
    public boolean checkUser(String username, String password){
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM `user` WHERE username = '" +username+ "' AND password = '" +password+ "'";
        boolean equal = false;
        
        try{
            con = DriverManager.getConnection(host, user, pass);
            stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                if(username.equals(result.getString("username")) && password.equals(result.getString("password")))
                    equal = true;
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }        

       return equal;
    }
 
    // ---------------------   MAIN GAME FUNCTIONS ------------------------
    // Check if word contains specific letter
    public boolean checkForLetter(char letter, String word){
        if(word.toLowerCase().indexOf(Character.toLowerCase(letter)) >= 0)
            return true;
        else
            return false;
    }
    
    // Unhide the letter
    public String unhideLetter(char letter, String word, String current){
        char[] temp = current.toCharArray();                // convert current shown to char array
        char[] arr = word.toCharArray();                    // convert word to char array
        List idx = new ArrayList();                         // list of indexes of given letter
        
        for(int i = 0; i < arr.length; i++){
            if(Character.toLowerCase(arr[i]) == Character.toLowerCase(letter))
                idx.add(i);
        }
        
        for(int i = 0; i < idx.size(); i++){                // go through list
            if((int)idx.get(i) == 0)
                temp[(int)idx.get(i)*2] = letter;           // change the letter in specific position
            else
                temp[(int)idx.get(i)*2] = Character.toLowerCase(letter);        // change the letter in specific position
        }
        
        return String.valueOf(temp);                        // return new string (with changed letter from '_'
    }
    
}
