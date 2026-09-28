package Functions;

public class Main {
    public static void main(String[] args) {
        //Recursive functions
       /* printNum(5);
        printReverse(5);
        System.out.println(sum(5));
        System.out.println(factorial(5));
        countDown(5);*/
//        System.out.println(power(2,5));
        System.out.println(sumOfDigits(1234));

    }

    static void printNum(int n){
        if(n == 0) return;
        printNum(n-1);
        System.out.println(n);
    }

    static void printReverse(int n){
        if(n == 0) return;
        System.out.println(n);
        printReverse(n-1);
    }

    static int sum(int n){
        if(n == 0) return 0;
        int x = sum(n-1);
       return n+x;
    }

    static int factorial(int n){
        if(n == 0) return 1;
        int x = factorial(n-1);
        return x * n;
    }

    static void countDown(int n){
        if(n == -1) return;
        System.out.println(n);
        countDown(n-1);
    }

    static int power(int base, int exponent){
        if(exponent == 0) return 1;
        int x = power(base, exponent-1);
        return base * x;
    }

    static int sumOfDigits(int n){
        if(n == 0) return 0;
        int x = sumOfDigits(n /10);
        return (n%10)+x;
    }
}
