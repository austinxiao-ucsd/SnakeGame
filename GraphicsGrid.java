/**************************************
* NAME:   <HONGDA XIAO>               *
***************************************/

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
* Helper class to get the grid color.
*/
class gridColor{
    Coord myCoord;
    Color myColor;
    /**
    * Create an instance of Coord and Color.
    * @Param Coord coord. Copy the current positions.
    * @Param Color color. Copy the current color.
    */
    gridColor(Coord coord, Color color){
        myCoord = coord;
        myColor = color;
    }
}

/**
*
* Creates the initial grid for the game
* to play on.
*
*/
public class GraphicsGrid extends JPanel {
    
    private ArrayList<gridColor> fillCells;
    private int getPix;
    private int getWidth;
    private int getHeight;
    private int column;
    private int row;

    /**
    *
    * Constructor to create the cell. 
    * @Param int wid. Width of the grid.
    * @Param int height. Height of the grid.
    * @Param int pix. Pixel of the grid.
    *
    */
    public GraphicsGrid(int wid, int height, int pix) {
        
    	fillCells = new ArrayList<gridColor>();
        getWidth = wid;
        getHeight = height;
        getPix = pix;
        row = height/pix;
        column = wid/pix;
    }

    /**
    * Paint the grid. Also center the grid.
    * @Param Graphics g. Internally defined.<D-N>
    */
    @Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
        int wMarginal = (this.getWidth() - getWidth)/2;
        int hMarginal = (this.getHeight() - getHeight)/2;
		for (gridColor GC : fillCells) {
			int cellX = ((GC.myCoord.getColumn()-1) * getPix + wMarginal);
			int cellY = ((GC.myCoord.getRow()-1) * getPix + hMarginal);
			g.setColor(GC.myColor);
			g.fillRect(cellX, cellY, getPix, getPix);
		} 
		// Draw the grid
		g.setColor(Color.BLACK );
		g.drawRect(wMarginal, hMarginal,column*getPix, row*getPix);
		for (int i = 0; i < column*getPix; i += getPix) {
			g.drawLine(wMarginal+i, hMarginal, wMarginal + i, row*getPix +
                    hMarginal);
		};
		for (int i = 0 ; i < row*getPix; i += getPix) {
			g.drawLine(wMarginal, hMarginal+i,column*getPix + wMarginal,hMarginal+i);
		}
    }

    /**
    *
    * Get the horizontal boundary of the grid.
    */
	public int horiboundary(){
		return getWidth / getPix;
	}

    /**
    *
    * Get the vertical boundary of the grid.
    */
	public int vertboundary(){
		return getHeight / getPix;
	}

    /**
    *
    * Fill the cell with the color specified.
    *
    */
	public synchronized void fillCell(int r, int c, Color col) {
		fillCells.add(new gridColor(new Coord(r,c),col));
		repaint();
	}

    /**
    *
    * Clear the cell the snake passed by.
    *
    */
    public synchronized void clearCell(int r, int c){
        for(gridColor gc : fillCells)
        {
            if(gc.myCoord.row == r && gc.myCoord.column == c){
                fillCells.remove(gc);
                break;        
            }
        
        }
		repaint();
	}
       

}
