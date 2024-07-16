# Student Management System Documentation

In this example, a new student can be created by entering the relevant student information and all information about the student can be updated according 
to the student number. At the same time, if more than one grade is entered for the same course while updating, 
the average of these grades will be taken and this updated grade will be kept in the database. If a single note is entered, 
it will be saved as normal. In this system, only two endpoints, `create` and `update`, are allowed. Additionally, 
there are no user restrictions. "create" and "update" can be done by anyone with the right link and the right endpoint. 
Finally, a database connection is required for relevant use. If you are going to use MySQL for this, please 
follow the "src/main/resources" path in the project and follow the sections under "application.properties". If you are going 
to use another database, please check the documentation for the database you want to use and follow the steps.

&nbsp;

> [!NOTE]  
> Postman was used when giving relevant examples. How to use it will be examined under each heading.

&nbsp;

## Endpoints and Connections to Use
| Endpoint        | Link           | Method  |
| ------------- |-------------| -----|
| create      | http://localhost:8080/services/create | `POST` |
| update      |  http://localhost:8080/services/update/{stdNumber}     |   `PUT` |

&nbsp;

## Create a Student with example input JSON
> [!NOTE]  
> In this section, a single grade will be entered for each course. There will be only one grade information for each course.

JSON Input
```JSON
{
  "name": "Ahmet",
  "surname": "Yilmaz",
  "stdNumber": "B012X00033",
  "grades": [
    {
      "code": "MT101",
      "value": 50
    },
    {
      "code": "PH101",
      "value": 87
    },
    {
      "code": "CH101",
      "value": 60
    },
    {
      "code": "HS101",
      "value": 65
    },
    {
      "code": "HS101",
      "value": 65
    }
  ]
}
```
Output

```
Status: 200 OK

Student Created Successfully
```
## Update a Student with given Student Number

JSON Input
```JSON
{
  "name": "Ahmet",
  "surname": "Yilmaz",
  "stdNumber": "B012X00033",
  "grades": [
    {
      "code": "MT101",
      "value": 40
    },
    {
      "code": "PH101",
      "value": 90
    },
    {
      "code": "CH101",
      "value": 100
    },
    {
      "code": "HS101",
      "value": 80
    },
    {
      "code": "HS101",
      "value": 75
    }
  ]
}
```
```
Status: 200 OK

Student updated Successfully

(When the same query is asked again, the average of the grades in the relevant course codes
will be taken and this current value will be kept in the database.)
```
&nbsp;

## SQL Codes for Project

You can create a new database and two tables connected to it by entering the following code in a new tab on MySQL Workbench.

> [!WARNING]  
> Please make sure to establish the connection between the project and the database before applying these.
```sql

CREATE DATABASE  IF NOT EXISTS `student`;
USE `student`;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stdNumber VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL
);

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS grades;

CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    value INT NOT NULL,
    student_id BIGINT,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

```
