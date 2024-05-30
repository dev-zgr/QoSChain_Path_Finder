package org.example.presentationLayer;

import org.example.dataLayer.dataModels.RequestDataModel;
import org.example.serviceLayer.services.interfaces.QoSChainService;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.IntStream;

public class GraphVisualizer extends JFrame {
    private final JComboBox<String> startNodeBox;
    private final JComboBox<String> endNodeBox;
    private final JTextField requiredBandwidthBox;
    private final JTextField maxDelayBox;
    private final JPanel graphPanel;
    private final QoSChainService qosChainService;

    public GraphVisualizer(QoSChainService qosChainService) {
        this.qosChainService = qosChainService;

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
        setNodeOptions();


        JLabel pathlessMessageLabel = new JLabel("No pathless found!");
        pathlessMessageLabel.setForeground(Color.RED); // Set color to green
        pathlessMessageLabel.setVisible(false); // Initially, it's not visible


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        controlPanel.add(pathlessMessageLabel, gbc);

        JLabel pathletInfoLabel = new JLabel();
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Set grid width to span across two columns
        gbc.anchor = GridBagConstraints.WEST; // Set anchor to left-align
        controlPanel.add(pathletInfoLabel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Rest of your actionPerformed logic
                RequestDataModel request = new RequestDataModel();
                request.setIngress_node((String) startNodeBox.getSelectedItem());
                request.setEgress_node((String) endNodeBox.getSelectedItem());
                request.setRequired_bandwidth(Integer.parseInt(requiredBandwidthBox.getText()));
                request.setMax_delay(Integer.parseInt(maxDelayBox.getText()));
                boolean isPathReserved = visualizeShortestPath(request, pathlessMessageLabel, pathletInfoLabel);

                if (isPathReserved) {
                    calculateButton.setEnabled(false);

                    // Create a Timer to re-enable the calculateButton after 10 seconds
                    Timer timer = new Timer(10000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            calculateButton.setEnabled(true); // Re-enable the button
                        }
                    });

                    // Start the timer
                    timer.setRepeats(false); // Only run once
                    timer.start();
                }

            }
        });

        randomRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable the randomRequestButton


                // Rest of your actionPerformed logic
                RequestDataModel randomRequest = qosChainService.generateRandomRequest();
                startNodeBox.setSelectedItem(randomRequest.getIngress_node());
                endNodeBox.setSelectedItem(randomRequest.getEgress_node());
                requiredBandwidthBox.setText(String.valueOf(randomRequest.getRequired_bandwidth()));
                maxDelayBox.setText(String.valueOf(randomRequest.getMax_delay()));
                boolean isPathReserved = visualizeShortestPath(randomRequest, pathlessMessageLabel, pathletInfoLabel);

                if (isPathReserved) {
                    randomRequestButton.setEnabled(false);

                    Timer timer = new Timer(10000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            randomRequestButton.setEnabled(true); // Re-enable the button
                        }
                    });

                    // Start the timer
                    timer.setRepeats(false); // Only run once
                    timer.start();
                }
            }
        });
        qosChainService.visualizeGraph();
    }

    private void setNodeOptions() {
        qosChainService.getNodeOptions()
                .forEach(node -> {
                    startNodeBox.addItem(node.getId());
                    endNodeBox.addItem(node.getId());
                });
    }

    private boolean visualizeShortestPath(RequestDataModel request, JLabel pathletMessageLabel, JLabel pathletInfoLabel) {
        pathletMessageLabel.setVisible(false);
        pathletInfoLabel.setVisible(false);
        qosChainService.paintAllEdges("black");
        Path leastDelayedPath = qosChainService.calculateTheRequest(request);
        if (leastDelayedPath != null) {
            for (Edge edge : leastDelayedPath.getEdgeSet()) {
                edge.setAttribute("ui.style", "fill-color: red;");
            }
            pathletMessageLabel.setText("Path Found!");
            pathletMessageLabel.setForeground(new Color(0, 128, 0));
            pathletInfoLabel.setText(nodeInfoToString(leastDelayedPath));
            pathletMessageLabel.setVisible(true);
            pathletInfoLabel.setVisible(true);
        } else {
            pathletMessageLabel.setText("No path found!");
            pathletMessageLabel.setForeground(Color.RED);
            pathletMessageLabel.setVisible(true);
        }
        graphPanel.revalidate();
        graphPanel.repaint();
        return leastDelayedPath != null;
    }


    private void updateGraph() {
        qosChainService.paintAllEdges("red");
    }

    private String nodeInfoToString(Path path) {
        List<Node> nodePath = path.getNodePath();
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<b>Path:</b> ");
        for (int i = 0; i < nodePath.size(); i++) {
            Node node = nodePath.get(i);
            if (i == 0 || i == nodePath.size() - 1) { // Check if it's the first or last node
                // Style the first and last node differently
                sb.append("<span style=\"color: red;\">").append(node).append("</span>");
            } else {
                sb.append(node);
            }
            if (i < nodePath.size() - 1) {
                sb.append(" \u2192 ");
            }
        }
        sb.append("<br>");

        List<String> edges = path.getEdgePath().stream().map(Element::getId).toList();
        sb.append("<b>Pathlet Path:</b> ");
        for (int i = 0; i < edges.size(); i++) {
            String pathletID = edges.get(i);
            if (i == 0 || i == edges.size() - 1) { // Check if it's the first or last node
                // Style the first and last node differently
                sb.append("<span style=\"color: red;\">").append(pathletID).append("</span>");
            } else {
                sb.append(pathletID);
            }
            if (i < edges.size() - 1) {
                sb.append(" \u2192 ");
            }
        }

        sb.append("<br>");
        List<String> asnList = path.getEdgePath().stream()
                .map(edge -> {
                    boolean isInterconnected = (Boolean) edge.getAttribute("is_interconnecting_node");
                    String asn = edge.getAttribute("asn").toString();
                    return isInterconnected ? asn + " (Interconnecting Node)" : asn;
                })
                .toList();

        sb.append("<b>ASN Transition:</b> ");
        IntStream.range(0, asnList.size()).forEach(i -> {
            String asnID = asnList.get(i);
            if (i == 0 || i == asnList.size() - 1) { // Check if it's the first or last node
                // Style the first and last node differently
                sb.append("<span style=\"color: red;\">").append(asnID).append("</span>");
            } else {
                sb.append(asnID);
            }
            if (i < asnList.size() - 1) {
                sb.append(" \u2192 ");
            }
        });

        sb.append("</html>");
        return sb.toString();
    }
}

