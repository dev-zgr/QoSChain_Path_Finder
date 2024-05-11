package org.example.serviceLayer.services.implementations;

import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGenerator;
import org.example.serviceLayer.services.interfaces.RandomizationService;

public class RandomizationServiceImpl implements RandomizationService {
    private final TransactionTableRepository transactionTableRepository;
    private final RandomTransactionGenerator randomTransactionGenerator;

    public RandomizationServiceImpl(TransactionTableRepository transactionTableRepository, RandomTransactionGenerator randomTransactionGenerator){
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

