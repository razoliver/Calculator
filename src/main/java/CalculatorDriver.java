public class CalculatorDriver {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String[] expressionsArr = new String[]{
                " ((4 - 4/2 + 1) * 2 * ( 3 *  3 + 4 *4) / 2)    ",
                "3 + 4",
                "(1+5)",
                "1/5"
        };

        for (String expression : expressionsArr) {
            double ans = calculator.calculate(expression);
            System.out.println("For expression :" + expression);
            System.out.println("Answer: " + ans);
        }
    }
}