
# PII Shield - Bảo vệ dữ liệu cá nhân bằng phân mảnh dọc

## Thông tin đề tài

* Mã đề tài: 106
* Tên đề tài: Privacy-Preserving Vertical Fragmentation: PII Shield
* Sinh viên thực hiện: Huỳnh Thị Như Quỳnh 
* Mã sinh viên: N23DCCN186
* Môn học: Cơ sở dữ liệu phân tán

## Giới thiệu

PII Shield là một mô hình minh họa việc áp dụng kỹ thuật phân mảnh dọc (Vertical Fragmentation) trong cơ sở dữ liệu phân tán nhằm bảo vệ thông tin nhận dạng cá nhân của khách hàng.

Khác với các mô hình mô phỏng phân mảnh tĩnh, hệ thống lưu trữ toàn bộ dữ liệu ban đầu trong một quan hệ duy nhất là Customer_Data. Quá trình phân mảnh được thực hiện động thông qua API, từ đó tạo ra các fragment tương ứng tại các node phân tán.

Dữ liệu nhạy cảm như tên khách hàng, số định danh cá nhân và thông tin thẻ tín dụng được lưu trữ tại Secure Node. Trong khi đó, lịch sử mua hàng được lưu trữ tại Public Node.

Thuộc tính liên kết OID được mã hóa bằng thuật toán AES trước khi lưu tại Public Node nhằm tăng cường khả năng bảo vệ dữ liệu.

Hệ thống hỗ trợ thực hiện Distributed Join giữa các node và mô phỏng trường hợp một node gặp sự cố để đánh giá khả năng bảo vệ dữ liệu nhạy cảm.

---

## Công nghệ sử dụng

* Java
* Spring Boot
* MySQL
* Maven
* REST API
* AES Encryption

---

## Chuẩn bị cơ sở dữ liệu

Mở MySQL Workbench và thực thi các file SQL trong thư mục database.

Bước 1:

original_db.sql

Tạo cơ sở dữ liệu gốc:

original_db

Bảng dữ liệu:

customer_data

Đây là nơi lưu trữ toàn bộ dữ liệu khách hàng trước khi phân mảnh.

Bước 2:

public_db.sql

Tạo cơ sở dữ liệu:

public_db

Bảng:

public_fragment

Bước 3:

secure_db.sql

Tạo cơ sở dữ liệu:

secure_db

Bảng:

secure_fragment

---

## Khởi động Secure Node

Mở terminal tại thư mục:

secure-node

Chạy lệnh:

mvnw.cmd spring-boot:run

Secure Node sẽ hoạt động tại:

http://localhost:8082

---

## Khởi động Public Node

Mở terminal tại thư mục:

public-node

Chạy lệnh:

mvnw.cmd spring-boot:run

Public Node sẽ hoạt động tại:

http://localhost:8081

---

## Thực hiện phân mảnh dọc

Dữ liệu ban đầu chưa được phân mảnh.

Để thực hiện phân mảnh, truy cập:

http://localhost:8081/public/fragment

Hệ thống sẽ:

1. Đọc dữ liệu từ original_db.customer_data
2. Tạo Secure Fragment tại Secure Node
3. Tạo Public Fragment tại Public Node
4. Mã hóa OID bằng AES trước khi lưu tại Public Fragment

---

## Kiểm tra Public Fragment

http://localhost:8081/public/all

Kết quả trả về gồm:

* EncryptedOID
* PurchaseHistory

---

## Kiểm tra Secure Fragment

http://localhost:8082/secure/all

Kết quả trả về gồm:

* OID
* Name
* SSN
* CreditCard

---

## Kiểm tra AES Encryption

http://localhost:8081/public/encrypt-test

API minh họa quá trình:

OID → AES Encryption → EncryptedOID

và

EncryptedOID → AES Decryption → OID

---

## Thực hiện Distributed Join

Truy vấn:

http://localhost:8081/public/customer?purchase=Laptop

Quy trình xử lý:

1. Public Node tìm kiếm dữ liệu tại Public Fragment
2. Giải mã EncryptedOID
3. Gửi REST API tới Secure Node
4. Secure Node trả về thông tin khách hàng
5. Public Node thực hiện Distributed Join
6. Trả kết quả cho người dùng

Ví dụ:

Customer Name: Alice Johnson

Purchase: Bought Laptop

---

## Đo độ trễ truy vấn

Truy cập:

http://localhost:8081/public/latency?purchase=Laptop

API này đo Query Latency của toàn bộ quá trình:

* Giải mã OID
* Truy vấn Public Fragment
* Truy vấn Secure Fragment
* Truyền dữ liệu giữa các node
* Distributed Join

---

## Mô phỏng lỗi hệ thống

1. Khởi động cả hai node
2. Thực hiện Distributed Join thành công
3. Tắt Secure Node
4. Thực hiện lại truy vấn:

http://localhost:8081/public/customer?purchase=Laptop

Kết quả mong đợi:

* Public Node vẫn hoạt động
* Dữ liệu giao dịch vẫn truy cập được
* Thông tin nhận dạng khách hàng không thể truy xuất
* Dữ liệu nhạy cảm tiếp tục được bảo vệ

---

## Nội dung học thuật được minh họa

Đề tài minh họa các khái niệm chính của môn Cơ sở dữ liệu phân tán:

* Vertical Fragmentation
* Completeness
* Reconstruction
* Disjointness
* Linking Attribute
* AES Encryption
* Distributed Query Processing
* Distributed Join
* Query Latency
* Communication Cost
* Fault Tolerance
* Graceful Degradation

---

## Kết luận

PII Shield minh họa cách áp dụng phân mảnh dọc để tách biệt dữ liệu nhạy cảm khỏi dữ liệu giao dịch thông thường. Hệ thống sử dụng mã hóa AES để bảo vệ thuộc tính liên kết giữa các fragment và hỗ trợ thực hiện Distributed Join giữa các node.

Mô hình cho thấy có thể nâng cao khả năng bảo vệ dữ liệu cá nhân trong môi trường cơ sở dữ liệu phân tán mà vẫn duy trì khả năng truy vấn và tái cấu trúc dữ liệu khi cần thiết.
