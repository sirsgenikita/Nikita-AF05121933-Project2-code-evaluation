package code_evaluation;

import java.sql.*;

public class Problem {
    private int problemId;
    private String expectedOutput;

    public Problem(int problemId) {
        this.problemId = problemId;
    }

    public String fetchExpectedOutput() throws Exception {
        Connection con = Dbconnection.getConnection();
        String sql = "SELECT expected_output FROM problems WHERE problem_id=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, problemId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            expectedOutput = rs.getString("expected_output");
        }

        con.close();
        return expectedOutput;
    }
}