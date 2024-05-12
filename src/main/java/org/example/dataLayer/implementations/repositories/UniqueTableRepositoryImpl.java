package org.example.dataLayer.implementations.repositories;

import org.example.dataLayer.implementations.accessManager.DataSourceImpl;
import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;
import org.example.dataLayer.interfaces.repositories.UniqueTableRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniqueTableRepositoryImpl implements UniqueTableRepository {

    public UniqueTableRepositoryImpl() {
    }

    @Override
    public UniqueTableDataModel saveEdge(UniqueTableDataModel uniqueTableDataModel) {
        String sqlSelect = "SELECT * FROM unique_table WHERE pathlet_id = ?";
        String sqlInsert = "INSERT INTO unique_table (pathlet_id, ingress_node, egress_node, max_bandwidth,min_delay) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE unique_table SET max_bandwidth = ?, min_delay = ?  WHERE pathlet_id = ?";

        try (Connection connection = DataSourceImpl.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelect);
             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);
             PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {

            selectStatement.setString(1, uniqueTableDataModel.getPathlet_id());

            // Execute select statement
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setInt(1, uniqueTableDataModel.getMax_bandwidth());
                updateStatement.setInt(2, uniqueTableDataModel.getMin_delay());
                updateStatement.setString(3, uniqueTableDataModel.getPathlet_id());
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected == 1) {
                    return uniqueTableDataModel;
                }
            } else {
                insertStatement.setString(1, uniqueTableDataModel.getPathlet_id());
                insertStatement.setString(2, uniqueTableDataModel.getIngress_node());
                insertStatement.setString(3, uniqueTableDataModel.getEgress_node());
                insertStatement.setInt(4, uniqueTableDataModel.getMax_bandwidth());
                insertStatement.setInt(5, uniqueTableDataModel.getMin_delay());
                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected == 1) {
                    return uniqueTableDataModel; // Return the inserted data
                }
            }
        } catch (SQLException e) {
            return null;
        }

        return null;
    }


    @Override
    public boolean saveEdge(List<UniqueTableDataModel> uniqueTableDataModel) {
        boolean allSaved = true;
        for (UniqueTableDataModel uniqueTableDataModels : uniqueTableDataModel) {
            UniqueTableDataModel isSaved = saveEdge(uniqueTableDataModels);
            if (isSaved == null) {
                allSaved = false;
                break;
            }
        }
        return allSaved;
    }

    @Override
    public UniqueTableDataModel findEdgeByPathletId(String pathlethId) {
        String sql = "SELECT * FROM unique_table WHERE pathlet_id = ?";
        try (Connection connection = DataSourceImpl.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pathlethId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUniqueTableDataModel(resultSet);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UniqueTableDataModel> findAllEdges() {
        List<UniqueTableDataModel> UniqueTableDataModels = new ArrayList<>();

        String sql = "SELECT * FROM unique_table";

        try (Connection connection = DataSourceImpl.getConnection();
             Statement preparedStatement = connection.createStatement();
             ResultSet resultSet = preparedStatement.executeQuery(sql)) {
            while (resultSet.next()) {
                UniqueTableDataModel uniqueTableDataModel = mapResultSetToUniqueTableDataModel(resultSet);
                UniqueTableDataModels.add(uniqueTableDataModel);
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return UniqueTableDataModels;
    }

    @Override
    public boolean resetUniqueTable() {
        try (Connection connection = DataSourceImpl.getConnection()) {
            String sql = "DELETE FROM unique_table WHERE TRUE";
            try (Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(sql);
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            return false;
        }

    }

    private UniqueTableDataModel mapResultSetToUniqueTableDataModel(ResultSet resultSet) throws SQLException {
        return new UniqueTableDataModel(
                resultSet.getString("pathlet_id"),
                resultSet.getString("ingress_node"),
                resultSet.getString("egress_node"),
                resultSet.getInt("max_bandwidth"),
                resultSet.getInt("min_delay")
        );
    }
}
