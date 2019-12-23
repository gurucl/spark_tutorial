package com.mycompany.myproject.Interview_Questions;

public class ReverseStringLine {

    public static void main(String[] args) {

        String str = "Welcome to Edureka";

        reverse(str);

    }

    public static void reverse(String str ){

        String[] words = str.split("\\s+");

        for(int i=0; i<words.length; i++){

            char[]  ch = words[i].toCharArray();

            for(int j=ch.length-1; j>=0; j--){

                System.out.print(ch[j]);
            }

            System.out.print(" ");

        }
    }
}
