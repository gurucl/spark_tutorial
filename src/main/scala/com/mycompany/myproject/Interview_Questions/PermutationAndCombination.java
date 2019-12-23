package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class PermutationAndCombination {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(" ======== Enter the Below vallues to calculate Permutation And Combination ======== ");

        System.out.println("Enter the value of n:");

        int n = sc.nextInt();

        System.out.println("Enter the value of r:");

        int r = sc.nextInt();

        permutationAndCombination(n,r);

    }

    public static int fact(int num){

        int fact=1;

        for(int i=1; i<=num; i++){

            fact = fact*i;
        }

       // System.out.printf("Factorial of %d is: %d",num, fact);

        return fact;
    }


    public static void permutationAndCombination(int n, int r){

        int npr = (fact(n)/fact(n-r));

        int ncr = npr/fact(r);

        System.out.printf("Number of Permutations for a given set n=%d, r=%d are : %d\n",n,r,npr);

        System.out.printf("Number of Combinations for a given set n=%d, r=%d are : %d",n,r,ncr);

    }
}
