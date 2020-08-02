-- drop tables if they already exist
DROP TABLE IF EXISTS user_group_link;
DROP TABLE IF EXISTS document_info;
DROP TABLE IF EXISTS group_info;
DROP TABLE IF EXISTS user_info;



--create and populate User_Info table
CREATE TABLE user_info (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email_address VARCHAR(100) NOT NULL,
  created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO user_info (first_name, last_name, email_address) VALUES ('Niall', 'O Connor' ,'noconnor@visiblethread.com');
INSERT INTO user_info (first_name, last_name, email_address) VALUES ('Jason', 'O Connell' ,'joconnell@visiblethread.com');
INSERT INTO user_info (first_name, last_name, email_address) VALUES ('Eoin ', 'Wren' ,'ewren@visiblethread.com');



--create and populate Group_Info table
CREATE TABLE group_info (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  group_name VARCHAR(50) NOT NULL,
  created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
 
INSERT INTO group_info (group_name) VALUES ('Admin');
INSERT INTO group_info (group_name) VALUES ('Employee');



--create and populate User_Group_Link table 
CREATE TABLE user_group_link (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
  group_id INT NOT NULL,
  join_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  foreign key (user_id) references user_info(id),
  foreign key (group_id) references group_info(id)
);
 
INSERT INTO user_group_link (user_id, group_id) VALUES (
	(select id from user_info where email_address = 'joconnell@visiblethread.com'),
	(select id from group_info where group_name = 'Admin')
);
INSERT INTO user_group_link (user_id, group_id) VALUES (
	(select id from user_info where email_address = 'joconnell@visiblethread.com'),
	(select id from group_info where group_name = 'Employee')
);
INSERT INTO user_group_link (user_id, group_id) VALUES (
	(select id from user_info where email_address = 'ewren@visiblethread.com'),
	(select id from group_info where group_name = 'Employee')
);



--create Document_Info table 
CREATE TABLE document_info (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  file_name VARCHAR(100) NOT NULL,
  word_count INT NOT NULL,
  data CLOB NOT NULL,
  user_id INT NOT NULL,
  created_on TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  foreign key (user_id) references user_info(id)
);
