package com.ryan.brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author _ryan
 *
 */
public class MapGenerator {

	// *******************************
	// Fields
	// *******************************
	
	public int map[][];
	public int bricksWidth;
	public int bricksHeight;
	
	// *******************************
	// Constructors
	// *******************************

	public MapGenerator(int row, int col) {
		
		map = new int[row][col];
		
		for(int idx = 0; idx < map.length; idx++) {
			
			for(int jdx = 0; jdx < map[0].length; jdx++) {
				map[idx][jdx] = 1;
			}
		}
		
		bricksWidth = 540/col;
		bricksHeight = 150/row;
	}
	
	// *******************************
	// Methods
	// *******************************

	public void draw(Graphics2D g) {

		for (int idx = 0; idx < map.length; idx++) {

			for (int jdx = 0; jdx < map[0].length; jdx++) {
				
				if(map[idx][jdx] > 0) {
					g.setColor(Color.white);
					g.fillRect(jdx * bricksWidth + 80, idx * bricksWidth + 50, bricksWidth, bricksHeight );
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.red);
					g.drawRect(jdx * bricksWidth + 80, idx * bricksWidth + 50, bricksWidth, bricksHeight );
					
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		
		map[row][col] = value;
	}
	
	
	
}//MapGenerator Class
