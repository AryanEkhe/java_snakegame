import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList; 
import java.util.Random; 
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener , KeyListener{
    private class Tile{
        int x;
        int y; 
        Tile(int x , int y){
            this.x = x; 
            this.y = y; 
        }
    }
    int broadWidth;
    int broadHeight;
    int tileSize = 25; 

    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;   
    

    //food 
    Tile food; 
    Random random;


    //game logic 
    Timer gameLoop; 
    int velocityX;
    int velocityY;
    boolean gameOver = false;



    SnakeGame(int broadWidth , int broadHeight){
        this.broadWidth = broadWidth;
        this.broadHeight = broadHeight;
        setPreferredSize(new Dimension(this.broadWidth , this.broadHeight)); 
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true); 
        snakeHead = new Tile (5,5);
        snakeBody = new ArrayList<Tile>();
        
        food = new Tile(10,10);
        random = new Random(); 
        placeFood();
        velocityX = 0;
        velocityY = 0;


        gameLoop = new Timer(100 , this);
        gameLoop.start(); 


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        draw(g); 
    }
    public void draw(Graphics g){ 
        for( int i= 0 ; i <broadWidth/tileSize ; i++){ 
            g.drawLine(i*tileSize, 0, i*tileSize , broadHeight); 
            g.drawLine(0, i*tileSize  ,  broadWidth , i*tileSize); 
        }
        //food 
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize , food.y *tileSize , tileSize , tileSize); 

        
        
        //snakeHead
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize , snakeHead.y * tileSize , tileSize , tileSize); 

        //snakeBody 
        for(int i =0 ; i<snakeBody.size() ; i++){
            Tile snakePart = snakeBody.get(i); 
            g.fillRect(snakePart.x * tileSize , snakePart.y * tileSize , tileSize , tileSize);
        }
        //Score
        g.setFont(new Font("Arial" , Font.PLAIN , 16));
        if(gameOver){
            g.setColor(Color.red); 
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()) , tileSize - 16 , tileSize); 

        } else{ 
            g.drawString("Score: " + String.valueOf(snakeBody.size()) , tileSize -16 , tileSize);
        }


    }
    public void placeFood(){
        food.x = random.nextInt(broadWidth/tileSize);
        food.y = random.nextInt(broadHeight/tileSize); 

    }
    public boolean collision(Tile tile1 , Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    public void move(){ 
        if(collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x , food.y));
            placeFood();
        }


        //snake Body 
        for ( int i = snakeBody.size()-1 ; i>=0 ; i--){ 
            Tile snakePart = snakeBody.get(i);
            if(i == 0 ){ 
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevsnakePart = snakeBody.get(i-1);
                snakePart.x = prevsnakePart.x;          
                snakePart.y = prevsnakePart.y;

            }

        }

        //snakeHead 
        snakeHead.x += velocityX;
        snakeHead.y += velocityY; 

        //gameOver conditions 
        for ( int i =0 ; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collision with the snake head
            if(collision(snakeHead, snakePart)){ 
                gameOver = true;

            } 
        }
        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > broadWidth || 
           snakeHead.y*tileSize < 0 || snakeHead.y*tileSize>broadHeight){
           gameOver  = true; 
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if(gameOver){ 
            gameLoop.stop(); 

        }
    }
    @Override
    public void keyPressed(KeyEvent e) { 
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX=0;
            velocityY=-1;
        }else if( e.getKeyCode() == KeyEvent.VK_DOWN && velocityY !=-1){
            velocityX=0;
            velocityY=1;
        }else if(e.getKeyCode()== KeyEvent.VK_LEFT && velocityX != 1){
            velocityX=-1;
            velocityY=0;
        }else if(e.getKeyCode()== KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX=1;
            velocityY=0;
        }
       }


    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
