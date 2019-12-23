package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class Fibonacci_Java {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Fibonacci_Java fj = new Fibonacci_Java();

        System.out.println("Enter the number of Fibonacci series:");

        int num = sc.nextInt();

        System.out.printf("%d, %d",0,1);

         fj.fibonacci(num);

    }


    public void fibonacci(int num){

        int a=0;
        int b=1;
        int temp=0;

        for(int i=2; i<num; i++){
            temp = a+b;
            a=b;
            b=temp;
            System.out.printf(", %d",temp);
        }

    }
}
