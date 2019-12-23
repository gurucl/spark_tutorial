package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class PatternString {

    public static void main(String[] args) {

        //Scanner sc = new Scanner(System.in);

        //System.out.println("Enter the number of lines:");

        //int num = sc.nextInt();

        printPatternA(7);

    }


    public static void printPatternA(int n){

        for(int i=0; i<n; i++){

            for(int j=0; j<n/2; j++){

                // prints two parallel lines
                if((j==0 || j==n/2) && i!=0 ||

                        // prints first line of alphabet
                     i==0 &&  j!= n/2 ||

                    // prints middle line
                         i==n/2 )

                    System.out.println("*");
                else
                    System.out.println(" ");
            }

            System.out.println(" ");
        }
    }
}
