import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	//snake
	//head position is stored at first index of these arrays
	//snakexlength, snakeylength = snakexpos, snakeypos
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	//which way the snake is moving
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	
	private int lengthofsnake = 3;
	
	private ImageIcon titleImage;
	
	private int moves = 0;
	
	private Timer timer;
	//speed of timer
	private int delay = 100;
	
	private ImageIcon snakeimage;
	
	private int [] enemyxpos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 300, 325, 375, 400, 425, 450, 475, 500, 
								525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
	
	private int [] enemyypos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 
			525, 550, 575, 600, 625};
	
	private ImageIcon enemyimage;
	
	private Random random = new Random();
	
	//random index within the enemypos arrays
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	private int score = 0;
	
	public Gameplay() {
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//add the timer
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g) {
		
		//if game has just started draw default position of snake
		if(moves == 0) {
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		
		//draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		//draw title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw border of playing area
		g.setColor(Color.white);
		g.drawRect(24, 74, 851, 577);
		
		//draw play area background
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		
		//draw score
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Score: " + score, 780, 30);
		
		//draw length of snake
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: " + lengthofsnake, 780, 50);
		
		//draw default position of snake
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		
		for(int a = 0; a < lengthofsnake; a++) {
			
			//drawing head of snake
			//if a == 0 and direction is right
			if(a == 0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
 
			}
			if(a == 0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
 
			}
			if(a == 0 && down) {
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
 
			}
			if(a == 0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
 
			}
			
			//drawing body of snake
			if(a != 0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
 
			}
			
		}

		//head collision with enemy
		
		//load enemy image
		enemyimage = new ImageIcon("enemy.png");
		
		//if enemyxpos and enemyypos == snake head pos
		if((enemyxpos[xpos] == snakexlength[0]) && enemyypos[ypos] == snakeylength[0]) {
			
			//increment score
			score++;
			//picked up enemy so add length
			lengthofsnake++;
			
			//create new enemy x and y positions
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
		
		//draw enemy
		enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
		
		//snake collision with itself
		//iterate through body of snake
		for(int b = 1; b < lengthofsnake; b++) {
			
			//if snake position at [b] index is equal to snake head position
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0]) {
				//stop the snake
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Press Space to Restart", 325, 340);
			}
			
		}
		
		//clear the screen
		g.dispose();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		timer.start();
		
		//moving snake right
		if(right) {
			
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				
				if(r == 0) {
					snakexlength[r] = snakexlength[r] + 25;
				}
				else {
					snakexlength[r] = snakexlength[r-1];
				}
				//check if snake hits right side of border
				if(snakexlength[r] > 850) {
					snakexlength[r] = 25;
				}
			}
			
			repaint();
			
		}
		
		if(left) {
			
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r+1] = snakeylength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				
				if(r == 0) {
					snakexlength[r] = snakexlength[r] - 25;
				}
				else {
					snakexlength[r] = snakexlength[r-1];
				}
				//check if snake hits right side of border
				if(snakexlength[r] < 25) {
					snakexlength[r] = 850;
				}
			}
			
			repaint();
			
		}
		
		if(down) {
			
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				
				if(r == 0) {
					snakeylength[r] = snakeylength[r] + 25;
				}
				else {
					snakeylength[r] = snakeylength[r-1];
				}
				//check if snake hits right side of border
				if(snakeylength[r] > 625) {
					snakeylength[r] = 75;
				}
			}
			
			repaint();
			
		}
		
		if(up) {
			for(int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r+1] = snakexlength[r];
			}
			for(int r = lengthofsnake; r >= 0; r--) {
				
				if(r == 0) {
					snakeylength[r] = snakeylength[r] - 25;
				}
				else {
					snakeylength[r] = snakeylength[r-1];
				}
				//check if snake hits right side of border
				if(snakeylength[r] < 75) {
					snakeylength[r] = 625;
				}
			}
			
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		//restart game
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//increment moves because game has started
			moves++;
			right = true;
			
			//if snake is moving left, cant move right
			if(!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			
			up = false;
			down = false;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//increment moves because game has started
			moves++;
			left = true;
			
			//if snake is moving right, cant move left
			if(!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
			
			up = false;
			down = false;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			//increment moves because game has started
			moves++;
			up = true;
			
			//if snake is moving down, cant move up
			if(!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}
			
			left = false;
			right = false;
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//increment moves because game has started
			moves++;
			down = true;
			
			//if snake is moving up, cant move down
			if(!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}
			//make sure these are false when up is true
			left = false;
			right = false;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
