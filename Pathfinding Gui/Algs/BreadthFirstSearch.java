import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class BreadthFirstSearch {
	
	static ArrayList<Cell> open = new ArrayList<>(), path = new ArrayList<>();
	static int w = Game.w;
	
	static void runAlg(Graphics g, BufferStrategy bs) {
		for(int i=path.size()-1; i>=0; i--) {
			path.remove(i);
		}
		for(int i=open.size()-1; i>=0; i--) {
			open.remove(i);
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
		Game.start.used = true;
		Game.start.wall = false; Game.end.wall = false;
		open.add(Game.start);
		boolean loop = true;
		outerLoop: while(loop) {
			if(open.isEmpty()) {
				loop = false; continue outerLoop;
			}
			Cell current = open.get(0);
			open.remove(0);
			
			if(current == Game.end) {
				findBestPath(current);
				loop = false; continue outerLoop;
			}
			boolean had = checkNeighbors(current);
			current.used = true;
			Game.stopTime(Game.delay);
			drawOpenClose(g, current, had);
			Game.drawStartEnd(g);
			bs.show();
			}
			drawPath(g, bs);
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
	
	static boolean checkNeighbors(Cell current) {
		boolean had = false;
		for(int i=0; i<current.neighbors.size(); i++) {
			Cell neighbor = current.neighbors.get(i);
			boolean temp = legal(current, neighbor);
			if(temp) {
			had = true;
			neighbor.used = true;
			open.add(neighbor);
			neighbor.previous = current; 
			}
		}
		return had;
	}
	
	//checking if the neighbor is not a wall and hasn't been reached yet
		public static boolean legal(Cell current, Cell neighbor) {
			if(neighbor.wall) return false;
			if(neighbor.used) return false;
			return true;
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
		static void drawOpenClose(Graphics g, Cell current, boolean had) {
			int w = Game.w;
			//drawing closed set
			if(had)g.setColor(Color.red);
			else g.setColor(Color.green);
				g.fillRect(current.j * w, current.i * w, w, w);
			
				//drawing open set
			if(current != Game.start) {
			g.setColor(Color.green);
			g.fillRect(current.previous.j * w , current.previous.i * w, w,w);
			
			//drawing gird lines again
			g.setColor(Color.black);
			g.drawRect(current.j * w, current.i * w, w, w);
			g.drawRect(current.previous.j * w, current.previous.i * w, w, w);
		}
	}
}
