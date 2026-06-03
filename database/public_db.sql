-- Xóa database cũ nếu tồn tại
DROP DATABASE IF EXISTS public_db;

-- Tạo database mới
CREATE DATABASE public_db;

-- Chọn database
USE public_db;

-- Tạo bảng public_fragment
CREATE TABLE public_fragment (
    enc_oid VARCHAR(255),
    purchase_history VARCHAR(255)
);

-- Insert dữ liệu mẫu
INSERT INTO public_fragment VALUES
('encrypted_1', 'Bought Laptop'),
('encrypted_2', 'Bought Phone'),
('encrypted_3', 'Bought Keyboard'),
('encrypted_4', 'Bought Monitor'),
('encrypted_5', 'Bought Mouse'),
('encrypted_6', 'Bought Printer'),
('encrypted_7', 'Bought Tablet'),
('encrypted_8', 'Bought Smart Watch'),
('encrypted_9', 'Bought Headphones'),
('encrypted_10', 'Bought Camera'),
('encrypted_11', 'Bought Gaming Chair'),
('encrypted_12', 'Bought SSD'),
('encrypted_13', 'Bought Graphics Card'),
('encrypted_14', 'Bought Mechanical Keyboard'),
('encrypted_15', 'Bought Microphone');

-- Kiểm tra dữ liệu
SELECT * FROM public_fragment;