package code_evaluation;

import java.util.Scanner;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            User user = null;
            boolean loggedIn = false;

            while (true) {
                System.out.println("\n====== ONLINE CODE SYSTEM ======");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. View Problem");
                System.out.println("4. Submit Code");
                System.out.println("5. View Submission History");
                System.out.println("6. Logout");
                System.out.println("7. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // 1️⃣ REGISTER
                    case 1:
                        System.out.print("Enter Username: ");
                        String ru = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String rp = sc.nextLine();

                        User newUser = new User(ru, rp);
                        newUser.register();
                        break;

                    // 2️⃣ LOGIN
                    case 2:
                        System.out.print("Enter Username: ");
                        String lu = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String lp = sc.nextLine();

                        user = new User(lu, lp);

                        if (user.login()) {
                            loggedIn = true;
                            System.out.println("Login Successful!");
                        } else {
                            System.out.println("Invalid Credentials!");
                        }
                        break;

                    // 3️⃣ VIEW PROBLEM
                    case 3:
                        System.out.print("Enter Problem ID: ");
                        int pidView = sc.nextInt();
                        sc.nextLine();

                        Connection con1 = Dbconnection.getConnection();
                        String sql1 = "SELECT * FROM problems WHERE problem_id=?";
                        PreparedStatement ps1 = con1.prepareStatement(sql1);
                        ps1.setInt(1, pidView);

                        ResultSet rs1 = ps1.executeQuery();

                        if (rs1.next()) {
                            System.out.println("\nTitle: " + rs1.getString("title"));
                            System.out.println("Description: " + rs1.getString("description"));
                        } else {
                            System.out.println("Problem not found!");
                        }
                        con1.close();
                        break;

                    // 4️⃣ SUBMIT CODE + EVALUATE + RESULT
                    case 4:
                        if (!loggedIn) {
                            System.out.println("Please login first!");
                            break;
                        }

                        System.out.print("Enter Problem ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();

                        Problem problem = new Problem(pid);
                        String expectedOutput = problem.fetchExpectedOutput();

                        System.out.print("Enter your code output: ");
                        String code = sc.nextLine();

                        Submission sub = new Submission(user.getUserId(), pid, code);
                        sub.evaluate(expectedOutput);
                        sub.save();

                        System.out.println("Result: " + sub.getStatus());
                        break;

                    // 5️⃣ VIEW SUBMISSION HISTORY
                    case 5:
                        if (!loggedIn) {
                            System.out.println("Please login first!");
                            break;
                        }

                        Connection con2 = Dbconnection.getConnection();
                        String sql2 = "SELECT * FROM submissions WHERE user_id=?";
                        PreparedStatement ps2 = con2.prepareStatement(sql2);
                        ps2.setInt(1, user.getUserId());

                        ResultSet rs2 = ps2.executeQuery();

                        System.out.println("\n--- Submission History ---");
                        while (rs2.next()) {
                            System.out.println("Problem ID: " + rs2.getInt("problem_id"));
                            System.out.println("Code: " + rs2.getString("code"));
                            System.out.println("Output: " + rs2.getString("output"));
                            System.out.println("Status: " + rs2.getString("status"));
                            System.out.println("------------------------");
                        }
                        con2.close();
                        break;

                    // 6️⃣ LOGOUT
                    case 6:
                        user = null;
                        loggedIn = false;
                        System.out.println("Logged out successfully!");
                        break;

                    // 7️⃣ EXIT
                    case 7:
                        System.out.println("Exiting system...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}