package org.example.presentationLayer;

import org.example.dataLayer.implementations.dataModels.RequestDataModel;
import org.example.serviceLayer.pathFinder.PathFinder;
import org.example.serviceLayer.randomizer.interfaces.RandomRequestGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Path;
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
    private final JTextField maxDelayBox;
    private final JPanel graphPanel;
    private final Graph graph;
    private final PathFinder pathFinder;
    private final RandomRequestGenerator randomRequestGenerator;

    public GraphVisualizer(Graph graph, PathFinder pathFinder, RandomRequestGenerator randomRequestGenerator) {
        this.graph = graph;
        this.pathFinder = pathFinder;
        this.randomRequestGenerator = randomRequestGenerator;

        setTitle("QoSChain Shortest Path Visualizer");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // padding

        startNodeBox = new JComboBox<>();
        endNodeBox = new JComboBox<>();
        requiredBandwidthBox = new JTextField(10);
        maxDelayBox = new JTextField(10);
        JButton calculateButton = new JButton("Calculate The Request");
        JButton randomRequestButton = new JButton("Generate Random Request");

        // Row 1: Start Node
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(new JLabel("Start Node:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(startNodeBox, gbc);

        // Row 2: End Node
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(new JLabel("End Node:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(endNodeBox, gbc);

        // Row 3: Required Bandwidth
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(new JLabel("Required Bandwidth:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(requiredBandwidthBox, gbc);

        // Row 4: Max Delay
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(new JLabel("Max Delay:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(maxDelayBox, gbc);

        // Row 5: Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        controlPanel.add(calculateButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(randomRequestButton, gbc);

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
                RequestDataModel request = new RequestDataModel();
                request.setIngress_node((String) startNodeBox.getSelectedItem());
                request.setEgress_node((String) endNodeBox.getSelectedItem());
                request.setRequired_bandwidth(Integer.parseInt(requiredBandwidthBox.getText()));
                request.setMax_delay(Integer.parseInt(maxDelayBox.getText()));
                visualizeShortestPath(request);
            }
        });

        randomRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestDataModel randomRequest = randomRequestGenerator.generateRandomRequest(graph);
                startNodeBox.setSelectedItem(randomRequest.getIngress_node());
                endNodeBox.setSelectedItem(randomRequest.getEgress_node());
                requiredBandwidthBox.setText(String.valueOf(randomRequest.getRequired_bandwidth()));
                maxDelayBox.setText(String.valueOf(randomRequest.getMax_delay()));
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

    private void visualizeShortestPath(RequestDataModel request) {
        for (Edge edge : graph.edges().toList()) {
            edge.setAttribute("ui.style", "fill-color: black;");
        }
        Path leastDelayedPath = pathFinder.findTheLeastDelayedPath(graph, request);
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
