CREATE database crmTest CHARACTER SET utf8 COLLATE utf8_general_ci;
use crmTest;

CREATE TABLE Users (
    id INT AUTO_INCREMENT,
    email VARCHAR(255),
    pwd VARCHAR(25),
    firstName NVARCHAR(255),
    lastName NVARCHAR(255),
    fullName NVARCHAR(255),
    userName VARCHAR(255),
    phone VARCHAR(11),
    id_role INT,
    image VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE Role (
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    description TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE Project (
    id INT AUTO_INCREMENT,
    name NVARCHAR(255),
    startDate DATE,
    endDate DATE,
    PRIMARY KEY (id)
);

CREATE TABLE Status (
    id INT AUTO_INCREMENT,
    name NVARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE Job (
    id INT AUTO_INCREMENT,
    name NVARCHAR(255),
    content NVARCHAR(255),
    startDate DATE,
    endDate DATE,
    id_project INT,
    id_user INT,
    id_status INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_project) REFERENCES Project (id),
    FOREIGN KEY (id_user) REFERENCES Users (id),
    FOREIGN KEY (id_status) REFERENCES Status (id)
);
ALTER TABLE Users ADD CONSTRAINT FK_id_role_Users FOREIGN KEY (id_role) REFERENCES Role(id);

INSERT INTO Role (name, description) VALUES ('Admin', 'Administrator role');
INSERT INTO Role (name, description) VALUES ('User', 'Regular user role');

INSERT INTO Users (email, pwd, firstName, lastName, fullName, userName, phone, id_role, image) 
VALUES ('admin@example.com', 'admin123', 'Admin', 'Admin', 'Admin Admin', 'admin', '1234567890', 1, 'admin.jpg');
INSERT INTO Users (email, pwd, firstName, lastName, fullName, userName, phone, id_role, image) 
VALUES ('user@example.com', 'user123', 'User', 'User', 'User User', 'user', '9876543210', 2, 'user.jpg');

INSERT INTO Project (name, startDate, endDate) VALUES ('Project A', '2023-01-01', '2023-12-31');
INSERT INTO Project (name, startDate, endDate) VALUES ('Project B', '2023-02-01', '2023-11-30');

INSERT INTO Status (name) VALUES ('In Progress');
INSERT INTO Status (name) VALUES ('Completed');

INSERT INTO Job (name, content, startDate, endDate, id_project, id_user, id_status) 
VALUES ('Task 1', 'Complete task 1', '2023-03-01', '2023-03-10', 1, 1, 1);
INSERT INTO Job (name, content, startDate, endDate, id_project, id_user, id_status) 
VALUES ('Task 2', 'Complete task 2', '2023-04-01', '2023-04-15', 1, 1, 2);

SELECT *from Users u;
select * from `Role` r;
SELECT * from Job j;
 
SELECT * from Status s;


SELECT 
	j.id,
    j.name,
    p.name AS project_name,
    u.fullName  AS performer,
    j.startDate AS start_date,
    j.endDate AS end_date
FROM Job j
JOIN Project p ON j.id_project = p.id
JOIN Users u ON j.id_user = u.id
where j.id = 1;

UPDATE Job SET name = "Hello" , startDate = '2023-01-04', endDate = '2024-01-04', id_user = 1 ,
				id_project  = 2 , id_status  = 1 WHERE id = 1;

select u.id, u.firstName, u.lastName, u.userName, r.name from Users u join Role r ON u.id_role = r.id
