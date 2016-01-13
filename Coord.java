/**************************************
* NAME:   <HONGDA XIAO>               *
***************************************/

/**
*
* Helper class to keep track of the 
* coordinates used by GameGrid.java
*
*/
public class Coord{
    public int row; 
    public int column;
    
    /**
    * 
    * Create a new Coord object. It contains the information of row and
    * colunm. 
    * @Param r the row of the grid
    * @Param c the column of the grid   
    */

    public Coord(int r, int c){
        row = r;
        column = c;
    }

    /**
    * 
    * Get the position of the constructed initial instance.
    * @Param initial. Of type Coord. To get an instance of 
    * the initial coordinates.
    */
    public Coord(Coord initial)
    {
        row = initial.getRow();
        column = initial.getColumn();
    }

   
    /**
    * 
    * Get the column of current grid. 
    * return the number of row.
    */
    public int getColumn(){
        return column;
    }

    /**
    * 
    * Get the row of current grid. 
    *@Return the number of row.
    */
    public int getRow(){
        return row;
    }

    
}
