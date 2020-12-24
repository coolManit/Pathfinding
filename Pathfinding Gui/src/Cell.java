import java.util.ArrayList;

public class Cell {
	public ArrayList<Cell> neighbors = new ArrayList<>();
	public Cell previous;
	public boolean wall, weight;
	public boolean used;
	public boolean tested;
	public int i, j, f, g, h;
	public int cost = 1;
	public int currCost = 0;
	
	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
		this.f = 0;
		this.g = 0;
		this.h = 0;
	}
}
