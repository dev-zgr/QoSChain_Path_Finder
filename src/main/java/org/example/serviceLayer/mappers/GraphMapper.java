package org.example.serviceLayer.mappers;

import org.example.dataLayer.dataModels.UniqueTableDataModel;
import org.example.dataLayer.repositories.interfaces.UniqueTableRepository;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.List;

public class GraphMapper {
    private final UniqueTableRepository uniqueTableRepository;

    public GraphMapper(UniqueTableRepository uniqueTableRepository) {
        this.uniqueTableRepository = uniqueTableRepository;
    }

    public Graph mapUniqueTableDataModelToGraph() {
        Graph graph = new MultiGraph("QoSChain");
        List<UniqueTableDataModel> uniqueEdges = uniqueTableRepository.findAllEdges();

        for (UniqueTableDataModel uniqueEdge : uniqueEdges) {
            String ingressNode = uniqueEdge.getIngress_node();
            String egressNode = uniqueEdge.getEgress_node();
            String pathletId = uniqueEdge.getPathlet_id();
            int minDelay = uniqueEdge.getMin_delay();
            int maxBandwidth = uniqueEdge.getMax_bandwidth();
            boolean isInterconnectingNode = uniqueEdge.isInterConnectingNode();
            String asn = uniqueEdge.getAsn();

            if (graph.getNode(ingressNode) == null){
                graph.addNode(ingressNode);
            }
            if (graph.getNode(egressNode) == null){
                graph.addNode(egressNode);
            }
            if (graph.getEdge(pathletId) == null){
                graph.addEdge(pathletId, ingressNode, egressNode).setAttribute("min_delay", minDelay);
                graph.getEdge(pathletId).setAttribute("max_bandwidth", maxBandwidth);
                graph.getEdge(pathletId).setAttribute("is_interconnecting_node", isInterconnectingNode);
                graph.getEdge(pathletId).setAttribute("asn", asn);
            }
        }

        graph.nodes().forEach(n -> {
            n.setAttribute("label", n.getId());
            n.setAttribute("ui.style", "text-size: 30px; text-alignment: at-right; fill-color: #E74C3C;"); // Change color to elegant red
        });

        graph.edges().forEach(e -> {
            int minDelay = (int) e.getNumber("min_delay");
            int maxBandwidth = (int) e.getNumber("max_bandwidth");
            e.setAttribute("label", String.format("%d | %d", maxBandwidth, minDelay));
            e.setAttribute("ui.style", "text-size: 25px; size: 1.5px; fill-color: black;");
        });

        return graph;
    }
}
