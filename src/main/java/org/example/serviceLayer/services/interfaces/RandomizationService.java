package org.example.serviceLayer.services.interfaces;

public interface RandomizationService {
    /**
     * This method used to generate & persist random Transactions with the given list of asnList & pathlet list
     * This method works asynchronously
     * @param numberOfTransactions number of transactions to generate
     */
    void generateRandomTransactions(Long numberOfTransactions, int maxBandwidthBottomLimit, int maxBandwidthUpperLimit, int minDelayBottomLimit, int minDelayUpperLimit, boolean isInterConnectingNode);
}
