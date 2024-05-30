package org.example.dataLayer.dataModels;

public class RequestDataModel {
    private String ingress_node;
    private String egress_node;
    private int required_bandwidth;
    private int max_delay;

    public RequestDataModel(String ingress_node, String egress_node, int required_bandwidth, int max_delay) {
        this.ingress_node = ingress_node;
        this.egress_node = egress_node;
        this.required_bandwidth = required_bandwidth;
        this.max_delay = max_delay;
    }

    public RequestDataModel(){
        this.ingress_node = this.egress_node = null;
        this.required_bandwidth = this.max_delay = 0;
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

    public int getRequired_bandwidth() {
        return required_bandwidth;
    }

    public void setRequired_bandwidth(int required_bandwidth) {
        this.required_bandwidth = required_bandwidth;
    }

    public int getMax_delay() {
        return max_delay;
    }

    public void setMax_delay(int max_delay) {
        this.max_delay = max_delay;
    }
}
