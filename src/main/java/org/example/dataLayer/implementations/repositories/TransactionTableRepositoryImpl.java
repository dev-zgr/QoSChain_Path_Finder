package org.example.dataLayer.implementations.repositories;

import org.example.dataLayer.implementations.accessManager.DataSourceImpl;
import org.example.dataLayer.implementations.dataModels.TransactionTableDataModel;
import org.example.dataLayer.interfaces.repositories.TransactionTableRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionTableRepositoryImpl implements TransactionTableRepository {

    public TransactionTableRepositoryImpl() {
    }

    @Override
    public TransactionTableDataModel saveTransaction(TransactionTableDataModel transactionTableDataModel) {
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node,egress_node,max_bandwidth,min_delay, is_interconnecting_node) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transactionTableDataModel.getTx_id());
            preparedStatement.setString(2, transactionTableDataModel.getSignature());
            preparedStatement.setString(3, transactionTableDataModel.getAsn());
            preparedStatement.setString(4, transactionTableDataModel.getPathlet_id());
            preparedStatement.setString(5, transactionTableDataModel.getIngress_node());
            preparedStatement.setString(6, transactionTableDataModel.getEgress_node());
            preparedStatement.setInt(7, transactionTableDataModel.getMax_bandwidth());
            preparedStatement.setInt(8, transactionTableDataModel.getMin_delay());
            preparedStatement.setBoolean(9,transactionTableDataModel.getIsInterConnectingNode());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                return transactionTableDataModel;
            } else {
                return null;
            }
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

    @Override
    public boolean resetTransactionTable() {
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "DELETE FROM transaction_table WHERE TRUE";
            try (Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(sql);
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean loadDataFromFile(File sampleDataFile) {
        try(Connection connection = DataSourceImpl.getConnection()) {
            BufferedReader reader = new BufferedReader(new FileReader(sampleDataFile));
            String line;
            List<String> transactions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                transactions.add(line);
                Statement statement = connection.createStatement();
                statement.execute(line);
            }
            reader.close();
            return true;
        } catch (IOException | SQLException e) {
            return false;
        }
    }

    @Override
    public String getAvailableTransactionID(String asnNumber) {
        try(Connection connection = DataSourceImpl.getConnection()){
            String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(tx_id, '_', -1) AS UNSIGNED)) AS max_tx_id FROM transaction_table";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                 int txID = resultSet.getInt("max_tx_id");
                 return String.format("%s_%d", asnNumber,txID +1);
            } else {
                return "";
            }

        } catch (SQLException e) {
            return "";
        }
    }

    @Override
    public List<String> getDistinctASNs() {
        List<String> distinctASNs = new ArrayList<>();
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "SELECT DISTINCT asn FROM transaction_table";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    distinctASNs.add(resultSet.getString("asn"));
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return distinctASNs;
    }

    @Override
    public List<String> getDistinctPathletIDs() {
        List<String> distinctPathletIDs = new ArrayList<>();
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "SELECT DISTINCT pathlet_id FROM transaction_table";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    distinctPathletIDs.add(resultSet.getString("pathlet_id"));
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return distinctPathletIDs;
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
        transaction.setInterConnectingNode(resultSet.getBoolean("is_interconnecting_node"));
        return transaction;
    }


}
