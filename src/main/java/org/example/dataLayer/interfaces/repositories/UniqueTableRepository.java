package org.example.dataLayer.interfaces.repositories;

import org.example.dataLayer.implementations.dataModels.UniqueTableDataModel;

import java.util.List;

public interface UniqueTableRepository {
    /**
     * Persists the edge data to the database. Creates new row if the edge does not exist, updates the existing row otherwise.
     * @param uniqueTableDataModel The edge data to be persisted.
     * @return The persisted edge data if successful, null otherwise.
     */
    UniqueTableDataModel saveEdge(UniqueTableDataModel uniqueTableDataModel);

    /**
     * Persists the edge data to the database. Creates new row if the edge does not exist, updates the existing row otherwise.
     * @param uniqueTableDataModels The list of edge data to be persisted.
     * @return The persisted list edge data if successful, false otherwise.
     */
    boolean saveEdge(List<UniqueTableDataModel> uniqueTableDataModels);

    /**
     * Finds an edge by its pathlet_id.
     * @param pathlethId The pathlet_id to search for.
     * @return The edge data if found, null otherwise.
     */
    UniqueTableDataModel findEdgeByPathletId(String pathlethId);

    /**
     * Finds all edges in the database.
     * @return A list of all edges in the database.
     */
    List<UniqueTableDataModel> findAllEdges();

    /**
     * This method deletes all the transactions inside the transaction table.
     * @return true if the transaction table is successfully cleared, false otherwise.
     */
    boolean resetUniqueTable();


}
