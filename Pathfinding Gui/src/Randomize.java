import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Randomize {
	
	static ArrayList<Cell> open;
	static int n = Game.n, w = Game.w;
	static Random rand = new Random();
	
	public static void overlay(Graphics g, BufferStrategy bs) {
		open = new ArrayList<>();
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				Game.grid[i][j].wall = true;
				g.fillRect(j * w, i * w, w, w);
				Game.grid[i][j].tested = false;
			}
		}
		Cell start = Game.start, end = Game.end;
		Game.grid[start.i][start.j].wall = false;
		Game.grid[start.i][start.j].tested = true; 
		open.add(Game.grid[start.i][start.j]);
		boolean running = true;
		while(running) {
			running = randomizeGrid();
			Game.stopTime(10);
			draw(g, bs);
		}
	}
	
	static void draw(Graphics g, BufferStrategy bs) {
		g.setColor(Color.white);
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(!Game.grid[i][j].wall) {
					g.fillRect(j*w, i*w, w, w); 
					g.setColor(Color.black);
					g.drawRect(j * w, i * w , w, w);
					g.setColor(Color.white);
				}
			}
		}
		Game.drawStartEnd(g);
		g.setColor(Color.black);
		g.drawLine(0, 0, 0, w*n);
		g.drawLine(0, 0, n*w, 0);
		g.setColor(Color.white);
		bs.show();
	}
	
	static boolean randomizeGrid() {
		if(open.size() == 0) {
			return false;
		}
		Cell current = open.get(0);
		open.remove(0);
		int count = 0, avialable = 0;
		for(int i=0; i<current.neighbors.size(); i++) {
			Cell temp = current.neighbors.get(i);
			if(!temp.tested) {
				avialable++;
				int random = rand.nextInt(3);
				if(random == 1) {
					count++;
					open.add(temp);
					temp.wall = false;
				}
			}
			if(count == 2) i = 10;
		}
		
		if(count == 0 && avialable > 0 ) {
			Collections.shuffle(current.neighbors);
			for(int i=0; i<current.neighbors.size(); i++) {
				Cell temp = current.neighbors.get(i);
				if(!temp.tested) {
					open.add(temp); temp.wall = false;
					i = 10;
				}
			}
		}
		
		for(int i=0; i<current.neighbors.size(); i++) {
			current.neighbors.get(i).tested = true;
		}
		
		return true;
		
	}
}
