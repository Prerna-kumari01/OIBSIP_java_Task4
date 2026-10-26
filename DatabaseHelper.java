import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:exam.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS student_profile ("
                + "id INTEGER PRIMARY KEY CHECK (id = 1), "
                + "student_name TEXT NOT NULL, "
                + "username TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL"
                + ")";

        try (Connection con = connect();
             Statement stmt = con.createStatement()) {

            stmt.execute(sql);
            System.out.println("Database connected successfully!");
            System.out.println("Student profile table ready!");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    public static void createResultTable() {
    String sql = "CREATE TABLE IF NOT EXISTS exam_results ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "student_name TEXT NOT NULL, "
            + "score INTEGER NOT NULL, "
            + "correct_answers INTEGER NOT NULL, "
            + "incorrect_answers INTEGER NOT NULL, "
            + "unanswered INTEGER NOT NULL, "
            + "time_taken TEXT NOT NULL, "
            + "exam_date TEXT NOT NULL"
            + ")";

    try (Connection con = connect();
         java.sql.Statement stmt = con.createStatement()) {

        stmt.execute(sql);
        System.out.println("Exam results table ready!");

    } catch (SQLException e) {
        System.out.println("Result table error: " + e.getMessage());
    }
}
public static void saveResult(
        String studentName,
        int score,
        int correct,
        int incorrect,
        int unanswered,
        String timeTaken) {

    String sql = "INSERT INTO exam_results "
            + "(student_name, score, correct_answers, "
            + "incorrect_answers, unanswered, time_taken, exam_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = connect();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, studentName);
        ps.setInt(2, score);
        ps.setInt(3, correct);
        ps.setInt(4, incorrect);
        ps.setInt(5, unanswered);
        ps.setString(6, timeTaken);
        ps.setString(7, java.time.LocalDateTime.now().toString());

        ps.executeUpdate();

        System.out.println("Exam result saved successfully!");

    } catch (SQLException e) {
        System.out.println("Result save error: " + e.getMessage());
    }
}
public static String getPreviousResults(String studentName) {
    String sql = "SELECT score, correct_answers, incorrect_answers, "
            + "unanswered, time_taken, exam_date "
            + "FROM exam_results WHERE student_name = ? "
            + "ORDER BY id DESC";

    StringBuilder output = new StringBuilder();

    try (Connection con = connect();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, studentName);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                output.append("Score: ")
                    .append(rs.getInt("score"))
                    .append("\nCorrect: ")
                    .append(rs.getInt("correct_answers"))
                    .append("\nIncorrect: ")
                    .append(rs.getInt("incorrect_answers"))
                    .append("\nUnanswered: ")
                    .append(rs.getInt("unanswered"))
                    .append("\nTime: ")
                    .append(rs.getString("time_taken"))
                    .append("\nDate: ")
                    .append(rs.getString("exam_date"))
                    .append("\n-------------------------\n");
            }
        }

    } catch (SQLException e) {
        return "Error loading results: " + e.getMessage();
    }

    if (output.length() == 0) {
        return "No previous results found.";
    }

    return output.toString();
}
public static void saveProfile(
        String name, String username, String password) {

    String sql = "INSERT INTO student_profile "
            + "(id, student_name, username, password) "
            + "VALUES (1, ?, ?, ?) "
            + "ON CONFLICT(id) DO UPDATE SET "
            + "student_name = excluded.student_name, "
            + "username = excluded.username, "
            + "password = excluded.password";

    try (Connection con = connect();
         java.sql.PreparedStatement ps =
             con.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.executeUpdate();

        System.out.println("Profile saved successfully!");

    } catch (SQLException e) {
        System.out.println("Save error: " + e.getMessage());
    }
}

public static String[] loadProfile() {
    String sql = "SELECT student_name, username, password "
            + "FROM student_profile WHERE id = 1";

    try (Connection con = connect();
         Statement stmt = con.createStatement();
         java.sql.ResultSet rs = stmt.executeQuery(sql)) {

        if (rs.next()) {
            return new String[] {
                rs.getString("student_name"),
                rs.getString("username"),
                rs.getString("password")
            };
        }

    } catch (SQLException e) {
        System.out.println("Load error: " + e.getMessage());
    }

    return null;
}
    public static void main(String[] args) {
    createTable();
    createResultTable();
}
}