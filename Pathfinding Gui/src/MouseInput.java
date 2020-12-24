import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseMotionListener, MouseListener{
	
	public Game game;
	public static boolean startPressed = false, endPressed = false;
	
	public MouseInput(Game game) {
		this.game = game;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		int j = mx-10, i = my-5;
		i /= Game.w; j /= Game.w;
		if(mx >= 5 && mx < Game.w * Game.n + 10 && !Game.runAlg) {
			if(my >= 5 && my < Game.w * Game.n + 10) {
				if(!(i == Game.start.i && j == Game.start.j)) {
					if(!(i == Game.end.i && j == Game.end.j)) {
						if(Game.grid[i][j].wall) {
							Game.grid[i][j].wall = false; Game.grid[i][j].weight = false;
							Game.grid[i][j].cost = 1;
						} else if(Game.grid[i][j].weight) {
							Cell temp = Game.grid[i][j]; temp.weight = false; temp.cost = 1;
						}
						else {
							if(Game.toolWall) {
								Game.grid[i][j].wall = true;
								Game.grid[i][j].cost = 1;
							}
						else {
								Game.grid[i][j].weight = true;
								Game.grid[i][j].cost = 5;
							}
						}
					}
				}
				if(i == Game.start.i && j == Game.start.j) startPressed = true;
				if(i == Game.end.i && j == Game.end.j) endPressed = true;
			}
		} 
	} 
	
	
	public void mouseDragged(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(mx >= 5 && mx < Game.w * Game.n && !Game.runAlg) {
			if(my >= 5 && my < Game.w * Game.n) {
				int j = mx-10, i = my-5;
				i /= Game.w; j /= Game.w;
						if(startPressed || endPressed) {
							Cell temp = Game.grid[i][j]; temp.wall = false; temp.weight = false; temp.cost = 1;
							if(startPressed) Game.start = temp;
							else Game.end = temp;
						} else {
						if(Game.toolWall) {
							Game.grid[i][j].wall = true;
							Game.grid[i][j].cost = 1;
						}
						else {
							Game.grid[i][j].weight = true;
							Game.grid[i][j].cost = 5;
						}
			//		}
		//		}
				}	
			}
		}
	}	
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		startPressed = false; endPressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated 

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseOver(MouseEvent e) {
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
