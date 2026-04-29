package main.logic;

import java.util.Scanner;

public class MyUtils {
    public static int selectChoice(int maxSize)
    {
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true){
            System.out.print("\nEnter your choice (1 - " + maxSize + "): ");
            if(scanner.hasNextInt()){
                choice = scanner.nextInt();
                if(choice > 0 && choice <= maxSize){
                    return choice;
                }else{
                    System.out.println("Invalid number(range)! Please try again.");
                }
            }else {
                System.out.println("Please enter a valid NUMBER.");
                scanner.next();
            }
        }
    }
}
