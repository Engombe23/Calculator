import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    private float currentValue;
    private float memval;
    private final ArrayList<Float> results = new ArrayList<>();
    private char[] tokens;
    private float history_results;

    public float evaluate(String expression){
        return userInput(expression);
    }

    public float getCurrentValue(){
        return currentValue;
    }

    private float userInput(String input) {
        switch (input) {
            case "m":
                memval = currentValue;
                break;
            case "mr": return getMemoryValue();
            case "c": clearMemory();
            case "h": return historyResults();
        }
        return calculate(input);
    }

    public float getMemoryValue() {
        return memval;
    }

    public void setMemoryValue(float memval) {
        this.memval = memval;
    }

    public void clearMemory(){
        setMemoryValue(0);
    }

    public float getHistoryValue(int index){
        float historyValue = 0;
        for (int i = 0; i < results.size(); i++)
            if (i == index) {
                historyValue = results.get(index);
                break;
            }
        return historyValue;
    }

    private float historyResults(){
        for (int i = 0; i < results.size(); i++)
            history_results = getHistoryValue(i);
        return history_results;
    }

    public boolean expIsValid(String expression){
        return expression.length() >= 5;
    }

    public float calculate(String expression) {
        if(expression.contains(".")){
            String[] item = expression.split(" ");

            String operator = item[1];

            float operand1 = Float.parseFloat(item[0]);
            float operand2 = Float.parseFloat(item[2]);

            switch (operator) {
                case "+" -> currentValue = operand1 + operand2;
                case "-" -> currentValue = operand1 - operand2;
                case "*" -> currentValue = operand1 * operand2;
                case "/" -> currentValue = operand1 / operand2;
            }
        } else {
            tokens = expression.toCharArray();

            Stack<Float> numbers = new Stack<>();
            Stack<Character> ops = new Stack<>();

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i] == ' ')
                    continue;

                if (isNumber(i)) {
                    StringBuilder stringB = new StringBuilder();

                    while (i < tokens.length && isNumber(i))
                        stringB.append(tokens[i++]);
                    numbers.push(Float.parseFloat(stringB.toString()));
                    i--;
                } else if (isOperator(i)) {
                    while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                        numbers.push(operators(ops, numbers));

                    ops.push(tokens[i]);
                }
            }
            while (!ops.empty())
                numbers.push(operators(ops, numbers));

            currentValue = numbers.pop();

        }

        results.add(currentValue);

        return getCurrentValue();
    }

    private boolean isNumber(int i){
        return tokens[i] >= '0' && tokens[i] <= '9';
    }

    private boolean isOperator(int i){
       return tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/';
    }
    
    private boolean hasPrecedence(char operator_1, char operator_2) {
        if (operator_2 == '(' || operator_2 == ')') return false;
        return (operator_1 != '*' && operator_1 != '/') || (operator_2 != '+' && operator_2 != '-');
    }

    private float operators(Stack<Character> ops, Stack<Float> values){
        return applyOp(ops.pop(), values.pop(), values.pop());
    }

    private float applyOp(char operator, float var2, float var1) {
        switch (operator) {
            case '+': return var1 + var2;
            case '-': return var1 - var2;
            case '*': return var1 * var2;
            case '/':
                if (var2 == 0) {
                    System.out.println("Invalid input");
                    return Float.MIN_VALUE;
                }
                return var1 / var2;
        }
        return 0;
    }
}