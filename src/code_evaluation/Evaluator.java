package code_evaluation;

public class Evaluator {

    public static String runCode(String code) {
        // Simulated execution
        return code.trim();
    }

    public static String evaluate(String output, String expected) {
        if (output.equals(expected))
            return "PASS";
        else
            return "FAIL";
    }
}