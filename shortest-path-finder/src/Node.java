public class Node implements Comparable<Node> {
	Node ancestor;
	int row;
	int col;
	String value;
	boolean visited;
	int distance;
	boolean partOfSolution;
	
	public Node(int r, int c, String s) {
		row = r;
		col = c;
		value = s;
		visited = false;
		distance = 0;
		partOfSolution = false;
	}

	@Override
	public int compareTo(Node n) {
		return distance - n.distance;
	}
	
	@Override
	public String toString() {
		return partOfSolution && !value.equals("S") && !value.equals("E") ? "x" : value;
	}
}
