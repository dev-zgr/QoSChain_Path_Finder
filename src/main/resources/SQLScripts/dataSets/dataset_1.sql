INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS1_1', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS1', 'R4_R5_1', 'R4', 'R5', 15, 8, true);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS1_2', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS1', 'R4_R10_1', 'R4', 'R10', 5, 2, true);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS2_3', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS2', 'R5_R7_1', 'R5', 'R7', 10, 3, false);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS2_4', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS2', 'R5_R8_1', 'R5', 'R8', 18, 9, false);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS1_5', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS2', 'R7_R8_1', 'R7', 'R8', 6, 3, false);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS3_6', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS3', 'R7_R10_1', 'R7', 'R10', 12, 4, true);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS3_7', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS3', 'R8_R14_1', 'R8', 'R14', 25, 10, true);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS4_8', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS4', 'R10_R12_1', 'R10', 'R12', 8, 5, false);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS1_9', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS4', 'R12_R13_1', 'R12', 'R13', 15, 6, true);

INSERT INTO transaction_table (tx_id, signature, asn, pathlet_id, ingress_node, egress_node, max_bandwidth, min_delay, is_interconnecting_node)
VALUES ('AS4_10', CONCAT('0x', LPAD(CONV(FLOOR(RAND() * 9999999999999999), 10, 16), 16, '0')), 'AS4', 'R13_R14_1', 'R13', 'R14', 30, 40, true);