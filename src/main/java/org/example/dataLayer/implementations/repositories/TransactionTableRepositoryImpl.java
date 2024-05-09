package org.example.dataLayer.implementations.repositories;

import org.example.dataLayer.implementations.accessManager.DataSourceImpl;
import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionTableRepositoryImpl implements TransactionTableRepository {

    public TransactionTableRepositoryImpl() {
    }

    @Override
    public TransactionTableDataModel saveTransaction(TransactionTableDataModel transactionTableDataModel) {
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node,egress_node,max_bandwidth,min_delay) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transactionTableDataModel.getTx_id());
            preparedStatement.setString(2, transactionTableDataModel.getSignature());
            preparedStatement.setString(3, transactionTableDataModel.getAsn());
            preparedStatement.setString(4, transactionTableDataModel.getPathlet_id());
            preparedStatement.setString(5, transactionTableDataModel.getIngress_node());
            preparedStatement.setString(6, transactionTableDataModel.getEgress_node());
            preparedStatement.setInt(7, transactionTableDataModel.getMax_bandwidth());
            preparedStatement.setInt(8, transactionTableDataModel.getMin_delay());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Transaction data inserted successfully.");
            } else {
                System.out.println("Failed to insert transaction data.");
            }
            return transactionTableDataModel;
        } catch (Exception e){
            return null;
        }
    }


    @Override
    public boolean saveTransaction(List<TransactionTableDataModel> transactionTableDataModels) {
        boolean allSaved = true;
        for (TransactionTableDataModel transactionTableDataModel : transactionTableDataModels) {
            TransactionTableDataModel isSaved = saveTransaction(transactionTableDataModel);
            if (isSaved == null) {
                allSaved = false;
                break;
            }
        }
        return allSaved;
    }

    @Override
    public TransactionTableDataModel findTransactionByTxId(String TxId) {
        String sql = "SELECT * FROM transaction_table WHERE tx_id = ?";
        try (Connection connection = DataSourceImpl.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, TxId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToTransaction(resultSet);
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
        public List<TransactionTableDataModel> findAllTransactions () {
        String sql = "SELECT * FROM transaction_table";
        try (Connection connection = DataSourceImpl.getConnection(); Statement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);

            List<TransactionTableDataModel> transactionTableDataModelList = new ArrayList<>();
            while (resultSet.next()) {
                TransactionTableDataModel transaction = mapResultSetToTransaction(resultSet);
                transactionTableDataModelList.add(transaction);
            }
            return transactionTableDataModelList;
        } catch (Exception e) {
            return null;
        }
        }

    @Override
    public List<TransactionTableDataModel> findLatestDistinctTransactions() {
        List<TransactionTableDataModel> distinctTransactions = new ArrayList<>();

        String sql =
                "SELECT *                        " +
                "FROM transaction_table          " +
                "WHERE (pathlet_id, tx_id) IN ("   +
                "   SELECT pathlet_id, MAX(tx_id)" +
                "   FROM transaction_table " +
               "    GROUP BY pathlet_id)";

        try (Connection connection = DataSourceImpl.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                TransactionTableDataModel transaction = mapResultSetToTransaction(resultSet);
                distinctTransactions.add(transaction);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return distinctTransactions;
    }


    private TransactionTableDataModel mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        TransactionTableDataModel transaction = new TransactionTableDataModel();
        transaction.setTx_id(resultSet.getString("tx_id"));
        transaction.setSignature(resultSet.getString("signature"));
        transaction.setAsn(resultSet.getString("asn"));
        transaction.setPathlet_id(resultSet.getString("pathlet_id"));
        transaction.setIngress_node(resultSet.getString("ingress_node"));
        transaction.setEgress_node(resultSet.getString("egress_node"));
        transaction.setMax_bandwidth(resultSet.getInt("max_bandwidth"));
        transaction.setMin_delay(resultSet.getInt("min_delay"));
        return transaction;
    }
}
