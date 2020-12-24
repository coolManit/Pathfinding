import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Window extends Canvas implements MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	JButton randomGrid, button, clear, bfsButton, aStar, dfsButton, clearPath, wall, weight;
	JComboBox algs, speeds;
	static String [] differentalgs = {"BFS", "DFS", "Astar", "Dijkstra"}, differentspeed = {"Slow", "Medium", "Fast"};
	JLabel algDis;
	static Random rand = new Random();
	static BufferedImage image, endImage;
	
	public Window(int width, int height, String title, Game game) {
		
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		//clear board button
		clear = new JButton("Clear Grid");
		clear.setBounds(width-110, 20, 80, 35);
		clear.setFont(new Font("Serif", Font.PLAIN, 11));
		clear.addMouseListener(this);
		frame.add(clear);
		
		//clear path button
		clearPath = new JButton("Clear Path");
		clearPath.setBounds(width-110, 90, 80, 35);
		clearPath.setFont(new Font("Serif", Font.PLAIN, 11));
		clearPath.addMouseListener(this);
		frame.add(clearPath);
		
		//dropBox
		JLabel currentAlg = new JLabel("Algorithms");
		currentAlg.setBounds(width-100, 145, 80, 30);
		algs = new JComboBox(differentalgs);
		algs.setBounds(width-110, 170, 80, 30);
		algs.addActionListener(this);
		frame.add(algs); frame.add(currentAlg);
		
		JLabel currentSpeed = new JLabel("Speed");
		currentSpeed.setBounds(width-100, 195, 80, 30);
		speeds = new JComboBox(differentspeed);
		speeds.setBounds(width-110, 220, 80, 30);
		speeds.addActionListener(this);
		frame.add(currentSpeed); frame.add(speeds);
		
		
		//wall button
		wall = new JButton("WALL");
		wall.setBounds(width-110, 280, 80, 30);
		wall.setFont(new Font("Serif", Font.BOLD, 10));
		wall.addMouseListener(this);
		frame.add(wall);
		
		//weight button
		weight = new JButton("WEIGHT");
		weight.setBounds(width-110, 330, 80, 30);
		weight.setFont(new Font("Serif", Font.BOLD, 10));
		weight.addMouseListener(this);
		frame.add(weight);
		
		//discription
		algDis = new JLabel("<html> Breath-first Search is unweighted and guarantees the shortest path </html>");
		algDis.setBounds(width-110, 350, 80, 150);
	//	algDis.setFont(new Font("Serif", Font.BOLD, 13));
		frame.add(algDis);
	
	//image	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Weight.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			endImage = ImageIO.read(getClass().getResourceAsStream("/End.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//random button
		randomGrid = new JButton("Randomize grid");
		randomGrid.setBounds(120, height-100, 280, 40);
		randomGrid.addMouseListener(this);
		frame.add(randomGrid);
		
		//run button
		button = new JButton("RUN ALG");
		button.setBounds(10, height-100, 100, 40);
		button.addMouseListener(this);
		frame.add(button);
		
		//regular frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		
		
		frame.setVisible(true);
		game.start();

	}

	//running algorithm
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == randomGrid) {
			Game.random = true;
		}
		else if(e.getSource() == button) Game.runAlg = true;
		else if(e.getSource() == clear) clearGrid();
		else if(e.getSource() == clearPath) clearPath();
		else if(e.getSource() == wall) {
			Game.toolWall = true;
		} else if(e.getSource() == weight) {
			Game.toolWall = false;
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String name = (String) cb.getSelectedItem();
		if(name.equals("Fast") || name.equals("Medium") || name.equals("Slow")) {
			if(name.equals("Fast")) Game.delay = 2;
			else if(name.equals("Medium")) Game.delay = 12;
			else Game.delay = 19;
			} 
		else {
		Game.dfs = false; Game.bfs = false; Game.aStar = false;
		if(name.equals("DFS")) {
			Game.dfs = true;
			algDis.setText("<html> Depth-first Search is unweighted and does not guarantees the shortest path </html>");
		}
		else if(name.equals("BFS")) {
			Game.bfs = true;
			algDis.setText("<html> Breath-first Search is unweighted and guarantees the shortest path </html>");
		}
		else if(name.equals("Astar")) {
			Game.aStar = true;
			algDis.setText("<html> A* Search is weighted and guarantees the shortest path </html>");
		}
		else if(name.equals("Dijkstra")) {
			Game.dij = true;
			algDis.setText("<html> Dijkstra's Algorithm is weighted and guarantees the shortest path </html>");
		}
		}
	}
		

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	static void clearGrid() {
		if(!Game.done && Game.runAlg) return;
		for(int i=0; i<Game.n; i++) {
			for(int j=0; j<Game.n; j++) {
				Cell current = Game.grid[i][j];
				current.wall = false;
				current.weight = false;
				current.used = false;
				current.tested = false;
				current.previous = null;
				current.currCost = 0;
			}
		}
		Game.done = false;
		Game.runAlg = false;
	}
	static void clearPath() {
		if(!Game.done && Game.runAlg) return;
		for(int i=0; i<Game.n; i++) {
			for(int j=0; j<Game.n; j++) {
				Cell current = Game.grid[i][j];
				current.tested = false;
				current.used = false;
				current.previous = null;
				current.currCost = 0;
			}
		}
		Game.done = false;
		Game.runAlg = false;
	}
	
	
	
}
