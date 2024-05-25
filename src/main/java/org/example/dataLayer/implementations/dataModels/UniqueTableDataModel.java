package org.example.dataLayer.implementations.dataModels;

public class UniqueTableDataModel {
    private String pathlet_id;
    private String ingress_node;
    private String egress_node;
    private int max_bandwidth;
    private int min_delay;

    private String asn;

    private boolean isInterConnectingNode;

    public UniqueTableDataModel(String pathlet_id, String ingress_node, String egress_node, int max_bandwidth, int min_delay, boolean isInterConnectingNode,String asnNumber) {
        this.pathlet_id = pathlet_id;
        this.ingress_node = ingress_node;
        this.egress_node = egress_node;
        this.max_bandwidth = max_bandwidth;
        this.min_delay = min_delay;
        this.isInterConnectingNode = isInterConnectingNode;
        this.asn = asnNumber;
    }

    public UniqueTableDataModel() {
        this.pathlet_id = null;
        this.ingress_node = null;
        this.egress_node = null;
        this.max_bandwidth = 0;
        this.min_delay = 0;
        this.isInterConnectingNode = false;
        this.asn = null;
    }
    public String getPathlet_id() {
        return pathlet_id;
    }

    public void setPathlet_id(String pathlet_id) {
        this.pathlet_id = pathlet_id;
    }

    public String getIngress_node() {
        return ingress_node;
    }

    public void setIngress_node(String ingress_node) {
        this.ingress_node = ingress_node;
    }

    public String getEgress_node() {
        return egress_node;
    }

    public void setEgress_node(String egress_node) {
        this.egress_node = egress_node;
    }

    public int getMax_bandwidth() {
        return max_bandwidth;
    }

    public void setMax_bandwidth(int max_bandwidth) {
        this.max_bandwidth = max_bandwidth;
    }

    public int getMin_delay() {
        return min_delay;
    }

    public void setMin_delay(int min_delay) {
        this.min_delay = min_delay;
    }

    public boolean isInterConnectingNode() {
        return isInterConnectingNode;
    }

    public void setInterConnectingNode(boolean interConnectingNode) {
        isInterConnectingNode = interConnectingNode;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    @Override
    public String toString() {
        return "UniqueTableDataModel{" +
                "pathlet_id='" + pathlet_id + '\'' +
                ", ingress_node='" + ingress_node + '\'' +
                ", egress_node='" + egress_node + '\'' +
                ", max_bandwidth=" + max_bandwidth +
                ", min_delay=" + min_delay +
                '}';
    }
}
