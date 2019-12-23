package com.mycompany.myproject.Interview_Questions;

public class MirrorInverse {

    public static void main(String[] args) {

        int[] arr = {4,3,2,1,0,6,5};

        mirrorInverse(arr);

    }

    public static void mirrorInverse(int[]  arr){

        boolean flag = true;

        for(int i=0; i<arr.length; i++){

            System.out.printf("index: %d and value: %d => %d\n", i,   arr[i], arr[arr[i]]);

            if(arr[arr[i]]!=i){
                flag = false;
                System.out.printf("\nArray is not Mirror Inversed : %b", flag ); return;
            }
        }
            System.out.printf("\nArray is Mirror Inversed : %b", flag ); return;


        }

    }

