package org.example;

import org.example.dataLayer.implementations.repositories.TransactionTableRepositoryImpl;
import org.example.dataLayer.implementations.repositories.UniqueTableRepositoryImpl;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.dataLayer.interfaces.repositories.UniqueTableRepository;
import org.example.presentationLayer.GraphVisualizer;
import org.example.serviceLayer.mappers.GraphMapper;
import org.example.serviceLayer.pathFinder.PathFinder;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGeneratorImpl;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.services.implementations.QoSChainServiceImpl;
import org.example.serviceLayer.services.implementations.RandomizationServiceImpl;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        File sampleDataFile = new File("src/main/resources/SQLScripts/dataSets/dataset_1.sql");
        TransactionTableRepository transactionTableRepository = new TransactionTableRepositoryImpl();
        UniqueTableRepository uniqueTableRepository = new UniqueTableRepositoryImpl();
        RandomTransactionGenerator randomTransactionGenerator = new RandomTransactionGeneratorImpl(transactionTableRepository);
        GraphMapper graphMapper = new GraphMapper(uniqueTableRepository);

        uniqueTableRepository.resetUniqueTable();


        SwingUtilities.invokeLater(() -> new GraphVisualizer(new QoSChainServiceImpl(new PathFinder(), randomTransactionGenerator, transactionTableRepository, uniqueTableRepository, graphMapper)).setVisible(true));

    }
}