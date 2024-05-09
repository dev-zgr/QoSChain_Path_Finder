package org.example.dataLayer.interfaces.repositories;

import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;

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
     * @return The persisted  list transaction data if successful, null otherwise.
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

}
