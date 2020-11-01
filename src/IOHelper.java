import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOHelper {

    String inputName(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public List<String> inputCourses (List<String> courses){

        List<String> coursesBeingTaken = new ArrayList<>();
        
            Scanner scan = new Scanner(System.in);
            System.out.println("What Classes are you taking?");
            System.out.println("The Courses we offer are:");
            for(String course:courses){
                System.out.print(course+", ");
            }
            System.out.println("When you are done entering classes please input QUIT");
            boolean inputFlag = false;
            while(!inputFlag){

                String input = scan.nextLine();
                if(input.toUpperCase().equals("QUIT")){
                    inputFlag = true;
                }else{
                    if(courses.contains(input.toUpperCase())){
                        coursesBeingTaken.add(input.toUpperCase());
                        inputFlag = false;
                    }else{
                        System.out.println("That input was not valid");
                        inputFlag = false;
                    }
                }
            }
            return coursesBeingTaken;
        }

}



