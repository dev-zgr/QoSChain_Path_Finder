package org.example.serviceLayer.mappers;

import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;
import org.example.dataLayer.interfaces.repositories.UniqueTableRepository;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class GraphMapper {
    private final UniqueTableRepository uniqueTableRepository;

    private final Graph graph = new MultiGraph("QoSChain");

    public GraphMapper(UniqueTableRepository uniqueTableRepository) {
        this.uniqueTableRepository = uniqueTableRepository;
    }

    public Graph mapUniqueTableDataModelToGraph() {
        List<UniqueTableDataModel> uniqueEdges = uniqueTableRepository.findAllEdges();

        for (UniqueTableDataModel uniqueEdge : uniqueEdges) {
            String ingressNode = uniqueEdge.getIngress_node();
            String egressNode = uniqueEdge.getEgress_node();
            String pathletId = uniqueEdge.getPathlet_id();
            int weight = uniqueEdge.getMin_delay();

            if (graph.getNode(ingressNode) == null){
                graph.addNode(ingressNode);
            }
            if (graph.getNode(egressNode) == null){
                graph.addNode(egressNode);
            }
            if (graph.getEdge(pathletId) == null){
                graph.addEdge(pathletId, ingressNode, egressNode).setAttribute("min_delay", weight);
            }
        }

        graph.nodes().forEach(n -> n.setAttribute("label", n.getId()));
        graph.nodes().forEach(n -> n.setAttribute("ui.style", "text-size: 20;"));

        graph.edges().forEach(e -> e.setAttribute("label", "" + (int) e.getNumber("min_delay")));
        graph.edges().forEach(e -> e.setAttribute("ui.style", "text-size: 20;"));

        return graph;
    }
}
