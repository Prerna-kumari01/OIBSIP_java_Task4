Online Examination System — Project Documentation

1. Introduction

The Online Examination System is a Java-based desktop application developed using Java Swing and SQLite. It is designed to provide students with a simple interface for logging in, managing their profiles, attempting examinations, and viewing previous examination results.

2. Project Objectives

The main objectives of this project are:

- To develop a user-friendly examination application.
- To provide student login and profile management.
- To conduct examinations within a specified time limit.
- To implement automatic submission when the examination timer expires.
- To store examination results in an SQLite database.
- To provide access to previously saved examination results.

3. System Requirements

Software Requirements

- Operating System: Windows
- Programming Language: Java
- Java Development Kit (JDK)
- IDE: Visual Studio Code
- Database: SQLite
- Database Connectivity: JDBC
- GUI Framework: Java Swing
- SQLite JDBC Driver

Hardware Requirements

- A computer or laptop
- Sufficient memory and storage to run the application
- Keyboard and display

4. System Architecture

The application consists of three main components:

4.1 User Interface

Java Swing provides the graphical interface for login, profile management, examination questions, timer display, and result viewing.

4.2 Application Logic

Java handles user interactions, examination flow, timer functionality, answer selection, and result generation.

4.3 Database Layer

SQLite stores student profile information and examination results. JDBC connects the Java application to the database.

5. Modules and Functionality

5.1 Student Login

Allows students to access the application using their login credentials.

5.2 Profile Management

Provides functionality for viewing and updating student profile information.

5.3 Main Menu

Provides access to the available examination operations, including starting an examination, updating a profile, viewing previous results, and logging out.

5.4 Examination Module

Displays examination questions and answer options through the graphical interface.

5.5 Timer and Automatic Submission

A timer tracks the examination duration. When the allotted time expires, the examination is submitted automatically.

5.6 Result Management

Examination results are saved in the database and can be viewed later through the previous-results feature.

6. Database Implementation

SQLite is used as the database system. The DatabaseHelper class manages database connectivity and the creation of the required tables.

The database stores:

- Student profile information.
- Examination result records.

JDBC is used to execute database operations from the Java application.

7. Implementation Steps

1. Set up the Java development environment in Visual Studio Code.
2. Added the SQLite JDBC driver to the project.
3. Created the database helper class.
4. Implemented database tables for student profiles and examination results.
5. Developed student login and profile management.
6. Created the main menu and examination interface.
7. Implemented the examination timer.
8. Added automatic submission when the timer expires.
9. Implemented examination result storage and previous-result viewing.
10. Tested the implemented application features.

8. Testing

The application was tested to verify its main functionality.

The testing process included:

- Checking application startup and login.
- Checking profile update functionality.
- Starting and attempting an examination.
- Verifying timer operation and automatic submission.
- Checking examination result storage.
- Viewing previously saved results.

9. Outcome

The project demonstrates a desktop-based examination system with student login, profile management, timed examinations, automatic submission, and database-backed result handling.

The application also provides a way to access previously saved examination results.

10. Conclusion

The Online Examination System demonstrates how Java Swing, SQLite, and JDBC can be combined to develop a desktop-based examination application.

This project provides practical experience in Java programming, graphical user interface development, database connectivity, data persistence, and timer-based application logic.