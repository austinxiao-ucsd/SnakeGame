import java.util.ArrayList;
import java.lang.Math;

/*
* The purpose of this class is to move, grow and
* check if the snake intersects with its own body.
*/
public class Snake{
    private static ArrayList<Coord> snakeBody;
    private int snakeLength = 0;
    private static Coord copyTail = null;
    private GraphicsGrid color;
    public boolean right = false;
    public boolean left = false;
    public boolean up = false;
    public boolean down = true;


    /** Constructor will create the snake head in a certain location and add
    * the head to the snakeBody arraylist 
    * @Param the coordinate of the head
    */
    public Snake(Coord initial){
        snakeBody = new ArrayList<Coord>();
        snakeBody.add(initial);
        snakeLength ++;
    }
    /**
    * get the length of the snakeBody
    * @return the snake length
    */
    public int length(){
        return snakeLength;
    }
    /**
    * get the head of the snake
    * @return the coordinate of the snake head
    */
    public Coord getHead(){
        if(snakeBody != null){
            return snakeBody.get(0);
        }
        return null ;
    }
    /**
    * get the tail of the snake
    * @return the tail of the snake head
    */
    public Coord getTail(){
        if(snakeBody != null){
            return snakeBody.get(snakeBody.size() - 1);
        }
        return null;
    }
    /**
    * make a copy of the snakeBody and store it into a new array
    *@return a new array containing the coordinate of the snake body
    */
    public Coord [] getSegs(){
        Coord [] segs = new Coord[snakeLength];
        for (int i = 0; i < snakeBody.size(); i++)
            segs[i] = snakeBody.get(i);
        return segs;
    }
    /**
    * move the snake. The method will check if the move is valid. It will
    * return false if told to move the snake in a invalid direction
    * @param x the vertical direction 
    * @param y the horizontal direction
    * @return if the move is valid
    */
    public boolean moveSnake(int x, int y){
        // Move the coordinate of the snake head.
        if((Math.abs((float)x) == 1 &&  Math.abs((float)y) == 0)|| 
                (Math.abs((float)x) == 0 &&  Math.abs((float)y) == 1))
        {
            Coord head = new Coord(getHead().getRow() + x, 
                                  getHead().getColumn() + y);
            System.out.println("head" + head.getRow() + "," 
                + head.getColumn());
            snakeBody.remove(snakeBody.size()-1);
            snakeBody.add(0, head);
        return true;
        }
        return false;
    }
    /** 
    *get the snakebody. 
    *@return the arrayList of the snakeBody 
    */
    public ArrayList<Coord> paintSnake(){
        return snakeBody;
    }

    /**
    * The method determines if the snakehead intersects with its body
    * @Param head the coordinate of the snakehead 
    * @Return if the snake head hits its body
    */
    public boolean intersects(Coord head)
    {
        if (head != null){
            if(snakeBody.size() == 1){
                return false;
            }
            else{
                for(int j = 1; j < snakeBody.size(); j++){  
                    if (snakeBody.get(j).getColumn() == head.getColumn() &&
                        snakeBody.get(j).getRow() == head.getRow()){
                        System.out.println("Heads equal!");
                        return true;
                    }


                }
             }
        }
        return false;
    }
    /**
    * grow the snake. The method will check if the move is valid. It will
    * return false if told to move the snake in a invalid direction. It will
    * grow the snake length after a valid move. 
    * @param x the vertical direction 
    * @param y the horizontal direction
    * @return if the grow is valid
    */

    public boolean growSnake(int x, int y){
        boolean check;
        copyTail = new Coord(getTail().getRow(), getTail().getColumn());
        check = moveSnake(x,y);
        if(check == false){
            return false;
        }
        
        snakeBody.add(snakeBody.size(),copyTail);
        snakeLength++;
        return true;
    }
    
}

