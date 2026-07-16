package CoreJava.JvmInternal;




/*
Stack Stores
Primitive variables
Method parameters
Local variables
References (addresses)
Return addresses 
*/

/*
Rule: 
1. instance variable stored in heap memory
2. static variable stored in method area
3. local variable stored in stack memory
 */

public class Main {

    public static void main(String[] args){
        Employee emp1 = new Employee(); // emp1 reference variable stored in stack memory, Employee object stored in heap memory

        //new Employee(); ====> This is the object.
        //Employee emp1 =====> This is the reference variable that holds the address of the Employee object in heap memory.

        //when we use new keyword, that is the time when the object is created in heap memory. 
        //The reference variable (emp1) is stored in stack memory, which holds the address of the Employee object in heap memory.

        System.out.println("Employee Name: " + emp1.name); // Accessing instance variable from heap memory
        System.out.println("Employee Age: " + emp1.age); // Accessing instance variable from heap memory
        System.out.println("Company: " + Employee.company); // Accessing static variable from method area


    }

}
