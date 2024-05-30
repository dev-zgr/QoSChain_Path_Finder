package org.example.serviceLayer.services.interfaces;

import org.example.dataLayer.dataModels.RequestDataModel;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.List;

public interface QoSChainService {

    /**
     * This method returns the nodes options in sorted order
     * @return List of nodes in sorted order.
     */
    public List<Node> getNodeOptions();

    /**
     * Visualizes Graph
      */
    void visualizeGraph();

    /**
     * calculates the request and returns the path related with it
     * @param request is the requested data
     * @return path found, null otherwise
     */
    Path calculateTheRequest(RequestDataModel request);


    /**
     * Generates random request
     * @return Request Data Model
     */
    RequestDataModel generateRandomRequest();

    /**
     * Paints all edges to specified color
     * @param color color to paint edges
     */
    void paintAllEdges(String color);
}
