import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String exp;
        do {
            Scanner input = new Scanner(System.in);
            exp = input.nextLine();
            float result = calculator.evaluate(exp);
            System.out.println(result);
        } while (calculator.expIsValid(exp));
    }
}
