import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class AstarAlg {
	static Cell start, end, closedCell;
	static ArrayList<Cell> open, closed, path;
	static int w = Game.w;
	
	static void runAlg(Graphics g, BufferStrategy bs) {
		open = new ArrayList<>();
		closed = new ArrayList<>();
		start = Game.start; end = Game.end;
		open.add(start);
		boolean running = true, foundEnd = false;
		outerLoop: while(running ) {
			Game.stopTime(Game.delay);
			if(!open.isEmpty()) {
				int winner = 0;
				for(int i=0; i<open.size(); i++) {
					if(open.get(i).f < open.get(winner).f) {
						winner = i;
					}
				}
				Cell current = open.get(winner);
				closedCell = current;
				
				if(current == end) {
					foundEnd = true;
					path = new ArrayList<>();
					Cell temp = current;
					path.add(temp);
					while(true) {
						if(temp == start) {
							path.add(start); running = false; continue outerLoop;
						}
						path.add(temp.previous);
						temp = temp.previous;
					} 
				}
				removeIndex(current);
				closed.add(current);for(int i=0; i<current.neighbors.size(); i++) {
					Cell neighbor = current.neighbors.get(i);
					
					if(!closed.contains(neighbor) && !neighbor.wall) {
						int tempG = current.g + neighbor.cost;
						
						if(open.contains(neighbor)) {
							if(tempG < neighbor.g) neighbor.g = tempG;
						} else {
							neighbor.g = tempG;
							open.add(neighbor);
							//openSet
							g.setColor(Color.red);
							g.fillRect(neighbor.j * w, neighbor.i * w, w, w);
							if(neighbor.weight) {
								g.drawImage(Window.image, neighbor.j * w, neighbor.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
							}
							g.setColor(Color.black);
							g.drawRect(neighbor.j * w, neighbor.i * w, w, w);
						}
						neighbor.h = heuristic(neighbor, end);
						neighbor.f = neighbor.g + neighbor.h;
						neighbor.previous = current;
					}
				}
			} else {
				running = false;
				continue outerLoop;
		}
			//closed Set 
			g.setColor(Color.green);
			g.fillRect(closedCell.j * w, closedCell.i * w, w, w);
			if(closedCell.weight) {
				g.drawImage(Window.image, closedCell.j * w, closedCell.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
			}
			g.setColor(Color.black);
			g.drawRect(closedCell.j * w, closedCell.i * w, w, w);
			Game.drawStartEnd(g);
			bs.show();
	}
		//drawing path
		if(foundEnd) {
			for(int i=path.size()-3; i>=0; i--) {
				g.setColor(Color.blue);
				Game.stopTime(10);
				Cell current = path.get(i);
				g.fillRect(current.j * w, current.i * w, w, w);
				if(current.weight) g.drawImage(Window.image, current.j * w, current.i * w , Window.image.getHeight()-1, Window.image.getHeight()-1, null);
				g.setColor(Color.black);
				g.drawRect(current.j * w, current.i * w, w, w);
				bs.show();
		}
	}
}
	
	static void removeIndex(Cell current) {
		for(int i= open.size()-1; i>=0; i--) {
			if(open.get(i) == current) {
				open.remove(i);
			}
		}
	}
	static int heuristic(Cell a, Cell b) {
		int d = Math.abs(a.i - b.i);
		d += Math.abs(a.j - b.j);
		return d;
	}
	
}
