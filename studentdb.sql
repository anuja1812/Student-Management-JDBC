create database studentdb;
use studentdb;
create table students(
id int primary key auto_increment,
name varchar(100) not null,
age int not null,
grade varchar(10) not null);