/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.brickbreaker;

/**
 *
 * @author sourabh
 */

import javax.swing.JFrame;

public class BrickBreaker {

    public static void main(String[] args) {
        
        //Creating frame
        JFrame frame = new JFrame();
        
        //gameplay class whole game logic
        GamePlay  gameplay = new GamePlay();
        
        //Creating boudary and setting width and heght of frame
        frame.setBounds(10,10,700,600);
        
        //frame title
        frame.setTitle("Brick Breaker");
        
        //making frame non-resizabe
        frame.setResizable(false);
        
        //visibility of frame is false by defualt, we set it true
        //to make frame visible
        frame.setVisible(true);
        
        //on tapping the close window button it will close the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //adding our game to the frame
        frame.add(gameplay);
        
        
        
        
    }
}
