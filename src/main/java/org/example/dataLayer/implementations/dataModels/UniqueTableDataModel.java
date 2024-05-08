package org.example.dataLayer.implementations.dataModels;

public class UniqueTableDataModel {
    private String pathlet_id;
    private String ingress_node;
    private String egress_node;
    private int max_bandwidth;
    private int min_delay;

    public UniqueTableDataModel(String pathlet_id, String ingress_node, String egress_node, int max_bandwidth, int min_delay) {
        this.pathlet_id = pathlet_id;
        this.ingress_node = ingress_node;
        this.egress_node = egress_node;
        this.max_bandwidth = max_bandwidth;
        this.min_delay = min_delay;
    }

    public UniqueTableDataModel() {
        this.pathlet_id = null;
        this.ingress_node = null;
        this.egress_node = null;
        this.max_bandwidth = 0;
        this.min_delay = 0;
    }
}
