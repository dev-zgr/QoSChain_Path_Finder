package org.example.serviceLayer.randomizer.implementations;

import org.example.dataLayer.dataModels.RequestDataModel;
import org.graphstream.graph.Graph;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomRequestGenerator{
    /**
     * Generates a random request based on the provided graph.
     * The request includes a randomly chosen ingress node, egress node,
     * required bandwidth, and maximum delay.
     *
     * @param graph Graph used for creating the random request
     * @return A RequestDataModel object containing the randomly generated request details:
     */
    public  static RequestDataModel generateRandomRequest(Graph graph) {
        Random random = new Random(System.currentTimeMillis());

        int randomIngressNodeIndex;
        int randomEgressNodeIndex;

        do {
            randomIngressNodeIndex = random.nextInt(graph.getNodeCount());
            randomEgressNodeIndex = random.nextInt(graph.getNodeCount());
        }while (randomIngressNodeIndex == randomEgressNodeIndex);

        String ingressNode = graph.getNode(randomIngressNodeIndex).toString();
        String egressNode = graph.getNode(randomEgressNodeIndex).toString();

        AtomicInteger minBandwidth = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maxBandwidth = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger minDelay = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger maxDelay = new AtomicInteger(Integer.MIN_VALUE);

        graph.edges().forEach(edge -> {
            int edgeBandwidth = (int) edge.getAttribute("max_bandwidth");
            int edgeDelay = (int) edge.getAttribute("min_delay");

            minBandwidth.updateAndGet(value -> Math.min(value, edgeBandwidth));
            maxBandwidth.updateAndGet(value -> Math.max(value, edgeBandwidth));
            minDelay.updateAndGet(value -> Math.min(value, edgeDelay));
            maxDelay.updateAndGet(value -> Math.max(value, edgeDelay));
        });

        int requiredBandwidth = random.nextInt(maxBandwidth.get() - minBandwidth.get() + 1) + minBandwidth.get();
        int maxAllowedDelay = random.nextInt(maxDelay.get() - minDelay.get() + 1) + minDelay.get();

        return new RequestDataModel(ingressNode, egressNode, requiredBandwidth, maxAllowedDelay);
    }
}
