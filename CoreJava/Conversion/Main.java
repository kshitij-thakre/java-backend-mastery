package Conversion;

public class Main {
    public static void main(String[] args){
      //Implicit conversion
        byte b = 12;
        int i = b;  //=====> This is widening conversion
        System.out.println(i);

      //Explicit conversion
        int a = 300;
        byte c = (byte)a;
        System.out.println(c);
    }
}
