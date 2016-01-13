/**************************************
* NAME:   <HONGDA XIAO>               *
***************************************/

import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
*  
* Determines the rules of the game. Change the 
* basic ASCII snake in snake.java to a graphical
* representation.
*
*/
public class SnakeMover implements Runnable,KeyListener{
		private boolean run = true;
		private boolean turned = false;
		private int r = 1;
		private int c = 0;	
		private GameGrid myGrid;
        private SnakeGame myGame;
        private boolean turnLeft = false;
        private boolean turnRight = false;
        public int countMove;
        	
        /**
        *
        * Constructor to the class.  
        * @Param game is of type GameGrid.
        * @Param Game is of type SnakeGame.
        *
        */
		public SnakeMover(GameGrid game, SnakeGame Game){	
			myGrid = game;
            myGame = Game;
        }

	    /**
        * Determines of the 'j' or 'l' key is pressed on the keyboard.
        * Determines direction of the snake.
        * @Param e the key event
        */
		@Override
		public synchronized void keyPressed(KeyEvent e){    
			if (e.getKeyCode() == KeyEvent.VK_J) {     
			    turnLeft = true;
                turned = true;
                
                System.out.println("J pressed");
				                      
			}
			if (e.getKeyCode() == KeyEvent.VK_L) { 
                turnRight = true; 
                turned = true;
                System.out.println("L pressed") ; 
			} 
		}

        /**
        *Corresponds to the keylistener interface.
        */
		public void keyReleased(KeyEvent e){
		}
	
        /**
        *Corresponds to the keylistener interface.
        */
		public void keyTyped(KeyEvent e){
		}

        /**
        *Overrides the run method to control the snake. The value of r and c
        * reflects the current direction that the snake is facing. Modifying r
        * and c will change the direction of the snake.
        *
        */
		@Override
		public void run(){
	        System.out.println("method 'run' entered");
	        while(run){
                 // Snake moving up.
                 if (r == -1 && c == 0){   
                    
                    try { TimeUnit.MILLISECONDS.sleep(myGame.speedIncrease());}
	                catch (InterruptedException e){}
                    if(turnLeft && myGrid.growSnake(0,-1)){
                        r = 0;							    
					    c = -1;
					    myGame.countScore();
                        turnLeft = false;
                    
                    }
                    else if(turnRight && myGrid.growSnake(0,1)){
                        r = 0;						 		
                        c = 1;
                        myGame.countScore();
                        turnRight = false;

                        
                    }
                    else if(!turned && myGrid.snakeMove(-1,0)){
                        System.out.println("move up forward"); 

                        
                    } 
                    else{
                        run = false;
                        myGame.gameOver();
                        break;
                    }
                    turned = false;

                    
	            } 
                // Snake moving down.
		        if (r == 1 && c == 0){  
                    try { TimeUnit.MILLISECONDS.sleep(myGame.speedIncrease());}
	                catch (InterruptedException e){}

                    if(turnLeft && myGrid.growSnake(0,1)){
                        r = 0;
                        c = 1;
                        myGame.countScore();
                        turnLeft = false;

                    }
                    else if(turnRight && myGrid.growSnake(0,-1)){
                        r = 0;
                        c = -1;
                        myGame.countScore();
                        turnRight = false;

                        
                    }
                    else if(!turned && myGrid.snakeMove(1, 0)){
                    	System.out.println("move down foward");

                    }
                    else{
                        run = false;
                        myGame.gameOver();
                        break;
                    }
                    turned = false;
                    
	            }
	            
                // Snake moving right.
		        if (r == 0 && c == 1){  
                    try { TimeUnit.MILLISECONDS.sleep(myGame.speedIncrease());}
	                catch (InterruptedException e){}
                    if(turnLeft && myGrid.growSnake(-1,0)){
                        r = -1;
                        c = 0;
                        myGame.countScore();
                        turnLeft = false;

                    }
                    else if(turnRight && myGrid.growSnake(1,0)){
                        r = 1;
                        c = 0;
                        myGame.countScore();
                        turnRight = false;

                        
                    }
                    else if(!turned && myGrid.snakeMove(0, 1)){
                    	System.out.println("move right forward");

                    }
                    else{
                        run = false;
                        myGame.gameOver();
                        break;
                    }
                    turned = false;
                    
	            }
	            
                // Snake moving left.
		        if (r == 0 && c == -1){  
                    try { TimeUnit.MILLISECONDS.sleep(myGame.speedIncrease());}
	                catch (InterruptedException e){}
                    if(turnLeft && myGrid.growSnake(1,0)){
                        r = 1;
                        c = 0;
                        myGame.countScore();
                        turnLeft = false;

                    }
                    else if(turnRight && myGrid.growSnake(-1,0)){
                        r = -1;
                        c = 0;
                        myGame.countScore();
                        turnRight = false;

                    }
                    else if(!turned && myGrid.snakeMove(0, -1)){
                    	
                    	System.out.println("move left forward");

                    }
                    else{
                        run = false;
                        myGame.gameOver();
                        break;
                    }
                    turned = false;
                    

	            }
                myGame.countMove();
                // Counts the number of moves made by the snake.
                
	        }
		    System.out.println("exit");
	}

        /**
         * Stop the thread. Method be called in SnakeGame
         *
         */
		public void stop()
		{
			run = false;
			myGame.gameOver();
			System.out.println("alive = false");
		}
} 

