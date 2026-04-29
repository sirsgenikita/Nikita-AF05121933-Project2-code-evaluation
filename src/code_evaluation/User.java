package code_evaluation;

import java.sql.*;

public class User {
    private int userId;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Register
    public void register() throws Exception {
        Connection con = Dbconnection.getConnection();
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ps.executeUpdate();
        System.out.println("User Registered!");
        con.close();
    }

    // Login
    public boolean login() throws Exception {
        Connection con = Dbconnection.getConnection();
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            userId = rs.getInt("user_id");
            con.close();
            return true;
        }

        con.close();
        return false;
    }

    public int getUserId() {
        return userId;
    }
}