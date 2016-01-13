/**************************************
* NAME:   <HONGDA XIAO>               *
***************************************/

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
* 
* This class creates the grid of which
* the snake will be placed. Calls the 
* Also checks the snake movement and 
* add obstacles to the game.
*
*/

public class GameGrid{
	public Snake mySnake;    
    public GraphicsGrid grid;
    public ArrayList <Coord> obstacle;
    public int xObstacle;
    public int yObstacle; 
	
    /**
    *
    * Constructor to create a new Snake instance.
    * Also construct an obstacle array.
    * @Param int width. Width of the grid.
    * @Param int height. Height of the grid.
    * @Param int pixel. Pixel of the cells.
    */

    public GameGrid(int width, int height, int pixel){
		mySnake = new Snake(new Coord(0,width/(2*pixel)));
        grid = new GraphicsGrid(width, height, pixel);
        obstacle = new ArrayList<Coord>();
    }

	/**
    * Check if the snake makes a valid move.
    * @Param int r. Current row of the snake.
    * @Param int c. Current column of the snake.
    */

	public boolean snakeMove(int r, int c ){
        Coord [] copySnake = mySnake.getSegs();
		
		if(mySnake.moveSnake(r,c) && moveCheck()){
            clearSnake(copySnake);
            colorSnake(mySnake.paintSnake());
            return true;

		}
        else{
            return false;
        }
	}

	/**
    *
    * Grows the snake by one segment.
    * @param int R. Row coordinate of the snake tail.
    * @Param int C. Column coordinate of the snake tail.
    */

	public boolean growSnake(int R, int C){
        Coord [] tempSnake = mySnake.getSegs();
       
        if (mySnake.growSnake(R,C) && moveCheck()){
            clearSnake(tempSnake);
            colorSnake(mySnake.paintSnake());
            return true;      
        }
        else{
            return false;
        }
	}

    /**
    *
    * Getters method to get the grid created.
    * @Return the grid created.
    */

    public GraphicsGrid mygrid(){
        return grid;
    }

    /**
    *
    * Get the row coordinate of the 
    * R position of the snake segments.
    *
    */
    public int getRpos(int R){
    	System.out.println("R" + R);
    	return mySnake.paintSnake().get(R).getRow();
    }
    /**
    *
    * Get the row coordinate of the 
    * R position of the snake segments.
    *
    */
    public int getCpos(int C){
    	return mySnake.paintSnake().get(C).getColumn();
    }
    /**
    *
    * Colors the snake head and the snake body.
    * @Param snakeInfo. Arraylist that contains the snake body
    * segments.
    */
    public void colorSnake(ArrayList <Coord> snakeInfo){
    	grid.fillCell(snakeInfo.get(0).getRow(), 
    			snakeInfo.get(0).getColumn(), Color.BLACK);
    	int i = 1;
    	for (i = 1; i < snakeInfo.size(); i++){
    		grid.fillCell(snakeInfo.get(i).getRow(), 
    			snakeInfo.get(i).getColumn(), Color.GREEN);
    	}
    	
    }
    /**
    *
    * Clears the snake body that is being used.
    *
    */
    public void clearSnake(Coord [] snakeInfo)
    {
        for (int i = 0; i < snakeInfo.length; i++){
        	//System.out.println(i);
            grid.clearCell(snakeInfo[i].getRow(), snakeInfo[i].getColumn());
        }
    }

    /**
    *
    * Check if the snake head intersects any invalid objects.
    * @return hit then true. Else then false.
    */

    public boolean moveCheck(){
        if (mySnake.intersects(mySnake.getHead())){
            return false;
        }
        if(mySnake.getHead().getRow() == 0 || mySnake.getHead().getColumn() 
                == 0 || mySnake.getHead().getRow() > grid.vertboundary() 
                || mySnake.getHead().getColumn() > grid.horiboundary()){
            return false;
        }
        for( int i = 0; i < obstacle.size(); i++){
            if(mySnake.getHead().getRow() 
            == obstacle.get(i).getRow() && mySnake.getHead().getColumn()
            == obstacle.get(i).getColumn()){
                    return false;
            }
        }
        return true;

    }
    

    /**
    *
    * Generate a random obstacle to be placed on the grid. The method will 
    *also make sure the obstacle will not be genetated on the snake body. 
    * 
    */
    public void obstacle(){
        Random randomGenerator = new Random();
        boolean obstaclecheck = false;
        do{
            xObstacle = randomGenerator.nextInt(grid.vertboundary()) + 1;
            yObstacle = randomGenerator.nextInt(grid.horiboundary()) + 1;

            for(int i = 0; i < mySnake.paintSnake().size(); i++){
                if(getRpos(i) == xObstacle && getCpos(i) == yObstacle){
                    obstaclecheck = false;
                    System.out.println("FALSE FALSE FALSE" + xObstacle + "," +
                            yObstacle);

                    break;
                }
                else{
                    obstaclecheck = true;
                    
                }
            }
        }while(obstaclecheck == false);
        grid.fillCell(xObstacle, yObstacle, Color.BLUE);
        obstacle.add(new Coord(xObstacle, yObstacle));
        System.out.println("-------(" + xObstacle + "," + 
                    yObstacle + ")---------OBSTACLE ADDED------------------");


        
       
    }   

}
	
