package project1;

import java.util.Scanner;

public class FrontendDeveloperTest {

    public static boolean test1() 
    {
        
        TextUITester test = new TextUITester("1\n22\\n4\n");
        Scanner sc = new Scanner(System.in);
        FDBookMapperBackend a = new FDBookMapperBackend();
        FDIISBNValidator b = new FDIISBNValidator();
        BookMapperFrontend test1 = new BookMapperFrontend(sc,a,b);
        test1.runCommandLoop();

        String output = test.checkOutput();
        System.out.println(output);
        return true;
    }

    public static boolean test2() 
    {
        return true; 
    }

    public static boolean test3() 
    {
        return true;
    }

    public static boolean test4() 
    {
        return true; 
    }

    public static boolean test5() 
    {
        return true; 
    }
    public static void main(String[] args)
    {
        System.out.println(test1());
        // System.out.println("Test 2: "+test2());
        // System.out.println("Test 3: "+test3());
        // System.out.println("Test 4: "+test4());
        // System.out.println("Test 5: "+test5());
    }
}
