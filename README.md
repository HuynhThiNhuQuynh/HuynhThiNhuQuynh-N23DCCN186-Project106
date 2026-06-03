# PII Shield - Bảo vệ dữ liệu cá nhân bằng phân mảnh dọc

## Thông tin dự án

* Mã đề tài: 106
* Tên đề tài: Privacy-Preserving Vertical Fragmentation: PII Shield
* Sinh viên thực hiện: Huỳnh Thị Như Quỳnh
* Môn học: Cơ sở dữ liệu phân tán

## Giới thiệu

Dự án PII Shield được xây dựng nhằm minh họa việc áp dụng kỹ thuật phân mảnh dọc (Vertical Fragmentation) trong cơ sở dữ liệu phân tán để bảo vệ thông tin nhận dạng cá nhân của khách hàng.

Ý tưởng chính của hệ thống là tách dữ liệu khách hàng thành hai phần độc lập:

* Dữ liệu nhạy cảm (tên khách hàng, số định danh cá nhân, thông tin thẻ tín dụng) được lưu trữ tại Secure Node.
* Dữ liệu lịch sử mua hàng được lưu trữ tại Public Node.

Khóa liên kết giữa hai phân mảnh được mã hóa trước khi lưu trữ nhằm hạn chế nguy cơ lộ thông tin khi một node bị truy cập trái phép.

Hệ thống vẫn cho phép truy vấn và ghép nối dữ liệu thông qua cơ chế Distributed Join giữa các node.

## Công nghệ sử dụng

* Java
* Spring Boot
* MySQL
* Maven
* REST API
* AES Encryption

## Cấu trúc thư mục

```text
PROJECT_CUOI_KY
│
├── database
│   ├── public_db.sql
│   └── secure_db.sql
│
├── public-node
│
├── secure-node
│
├── Proposal_106.docx
├── Design_Document_106.docx
├── Analysis_Report_106.docx
│
└── README.md
```

## Chuẩn bị cơ sở dữ liệu

Mở MySQL Workbench và lần lượt thực thi hai file:

```text
database/secure_db.sql
database/public_db.sql
```

Sau khi thực hiện xong sẽ tạo ra hai cơ sở dữ liệu:

```text
secure_db
public_db
```

## Khởi động Secure Node

Mở terminal tại thư mục:

```text
secure-node
```

Chạy lệnh:

```bash
mvnw.cmd spring-boot:run
```

Hệ thống sẽ khởi động Secure Node tại:

```text
http://localhost:8082
```

## Khởi động Public Node

Mở terminal tại thư mục:

```text
public-node
```

Chạy lệnh:

```bash
mvnw.cmd spring-boot:run
```

Hệ thống sẽ khởi động Public Node tại:

```text
http://localhost:8081
```

## Kiểm tra hoạt động của hệ thống

### Xem dữ liệu Public Fragment

```text
http://localhost:8081/public/all
```

### Truy vấn Distributed Join

```text
http://localhost:8081/public/customer?purchase=Laptop
```

Kết quả mong đợi:

```text
Customer Name: Alice Johnson | Purchase: Bought Laptop
```

### Đo độ trễ truy vấn

```text
http://localhost:8081/public/latency?purchase=Laptop
```

Ví dụ:

```text
Customer: Alice Johnson
Latency: 16 ms
```

## Mô phỏng lỗi hệ thống

Để kiểm tra khả năng hoạt động khi một node gặp sự cố:

1. Khởi động cả Public Node và Secure Node.
2. Kiểm tra truy vấn Distributed Join hoạt động bình thường.
3. Tắt Secure Node.
4. Thực hiện lại truy vấn:

```text
http://localhost:8081/public/customer?purchase=Laptop
```

Kết quả mong đợi:

* Public Node vẫn hoạt động.
* Lịch sử mua hàng vẫn được truy xuất.
* Thông tin nhận dạng khách hàng không thể truy xuất.
* Dữ liệu nhạy cảm tiếp tục được bảo vệ.

## Nội dung học thuật được minh họa trong đề tài

Dự án minh họa các khái niệm của môn Cơ sở dữ liệu phân tán:

* Phân mảnh dọc (Vertical Fragmentation)
* Tính đầy đủ (Completeness)
* Khả năng tái thiết dữ liệu (Reconstruction)
* Tính không chồng lắp thuộc tính (Disjointness)
* Thuộc tính liên kết (Linking Attribute)
* Xử lý truy vấn phân tán (Distributed Query Processing)
* Distributed Join
* Chi phí truyền thông giữa các node
* Khả năng chịu lỗi cơ bản của hệ thống phân tán

## Kết luận

PII Shield cho thấy việc áp dụng phân mảnh dọc có thể giúp tách biệt dữ liệu nhạy cảm khỏi dữ liệu giao dịch thông thường, từ đó giảm thiểu nguy cơ lộ thông tin cá nhân. Đồng thời hệ thống vẫn duy trì khả năng truy vấn thông qua cơ chế Distributed Join giữa các node trong môi trường cơ sở dữ liệu phân tán.
