package org.example.presentationLayer;

import org.example.serviceLayer.problemSolver.DijkstraSolver;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.stream.Collectors;

public class GraphVisualizer extends JFrame {
    private final JComboBox<String> startNodeBox;
    private final JComboBox<String> endNodeBox;
    private final JPanel graphPanel;
    private final Graph graph;
    private final DijkstraSolver dijkstraSolver;

    public GraphVisualizer(Graph graph, DijkstraSolver dijkstraSolver) {
        this.graph = graph;
        this.dijkstraSolver = dijkstraSolver;
        setTitle("QoSChain Shortest Path Visualizer");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        startNodeBox = new JComboBox<>();
        endNodeBox = new JComboBox<>();
        JButton calculateButton = new JButton("Calculate Shortest Path");

        controlPanel.add(new JLabel("Start Node:"));
        controlPanel.add(startNodeBox);
        controlPanel.add(new JLabel("End Node:"));
        controlPanel.add(endNodeBox);
        controlPanel.add(calculateButton);
        add(controlPanel, BorderLayout.NORTH);

        // Create the graph visualization panel
        graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);

        // Set node options
        setNodeOptions(graph);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startNode = (String) startNodeBox.getSelectedItem();
                String endNode = (String) endNodeBox.getSelectedItem();
                visualizeShortestPath(startNode, endNode);
            }
        });
        visualizeGraph();
    }

    private void setNodeOptions(Graph graph) {
        graph.nodes()
                .sorted(Comparator.comparingInt(node -> extractInt(node.getId())))
                .forEach(node -> {
                    startNodeBox.addItem(node.getId());
                    endNodeBox.addItem(node.getId());
                });

    }

    private void visualizeGraph() {
        Viewer viewer = graph.display();

    }

    private void visualizeShortestPath(String startNode, String endNode) {
        for (Edge edge : graph.edges().toList()) {
            edge.setAttribute("ui.style", "fill-color: black;");
        }
        Iterable<Edge> shortestPath = dijkstraSolver.findTheShortestPathByEdges(graph, startNode, endNode);
        if (shortestPath != null) {
            for (Edge edge : shortestPath) {
                edge.setAttribute("ui.style", "fill-color: red;");
            }
        }
        graphPanel.revalidate();
        graphPanel.repaint();
    }

    private int extractInt(String nodeId) {
        try {
            return Integer.parseInt(nodeId.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE; // or any default value indicating parsing failure
        }
    }

}