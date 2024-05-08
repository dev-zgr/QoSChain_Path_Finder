CREATE TABLE IF NOT EXISTS transaction_table (
    tx_id VARCHAR(20) PRIMARY KEY,
    signature VARCHAR(250),
    asn VARCHAR(50),
    pathlet_id VARCHAR(100),
    ingress_node VARCHAR(50),
    egress_node VARCHAR(50),
    max_bandwidth INT,
    min_delay INT
);