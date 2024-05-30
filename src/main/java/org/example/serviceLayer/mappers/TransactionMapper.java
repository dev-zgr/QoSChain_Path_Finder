package org.example.serviceLayer.mappers;

import org.example.dataLayer.dataModels.TransactionTableDataModel;
import org.example.dataLayer.dataModels.UniqueTableDataModel;

/**
 * This class maps the TransactionTableDataModel to UniqueTableDataModel
 */
public class TransactionMapper {
    public static UniqueTableDataModel mapTransactionTableDataModelToUniqueTableDataModel(TransactionTableDataModel transactionTableDataModel, UniqueTableDataModel uniqueTableDataModel){
        uniqueTableDataModel.setPathlet_id(transactionTableDataModel.getPathlet_id());
        uniqueTableDataModel.setIngress_node(transactionTableDataModel.getIngress_node());
        uniqueTableDataModel.setEgress_node(transactionTableDataModel.getEgress_node());
        uniqueTableDataModel.setMax_bandwidth(transactionTableDataModel.getMax_bandwidth()); // Correct this line
        uniqueTableDataModel.setMin_delay(transactionTableDataModel.getMin_delay()); // Correct this line
        uniqueTableDataModel.setInterConnectingNode(transactionTableDataModel.getIsInterConnectingNode());
        uniqueTableDataModel.setAsn(transactionTableDataModel.getAsn());
        return uniqueTableDataModel;
    }

}
