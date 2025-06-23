import javax.swing.*;
public class App{
    public static void main(String[]args) throws Exception{ 
        int broadWidth = 600 ; 
        int broadHeight = broadWidth; 
        JFrame frame  = new JFrame("snake"); 
        frame.setVisible(true);
        frame.setSize(broadHeight, broadWidth); 
        frame.setResizable(false); 
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakeGame snakeGame = new SnakeGame(broadWidth, broadHeight);
        frame.add(snakeGame);  
        frame.pack();
        snakeGame.requestFocus();


    }
}