package org.example.serviceLayer.services.implementations;

import org.example.dataLayer.dataModels.RequestDataModel;
import org.example.dataLayer.dataModels.TransactionTableDataModel;
import org.example.dataLayer.dataModels.UniqueTableDataModel;
import org.example.dataLayer.repositories.interfaces.TransactionTableRepository;
import org.example.dataLayer.repositories.interfaces.UniqueTableRepository;
import org.example.serviceLayer.mappers.GraphMapper;
import org.example.serviceLayer.mappers.TransactionMapper;
import org.example.serviceLayer.pathFinder.PathFinder;
import org.example.serviceLayer.randomizer.implementations.RandomRequestGenerator;
import org.example.serviceLayer.randomizer.interfaces.RandomTransactionGenerator;
import org.example.serviceLayer.services.interfaces.QoSChainService;
import org.graphstream.graph.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class QoSChainServiceImpl implements QoSChainService {
    private final PathFinder pathFinder;
    private volatile Graph graph;
    private final RandomTransactionGenerator randomTransactionGenerator;
    private final TransactionTableRepository transactionTableRepository;
    private final UniqueTableRepository uniqueTableRepository;
    private final GraphMapper graphMapper;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private volatile boolean generating = true;

    public QoSChainServiceImpl(PathFinder pathFinder, RandomTransactionGenerator randomTransactionGenerator, TransactionTableRepository transactionTableRepository, UniqueTableRepository uniqueTableRepository, GraphMapper graphMapper) {
        this.pathFinder = pathFinder;
        this.randomTransactionGenerator = randomTransactionGenerator;
        this.transactionTableRepository = transactionTableRepository;
        this.uniqueTableRepository = uniqueTableRepository;
        this.graphMapper = graphMapper;

        List<TransactionTableDataModel> transactionTableDataModelList = transactionTableRepository.findLatestDistinctTransactions();
        List<UniqueTableDataModel> uniqueTableDataModels = transactionTableDataModelList.stream()
                .map(transactionTableDataModel ->
                        TransactionMapper
                                .mapTransactionTableDataModelToUniqueTableDataModel(transactionTableDataModel, new UniqueTableDataModel()))
                .toList();
        uniqueTableRepository.saveEdge(uniqueTableDataModels);

        this.graph = graphMapper.mapUniqueTableDataModelToGraph();
        startRandomTransactionGeneration();
    }

    @Override
    public List<Node> getNodeOptions() {
        return graph.nodes()
                .sorted(Comparator.comparingInt(node -> extractInt(node.getId()))).toList();
    }

    @Override
    public void visualizeGraph() {
        graph.display();
    }

    @Override
    public synchronized Path calculateTheRequest(RequestDataModel request) {
        pauseRandomTransactionGeneration(100000);
        Path path = pathFinder.findTheLeastDelayedPath(this.graph, request);
        System.out.println(path.getEdgePath());
        if (path != null) {
            synchronized (this) {
                path.getEdgePath()
                        .stream()
                        .map(Element::getId)
                        .map(pathletID -> {
                            UniqueTableDataModel temporaryData = uniqueTableRepository.findEdgeByPathletId(pathletID);
                            String recentTXID = transactionTableRepository.getAvailableTransactionID(temporaryData.getAsn());
                            String randomSignature = String.format("0x%s", new Random().nextInt(1000000));
                            return new TransactionTableDataModel(
                                    recentTXID,
                                    randomSignature,
                                    temporaryData.getAsn(),
                                    temporaryData.getPathlet_id(),
                                    temporaryData.getIngress_node(),
                                    temporaryData.getEgress_node(),
                                    temporaryData.getMax_bandwidth() - request.getRequired_bandwidth(),
                                    temporaryData.getMin_delay(),
                                    temporaryData.isInterConnectingNode());
                        }).collect(Collectors.toList())
                        .forEach((transaction) -> {
                                    transactionTableRepository.saveTransaction(transaction);
                                    uniqueTableRepository.saveEdge(TransactionMapper.mapTransactionTableDataModelToUniqueTableDataModel(transaction, new UniqueTableDataModel()));
                                }
                        );
                updateGraph(graphMapper.mapUniqueTableDataModelToGraph(), false);
            }
        }
        return path;
    }

    @Override
    public RequestDataModel generateRandomRequest() {
        return RandomRequestGenerator.generateRandomRequest(this.graph);
    }

    @Override
    public void paintAllEdges(String color) {
        for (Edge edge : graph.edges().toList()) {
            edge.setAttribute("ui.style", String.format("fill-color: %s;", color));
        }
    }

    private int extractInt(String nodeId) {
        try {
            return Integer.parseInt(nodeId.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE; // or any default value indicating parsing failure
        }
    }

    private void startRandomTransactionGeneration() {
        new Thread(() -> {
            while (true) {
                try {
                    if (generating) {
                        TransactionTableDataModel randomTransaction = randomTransactionGenerator.generateRandomTransaction(0, 100, 0, 100);
                        transactionTableRepository.saveTransaction(randomTransaction);
                        uniqueTableRepository.saveEdge(TransactionMapper.mapTransactionTableDataModelToUniqueTableDataModel(randomTransaction, new UniqueTableDataModel()));
                        updateGraph(graphMapper.mapUniqueTableDataModelToGraph(),true);
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void pauseRandomTransactionGeneration(int seconds) {
        generating = false;
        scheduler.schedule(() -> generating = true, seconds, TimeUnit.SECONDS);
    }


    private synchronized void updateGraph(Graph newGraph, boolean updatePath) {
        newGraph.edges().forEach(edge -> {
            int minDelay = (int) edge.getNumber("min_delay");
            int maxBandwidth = (int) edge.getNumber("max_bandwidth");
            this.graph.getEdge(edge.getId()).setAttribute("label", String.format("%d | %d", maxBandwidth, minDelay));
            if (updatePath) {
                this.graph.getEdge(edge.getId()).setAttribute("ui.style", "text-size: 25px; size: 1.5px; fill-color: black;");
            }
        });
    }
}
