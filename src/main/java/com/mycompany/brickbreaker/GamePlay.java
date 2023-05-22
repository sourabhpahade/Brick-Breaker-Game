/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;

/**
 *
 * @author sourabh
 */

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

//for capturing and to listen the events
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener,ActionListener{
    
    //game is stop initially
    private boolean play = false;
    
    //initially score is 0
    private int score = 0;
    
    //count of bricks in the game
    private int totalBricks = 21;
    
    //making timer
    private Timer timer;
    
    //making delay to make sure there is friction while moving the ball
    private int delay = 8;
    
    //x cordinate which defines the initial position of slider 
    //for slider y postion if fixed
    private int playerX = 350;
    
    //initial position of ball;
    private int ballposX = 120;
    private int ballposY = 350;
    
    //setting initial movements of ball
    private int ballXDir = +1;
    private int ballYDir = +2;
    
    //map Generator
    private MapGenerator map;
    
    public GamePlay(){
        
        // initializing brick container of 3*7 size
        map = new MapGenerator(3,7);
        
        //listing to whatever is going in the gamplay class
        addKeyListener(this);
        
        //it will tell the cpu that this program is started running, please give attention to it
        //if focus is false our app will not be seen in the panel
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //this will make delay in action of each component, delay is used to 
        //introduce friction in game
        timer = new Timer(delay,this);
        timer.start();
    }
    
    //we are painting every component of game
    public void paint(Graphics g){
        
        //filling bg color
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        //filling our map generator i.e bricks
        // we type cast graphics to graphics2d as graphics2d is subclass of graphics
        map.draw((Graphics2D) g);
        
        //making top x-axis border
        g.setColor(Color.blue);
        g.fillRect(0, 0, 692, 3);
        
        //left y-axis border
        g.fillRect(0, 0, 3, 592);
        
        //rigth y-axis border
        g.fillRect(680, 0, 3, 592);
        
        //Setting score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("" + score, 590, 30);
        
        //drawing slider
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);
        
        //drawing ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 30);
        
        //Conditions when the game stops
        if (ballposY > 570) {
            
            play = false;
            
            //because the ball is gone in the exit zone
            ballXDir = 0;
            ballYDir = 0;
            
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        
        if(totalBricks == 0){
            
            play = false;
            ballYDir = -2;
            ballXDir = -1;
            
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);

        }
        //we are clearing all the graphics from memory to render new graphics at next frame
        g.dispose();

             
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //if play is true our game will start running
        //when game is running friction is also come into picture so we also started the timer
        timer.start();
        
        if (play) {
            
            //if ball touches the slider, the ball reflects at equal angle in opposite direction
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }
                      
            //handling scenerios when the ball hits the bricks
            A:
            for (int i = 0; i < map.map.length; i++) {
                
                for (int j = 0; j < map.map[0].length; j++) {
                    
                    if (map.map[i][j] > 0) {
                        
                        //This code is giving us location and dimention of each brick 
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;
                        
                        //making new rectangle for when ball hits the red brick this rec. will replace it
                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        
                        //making ball rectangle representing ball
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        
                        //making copy of rect to maintain the original rect
                        Rectangle brickrect = rect;
                        
                        //if ball hits the bricks
                        if (ballrect.intersects(brickrect)) {
                            
                            //painting bricks to black color and changing 1 -> 0,means no brick present at
                            // (i,j)
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            
                            // handling horizontal vertices striking 
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXDir = -ballXDir;
                            } 
                            //handling other cases
                            else {
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                        
                    }

                }
            }

            //ball movement when it strikes at left,right,top border and in free space
            //movement in free space
            ballposX += ballXDir;
            ballposY += ballYDir;
            
            //hitting left border
            if (ballposX < 0) {
                ballXDir = -ballXDir;
            }
            
            //hitting top border
            if (ballposY < 0) {
                ballYDir = -ballYDir;
            }
            //hitting right border
            if (ballposX > 670) {
                ballXDir = -ballXDir;
            }
        }
        
        //it is repainting every thing after each game
        repaint();
        
    }
            
    @Override
    public void keyPressed(KeyEvent e) {
        
        //handling right key event
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            
            if(playerX >= 600){
                playerX = 600;
            }else{
                moveRight();
            }
        }
        
        //handling left key event
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            
            if(playerX < 10){
                playerX = 10;
            }else{
                moveLeft();
            }
            
        }
        
        //handling Enter key event
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            
            //if game is ended we are reseting the game to initial state
            //so that the new game can start
            if(play == false){
                
                ballposX = 120;
                ballposY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
            
        }
        
        
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    public void moveRight() {
       play = true;
       playerX += 20;
    }
    
    public void moveLeft() {    
       play = true;
       playerX -= 20;
    }

    
    
        
}
