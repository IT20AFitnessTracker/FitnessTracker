package fitnesstracker;

import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.LocalDateTime;



public class FitnessTracker {

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

        if (Part1.users.containsKey(user)) {
            JOptionPane.showMessageDialog(null, "Username already exists.");
            return;
        }

        String pass = JOptionPane.showInputDialog("Enter password:");

        if (pass == null || pass.isEmpty()) return;

        Part1.users.put(user, pass);
        Part1.sessionHistory.put(user, new StringBuilder());

        JOptionPane.showMessageDialog(null, "Account created!");
    }

    protected static void login() {
        String user = JOptionPane.showInputDialog("Username:");

        if (user == null) return;

        String pass = JOptionPane.showInputDialog("Password:");

        if (pass == null) return;

        if (Part1.users.containsKey(user) && Part1.users.get(user).equals(pass)) {
            Part1.loggedIn = true;
            Part1.currentUser = user;
            JOptionPane.showMessageDialog(null, "Welcome, " + user + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login.");
        }
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
                """.formatted(Part1.currentUser);

        String input = JOptionPane.showInputDialog(null, menu);

        if (input == null) return;

        switch (input) {
            case "1" -> Part4.timerScreen("Cardio");
            case "2" -> Part4.timerScreen("Strength");
            case "3" -> Part4.timerScreen("Yoga");
            case "4" -> Part4.customWorkout();
            case "5" -> Part4.showHistory();
            case "6" -> {
                Part1.loggedIn = false;
                Part1.currentUser = "";
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
        if (!Part1.isRunning) {
            Part1.startTime = LocalDateTime.now();
            Part1.isRunning = true;
            JOptionPane.showMessageDialog(null, "Timer started!");
        } else {
            JOptionPane.showMessageDialog(null, "Timer already running.");
        }
    }

