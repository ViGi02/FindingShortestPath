/**
 * 
 */

/**
 * @author Unathi Vayeke
 *
 */
public class Node {
	private String name;
	private double x;
	private double y;

    private static final int RADIUS = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
	
	/**
	 * @param name initial node name
	 */
	public Node(String name) {
	    this.name = name;
	    this.x = Math.random() * (WIDTH - RADIUS * 2) + RADIUS;
	    this.y = Math.random() * (HEIGHT - RADIUS * 2) + RADIUS;
	}

	/**
	 * @return node name
	 */
	public String getName() {
	    return name;
	}

	/**
	 * @return x of the node
	 */
	public double getX() {
	    return x;
	}

	/**
	 * @return y of the node
	 */
	public double getY() {
	    return y;
	}
}

