-- Xóa database cũ nếu tồn tại
DROP DATABASE IF EXISTS secure_db;

-- Tạo database mới
CREATE DATABASE secure_db;

-- Chọn database
USE secure_db;

-- Tạo bảng secure_fragment
CREATE TABLE secure_fragment (
    oid INT PRIMARY KEY,
    name VARCHAR(100),
    ssn VARCHAR(100),
    credit_card VARCHAR(100)
);

-- Insert dữ liệu mẫu
INSERT INTO secure_fragment VALUES
(1, 'Alice Johnson', '123456789', '4111111111111111'),
(2, 'Bob Smith', '987654321', '5555555555555555'),
(3, 'Charlie Brown', '111222333', '4444333322221111'),
(4, 'David Wilson', '222333444', '4000123412341234'),
(5, 'Emma Davis', '333444555', '4012888888881881'),
(6, 'Frank Miller', '444555666', '4222222222222222'),
(7, 'Grace Taylor', '555666777', '5105105105105100'),
(8, 'Henry Anderson', '666777888', '5200828282828210'),
(9, 'Isabella Thomas', '777888999', '5300111122223333'),
(10, 'Jack Martinez', '888999000', '5404000000000001'),
(11, 'Karen Lee', '101202303', '5500000000000004'),
(12, 'Liam Harris', '202303404', '6011111111111117'),
(13, 'Mia Clark', '303404505', '6011000990139424'),
(14, 'Noah Lewis', '404505606', '3530111333300000'),
(15, 'Olivia Walker', '505606707', '3566002020360505');

-- Kiểm tra dữ liệu
SELECT * FROM secure_fragment;