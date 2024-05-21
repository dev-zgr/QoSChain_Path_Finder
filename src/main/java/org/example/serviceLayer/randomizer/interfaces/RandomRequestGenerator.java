package org.example.serviceLayer.randomizer.interfaces;

import org.example.dataLayer.implementations.dataModels.RequestDataModel;
import org.graphstream.graph.Graph;

public interface RandomRequestGenerator {
    /**
     * Generates a random request based on the provided graph.
     * The request includes a randomly chosen ingress node, egress node,
     * required bandwidth, and maximum delay.
     *
     * @param graph Graph used for creating the random request
     * @return A RequestDataModel object containing the randomly generated request details:
     */
    RequestDataModel generateRandomRequest(Graph graph);
}
