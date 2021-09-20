

CREATE TABLE ORGANIZATION AS
SELECT *
FROM CSVREAD('classpath:data-organization.csv');
CREATE TABLE EMPLOYEE AS
SELECT *
FROM CSVREAD('classpath:data-employee.csv');

CREATE TABLE DEPARTMENT AS
SELECT *
FROM CSVREAD('classpath:data-department.csv');

CREATE TABLE BOOK AS
SELECT *
FROM CSVREAD('classpath:books.csv');