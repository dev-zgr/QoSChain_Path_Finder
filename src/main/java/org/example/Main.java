package org.example;

import org.example.dataLayer.implementations.repositories.TransactionTableRepositoryImpl;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGenerator;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGeneratorImpl;
import org.example.serviceLayer.services.implementations.RandomizationServiceImpl;
import org.example.serviceLayer.services.interfaces.RandomizationService;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File sampleDataFile = new File("src/main/resources/SQLScripts/dataSets/dataset_1.sql");
        TransactionTableRepository transactionTableRepository = new TransactionTableRepositoryImpl();
        RandomTransactionGenerator randomTransactionGenerator = new RandomTransactionGeneratorImpl(transactionTableRepository);
        RandomizationService randomizationService = new RandomizationServiceImpl(transactionTableRepository, randomTransactionGenerator);
        randomizationService.generateRandomTransactions(10L, 100, 1000, 10, 100);
        System.out.println(transactionTableRepository.findLatestDistinctTransactions());
    }
}