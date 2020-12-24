import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = 1L;
	public static final int WIDTH=550, HEIGHT= 550 ;
	public static int n = 25;
	public static final int w = 425/ n;
	private Thread thread;
	public boolean running =false;
	static ArrayList<Cell> randomGrid = new ArrayList<>();
	static Cell [][] grid;
	static Cell start, end;
	static boolean done = false;
	static boolean runAlg = false;
	static boolean random = false;
	static boolean bfs = true, aStar = false, dfs = false, dij = false, toolWall = true;
	static Window window;
	static int delay = 19;
	
	
	public Game() {
		this.addMouseListener(new MouseInput(this));
		this.addMouseMotionListener(new MouseInput(this));
		new Window (WIDTH, HEIGHT, "Pathfinding Alg", this);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		 this.requestFocus();
		  long lastTime = System.nanoTime();
		  double amountOfTicks = 60.0;
		  double ns = 1000000000 / amountOfTicks;
		  double delta = 0;
		  long timer = System.currentTimeMillis();
		  while (running) {
		   long now = System.nanoTime();
		   delta += (now - lastTime) /ns;
		   lastTime = now;
		   while(delta >= 1) {
		    delta--;
		   }
		   render();
		   
		   if (System.currentTimeMillis() - timer > 1000) {
		    timer += 1000;
		   }
		  }

		 }
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g =bs.getDrawGraphics();
		g.translate(10,5);
		//////////////
		
		//drawing grid
		if(!runAlg) {
			drawGrid(g);
		}	
		
		//randomizing grid
		if(random) {
			Randomize.overlay(g, bs);
			random = false;
		}  
		
		drawStartEnd(g);
		
		if(!done && runAlg) {
			if(bfs)BreadthFirstSearch.runAlg(g, bs);
			else if(aStar)AstarAlg.runAlg(g, bs);
			else if(dfs) DepthFirstSearch.runAlg(g, bs);
			else if(dij) Dijkstra.runAlg(g, bs);
			done = true;
		} 
		
		////////////////////
		bs.show();
	}
	
	static void drawGrid(Graphics g) {
		g.setColor(Color.black);
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(grid[i][j].wall) g.fillRect(j*w, i*w, w, w);
				else {
					g.setColor(Color.white);
					g.fillRect(j * w, i * w, w, w);
					g.setColor(Color.black);
				}
				 if(grid[i][j].weight) {
						g.setColor(Color.white);
						g.fillRect(j * w, i * w, w, w);
						g.drawImage(window.image, j * w, i * w , window.image.getHeight()-1, window.image.getHeight()-1, null);
						g.setColor(Color.black);
					}
				g.drawRect(j * w, i * w , w, w);
			}
		}		
		drawStartEnd(g);
	}
	
	//drawing start and end blocks
	static void drawStartEnd(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(start.j * w , start.i * w, w, w);
		g.drawImage(window.endImage, end.j *w, end.i *w, null);
		g.setColor(Color.black);
		g.drawRect(start.j * w , start.i * w, w, w);
		g.drawRect(end.j * w, end.i * w, w, w);
	}
	
	//driver
	public static void main(String []args) {
		setup(0, 0, n-1, n-1);
		new Game();
	}
	
	//setting up grid, with start and end spots
	static void setup(int si, int sj, int ei, int ej) {
		grid = new Cell[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				grid[i][j] = new Cell(i, j);
			}
		}	
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i > 0) grid[i][j].neighbors.add(grid[i-1][j]);
				if(j > 0) grid[i][j].neighbors.add(grid[i][j-1]);
				if(i < n-1) grid[i][j].neighbors.add(grid[i+1][j]);
				if(j < n-1) grid[i][j].neighbors.add(grid[i][j+1]);
			}
		}
		
		start = grid[si][sj];
		end = grid[ei][ej];
		end.wall = false; start.wall = false;
	}
	//stopping system for x amount of time
	static void stopTime(int x) {
		try {
			TimeUnit.MILLISECONDS.sleep(x);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
