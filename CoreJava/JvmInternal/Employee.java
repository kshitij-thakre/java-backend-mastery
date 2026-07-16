package CoreJava.JvmInternal;

public class Employee {
    String name; // instance variable stored in heap memory
    int age; // instance variable stored in heap memory

    static String company = "ABC Corp"; // static variable stored in method area

    public void workingHrous(){
        int hours = 8; // local variable stored in stack memory
        System.out.println("Working hours: " + hours);
    }
}//Blueprint of class will allways stored in method area


//Inside method area, we have the class structure, method definitions, and static variables. 
//The instance variables are stored in the heap memory when an object of the class is created. 
//Local variables are stored in the stack memory during method execution.