# CRM_Project_BC02
Yêu cầu hệ thống
Xây dựng hệ thống CRM quản lý công việc nhân viên công ty với các yêu cầu sau:
Hệ thống cho phép quản trị hệ thống (ADMIN) đăng nhập và thêm mới, sửa, xóa, xem thông tin, cấp quyền cho nhân viên.
Hệ thống đảm bảo cho “quản lý dự án” (LEADER) có thể dễ dàng đăng nhập thêm mới, sửa, xóa, xem thông tin dự án. Đồng thời có thể thêm nhân viên vào dự án và phân công công việc cho từng nhân viên thuộc dự án.
Hệ thống cũng cho phép “quản lý dự án” có thể theo dõi các thống kê về tiến độ công việc của từng nhân viên trong mỗi dự án.
Hệ thống cho phép nhân viên đăng nhập với tư cách thành viên, cập nhật tiến độ công việc. Xem các thống kê về tiến độ của các việc đã và đang thực hiện.
Hệ thống cũng cho phép quản trị hệ thống xem các thống kê về tiến độ các dự án.
Phân tích hệ thống
Loại người dùng (quyền)
Quản trị hệ thống (Admin): Người dùng có toàn quyền đối với hệ thống, có thể thêm, sửa, xóa, xem thông tin và cấp quyền cho các thành viên khác, xem thống kê về tất cả các dự án.
Quản lý dự án (Leader): Xem danh sách nhân viên của công ty, thêm, sửa, xóa, xem thông tin dự án do mình quản lý, ngoài ra có thể thêm hoặc loại bỏ người dùng là nhân viên thường vào dự án, phân công công việc và xem thống kê về dự án thuộc về mình.
Nhân viên (Member): Người dùng chỉ có quyền xem các công việc được giao trong mỗi dự án, cập nhật tiến độ công việc được giao, xem thống kê tiến độ các dự án của bản thân.
Các module:
Quản lý quyền: Các thông tin như tên quyền, mô tả
Quản lý người dùng: Các thông tin như email, mật khẩu, họ tên, địa chỉ, số điện thoại, loại thành viên (quyền).
Quản lý dự án: Các thông tin như tên dự án, mô tả, ngày bắt đầu, ngày kết thúc, mã người dùng (người tạo dự án).
Quản lý công việc: Các thông tin như tên công việc, mô tả, ngày bắt đầu, ngày kết thúc, mã người dùng(người thực hiện), mã dự án (thuộc dự án nào), mã trạng thái (chưa bắt đầu, đang thực hiện, đã hoàn thành).
Use case
Quản trị viên (admin): Quản lý tất cả thành viên Quản lý tất cả nhóm việc Quản lý quyền người dùng Quản lý tất cả các dự án Đăng nhập hệ thống Quản lý tài khoản cá nhân
Quản lý dự án (leader): Quản lý dự án Quản lý thành viên dự án Xem danh sách tất cả nhân viên Đăng nhập hệ thống Quản lý tài khoản cá nhân
Thành viên (member) Đăng nhập hệ thống Cập nhật tiến độ công việc Xem thống kê công việc (cá nhân) Quản lý tài khoản cá nhân

*ERD
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/d863bc84-4056-43c2-84dc-238b6ebf3398)

*Thêm mới thành viên
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/5dd579e2-bf9c-46ef-8b24-c07375f80ad8)

*Danh sách thành viên
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/8e9876da-6a2a-440b-a87b-0a07147ebe09)

*Thêm mới công việc
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/1701bc6d-2b35-4d1a-acf2-206e2876815d)

*Danh sách công việc 
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/2f0595b7-fda7-4ac3-87b1-273c7d853444)

*Dashboard
![image](https://github.com/idiotman-2212/CRM_Project_BC02/assets/82036270/009ff3a4-c3bf-409e-a813-2810afebe42e)

- CÔNG NGHỆ SỬ DỤNG:
  * Eclipse IDE for Enterprise Java and Web Developers
  * DBeaver
  * Docker
  * Tomcat v9.0 Server
  




