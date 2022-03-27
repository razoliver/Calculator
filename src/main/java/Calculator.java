import java.util.Stack;

/**
 * Constraints:
 *      Supports just a single type of parenthesis - '()'
 *      Doesn't support minus sign before a number - 5 + -2 is Illegal. To overcome this, use 5 + 0 - 2.
 *      Doesn't support square/power/trigonometric functions
 */
class Calculator {

    public Calculator() {
    }

    public double calculate(String mathExpression) {
        if (!isExpressionValid(mathExpression)) {
            throw new RuntimeException("Equation not valid");
        }
        // Converting string to char array in order to use indexes instead of wasteful substrings on each recursive call
        char[] mathExpressionCharArray = mathExpression.toCharArray();
        return calculate(mathExpressionCharArray, 0,mathExpressionCharArray.length);
    }

    private double calculate(char[] mathExpression, int startIndex, int endIndex) {
        Stack<Double> integerStack = new Stack<>();
        return calculate(mathExpression, startIndex, endIndex, integerStack);
    }

    private double calculate(char[] mathExpression, int startIndex, int endIndex, Stack<Double> stack) {

        double currNum = 0;
        char lastOperator = '+';

        for (int i=startIndex;i<endIndex;i++) {
            char currChar = mathExpression[i];

            if (Character.isDigit(currChar)) {
                currNum = 10.0 * currNum + currChar - '0';
            }

            if (currChar == '(') {
                int endIndexOfParenthesisBlock = getEndIndexOfParenthesisBlock(mathExpression, i, endIndex);
                currNum = calculate(mathExpression, i+1, endIndexOfParenthesisBlock);
                i = endIndexOfParenthesisBlock;
            }

            if (currChar == '+' || currChar == '-' || currChar == '*' || currChar == '/' || i == endIndex - 1) {
                switch (lastOperator) {
                    case '+' -> stack.push(currNum);
                    case '-' -> stack.push(currNum * -1);
                    case '*' -> stack.push(stack.pop() * currNum);
                    case '/' -> stack.push(stack.pop() / currNum);
                }
                lastOperator = currChar;
                currNum = 0;
            }
        }
        return stack.stream().mapToDouble(Double::doubleValue).sum();
    }

    private int getEndIndexOfParenthesisBlock(char[] mathExpression, int currIndex, int endIndex) {
        int i;
        int numOfParenthesis = 1;
        for (i = currIndex + 1; i < endIndex; i++) {
            if (mathExpression[i] == '(') ++numOfParenthesis;
            if (mathExpression[i] == ')') --numOfParenthesis;
            if (numOfParenthesis == 0) break;
        }
        return i;
    }

    private boolean isExpressionValid(String mathExpression) {

        if (mathExpression == null || mathExpression.isEmpty()) {
            return false;
        }
        Stack<Character> parenthesisStack = new Stack<>();
        boolean isLastCharOperator = false;
        boolean isLastCharOpenParenthesis = false;

        for (int i=0;i<mathExpression.length();i++) {
            char currChar = mathExpression.charAt(i);

            if (Character.isWhitespace(currChar)) {
                continue;

            } else if (currChar == ')') {
                if (parenthesisStack.isEmpty() || isLastCharOperator) {
                    return false;
                }
                parenthesisStack.pop();

            } else if (currChar == '(') {
                parenthesisStack.push(currChar);
                isLastCharOpenParenthesis = true;
                continue;

            } else if (isValidOperator(currChar)){
                if (isLastCharOperator || isLastCharOpenParenthesis) return false;
                isLastCharOperator = true;
                continue;

            } else if(!Character.isDigit(currChar)) {
                return false;
            }

            isLastCharOperator = false;
            isLastCharOpenParenthesis = false;
        }

        return !isLastCharOperator && !isLastCharOpenParenthesis && parenthesisStack.isEmpty();
    }

    private boolean isValidOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}