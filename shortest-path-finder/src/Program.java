import java.io.*;
import java.util.*;

public class Program {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("example-input.txt"));
		
		while (sc.hasNext()) {
			int rows = sc.nextInt();
			int cols = sc.nextInt();
			sc.nextLine();
			
			Node[][] grid = new Node[rows][cols];
			int startRow = 0;
			int startCol = 0;
			int endRow = 0;
			int endCol = 0;
			
			for (int r = 0; r < rows; r++) {
				String[] line = sc.nextLine().split("");
				for (int c = 0; c < cols; c++) {
					grid[r][c] = new Node(r, c, line[c]); // convert each grid space to a Node object
					
					if (grid[r][c].value.equals("S")) { // if this is the start node, mark it
						startRow = r;
						startCol = c;
					}
					
					else if (grid[r][c].value.equals("E")) { // if this is the end node, mark it
						endRow = r;
						endCol = c;
					}
				}
			}
			
			System.out.println(shortestDistance(grid, startRow, startCol)); // prints shortest distance from S to E
			markPath(grid[endRow][endCol]); // marks each node that was used to construct the solution
			printBoard(grid, rows, cols); // prints path taken
		}
	}
	
	static int shortestDistance(Node[][] grid, int startRow, int startCol) {
		PriorityQueue<Node> queue = new PriorityQueue<Node>();
		
		grid[startRow][startCol].distance = 0;
		grid[startRow][startCol].visited = true;
		queue.add(grid[startRow][startCol]); // adds the start node to initiate pathfinding
		
		while (!queue.isEmpty()) { // do until queue is empty or the end is reached
			Node current = queue.poll();
			int row = current.row;
			int col = current.col;
			
			if (current.value.equals("E")) { // if current node is the end, return the distance traveled
				return current.distance;
			}
			
			else { // add each surrounding node into the queue
				if (col-1 >= 0 && !grid[row][col-1].value.equals("#") && !grid[row][col-1].visited) {
					grid[row][col-1].distance = current.distance + 1;
					grid[row][col-1].visited = true;
					queue.add(grid[row][col-1]);
					grid[row][col-1].ancestor = current;
				}
				
				if (col+1 < grid[row].length && !grid[row][col+1].value.equals("#") && !grid[row][col+1].visited) {
					grid[row][col+1].distance = current.distance + 1;
					grid[row][col+1].visited = true;
					queue.add(grid[row][col+1]);
					grid[row][col+1].ancestor = current;
				}
				
				if (row-1 >= 0 && !grid[row-1][col].value.equals("#") && !grid[row-1][col].visited) {
					grid[row-1][col].distance = current.distance + 1;
					grid[row-1][col].visited = true;
					queue.add(grid[row-1][col]);
					grid[row-1][col].ancestor = current;
				}
				
				if (row+1 < grid.length && !grid[row+1][col].value.equals("#") && !grid[row+1][col].visited) {
					grid[row+1][col].distance = current.distance + 1;
					grid[row+1][col].visited = true;
					queue.add(grid[row+1][col]);
					grid[row+1][col].ancestor = current;
				}
			}
		}
		
		return -1; // end cannot be reached from start
	}
	
	static void printBoard(Node[][] grid, int rows, int cols) {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				System.out.print(grid[r][c]);
			}
			System.out.println();
		}
	}
	
	static void markPath(Node n) {
		n.partOfSolution = true;
		
		if (n.ancestor != null) {
			markPath(n.ancestor);
		}
	}
}
