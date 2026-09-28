package Arrays;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Array - 1D array.

        int[] marks = new int[3];
        // Creates an int array object.
        // The array elements are stored contiguously within the array.

        marks[0] = 50;
        marks[1] = 40;
        marks[2] = 90;

        //Iteration of array element to get each value on each index 1D array
        for(int i = 0; i < marks.length; i++){
            System.out.println(marks[i]);
        }

        System.out.println(Arrays.toString(marks));



        //2D - Array
        int[][] marksOfStudent = new int[3][3];

        marksOfStudent[0][0] = 50;
        marksOfStudent[0][1] = 40;
        marksOfStudent[0][2] = 90;
        marksOfStudent[1][0] = 70;
        marksOfStudent[1][1] = 60;
        marksOfStudent[1][2] = 50;
        marksOfStudent[2][0] = 75;
        marksOfStudent[2][1] = 90;
        marksOfStudent[2][2] = 95;

        System.out.println(Arrays.deepToString(marksOfStudent));
        for(int row =0; row < marksOfStudent.length; row ++){
            for(int col = 0; col < marksOfStudent[row].length; col ++){
                System.out.print(marksOfStudent[row][col] + " ");
            }
            System.out.println();
        }

        //
        int[][] marksOfStudentForDifferentSub = new int[3][];

        marksOfStudentForDifferentSub[0] = new int[2];
        marksOfStudentForDifferentSub[1] = new int[1];
        marksOfStudentForDifferentSub[2] = new int[3];

        marksOfStudentForDifferentSub[0][0] = 45;
        marksOfStudentForDifferentSub[0][1] = 89;

        marksOfStudentForDifferentSub[1][0] = 45;

        marksOfStudentForDifferentSub[2][0] = 89;
        marksOfStudentForDifferentSub[2][1] = 76;
        marksOfStudentForDifferentSub[2][2] = 90;

        System.out.println(Arrays.deepToString(marksOfStudentForDifferentSub));
        for(int row =0; row < marksOfStudentForDifferentSub.length; row ++){
            for(int col = 0; col < marksOfStudentForDifferentSub[row].length; col ++){
                System.out.print(marksOfStudentForDifferentSub[row][col] + " ");
            }
            System.out.println();
        }
    }
}
