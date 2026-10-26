Online Examination System

1. Project Overview

The Online Examination System is a Java-based desktop application developed using Java Swing and SQLite. It allows students to log in, update their profiles, take an examination, and view their previous examination results.

2. Objectives

- To provide a simple interface for online examinations.
- To allow students to manage their profiles.
- To conduct examinations with a timer.
- To automatically submit the examination when the time expires.
- To save examination results in a database.
- To allow students to view their previous results.

3. Tools and Technologies Used

- Programming Language: Java
- GUI: Java Swing
- Database: SQLite
- Database Connectivity: JDBC
- IDE: Visual Studio Code
- JDK: Java Development Kit

4. Steps Performed

1. Set up the Java development environment in VS Code.
2. Added the SQLite JDBC driver.
3. Created database tables for student profiles and examination results.
4. Developed the student profile and login functionality.
5. Created the main menu for examination-related operations.
6. Implemented the examination interface and timer.
7. Added automatic submission when the timer expires.
8. Implemented result storage and previous-result viewing.
9. Tested the main application features.

5. Main Features

- Student login
- Update student profile
- Start examination
- Timed examination
- Automatic submission
- Save examination results
- View previous results
- Logout

6. How to Run the Project

1. Install the JDK and Visual Studio Code.
2. Open the project folder in VS Code.
3. Ensure the SQLite JDBC driver is available in the "lib" folder.
4. Ensure the database helper class and main application files are present.
5. Compile and run the application using the appropriate classpath and main class.

Example commands for Windows, if the main class is "onlineexaminationsystem":

javac -cp ".;lib\sqlite-jdbc-3.53.4.0.jar" onlineexaminationsystem.java
java -cp ".;lib\sqlite-jdbc-3.53.4.0.jar" onlineexaminationsystem

Note: The commands assume the source filename and main class name match the example. Adjust them if your project uses different names.

7. Outcomes

The application provides a graphical interface for student login, profile management, timed examinations, automatic submission, and result storage. Students can also access their previously saved examination results.

8. Conclusion

The Online Examination System demonstrates the use of Java Swing and SQLite for developing a desktop-based examination application. It combines basic student management, timed tests, and database-backed result handling in one system.