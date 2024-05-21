package org.example.serviceLayer.pathFinder;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.*;

public class PathFinder {
    public Path findTheLeastDelayedPath(Graph graph, String sourceNodeID, String targetNodeID, int requiredBandwidth, int maxDelay) {
        Path minDelayPath = null;
        int minDelay = Integer.MAX_VALUE;

        Node sourceNode = graph.getNode(sourceNodeID);
        Node targetNode = graph.getNode(targetNodeID);

        if (sourceNode != null && targetNode != null) {
            List<Path> allPaths = findAllPaths(sourceNode, targetNode);

            for (Path path : allPaths) {
                int totalBandwidth = 0;
                int totalDelay = 0;

                for (Edge edge : path.getEdgeSet()) {
                    Object bandwidthAttr = edge.getAttribute("max_bandwidth");
                    Object delayAttr = edge.getAttribute("min_delay");

                    if (bandwidthAttr != null && delayAttr != null) {
                        int bandwidth = Integer.parseInt(bandwidthAttr.toString());
                        int delay = Integer.parseInt(delayAttr.toString());

                        totalBandwidth += bandwidth;
                        totalDelay += delay;
                    }
                }

                if (totalBandwidth >= requiredBandwidth && totalDelay <= maxDelay && totalDelay < minDelay) {
                    minDelay = totalDelay;
                    minDelayPath = path;
                }
            }
        }

        return minDelayPath;
    }

    private static List<Path> findAllPaths(Node source, Node target) {
        List<Path> allPaths = new ArrayList<>();
        List<Node> currentPath = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        dfs(source, target, visited, currentPath, allPaths);
        return allPaths;
    }

    private static void dfs(Node current, Node target, Set<Node> visited, List<Node> currentPath, List<Path> allPaths) {
        visited.add(current);
        currentPath.add(current);

        if (current.equals(target)) {
            Path path = new Path();
            path.setRoot(currentPath.get(0));
            for (int i = 0; i < currentPath.size() - 1; i++) {
                Node node = currentPath.get(i);
                Node nextNode = currentPath.get(i + 1);
                Edge edge = node.getEdgeToward(nextNode);
                path.add(edge);
            }
            allPaths.add(path);
        } else {
            Iterator<Edge> edges = current.edges().iterator();
            while (edges.hasNext()) {
                Edge edge = edges.next();
                Node nextNode = edge.getOpposite(current);
                if (!visited.contains(nextNode)) {
                    dfs(nextNode, target, visited, currentPath, allPaths);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(current);
    }
}