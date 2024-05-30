package org.example.dataLayer.repositories.interfaces;

import org.example.dataLayer.dataModels.TransactionTableDataModel;
import org.example.dataLayer.dataModels.UniqueTableDataModel;

import java.io.File;
import java.util.List;

/**
 * This interface is used to interact with the Transaction Table in the database.
 */
public interface TransactionTableRepository {
    /**
     * Persists the transaction data to the database.
     * @param transactionTableDataModel The transaction data to be persisted.
     * @return The persisted transaction data if successful, null otherwise.
     */
    TransactionTableDataModel saveTransaction(TransactionTableDataModel transactionTableDataModel);

    /**
     * Persists the transaction data to the database.
     *
     * @param transactionTableDataModels The list of transaction data to be persisted.
     * @return The persisted list transaction data if successful, false otherwise.
     */
    boolean saveTransaction(List<TransactionTableDataModel> transactionTableDataModels);

    /**
     * Finds a transaction by its transaction id.
     * @param TxId The transaction id to search for.
     * @return The transaction data if found, null otherwise.
     */
    TransactionTableDataModel findTransactionByTxId(String TxId);

    /**
     * Finds all transactions in the database.
     * @return A list of all transactions in the database.
     */
    List<TransactionTableDataModel> findAllTransactions();

    /**
     * Finds the latest distinct transactions in the database. Transaction distinction is based on the unique pathlet_id*
     * @return A list of the latest distinct transactions in the database. If no transactions are found, an empty list is returned.
     */
    List<TransactionTableDataModel> findLatestDistinctTransactions();

    /**
     * This method deletes all the transactions inside the transaction table.
     * @return true if the transaction table is successfully cleared, false otherwise.
     */
    boolean resetTransactionTable();


    /**
     * This method use to load data from a file to the database
     * @param sampleDataFile The file to load data from
     * @return true if the data is successfully loaded, false otherwise.
     */
    boolean loadDataFromFile(File sampleDataFile);

    /**
     * This method is used to get the available transaction for a given asn number
     * @param asnNumber The asn number to get the available transaction for
     * @return The available transaction for the given asn number
     */
    String getAvailableTransactionID(String asnNumber);

    /**
     * This method used to retrieve distinct ASNs numbers from the Transaction table
     * @return the list of distinct ASNs is returned.
     */
    List<String> getDistinctASNs();

    /**
     * This method used to retrieve distinct pathleth ID numbers from the Transaction table
     * @return the list of distinct ASNs is returned.
     */
    List<String> getDistinctPathletIDs();

    boolean findIsInterConnectingNode(String pathletID);
}
