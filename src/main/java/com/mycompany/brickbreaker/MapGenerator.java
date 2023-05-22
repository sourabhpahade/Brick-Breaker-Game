/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 *
 * @author soura
 * 
 * This class is generating our bricks container
 */
public class MapGenerator {
    
   //container for our brick a[1][j] == 1 there id brick
   public int[][] map;
   
   // setting brick dimentions
   public int brickWidth;
   public int brickHeight; 
   
   public MapGenerator(int row, int col){
       
       map = new int[row][col];
       // initializing all index as 1 means there is bricks in each index
       for(int i = 0; i < row; i++){
           for(int j = 0; j < col; j++){
               map[i][j] = 1;
           }
       }
       
       //initializing dimensions of bricks
       brickWidth = 540/col;
       brickHeight = 150/row;
   }
   
   //creating draw function to draw brick in our map container
   //we passed the Graphics2D because it will provide us tools to draw whatever
   //kind of 2d object we want to draw
   public void draw(Graphics2D g){
       
       for(int i = 0; i < map.length; i++){
           for(int j = 0; j < map[0].length; j++){
               
               //filling color in the bricks
               if(map[i][j] > 0){
                   
                   //setting color
                   g.setColor(Color.yellow);
                   
                   //filling color in rectangle
                   g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                   
                   //making strokes and setting color of strokes
                   g.setStroke(new BasicStroke(3));
                   g.setColor(Color.red);
                   
                   //this will draw the rectangle, means boundaries with given dimentions (strokes)
                   g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                           
                   
               }
           }
       }
   }
   
   //for changing vvalue of bricks 1 to 0 when ball hits the brick
   public void setBricksValue(int value, int row, int col){
       
       map[row][col] = value;
       
   }
   
}
