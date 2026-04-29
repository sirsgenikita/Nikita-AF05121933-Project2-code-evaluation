package code_evaluation;
import java.sql.*;

	public class Dbconnection {
	    static final String URL = "jdbc:mysql://localhost:3306/code_system";
	    static final String USER = "root";
	    static final String PASS = "1234";

	    public static Connection getConnection() throws Exception {
	        return DriverManager.getConnection(URL, USER, PASS);
	    }
	}


