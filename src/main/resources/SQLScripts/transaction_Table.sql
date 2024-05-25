DROP TABLE IF EXISTS transaction_table;
CREATE TABLE IF NOT EXISTS transaction_table (
    tx_id VARCHAR(20) PRIMARY KEY,
    signature VARCHAR(250),
    asn VARCHAR(50),
    pathlet_id VARCHAR(100),
    ingress_node VARCHAR(50),
    egress_node VARCHAR(50),
    max_bandwidth INT,
    min_delay INT,
    is_interconnecting_node BOOLEAN
);


DELIMITER $$
DROP TRIGGER IF EXISTS trig_txID_check$$
CREATE TRIGGER trig_txID_check BEFORE INSERT ON transaction_table
    FOR EACH ROW
BEGIN
    IF (NEW.tx_id REGEXP '^AS[0-9]+_[0-9]+$') = 0 THEN
        SIGNAL SQLSTATE '40001'
            SET MESSAGE_TEXT = 'Invalid Tx ID';
    END IF;
END$$
DELIMITER


DELIMITER $$
DROP TRIGGER IF EXISTS trig_IngressNode_EgressNode_Check$$
CREATE TRIGGER trig_IngressNode_EgressNode_Check BEFORE INSERT ON transaction_table
    FOR EACH ROW
BEGIN
    IF (NEW.ingress_node REGEXP '^R[0-9]+$') = 0 THEN
        SIGNAL SQLSTATE '40001'
            SET MESSAGE_TEXT = 'Invalid Ingress Node';
    END IF;

    IF (NEW.egress_node REGEXP '^R[0-9]+$') = 0 THEN
        SIGNAL SQLSTATE '40001'
            SET MESSAGE_TEXT = 'Invalid Egress Node';
    END IF;
END$$
DELIMITER
