package fitnesstracker;

import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.LocalDateTime;



public class Part1 {

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
}
