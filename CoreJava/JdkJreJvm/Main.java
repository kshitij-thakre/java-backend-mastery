package JdkJreJvm;

public class Main {
    public static void main(String[] args){
        System.out.println("====== Java Runtime Information ======");

        System.out.println("Java Version  : " +
                System.getProperty("java.version"));

        System.out.println("Java Runtime  : " +
                System.getProperty("java.runtime.version"));

        System.out.println("JVM Name      : " +
                System.getProperty("java.vm.name"));

        System.out.println("JVM Version   : " +
                System.getProperty("java.vm.version"));

        System.out.println("Java Home     : " +
                System.getProperty("java.home"));

        System.out.println("Operating Sys : " +
                System.getProperty("os.name"));

        System.out.println("Architecture  : " +
                System.getProperty("os.arch"));
    }
}
