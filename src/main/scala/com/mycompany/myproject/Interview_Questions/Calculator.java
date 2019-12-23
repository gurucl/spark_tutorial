package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Calculator Application...");

        System.out.println("Enter the First number:");

        Double firstNum = sc.nextDouble();

        System.out.println("Enter the Second number:");

        Double secondNum = sc.nextDouble();

        System.out.println("Enter the Operator like:  +  -   *    %    /  :");

        char ch = sc.next().charAt(0);

        Double res;


        switch (ch) {

            case '+':  res = firstNum + secondNum; System.out.println("\nOperator is: +");break;

            case '-':  res = firstNum - secondNum;  System.out.println("\nOperator is: -");break;

            case '*':  res = firstNum * secondNum;  System.out.println("\nOperator is: *");break;

            case '/':  res = firstNum / secondNum;  System.out.println("\nOperator is: /");break;

            case '%':  res = firstNum % secondNum;  System.out.println("\nOperator is: %");break;

            default :
                System.out.println("Operator is not permitted: %.lf  %c %.lf  ");

                return;

        }

        System.out.printf("Operation: %.1f %c %.1f = %.1f", firstNum, ch, secondNum,res );





    }
}
