CREATE DATABASE layoutcrm;
USE layoutcrm;

CREATE TABLE Users(
	id int auto_increment,
	email varchar(255),
	pwd varchar(25),
	firstName nvarchar(255),
	lastName nvarchar(255),
	fullName nvarchar(255),
	userName varchar(255),
	phone varchar(11),
	id_role int,
	image varchar(255),
	
	primary key(id)
);

CREATE TABLE Role(
	id int auto_increment,
	name varchar(255),
	description text,
	
	primary key(id)
);

CREATE TABLE Project(
	id int auto_increment,
	name nvarchar(255),
	startDate date,
	endDate date,
	
	primary key(id)
);

CREATE TABLE Status(
	id int auto_increment,
	name nvarchar(255),
	
	primary key(id)
);

CREATE TABLE Job(
	id int auto_increment,
	name nvarchar(255),
	content nvarchar(255),
	startDate date,
	endDate date,
	id_project int,
	
	primary key(id)
);

CREATE TABLE Project_Users(
	id_user int,
	id_project int,
	createDate date,
	
	primary key(id_user, id_project)
);

CREATE TABLE Job_Status_Users(
	id_user int,
	id_status int,
	id_job int,
	createDate date,
	
	primary key(id_user, id_status, id_job)
);


ALTER TABLE Users 			 ADD CONSTRAINT FK_id_role_Users 				FOREIGN KEY (id_role) 		REFERENCES Role(id);
ALTER TABLE Job 			 ADD CONSTRAINT FK_id_project_Job 				FOREIGN KEY (id_project) 	REFERENCES Project(id);
ALTER TABLE Project_Users 	 ADD CONSTRAINT FK_id_user_Project_Users    	FOREIGN KEY (id_user) 		REFERENCES Users(id);
ALTER TABLE Project_Users 	 ADD CONSTRAINT FK_id_project_Project_Users 	FOREIGN KEY (id_project) 	REFERENCES Project(id);
ALTER TABLE Job_Status_Users ADD CONSTRAINT FK_id_user_Job_Status_Users    	FOREIGN KEY (id_user) 		REFERENCES Users(id);
ALTER TABLE Job_Status_Users ADD CONSTRAINT FK_id_status_Job_Status_Users  	FOREIGN KEY (id_status) 	REFERENCES Status(id);
ALTER TABLE Job_Status_Users ADD CONSTRAINT FK_id_job_Job_Status_Users     	FOREIGN KEY (id_job) 		REFERENCES Job(id);


INSERT INTO Users (fullname, email, pwd, phone , id_role)
values ('Nguynễ Vnă A', 'nguyenana@gmail.com', '123456', '09087346457', 1);


SELECT * FROM Users u 
select * from `Role` r 


insert INTO `Role` (name, description)
values ('test', 'day la role test');

-- Bơớc1: cần phải xác định tính năng sẽ làm trên màn hình
-- Bước 2: Xác định câu query giành cho tính năng là gì?
-- Bước 3: Test câu query đã xác định được ở database coi có chạy không
-- Bước 4: Tạo môt đường dẫn hiển thị giao diện cho người dùng
-- Bước 5: Xác định số lượng tham số nhận vào thông qua câu query và đường dẫn, phuong thức
-- sẽ nhận tham số đó ở servlet
-- Bước 6: Chuẩn bị câu query ở servlet
-- Bước 7: Mở kết nối tới CSDL
-- Bước 8: Truyền câu query đã chuẩn bị vào kết nối vừa mở và truyền giá trị cho tham số nếu có
-- Bước 9: Thực hiệnt truy vấn
-- Buơcs 10: Xử lý logic code dựa trên kết quả truy vấn được
