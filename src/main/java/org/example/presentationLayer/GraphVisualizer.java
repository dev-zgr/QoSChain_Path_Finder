package org.example.presentationLayer;

import org.example.serviceLayer.pathFinder.PathFinder;
import org.graphstream.graph.*;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class GraphVisualizer extends JFrame {
    private final JComboBox<String> startNodeBox;
    private final JComboBox<String> endNodeBox;
    private final JTextField requiredBandwidthBox;
    private final JTextField  maxDelayBox;
    private final JPanel graphPanel;
    private final Graph graph;
    private final PathFinder pathFinder;

    public GraphVisualizer(Graph graph, PathFinder pathFinder) {
        this.graph = graph;
        this.pathFinder = pathFinder;
        setTitle("QoSChain Shortest Path Visualizer");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        startNodeBox = new JComboBox<>();
        endNodeBox = new JComboBox<>();
        requiredBandwidthBox = new JTextField(10);
        maxDelayBox = new JTextField(10);
        JButton calculateButton = new JButton("Calculate The Path With The Minimum Delay");

        controlPanel.add(new JLabel("Start Node:"));
        controlPanel.add(startNodeBox);
        controlPanel.add(new JLabel("End Node:"));
        controlPanel.add(endNodeBox);
        controlPanel.add(new JLabel("Required Bandwidth:"));
        controlPanel.add(requiredBandwidthBox);
        controlPanel.add(new JLabel("Max Delay:"));
        controlPanel.add(maxDelayBox);
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
                int requiredBandwidth = Integer.parseInt(requiredBandwidthBox.getText());
                int maxDelay = Integer.parseInt(maxDelayBox.getText());
                visualizeShortestPath(startNode, endNode, requiredBandwidth, maxDelay);
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

    private void visualizeShortestPath(String startNode, String endNode, int requiredBandwidth, int maxDelay) {
        for (Edge edge : graph.edges().toList()) {
            edge.setAttribute("ui.style", "fill-color: black;");
        }
        Path leastDelayedPath = pathFinder.findTheLeastDelayedPath(graph, startNode, endNode, requiredBandwidth, maxDelay);
        if (leastDelayedPath != null) {
            for (Edge edge : leastDelayedPath.getEdgeSet()) {
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