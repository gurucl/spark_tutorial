package com.mycompany.myproject.core;

public class Matrix {

    // Calculate the sum of Diagonal elements in a Matrix using Java


    public static void main(String[] args) {


        int[][] arr = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};

        int res=0;

        for(int i=0;i<arr.length;i++ ){

            int[] ar = arr[i];

            for(int j=0; j<ar.length; j++){

                if (i==j){

                    res+= arr[i][j];

                    System.out.println(i+":"+j+"=>"+arr[i][j]);
                }


            }

        }

        System.out.println("Result: " +res);


    }
}
