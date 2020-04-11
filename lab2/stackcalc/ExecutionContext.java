package stackcalc;

import java.util.HashMap;
import java.util.Stack;

public class ExecutionContext {
    final private Stack<Double> stack = new Stack<>();
    final private HashMap<String, Double> parameters = new HashMap<>();

    public Stack<Double> getStack() {
        return stack;
    }

    public HashMap<String, Double> getParameters() {
        return parameters;
    }

    public void clear() {
        stack.clear();
        parameters.clear();
    }
}
