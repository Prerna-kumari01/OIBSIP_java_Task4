import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class onlineexaminationsystem extends JFrame {

    String studentName = "Student";
    String username = "student";
    String password = "1234";
    {
    DatabaseHelper.createTable();
    DatabaseHelper.createResultTable();

    String[] savedProfile = DatabaseHelper.loadProfile();
    

    if (savedProfile != null) {
        studentName = savedProfile[0];
        username = savedProfile[1];
        password = savedProfile[2];
    }
}

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String[]> options = new ArrayList<>();
    ArrayList<Integer> correctAnswers = new ArrayList<>();
    ArrayList<Integer> selectedAnswers = new ArrayList<>();

    int currentQuestion = 0;
    int timeLeft = 30*60;
    long startTime;

    JLabel timerLabel;
    JLabel questionLabel;
    JRadioButton[] radioButtons = new JRadioButton[4];
    ButtonGroup buttonGroup = new ButtonGroup();

    Timer timer;
    boolean examRunning = false;
    boolean examFinished = false;

    public onlineexaminationsystem() {
        setTitle("Online Examination System");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
        if (examRunning) {
            int choice = JOptionPane.showConfirmDialog(
                onlineexaminationsystem.this,
                "Are you sure you want to quit the exam?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                saveAnswer();
                showResult();
            }
        } else {
            dispose();
        }
    }
});

        loadQuestions();
        showLogin();
        setVisible(true);
    }

    void loadQuestions() {
        addQuestion("Which language is used in this project?",
            new String[]{"Java", "HTML", "CSS", "SQL"}, 0);

        addQuestion("Which keyword creates an object?",
            new String[]{"class", "new", "void", "static"}, 1);

        addQuestion("Which method starts a Java program?",
            new String[]{"run()", "start()", "main()", "begin()"}, 2);

        addQuestion("Which Swing component creates a button?",
            new String[]{"JLabel", "JFrame", "JButton", "JTextField"}, 2);

        addQuestion("Which collection stores a list of items?",
            new String[]{"ArrayList", "Scanner", "JFrame", "Timer"}, 0);
    }

    void addQuestion(String q, String[] opts, int answer) {
        questions.add(q);
        options.add(opts);
        correctAnswers.add(answer);
        selectedAnswers.add(-1);
    }

    void showLogin() {
        examRunning = false;
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("ONLINE EXAMINATION"));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        
         loginButton.addActionListener(e -> {
    if (userField.getText().trim().equals(username)
        && new String(passField.getPassword()).equals(password)) {
        showMainMenu();
    } else {
        JOptionPane.showMessageDialog(this,
            "Invalid username or password!");
    }
});
        add(panel);
        revalidate();
        repaint();
    }
void showMainMenu() {
    getContentPane().removeAll();

    JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
    panel.setBorder(
        BorderFactory.createEmptyBorder(25, 100, 25, 100)
    );

    JLabel heading = new JLabel(
        "WELCOME, " + studentName,
        SwingConstants.CENTER
    );
    heading.setFont(new Font("Arial", Font.BOLD, 22));

    JButton startButton = new JButton("Start Exam");
    JButton profileButton = new JButton("Update Profile");
    JButton resultsButton = new JButton("View Previous Results");
    JButton logoutButton = new JButton("Logout");

    startButton.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Do you want to start the exam?",
            "Start Exam",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            startExam();
        }
    });

    profileButton.addActionListener(e -> showProfile());

    resultsButton.addActionListener(e -> {
        String results =
            DatabaseHelper.getPreviousResults(studentName);

        JTextArea area = new JTextArea(results, 15, 35);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
            this,
            new JScrollPane(area),
            "Previous Exam Results",
            JOptionPane.INFORMATION_MESSAGE
        );
    });

    logoutButton.addActionListener(e -> showLogin());

    panel.add(heading);
    panel.add(startButton);
    panel.add(profileButton);
    panel.add(resultsButton);
    panel.add(logoutButton);
    panel.add(new JLabel(
        "Online Examination System",
        SwingConstants.CENTER
    ));

    add(panel);
    revalidate();
    repaint();
}
    void showProfile() {
        JTextField nameField = new JTextField(studentName);
        JPasswordField passField = new JPasswordField(password);

       JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.add(new JLabel("Student Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New Password:"));
        panel.add(passField);

        int choice = JOptionPane.showConfirmDialog(this, panel,
            "Update Profile", JOptionPane.OK_CANCEL_OPTION);

        if (choice != JOptionPane.OK_OPTION) {
            showLogin();
            return;
        }

        String name = nameField.getText().trim();
        String newPassword = new String(passField.getPassword());

        if (name.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Name and password cannot be empty!");
            showProfile();
            return;
        }

        studentName = name;
        password = newPassword;
DatabaseHelper.saveProfile(
    studentName, username, password
);
       showMainMenu();
    }

    void startExam() {
        currentQuestion = 0;
        timeLeft = 30*60;
        examFinished = false;

        for (int i = 0; i < selectedAnswers.size(); i++) {
            selectedAnswers.set(i, -1);
        }

        startTime = System.currentTimeMillis();
        examRunning = true;

        getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(timerLabel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        center.add(questionLabel);
        center.add(Box.createVerticalStrut(20));

        buttonGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            radioButtons[i] = new JRadioButton();
            radioButtons[i].setFont(new Font("Arial", Font.PLAIN, 15));
            buttonGroup.add(radioButtons[i]);
            center.add(radioButtons[i]);
        }

        panel.add(center, BorderLayout.CENTER);

        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton submitButton = new JButton("Submit Exam");

        previousButton.addActionListener(e -> {
            saveAnswer();
            if (currentQuestion > 0) {
                currentQuestion--;
                displayQuestion();
            }
        });

        nextButton.addActionListener(e -> {
            saveAnswer();
            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                displayQuestion();
            }
        });

        submitButton.addActionListener(e -> {
            saveAnswer();
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to submit?",
                "Confirm Submission", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                showResult();
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(previousButton);
        buttons.add(nextButton);
        buttons.add(submitButton);
        panel.add(buttons, BorderLayout.SOUTH);

        add(panel);
        displayQuestion();

        timer = new Timer(1000, e -> {
            timeLeft--;
            updateTimer();

            if (timeLeft <= 0) {
                timer.stop();
                saveAnswer();
                showResult();
            }
        });

        updateTimer();
        timer.start();

        revalidate();
        repaint();
    }

    void displayQuestion() {
        questionLabel.setText("Question " + (currentQuestion + 1)
            + " of " + questions.size() + ": "
            + questions.get(currentQuestion));

        buttonGroup.clearSelection();

        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText(options.get(currentQuestion)[i]);
        }

        int selected = selectedAnswers.get(currentQuestion);

        if (selected >= 0) {
            radioButtons[selected].setSelected(true);
        }

        revalidate();
        repaint();
    }

    void saveAnswer() {
        for (int i = 0; i < 4; i++) {
            if (radioButtons[i].isSelected()) {
                selectedAnswers.set(currentQuestion, i);
                return;
            }
        }

        selectedAnswers.set(currentQuestion, -1);
    }

    void updateTimer() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;

        timerLabel.setText(String.format(
            "Time Left: %02d:%02d", minutes, seconds));
    }

    void showResult() {
        if (examFinished) return;

        examFinished = true;
        examRunning = false;

        if (timer != null) timer.stop();

        int correct = 0;
        int incorrect = 0;
        int unanswered = 0;

        StringBuilder details = new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {
            int selected = selectedAnswers.get(i);

            details.append("\nQ").append(i + 1).append(": ");

            if (selected == correctAnswers.get(i)) {
                correct++;
                details.append("Correct");
            } else if (selected == -1) {
                unanswered++;
                details.append("Not Answered");
            } else {
                incorrect++;
                details.append("Incorrect");
            }
        }

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        int minutes = (int) (elapsed / 60);
        int seconds = (int) (elapsed % 60);
String timeTaken = String.format("%02d:%02d", minutes, seconds);

DatabaseHelper.saveResult(
    studentName,
    correct,
    correct,
    incorrect,
    unanswered,
    timeTaken
);
        getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel heading = new JLabel("EXAM RESULT",
            SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(heading, BorderLayout.NORTH);

        JTextArea result = new JTextArea(
            "Student: " + studentName
            + "\nScore: " + correct + " / " + questions.size()
            + "\nCorrect: " + correct
            + "\nIncorrect: " + incorrect
            + "\nUnanswered: " + unanswered
            + String.format("\nTime Taken: %02d:%02d", minutes, seconds)
            + "\n\nQuestion Summary:" + details
        );

        result.setEditable(false);
        result.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JScrollPane(result), BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLogin());

        JPanel bottom = new JPanel();
        bottom.add(logoutButton);
        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new onlineexaminationsystem();
        });
    }
}
//javac -cp ".;lib\sqlite-jdbc-3.53.4.0.jar" DatabaseHelper.java onlineexaminationsystem.java
//java -cp ".;lib\sqlite-jdbc-3.53.4.0.jar" onlineexaminationsystem