import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;

public class DepthFirstSearch {
	
	static ArrayList<Cell> path = new ArrayList<>();
	static int w = Game.w;
	
	static void runAlg(Graphics g, BufferStrategy bs) {
		for(int i=path.size()-1; i>=0; i--) {
			path.remove(i);
		}
		for(int i=0; i<Game.n; i++) {
			for(int j=0; j<Game.n; j++) {
				if(Game.grid[i][j].weight) {
					g.setColor(Color.white);
					Game.grid[i][j].weight = false; Game.grid[i][j].cost = 1;
					g.fillRect(j * w, i * w, w, w);
					g.setColor(Color.black);
					g.drawRect(j * w, i * w, w, w);
					Game.stopTime(10);
					bs.show();
				}
			}
		}
		Game.drawGrid(g);
		Cell current = Game.start;
		current.used = true;
		dfs(current, g, bs);
		if(path.size() > 0) {
			drawPath(g, bs);
		}
	}
	
	static void dfs(Cell current, Graphics g, BufferStrategy bs) {
		if(path.size() > 0) return;
		if(current == Game.end) {
			findBestPath(current);
			return;
		}
//		Collections.shuffle(current.neighbors);
		for(int i=0; i<current.neighbors.size(); i++) {
			Cell temp = current.neighbors.get(i);
			if(!temp.used && !temp.wall) {
			temp.used = true;
			temp.previous = current;
			Game.stopTime(Game.delay);
			drawCell(current.neighbors.get(i), g, bs);
			dfs(current.neighbors.get(i), g, bs);
			if(path.size() > 0) return;
			}
		}
		
	}
	
	static void drawPath(Graphics g, BufferStrategy bs) {
		int w = Game.w;
		for(int i=path.size()-3; i>=1; i--) {
			Game.stopTime(10);
			g.setColor(Color.blue);
			Cell t = path.get(i);
			g.fillRect(t.j * w , t.i*w, w, w);
			g.setColor(Color.black);
			g.drawRect(t.j * w, t.i * w, w, w);
			bs.show();
		}
	}
	
	static void drawCell(Cell current, Graphics g, BufferStrategy bs) {
		g.setColor(Color.green);
		g.fillRect(current.j*w, current.i*w, w, w);
		g.setColor(Color.black);
		g.drawRect(current.j*w, current.i*w, w, w);
		Game.drawStartEnd(g);
		bs.show();
	}
	
	static void findBestPath(Cell current) {
		Cell temp = current;
		path.add(temp);
		while(true) {
			if(temp == Game.start) {
				path.add(Game.start); return;
			}
			path.add(temp.previous);
			temp = temp.previous;
		}
	}
}
