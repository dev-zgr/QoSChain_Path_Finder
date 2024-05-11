package org.example.serviceLayer.randomizer.interfaces;

import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGenerator;

import java.util.List;
import java.util.Random;

public class RandomTransactionGeneratorImpl implements RandomTransactionGenerator {
    private final TransactionTableRepository transactionTableRepository;

    public RandomTransactionGeneratorImpl(TransactionTableRepository transactionTableRepository){
        this.transactionTableRepository = transactionTableRepository;
    }
    @Override
    public TransactionTableDataModel generateRandomTransaction( int maxBandwidthBottomLimit, int maxBandwidthUpperLimit, int minDelayBottomLimit, int minDelayUpperLimit) {
        Random random = new Random(System.currentTimeMillis());
        List<String> asnList = transactionTableRepository.getDistinctASNs();
        String randomASN = asnList.get(random.nextInt(asnList.size()));
        String recentTXID = transactionTableRepository.getAvailableTransactionID(randomASN);
        String randomSignature = String.format("0x%s", random.nextInt(1000000));
        List<String> pathletList = transactionTableRepository.getDistinctPathletIDs();
        String randomPathletID = pathletList.get(random.nextInt(pathletList.size()));
        String[] nodes = randomPathletID.split("_");
        String randomIngressNode = nodes[0];
        String randomEgressNode = nodes[1];
        int randomMaxBandwidth = random.nextInt(maxBandwidthBottomLimit,maxBandwidthUpperLimit);
        int randomMinDelay = random.nextInt(minDelayBottomLimit,minDelayUpperLimit);
        return new TransactionTableDataModel(recentTXID,randomSignature,randomASN,randomPathletID,randomIngressNode,randomEgressNode,randomMaxBandwidth,randomMinDelay);
    }
}
