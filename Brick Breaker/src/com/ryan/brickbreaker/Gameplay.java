package com.ryan.brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

/**
 * 
 * @author _ryan
 *
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{

	//******************************
	//Fields
	//******************************
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	//*******************************
	//Constructors
	//*******************************
	
	public Gameplay() {
		
		map = new MapGenerator(3,7); //rows then cols
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
	}

	//*******************************
	//General Methods
	//*******************************
	
	public void paint(Graphics g) {
		
		//Background attributes
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//Draw map
		map.draw((Graphics2D)g);
		
		//Scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		//border attributes
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0,692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Game Paddle (user)
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		if(totalBricks <= 0) {
			
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		//the Ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if(ballPosY > 570) {
			
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!: ", 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}
	
	public void moveRight() {
		
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		
		play = true;
		playerX -= 20;
	}
	
	//*******************************
	//Implemented Methods
	//*******************************
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		
		if(play) {
			
			//this if handles the contact of the ball on the paddle
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX,550,100,8))){
				ballYdir = -ballYdir;
			}
			
			//detects intersection of balls and bricks
			A: for(int idx = 0; idx < map.map.length; idx++) {
				for(int jdx = 0; jdx < map.map[0].length; jdx++) {
					if(map.map[idx][jdx] > 0) {
						int brickX = jdx*map.bricksWidth +80;
						int brickY = idx*map.bricksHeight +50;
						int brickWidth = map.bricksWidth;
						int brickHeight = map.bricksHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, idx, jdx);
							totalBricks--;
							score += 5; //each brick is worht five
							
							if(ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			ballPosX += ballXdir;
			ballPosY += ballYdir;
			
			if(ballPosX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballPosY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballPosX > 677) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			if(playerX >= 600) {
				playerX = 600;
			}else {
				
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			if(playerX < 10) {
				playerX = 10;
			}else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

}//Gameplay Class
