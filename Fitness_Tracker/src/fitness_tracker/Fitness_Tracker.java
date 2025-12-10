package fitnesstracker;

import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Fitness_Tracker {

    private static Map<String, String> users = new HashMap<>();
    private static Map<String, StringBuilder> sessionHistory = new HashMap<>();

    private static boolean loggedIn = false;
    private static String currentUser = "";

    private static LocalDateTime startTime;
    private static LocalDateTime pauseTime;
    private static boolean isRunning = false;

    public static void main(String[] args) {

        while (true) {

            if (!loggedIn) {
                authMenu();
            } else {
                mainMenu();
            }
        }
    }

    
    private static void authMenu() {

        String menu = """
                ------------------------------
                        FITNESS TRACKER
                ------------------------------

                [1] Sign Up
                [2] Log In
                [3] Exit

                Select option:
                """;

        String input = JOptionPane.showInputDialog(null, menu);

        if (input == null) return;

        switch (input) {
            case "1" -> signUp();
            case "2" -> login();
            case "3" -> System.exit(0);
            default -> JOptionPane.showMessageDialog(null, "Invalid choice.");
        }
    }

    private static void signUp() {
        String user = JOptionPane.showInputDialog("Enter new username:");

        if (user == null || user.isEmpty()) return;

        if (users.containsKey(user)) {
            JOptionPane.showMessageDialog(null, "Username already exists.");
            return;
        }

        String pass = JOptionPane.showInputDialog("Enter password:");

        if (pass == null || pass.isEmpty()) return;

        users.put(user, pass);
        sessionHistory.put(user, new StringBuilder());

        JOptionPane.showMessageDialog(null, "Account created!");
    }

    private static void login() {
        String user = JOptionPane.showInputDialog("Username:");

        if (user == null) return;

        String pass = JOptionPane.showInputDialog("Password:");

        if (pass == null) return;

        if (users.containsKey(user) && users.get(user).equals(pass)) {
            loggedIn = true;
            currentUser = user;
            JOptionPane.showMessageDialog(null, "Welcome, " + user + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login.");
        }
    }

   
    private static void mainMenu() {

        String menu = """
                ------------------------------
                        Welcome, %s!
                ------------------------------

                Choose Workout Type:
                1. Cardio
                2. Strength
                3. Yoga
                4. Custom Workout
                5. View History

                [ Logout = 6 ]
                ------------------------------
                """.formatted(currentUser);

        String input = JOptionPane.showInputDialog(null, menu);

        if (input == null) return;

        switch (input) {
            case "1" -> timerScreen("Cardio");
            case "2" -> timerScreen("Strength");
            case "3" -> timerScreen("Yoga");
            case "4" -> customWorkout();
            case "5" -> showHistory();
            case "6" -> {
                loggedIn = false;
                currentUser = "";
                JOptionPane.showMessageDialog(null, "Logged out!");
            }
            default -> JOptionPane.showMessageDialog(null, "Invalid option.");
        }
    }

    
    private static void customWorkout() {
        String w = JOptionPane.showInputDialog("Enter custom workout name:");

        if (w != null && !w.isEmpty()) {
            timerScreen(w);
        }
    }

    private static void timerScreen(String workout) {

        boolean exit = false;

        while (!exit) {

            String timerDisplay = """
                    ------------------------------
                    Workout: %s
                    Timer: %s
                    ------------------------------

                    [1] Start  
                    [2] Pause  
                    [3] Save Session  
                    [4] Back
                    """.formatted(workout, getTimerString());

            String input = JOptionPane.showInputDialog(null, timerDisplay);

            if (input == null) return;

            switch (input) {
                case "1" -> startTimer();
                case "2" -> pauseTimer();
                case "3" -> saveSession(workout);
                case "4" -> exit = true;
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

   
    private static void startTimer() {
        if (!isRunning) {
            startTime = LocalDateTime.now();
            isRunning = true;
            JOptionPane.showMessageDialog(null, "Timer started!");
        } else {
            JOptionPane.showMessageDialog(null, "Timer already running.");
        }
    }

    private static void pauseTimer() {
        if (isRunning) {
            pauseTime = LocalDateTime.now();
            isRunning = false;
            JOptionPane.showMessageDialog(null, "Timer paused!");
        } else {
            JOptionPane.showMessageDialog(null, "Timer is not running.");
        }
    }

    private static void stopTimer() {
        if (startTime == null) {
            JOptionPane.showMessageDialog(null, "Timer not started.");
            return;
        }

        pauseTime = LocalDateTime.now();
        isRunning = false;
        JOptionPane.showMessageDialog(null, "Timer stopped.");
    }

    private static String getTimerString() {
        if (startTime == null) return "00:00:00";

        LocalDateTime end = isRunning ? LocalDateTime.now() : pauseTime;

        long seconds = Duration.between(startTime, end).toSeconds();

        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    
    private static void saveSession(String workout) {

        if (startTime == null) {
            JOptionPane.showMessageDialog(null, "No session to save.");
            return;
        }

        String time = getTimerString();
        sessionHistory.get(currentUser)
                .append("Workout: ").append(workout)
                .append(" | Duration: ").append(time).append("\n");

        JOptionPane.showMessageDialog(null,
                "Session Saved!\nWorkout: " + workout + "\nTime: " + time);

        startTime = null;
        pauseTime = null;
        isRunning = false;
    }

    
    private static void showHistory() {
        StringBuilder history = sessionHistory.get(currentUser);

        if (history.length() == 0) {
            JOptionPane.showMessageDialog(null, "No sessions saved yet.");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "=== SESSION HISTORY ===\n\n" + history);
    }
}
