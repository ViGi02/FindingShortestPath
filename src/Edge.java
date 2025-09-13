/**
 * 
 */

/**
 * @author Unathi Vayeke
 *
 */
public class Edge {
    private Node src;
    private Node dest;
    private int dist;
    private boolean inSP;

    /**
     * @param src source node
     * @param dest destination node
     * @param dist distance
     */
    public Edge(Node src, Node dest, int dist) {
        this.src = src;
        this.dest = dest;
        this.dist = dist;
        this.inSP = false;
    }

    /**
     * @return source node
     */
    public Node getSource() {
        return src;
    }

    /**
     * @return destination node
     */
    public Node getDestination() {
        return dest;
    }

    /**
     * @return distance
     */
    public int getDistance() {
        return dist;
    }

    /**
     * @return flag for shortest path
     */
    public boolean isInShortestPath() {
        return inSP;
    }

    /**
     * @param inSP setting flag 
     */
    public void setInShortestPath(boolean inSP) {
        this.inSP = inSP;
    }
}

