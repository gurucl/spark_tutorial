package com.mycompany.myproject.Interview_Questions;

import java.util.Scanner;

public class Palindrome {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a String to check whether it is a Palindrome or not:");

        String str = sc.next();

        Palindrome.checkPalindrome(str);

    }


    // m a d a m
    // 0 1 2 3 4  => 4/2 => 2
    //  str[0] == str[4-0]    &&     str[1] == str[4-1]       &&      str[2] == str[4-2]
    // Then String is a Palindrome


    public  static void checkPalindrome(String str){

        int len = str.length();
        boolean flag = true;

        System.out.printf("String Length is: %d\n",len);

        for(int i=0; i<len/2; i++){

            if (str.charAt(i)!=str.charAt(len-1-i)){
                flag = false;
                System.out.printf("String %s is not a Palindrome",str);
                return;

            }
        }

        System.out.printf("String %s is a Palindrome",str);
    }
}
