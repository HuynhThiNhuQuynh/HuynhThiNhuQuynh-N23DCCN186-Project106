DROP DATABASE IF EXISTS original_db;
CREATE DATABASE original_db;
USE original_db;

CREATE TABLE customer_data (
    oid INT PRIMARY KEY,
    name VARCHAR(100),
    ssn VARCHAR(100),
    credit_card VARCHAR(100),
    purchase_history VARCHAR(255)
);

INSERT INTO customer_data VALUES
(1, 'Alice Johnson', '123456789', '4111111111111111', 'Bought Laptop'),
(2, 'Bob Smith', '987654321', '5555555555555555', 'Bought Phone'),
(3, 'Charlie Brown', '111222333', '4444333322221111', 'Bought Keyboard'),
(4, 'David Wilson', '222333444', '4000123412341234', 'Bought Monitor'),
(5, 'Emma Davis', '333444555', '4222222222222222', 'Bought Mouse'),
(6, 'Frank Miller', '444555666', '5105105105105100', 'Bought Printer'),
(7, 'Grace Taylor', '555666777', '4012888888881881', 'Bought Tablet'),
(8, 'Henry Anderson', '666777888', '378282246310005', 'Bought Smart Watch'),
(9, 'Isabella Thomas', '777888999', '6011111111111117', 'Bought Headphones'),
(10, 'Jack Martin', '888999000', '3530111333300000', 'Bought Camera'),
(11, 'Kelly White', '101202303', '5555444433331111', 'Bought Gaming Chair'),
(12, 'Lucas Harris', '202303404', '4111222233334444', 'Bought SSD'),
(13, 'Mia Clark', '303404505', '4000000000000002', 'Bought Graphics Card'),
(14, 'Noah Lewis', '404505606', '5200828282828210', 'Bought Mechanical Keyboard'),
(15, 'Olivia Walker', '505606707', '6011000990139424', 'Bought Microphone');

SELECT * FROM customer_data;