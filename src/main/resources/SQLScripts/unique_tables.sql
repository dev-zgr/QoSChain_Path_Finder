DROP TABLE IF EXISTS unique_table;
CREATE TABLE IF NOT EXISTS unique_table (
    asn VARCHAR(50),
    pathlet_id VARCHAR(100) PRIMARY KEY,
    ingress_node VARCHAR(50),
    egress_node VARCHAR(50),
    max_bandwidth INT,
    min_delay INT,
    is_interconnecting_node BOOLEAN
);

