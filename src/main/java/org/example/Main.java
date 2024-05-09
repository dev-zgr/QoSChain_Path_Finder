package org.example;

import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;
import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;
import org.example.dataLayer.implementations.repositories.TransactionTableRepositoryImpl;
import org.example.dataLayer.implementations.repositories.UniqueTableRepositoryImpl;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UniqueTableRepositoryImpl uniqueTableRepository = new UniqueTableRepositoryImpl();

        UniqueTableDataModel uniqueTableDataModel1 = new UniqueTableDataModel("R5_R7_1", "R5", "R7", 15, 8);
        UniqueTableDataModel uniqueTableDataModel2 = new UniqueTableDataModel("R5_R7_2", "R5", "R7", 5, 12);
        UniqueTableDataModel uniqueTableDataModel3 = new UniqueTableDataModel("R5_R7_3", "R5", "R7", 10, 15);
        UniqueTableDataModel uniqueTableDataModel4 = new UniqueTableDataModel("R5_R7_2", "R5", "R7", 20, 12);
        UniqueTableDataModel uniqueTableDataModel5 = new UniqueTableDataModel("R5_R7_3", "R5", "R7", 20, 15);
        UniqueTableDataModel uniqueTableDataModel16 = new UniqueTableDataModel("R5_R4_1", "R5", "R4", 40, 30);

        List<UniqueTableDataModel> uniques = List.of(uniqueTableDataModel1, uniqueTableDataModel2, uniqueTableDataModel3, uniqueTableDataModel4, uniqueTableDataModel5, uniqueTableDataModel16);
        uniqueTableRepository.saveEdge(uniques);

    }
}