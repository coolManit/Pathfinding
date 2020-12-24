import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Dijkstra {
	static ArrayList<Cell> open = new ArrayList<>(), path = new ArrayList<>();
	
	static void runAlg(Graphics g, BufferStrategy bs) {
		for(int i=path.size()-1; i>=0; i--) {
			path.remove(i);
		}
		for(int i=open.size()-1; i>=0; i--) {
			open.remove(i);
		}
		Game.start.used = true;
		Game.start.wall = false; Game.end.wall = false;
		open.add(Game.start);
		boolean running = true;
		outerLoop: while(running) {
			if(open.isEmpty()) {
				running = false;
				continue outerLoop;
			}
			Cell best = null, parent = null;
			int minCost = Integer.MAX_VALUE;
			for(int l=0; l<open.size(); l++) {
				Cell current = open.get(l);
				for(int i=0; i<current.neighbors.size(); i++) {
					Cell temp = current.neighbors.get(i);
					if(temp.used || temp.wall) continue;
					int cost = current.currCost + temp.cost;
						if(cost < minCost) {
							best = temp; parent = current;
							minCost = cost;
						}
					
				}
			}	
				if(best == null) {
					running = false; continue outerLoop;
				}
				Cell current = best;
				current.used = true;
				current.previous = parent;
				if(current == Game.end) {
					findBestPath(current);
					running = false; continue outerLoop;
				}
				Game.stopTime(Game.delay);
				open.add(current);
				current.currCost = current.cost + parent.currCost;
				drawCell(g, current);
				bs.show();
		}
		drawPath(g, bs);
	}
	static void drawCell(Graphics g, Cell current) {
		int w = Game.w;
		g.setColor(Color.red);
		g.fillRect(current.j  * w, current.i * w, w, w);
		if(current.weight) g.drawImage(Window.image, current.j * w, current.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
		g.setColor(Color.green);
		g.fillRect(current.previous.j * w, current.previous.i * w, w, w);
		g.setColor(Color.black);
		if(current.previous.weight) g.drawImage(Window.image, current.previous.j * w, current.previous.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
		g.drawRect(current.j  * w, current.i * w, w, w);
		g.drawRect(current.previous.j * w, current.previous.i * w, w, w);
		
		Game.drawStartEnd(g);
	}
	static void drawPath(Graphics g, BufferStrategy bs) {
		int w = Game.w;
		for(int i=path.size()-3; i>=1; i--) {
			Game.stopTime(10);
			g.setColor(Color.blue);
			Cell t = path.get(i);
			g.fillRect(t.j * w , t.i*w, w, w);
			if(t.weight) g.drawImage(Window.image, t.j * w, t.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
			g.setColor(Color.black);
			g.drawRect(t.j * w, t.i * w, w, w);
			bs.show();
		}
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
