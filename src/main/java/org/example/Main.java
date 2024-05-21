package org.example;

import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;
import org.example.dataLayer.implementations.repositories.TransactionTableRepositoryImpl;
import org.example.dataLayer.implementations.repositories.UniqueTableRepositoryImpl;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.dataLayer.interfaces.repositories.UniqueTableRepository;
import org.example.presentationLayer.GraphVisualizer;
import org.example.serviceLayer.mappers.GraphMapper;
import org.example.serviceLayer.pathFinder.PathFinder;
import org.example.serviceLayer.randomizer.implementations.RandomRequestGeneratorImpl;
import org.example.serviceLayer.randomizer.interfaces.RandomRequestGenerator;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGeneratorImpl;
import org.example.serviceLayer.services.implementations.RandomizationServiceImpl;
import org.example.serviceLayer.services.interfaces.RandomizationService;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        File sampleDataFile = new File("src/main/resources/SQLScripts/dataSets/dataset_1.sql");
        TransactionTableRepository transactionTableRepository = new TransactionTableRepositoryImpl();
        UniqueTableRepository uniqueTableRepository = new UniqueTableRepositoryImpl();
        RandomTransactionGenerator randomTransactionGenerator = new RandomTransactionGeneratorImpl(transactionTableRepository);
        RandomizationService randomizationService = new RandomizationServiceImpl(transactionTableRepository, randomTransactionGenerator);
        GraphMapper graphMapper = new GraphMapper(uniqueTableRepository);

        uniqueTableRepository.resetUniqueTable();

        UniqueTableDataModel uniqueTableDataModel1 = new UniqueTableDataModel("R4_R5_1", "R4", "R5", 15, 8);
        UniqueTableDataModel uniqueTableDataModel2 = new UniqueTableDataModel("R4_R10_1", "R4", "R10", 5, 2);
        UniqueTableDataModel uniqueTableDataModel3 = new UniqueTableDataModel("R5_R7_1", "R5", "R7", 10, 3);
        UniqueTableDataModel uniqueTableDataModel4 = new UniqueTableDataModel("R5_R8_1", "R5", "R8", 18, 9);
        UniqueTableDataModel uniqueTableDataModel5 = new UniqueTableDataModel("R7_R8_1", "R7", "R8", 6, 3);
        UniqueTableDataModel uniqueTableDataModel6 = new UniqueTableDataModel("R7_R10_1", "R7", "R10", 12, 4);
        UniqueTableDataModel uniqueTableDataModel7 = new UniqueTableDataModel("R8_R14_1", "R8", "R14", 25, 10);
        UniqueTableDataModel uniqueTableDataModel8 = new UniqueTableDataModel("R10_R12_1", "R10", "R12", 8, 5);
        UniqueTableDataModel uniqueTableDataModel9 = new UniqueTableDataModel("R12_R13_1", "R12", "R13", 15, 6);
        UniqueTableDataModel uniqueTableDataModel10 = new UniqueTableDataModel("R13_R14_1", "R13", "R14", 22, 11);

        List<UniqueTableDataModel> uniques = List.of(uniqueTableDataModel1, uniqueTableDataModel2, uniqueTableDataModel3, uniqueTableDataModel4,
                uniqueTableDataModel5, uniqueTableDataModel6, uniqueTableDataModel7, uniqueTableDataModel8, uniqueTableDataModel9, uniqueTableDataModel10);
        uniqueTableRepository.saveEdge(uniques);

        Graph graph = graphMapper.mapUniqueTableDataModelToGraph();

        randomizationService.generateRandomTransactions(10L, 100, 1000, 10, 100);
        System.out.println(transactionTableRepository.findLatestDistinctTransactions());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphVisualizer(graph, new PathFinder(), new RandomRequestGeneratorImpl()).setVisible(true);
            }
        });

    }
}