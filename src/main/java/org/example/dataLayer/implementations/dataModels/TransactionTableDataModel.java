package org.example.dataLayer.implementations.dataModels;

public class TransactionTableDataModel {
    private String tx_id;
    private String signature;
    private String asn;
    private String pathlet_id;
    private String ingress_node;
    private String egress_node;
    private int max_bandwidth;
    private int min_delay;

    public TransactionTableDataModel(String tx_id, String signature, String asn, String pathlet_id, String ingress_node, String egress_node, int max_bandwidth, int min_delay) {
        this.tx_id = tx_id;
        this.signature = signature;
        this.asn = asn;
        this.pathlet_id = pathlet_id;
        this.ingress_node = ingress_node;
        this.egress_node = egress_node;
        this.max_bandwidth = max_bandwidth;
        this.min_delay = min_delay;
    }

    public TransactionTableDataModel() {
        this.tx_id = null;
        this.signature = null;
        this.asn = null;
        this.pathlet_id = null;
        this.ingress_node = null;
        this.egress_node = null;
        this.max_bandwidth = 0;
        this.min_delay = 0;
    }
}