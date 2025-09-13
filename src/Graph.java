/**
 * 
 */

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Unathi Vayeke
 *
 */
public class Graph {
	private List<Node> nodes = new ArrayList<>();
    private Map<Node, Set<Edge>> edges = new HashMap<>();

    /**
     * @param node the node to add
     */
    public void addNode(Node node) {
        nodes.add(node);
        edges.put(node, new HashSet<>());
    }

    /**
     * @param edge the edge to add
     */
    public void addEdge(Edge edge) {
        edges.get(edge.getSource()).add(edge);
        edges.get(edge.getDestination()).add(new Edge(edge.getDestination(), edge.getSource(), edge.getDistance()));
    }
    
    /**
     * @return a list of nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }
    
    /**
     * @param source start node
     * @param destination end node
     * @return edge with the provided source and destination nodes
     */
    public Edge getEdge(Node source, Node destination) {
        for (Edge edge : getAllEdges()) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * @param node node to get edge of
     * @return a set of edges
     */
    public Set<Edge> getEdges(Node node) {
        return edges.get(node);
    }

    /**
     * @return a set of all the edges
     */
    public Set<Edge> getAllEdges() {
        Set<Edge> allEdges = new HashSet<>();
        for (Set<Edge> nodeEdges : edges.values()) {
            allEdges.addAll(nodeEdges);
        }
        return allEdges;
    }
    
    /**
     * @param srcNode source node
     * @param destNode destination node
     * @return a list of nodes on the shortest path
     */
    public List<Node> findShortestPath(Node srcNode, Node destNode) {
        int[] dists = new int[nodes.size()];
        Node[] prevNodes = new Node[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        for (int i = 0; i < dists.length; i++) {
        	dists[i] = Integer.MAX_VALUE;
        }
        dists[nodes.indexOf(srcNode)] = 0;
        
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> dists[nodes.indexOf(node)]));
        queue.add(srcNode);
        
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            int indexCN = nodes.indexOf(currentNode);
            
            if (visited[indexCN]) {
                continue;
            }
            visited[indexCN] = true;
            
            if (currentNode.equals(destNode)) {
                List<Node> path = new ArrayList<>();
                while (prevNodes[indexCN] != null) {
                    path.add(currentNode);
                    currentNode = prevNodes[indexCN];
                    indexCN = nodes.indexOf(currentNode);
                }
                path.add(srcNode);
                Collections.reverse(path);
                return path;
            }

            for (Edge edge : getEdges(currentNode)) {
                Node adjNode = edge.getDestination();
                int distAN = dists[indexCN] + edge.getDistance();
                int indexAN = nodes.indexOf(adjNode);
                if (distAN < dists[indexAN]) {
                	dists[indexAN] = distAN;
                    prevNodes[indexAN] = currentNode;
                    queue.add(adjNode);
                }
            }
        }
        
        return null;
    }

	/**
	 * @param name node name
	 * @return the node with the provided name
	 */
	public Node getNodeByName(String name) {
		for (Node node : nodes) {
	        if (node.getName().equalsIgnoreCase(name)) {
	            return node;
	        }
	    }
	    return null;
	}

}

