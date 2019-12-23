package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class Factorial_Java {

    public static void main(String[] args) {

        //Factorial_Java fj = new Factorial_Java();

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value to calculate the factorial:");

        Double num = sc.nextDouble();


        System.out.println("Factorial of 5 is :"+ Factorial_Java.factorial(num));

    }


    public static Double factorial(Double num){

       return factorial(num, (double)1);
    }




    public static Double factorial(Double num, Double res){


        if (num<=1) return num*res;

        else return factorial(num-1, num*res);


    }
}





