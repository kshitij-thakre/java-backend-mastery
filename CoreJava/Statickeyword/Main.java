package CoreJava.Statickeyword;

class Calculator {
      static int add(int a, int b) {
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) {

        int result = Calculator.add(10, 20);

        System.out.println(result);
    }
}