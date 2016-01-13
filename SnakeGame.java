/**************************************
* NAME:   <HONGDA XIAO>               *
***************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.event.*;


/**
 * In control of the layout of the game. Contain
 * the GUI class to display components and use it
 * to interact with the game. Also starts another
 * thread for the game to be played.
 * @author HONGDA XIAO, ZHOUHANG SHAO
 * @version CSE-11Winter2015-PR7
 */

public class SnakeGame extends JFrame implements ActionListener,ChangeListener{
	private static int wDefault = 400;
	private static int hDefault = 400;
	private static int pixel = 10;
	private final int wMarginal = 60;
	private final int hMarginal = 100;
    private final int MIN_SNAKESPEED = 1000;
    private final int MAX_SNAKESPEED = 50;
    private final int RATIO_OBSTACLE = 100;
    private final int RATIO_MOVE = 10;
    private final int MAX_SLIDER = 20;
    private final int SLIDER_DIFF = 50;
    private final int WIDTH_EXPAND = 0;
    private final int HEIGHT_EXPAND = 100;
	private JButton resetButton;
	private JButton newButton;
	private JLabel currentScore;
    private JLabel highScoreLabel; 
	private JSlider snakeSpeed;
	private JLabel	gameOver;
    private JPanel upperBound;
	private	JPanel lowerBound;
    private JPanel centerPanel;
    private Container container;
    private SnakeMover runSnake = null;
    private GameGrid game; 
    private Thread thread;
	private int highScore = 0;
    private int score = 0;
    private int speedCount = MIN_SNAKESPEED;
    private int countMove = 0;

    /**
    * Constructor of the class. Set up the initial
    * height, width, and pixels of the grid. Add keylistener
    * to the Jframe components and start the snake thread.
    * @param width of the window. Must be of type int.
    * @param height of the window. Must be of type int.
    * @param pixel of the window. Must be of type int.
    */
    public SnakeGame(int width, int height, int pixels) {
        wDefault = width;
        hDefault = height;
        pixel = pixels;
        container = getContentPane();
        snakeGraphics();
        newButton.addActionListener(this);
        resetButton.addActionListener(this);
        snakeSpeed.addChangeListener(this);
        startSnake();
    }

    /** 
    *
    * This method starts the snakegame and draws the grid in the middle
    * of the Jpanel layout.
    *
    */
    private void startSnake(){
    	setSize(wDefault + wMarginal, hDefault + hMarginal);
		game = new GameGrid(wDefault, hDefault, pixel);
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        container.add(centerPanel, BorderLayout.CENTER);
        container.validate();
        centerPanel.setPreferredSize(new Dimension(wDefault + 
                    WIDTH_EXPAND ,hDefault +HEIGHT_EXPAND));
        centerPanel.add(game.grid);
		setVisible(true);
	}



    /**
    * This method contains the GUI part of the game. It will
    * create all the GUI components and display the buttons,
    * sliders and labels on the appropriate positions.
    */
    private void snakeGraphics(){
		upperBound = new JPanel();
		upperBound.setLayout(new GridLayout(2,3));
		JLabel setLabel = new JLabel("Score: ");
		currentScore = new JLabel("" + score);
		gameOver = new JLabel("");
		JLabel highLabel = new JLabel("High Score: ");
		highScoreLabel = new JLabel("" + highScore);
		upperBound.add(setLabel);
		upperBound.add(currentScore);
		upperBound.add(gameOver);
		upperBound.add(highLabel);
		upperBound.add(highScoreLabel);

		lowerBound = new JPanel();
		lowerBound.setLayout(new GridLayout());
		snakeSpeed = new JSlider(1,MAX_SLIDER,1);
		resetButton = new JButton("Reset!");
		newButton = new JButton("New Game");
		
		lowerBound.add(newButton);
		lowerBound.add(resetButton);
		lowerBound.add(snakeSpeed);

       	container.add(upperBound, BorderLayout.NORTH);
        container.add(lowerBound, BorderLayout.SOUTH);
		container.validate(); 
	}	

    /**
    * Display the "gameover" text when the game is terminated. The method must
    * be called when the thread is stopped. 
    */
    public void gameOver(){
    	gameOver.setText("GAME OVER!");
    }
    
    /**
    * Adds the score of the 
    * game and places the obstacle on the 
    * screen.
    */
    public void countScore(){
        score += 10;
        if(score > highScore){
            highScore = score;
        }
        placeObstacle();
        currentScore.setText("" + score);
        highScoreLabel.setText("" + highScore);
    }
    public void countMove(){
        countMove++;
        if((countMove % RATIO_MOVE) == 0){
            System.out.println("ENTERED ENTERED ENTERED");
            game.obstacle();
        } 
        System.out.println(" ++++COUNTMOVE+++++" + countMove);
    }

    /**
    * Determines the conditions when an obstacle
    * should be placed on the grid.
    */
    private void placeObstacle(){
        if((score % RATIO_OBSTACLE) == 0){
            game.obstacle();
            if(speedCount > MAX_SNAKESPEED){
                speedCount -= SLIDER_DIFF;
                System.out.println("=============VALUE==============");
                System.out.println(speedCount);
                System.out.println("=============VALUE==============");
                snakeSpeed.setValue(MAX_SLIDER - speedCount/SLIDER_DIFF);
                System.out.println("Speed changed!");
            }
        }
        
        
    }

    /**
    * Detect if the new game button and reset button is hit.
    * Sets the new condition of the game correspondingly.
    * @Param e the actionEvent
    */
	public void actionPerformed(ActionEvent e){
        Object clicked = e.getSource();
            if(clicked == resetButton){
            	runSnake.stop();
            	gameOver.setText("");
            	highScore = 0;
            	score = 0;
            	currentScore.setText("" + score);
            	highScoreLabel.setText("" + highScore);
                speedCount = MIN_SNAKESPEED;
                snakeSpeed.setValue(1);             
            }
            else if(clicked == newButton){
                remove(centerPanel);
                wDefault = getSize().width - wMarginal;
                hDefault = getSize().height -hMarginal;
                

            	if (runSnake != null){
                    //runSnake.r = 1;
                    //runSnake.c = 0;
                    runSnake.stop();
                    remove(centerPanel);
                    revalidate();
                    repaint();
                    wDefault = getSize().width - wMarginal;
                    hDefault = getSize().height -hMarginal;
                }
                startSnake(); 
                runSnake = new SnakeMover(game,this);
                thread = new Thread(runSnake);
                thread.start();
                addKeyListener(runSnake);
                setFocusable(true);
   	            requestFocusInWindow();
                score = 0;
                currentScore.setText("" + score);
                gameOver.setText("");
                                
            }

	}
    /**
    * Detect if the slider is being used at all. This would
    * adjust the speed of the snake according to the slider 
    * input
    * @Param e the changeevent
    *
    */
    public void stateChanged(ChangeEvent e){
        speedCount = MIN_SNAKESPEED + MAX_SNAKESPEED - SLIDER_DIFF * 
            snakeSpeed.getValue();
        setFocusable(true);
        requestFocusInWindow(); 

         System.out.println("--++++++++++----" +snakeSpeed.getValue());
    }

    /**
     *
     * Get the current speed.
     * @return returns the value of the current speed.
     *
     */
    public int speedIncrease()
    {
        return speedCount; 
    }
    
    /**
     *
     * Helper class to show the usage statement when the
     * command-line inputs are invalid.
     *
     */
    private static void helper(){
        System.out.println("java SnakeGame [width height segmentsize]");
        System.out.println("   width - Integer width of the" +
                "playing grid in pixels");
        System.out.println("   height - Integer height of the" +
                "playing grid in pixels");
        System.out.println("   segmentsize - Integer size of" + 
                "each snake segment in pixels");
        System.out.println(" ");
        System.out.println("   defaults: width = 400, height = 400, " +
                "segmentsize= 10");
    }

    /**
     * Takes in command-line arguments.
     *
     * @Param args is the string to store the user input.
     *
     */

    public static void main(String[] args) {
 		if (args.length == 3){
            try{ 
 			    wDefault = Integer.parseInt(args[0]);
			    hDefault = Integer.parseInt(args[1]);
			    pixel = Integer.parseInt(args[2]);
            }
            catch(NumberFormatException excpt){
                helper();
                return;
            }
            if (wDefault <= 0 || hDefault <= 0 || pixel <= 0){
                helper();
                return;
            }
            SnakeGame gamePlay = new SnakeGame(wDefault, hDefault, pixel); 
        }
        else if (args.length == 1 && args[0].equals("help")){
            helper();
        }
 		else if (args.length == 0){
            SnakeGame gamePlay = new SnakeGame(wDefault, hDefault, pixel);
        }
        else{
            helper();       
        }

    }
}
