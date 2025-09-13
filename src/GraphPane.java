/**
 * 
 */

import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Unathi Vayeke
 *
 */
public class GraphPane extends Pane {
    private static final int RADIUS = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Graph graph;

    /**
     * default constructor
     */
    public GraphPane() {
        graph = new Graph();
        
        final int JHB_TO_PTA = 50;
        final int JHB_TO_SWT = 15;
        final int JHB_TO_EKN = 30;
        final int PTA_TO_SWT = 80;
        final int PTA_TO_EKN = 80;
        final int SWT_TO_EKN = 40;
        final int CTR_TO_PTA = 25;
        final int JHB_TO_KRP = 30;
        final int JHB_TO_MDR = 20;
        final int MDR_TO_PTA = 20;
        final int JHB_TO_RPT = 20;
        final int JHB_TO_VDB = 70;

        /*
         * JOHANNESBURG = JHB
         * PRETORIA = PTA
         * SOWETO = SWT
         * EKURHULENI = EKN
         * CENTURION = CTR
         * KRUGERSDORP = KRP
         * MIDRAND = MDR
         * ROODEPOORT = RPT
         * VANDERBIJLPARK = VDB
        */

        final Node JOHANNESBURG = new Node("JHB");
        final Node PRETORIA = new Node("PTA");
        final Node SOWETO = new Node("SWT");
        final Node EKURHULENI = new Node("EKN");
        final Node CENTURION = new Node("CTR");
        final Node KRUGERSDORP = new Node("KRP");
        final Node MIDRAND = new Node("MDR");
        final Node ROODEPOORT = new Node("RPT");
        final Node VANDERBIJLPARK = new Node("VDB");

        graph.addNode(JOHANNESBURG);
        graph.addNode(PRETORIA);
        graph.addNode(SOWETO);
        graph.addNode(EKURHULENI);
        graph.addNode(CENTURION);
        graph.addNode(KRUGERSDORP);
        graph.addNode(MIDRAND);
        graph.addNode(ROODEPOORT);
        graph.addNode(VANDERBIJLPARK);

        graph.addEdge(new Edge(JOHANNESBURG, PRETORIA, JHB_TO_PTA));
        graph.addEdge(new Edge(JOHANNESBURG, SOWETO, JHB_TO_SWT));
        graph.addEdge(new Edge(PRETORIA, SOWETO, PTA_TO_SWT));
        graph.addEdge(new Edge(PRETORIA, EKURHULENI, PTA_TO_EKN));
        graph.addEdge(new Edge(SOWETO, EKURHULENI, SWT_TO_EKN));
        graph.addEdge(new Edge(EKURHULENI, JOHANNESBURG, JHB_TO_EKN));
        graph.addEdge(new Edge(CENTURION, JOHANNESBURG, CTR_TO_PTA));
        graph.addEdge(new Edge(JOHANNESBURG, KRUGERSDORP, JHB_TO_KRP));
        graph.addEdge(new Edge(JOHANNESBURG, MIDRAND, JHB_TO_MDR));
        graph.addEdge(new Edge(PRETORIA, EKURHULENI, MDR_TO_PTA));
        graph.addEdge(new Edge(JOHANNESBURG, ROODEPOORT, JHB_TO_RPT));
        graph.addEdge(new Edge(JOHANNESBURG, VANDERBIJLPARK, JHB_TO_VDB));
    }

    /**
     * @param primaryStage primary stage
     */
    public void startPane(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGraph(gc);
        
        TextField txtSrc = new TextField();
        txtSrc.setPromptText("Enter source code");
        txtSrc.setLayoutX(10);
        txtSrc.setLayoutY(10);

        TextField txtDest = new TextField();
        txtDest.setPromptText("Enter destination code");
        txtDest.setLayoutX(10);
        txtDest.setLayoutY(40);
        
        Button btnSP = new Button("Find Shortest Path");
        btnSP.setLayoutX(10);
        btnSP.setLayoutY(70);
        btnSP.setOnAction(event -> {
            String srcName = txtSrc.getText().strip().toUpperCase();
            String destName = txtDest.getText().strip().toUpperCase();
            Node src = graph.getNodeByName(srcName);
            Node dest = graph.getNodeByName(destName);
            if (src != null && dest != null) {
                List<Node> path = graph.findShortestPath(src, dest);
                if (path != null) {
                    double totalWeight = 0;
                    for (int i = 0; i < path.size() - 1; i++) {
                        Edge edge = graph.getEdge(path.get(i), path.get(i+1));
                        if (edge != null) {
                            edge.setInShortestPath(true);
                            totalWeight += edge.getDistance();
                        }
                    }
                    drawGraph(gc);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Shortest path from " + srcName + " to " + destName + ": " + totalWeight + "km");
                    new Alert(AlertType.INFORMATION, sb.toString()).showAndWait();
                } else {
                    new Alert(AlertType.ERROR, "No path found from " + srcName + " to " + destName).showAndWait();
                }
            } else {
                new Alert(AlertType.ERROR, "Invalid source or destination code, please enter the correct source or destination code e.g JHB").showAndWait();
            }
        });

        
        Button btnExit = new Button("Exit");
        btnExit.setLayoutX(10);
        btnExit.setLayoutY(100);
        btnExit.setOnAction(event -> {
            primaryStage.close();
        });

        getChildren().addAll(canvas, txtSrc, txtDest, btnSP, btnExit);
    }

    /**
     * @param gc graphics context
     */
    private void drawGraph(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        for (Edge edge : graph.getAllEdges()) {
            double startX = edge.getSource().getX();
            double startY = edge.getSource().getY();
            double endX = edge.getDestination().getX();
            double endY = edge.getDestination().getY();
            gc.setStroke(edge.isInShortestPath() ? Color.RED : Color.BLACK);
            gc.setLineWidth(edge.isInShortestPath() ? 3 : 1);
            gc.strokeLine(startX, startY, endX, endY);
            drawText(gc, String.valueOf(edge.getDistance()), (startX + endX) / 2, (startY + endY) / 2);
        }

        for (Node node : graph.getNodes()) {
            gc.setFill(Color.WHITE);
            gc.fillOval(node.getX() - RADIUS, node.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeOval(node.getX() - RADIUS, node.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);
            gc.setFill(Color.BLUE);
            gc.fillText(node.getName(), node.getX() - (RADIUS / 2), node.getY() + (RADIUS / 2));
        }
    }
    
    /**
     * @param gc graphics context
     * @param txtDist distance
     * @param x x of the text
     * @param y y of the text
     */
    private void drawText(GraphicsContext gc, String txtDist, double x, double y) {
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(txtDist, x, y);
    }
}