package org.example.serviceLayer.problemSolver;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class DijkstraSolver {
    private final Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "min_delay");

    public Iterable<Edge> findTheShortestPathByEdges(Graph graph, String firstNode, String secondNode) {
        if (checkIfNodeExists(graph, firstNode)) {return null;}
        if (checkIfNodeExists(graph, secondNode)) {return null;}

        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode(firstNode));
        dijkstra.compute();

        return dijkstra.getPathEdges(graph.getNode(secondNode));
    }

    public Iterable<Node> findTheShortestPathByNodes(Graph graph, String firstNode, String secondNode) {
        if (checkIfNodeExists(graph, firstNode)) {return null;}
        if (checkIfNodeExists(graph, secondNode)) {return null;}

        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode(firstNode));
        dijkstra.compute();

        return dijkstra.getPathNodes(graph.getNode(secondNode));
    }

    private boolean checkIfNodeExists(Graph graph, String node) {
        if (graph.getNode(node) == null) {
            System.out.println("No such node: " + node);
            return true;
        }
        return false;
    }
}
