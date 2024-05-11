package org.example.serviceLayer.randomizer.implementations;

import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;

public interface RandomTransactionGenerator {
    /**
     * This method used to generate random Transactions with the given list of asnList & pathlet list
     * @param maxBandwidthBottomLimit bottom limit of max Bandwidth in transaction generation
     * @param maxBandwidthUpperLimit  upper limit of max Bandwidth in transaction generation
     * @param minDelayBottomLimit  bottom limit of min delay in transaction generation
     * @param minDelayUpperLimit upper limit of min delay in transaction generation
     * @return random Transaction
     */
    TransactionTableDataModel generateRandomTransaction( int maxBandwidthBottomLimit, int maxBandwidthUpperLimit, int minDelayBottomLimit, int minDelayUpperLimit);
}
