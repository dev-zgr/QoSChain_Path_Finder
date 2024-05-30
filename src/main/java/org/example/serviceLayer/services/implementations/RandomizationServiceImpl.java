package org.example.serviceLayer.services.implementations;

import org.example.dataLayer.dataModels.TransactionTableDataModel;
import org.example.dataLayer.repositories.interfaces.TransactionTableRepository;
import org.example.dataLayer.repositories.interfaces.UniqueTableRepository;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.services.interfaces.RandomizationService;

public class RandomizationServiceImpl implements RandomizationService {
    private final TransactionTableRepository transactionTableRepository;
    private final RandomTransactionGenerator randomTransactionGenerator;

    public RandomizationServiceImpl(TransactionTableRepository transactionTableRepository, UniqueTableRepository uniqueTableRepository, RandomTransactionGenerator randomTransactionGenerator){
        this.transactionTableRepository = transactionTableRepository;
        this.randomTransactionGenerator = randomTransactionGenerator;
    }
    @Override
    public void generateRandomTransactions(Long numberOfTransactions, int maxBandwidthBottomLimit, int maxBandwidthUpperLimit, int minDelayBottomLimit, int minDelayUpperLimit) {
        for (long i = 0; i < numberOfTransactions; i++) {
                TransactionTableDataModel transaction = randomTransactionGenerator.generateRandomTransaction(
                        maxBandwidthBottomLimit, maxBandwidthUpperLimit,
                        minDelayBottomLimit, minDelayUpperLimit);
                transactionTableRepository.saveTransaction(transaction);
            }
    }

}

