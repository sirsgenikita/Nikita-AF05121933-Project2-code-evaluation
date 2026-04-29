package code_evaluation;

import java.sql.*;

public class Submission {
    private int userId;
    private int problemId;
    private String code;
    private String output;
    private String status;

    public Submission(int userId, int problemId, String code) {
        this.userId = userId;
        this.problemId = problemId;
        this.code = code;
    }

    public void evaluate(String expectedOutput) {
        output = Evaluator.runCode(code);
        status = Evaluator.evaluate(output, expectedOutput);
    }

    public void save() throws Exception {
        Connection con = Dbconnection.getConnection();
        String sql = "INSERT INTO submissions(user_id, problem_id, code, output, status) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, problemId);
        ps.setString(3, code);
        ps.setString(4, output);
        ps.setString(5, status);

        ps.executeUpdate();
        System.out.println("Submission Saved!");
        con.close();
    }

    public String getStatus() {
        return status;
    }
}
