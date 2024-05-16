package org.example;

import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;
import org.example.dataLayer.implementations.repositories.TransactionTableRepositoryImpl;
import org.example.dataLayer.implementations.repositories.UniqueTableRepositoryImpl;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;
import org.example.dataLayer.interfaces.repositories.UniqueTableRepository;
import org.example.presentationLayer.GraphVisualizer;
import org.example.serviceLayer.mappers.GraphMapper;
import org.example.serviceLayer.problemSolver.DijkstraSolver;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.randomizer.implementations.RandomTransactionGeneratorImpl;
import org.example.serviceLayer.services.implementations.RandomizationServiceImpl;
import org.example.serviceLayer.services.interfaces.RandomizationService;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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
        DijkstraSolver dijkstraSolver = new DijkstraSolver();

//        uniqueTableRepository.resetUniqueTable();

//        UniqueTableDataModel uniqueTableDataModel1 = new UniqueTableDataModel("R1_R2_1", "R1", "R2", 15, 8);
//        UniqueTableDataModel uniqueTableDataModel2 = new UniqueTableDataModel("R2_R3_1", "R2", "R3", 5, 2);
//        UniqueTableDataModel uniqueTableDataModel3 = new UniqueTableDataModel("R3_R1_1", "R3", "R1", 10, 3);
//        UniqueTableDataModel uniqueTableDataModel4 = new UniqueTableDataModel("R1_R4_1", "R1", "R4", 18, 9);
//        UniqueTableDataModel uniqueTableDataModel5 = new UniqueTableDataModel("R4_R5_1", "R4", "R5", 6, 3);
//        UniqueTableDataModel uniqueTableDataModel6 = new UniqueTableDataModel("R5_R1_1", "R5", "R1", 12, 4);
//        UniqueTableDataModel uniqueTableDataModel7 = new UniqueTableDataModel("R1_R6_1", "R1", "R6", 25, 10);
//        UniqueTableDataModel uniqueTableDataModel8 = new UniqueTableDataModel("R6_R7_1", "R6", "R7", 8, 5);
//        UniqueTableDataModel uniqueTableDataModel9 = new UniqueTableDataModel("R7_R1_1", "R7", "R1", 15, 6);
//        UniqueTableDataModel uniqueTableDataModel10 = new UniqueTableDataModel("R2_R8_1", "R2", "R8", 22, 11);
//        UniqueTableDataModel uniqueTableDataModel11 = new UniqueTableDataModel("R8_R9_1", "R8", "R9", 7, 4);
//        UniqueTableDataModel uniqueTableDataModel12 = new UniqueTableDataModel("R9_R2_1", "R9", "R2", 13, 5);
//        UniqueTableDataModel uniqueTableDataModel13 = new UniqueTableDataModel("R3_R10_1", "R3", "R10", 20, 9);
//        UniqueTableDataModel uniqueTableDataModel14 = new UniqueTableDataModel("R10_R11_1", "R10", "R11", 5, 2);
//        UniqueTableDataModel uniqueTableDataModel15 = new UniqueTableDataModel("R11_R3_1", "R11", "R3", 10, 4);

//        List<UniqueTableDataModel> uniques = List.of(uniqueTableDataModel1, uniqueTableDataModel2, uniqueTableDataModel3, uniqueTableDataModel4,
//                uniqueTableDataModel5, uniqueTableDataModel6, uniqueTableDataModel7, uniqueTableDataModel8, uniqueTableDataModel9, uniqueTableDataModel10,
//                uniqueTableDataModel11, uniqueTableDataModel12, uniqueTableDataModel13, uniqueTableDataModel14, uniqueTableDataModel15);
//        uniqueTableRepository.saveEdge(uniques);

        Graph graph = graphMapper.mapUniqueTableDataModelToGraph();

//        graph.display();

        Iterable<Edge> shortestPathEdges = dijkstraSolver.findTheShortestPathByEdges(graph, "R1", "R9");

//        if (shortestPathEdges != null) {
//            for (Edge edge : shortestPathEdges)
//                edge.setAttribute("ui.style", "fill-color: red;");
//        }

        randomizationService.generateRandomTransactions(10L, 100, 1000, 10, 100);
        System.out.println(transactionTableRepository.findLatestDistinctTransactions());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphVisualizer(graph, new DijkstraSolver()).setVisible(true);
            }
        });

    }
}