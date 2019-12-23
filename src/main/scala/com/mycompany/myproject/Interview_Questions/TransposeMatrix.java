package com.mycompany.myproject.Interview_Questions;

public class TransposeMatrix {

    static final int N=4;

    public static void main(String[] args) {

        int[][] A = {
                {1,1,1,1},
                {2,2,2,2},
                {3,3,3,3},
                {4,4,4,4}
        };

        int[][] B =  new int[N][N];

        transposeMatrix(A, B);

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){

                System.out.print(B[i][j] + " ");
            }
            System.out.println(" ");
        }

    }


    public static void transposeMatrix(int[][] A, int[][]B){

            int i,j;
        for(i=0; i<N; i++){

            for(j=0; j<N; j++){

                B[i][j] = A[j][i];
            }
        }
    }
}
