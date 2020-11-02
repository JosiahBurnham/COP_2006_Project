import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOHelper {

    String inputName(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public List<String> inputCoursesTaken (List<String> courses){
        /**
         * The student decides what courses that they have taken
         *
         * @param courses The list of all the courses available to the student
         * @return coursesTaken a list of Strings of only the courses
         *                      being taken by the student
         */

        List<String> coursesTaken = new ArrayList<>();
        
            Scanner scan = new Scanner(System.in);
            System.out.println("What Classes are you taking?");
            System.out.println("The Courses we offer are:");
            //to display all the available courses to the student
            for(String course:courses){
                System.out.print(course+", ");
            }
            System.out.println("When you are done entering classes please input QUIT");
            boolean inputFlag = false;
            // the only way to set the inputFlag = true is if the user enters QUIT
            while(!inputFlag){
                String input = scan.nextLine();
                // checks for exit condition
                if(input.toUpperCase().equals("QUIT")){
                    inputFlag = true;
                }else{
                    // checks to see if the user input is in the List of courses
                    if(courses.contains(input.toUpperCase())){
                        coursesTaken.add(input.toUpperCase());
                        inputFlag = false;
                    }else{
                        // if input not in courses tell them it was not valid
                        System.out.println("That input was not valid");
                        inputFlag = false;
                    }
                }
            }
            return coursesTaken;
        }

}



