package org.example;

import org.example.dataLayer.dataModels.UniqueTableDataModel;
import org.example.dataLayer.repositories.implementations.TransactionTableRepositoryImpl;
import org.example.dataLayer.repositories.implementations.UniqueTableRepositoryImpl;
import org.example.dataLayer.repositories.interfaces.TransactionTableRepository;
import org.example.dataLayer.repositories.interfaces.UniqueTableRepository;
import org.example.presentationLayer.GraphVisualizer;
import org.example.serviceLayer.mappers.GraphMapper;
import org.example.serviceLayer.mappers.TransactionMapper;
import org.example.serviceLayer.pathFinder.PathFinder;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGeneratorImpl;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.services.implementations.QoSChainServiceImpl;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");



        TransactionTableRepository transactionTableRepository = new TransactionTableRepositoryImpl();
        UniqueTableRepository uniqueTableRepository = new UniqueTableRepositoryImpl();
        uniqueTableRepository.resetUniqueTable();
        uniqueTableRepository.saveEdge(transactionTableRepository.findLatestDistinctTransactions().stream().map(transactionTableDataModel -> TransactionMapper.mapTransactionTableDataModelToUniqueTableDataModel(transactionTableDataModel, new UniqueTableDataModel())).toList());
        RandomTransactionGenerator randomTransactionGenerator = new RandomTransactionGeneratorImpl(transactionTableRepository);
        GraphMapper graphMapper = new GraphMapper(uniqueTableRepository);
        SwingUtilities.invokeLater(() -> new GraphVisualizer(new QoSChainServiceImpl(new PathFinder(), randomTransactionGenerator, transactionTableRepository, uniqueTableRepository, graphMapper)).setVisible(true));

    }
}