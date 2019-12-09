package com.mycompany.myproject.Interview_Questions;

import java.util.Arrays;
import java.util.List;

public class LambdaExpression {


    public static void main(String[] args) {


//        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//
//
//        list.forEach(integer -> System.out.println(integer));


//        ABC obj = new XYZ();
//
//        obj.show();


//        ABC obj = new ABC() {
//            @Override
//            public void show() {
//                System.out.println("Hello...");
//            }
//        };
//    }


        ABC obj = i -> System.out.println("Hello" + i);

        obj.show(6);


    }
}


class XYZ implements ABC {


    @Override
    public void show(int i) {
        System.out.println("Hello..."+ i);
    }
}



interface  ABC {

    void show(int i);
}
