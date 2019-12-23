package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class Fibo_Java {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the Number to calculate the Fibonacci series:");

        int num = sc.nextInt();

        fibonacci(num);

    }

    public static void fibonacci(int num){

        int a=0;
        int b=1;
        int temp=0;

        while(a<num){

            System.out.printf("%d ",a);

            temp=a+b;
            a=b;
            b=temp;

        }


    }
}



