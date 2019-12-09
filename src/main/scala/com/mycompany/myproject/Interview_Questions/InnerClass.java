package com.mycompany.myproject.Interview_Questions;

public class InnerClass {


    public static void main(String[] args) {


//        A obj = new A();
//
//        System.out.println(obj.i);
//
//        obj.show();
//
//        System.out.println( A.i);
//
//       A.B obj1 = new A.B();
//
//        System.out.println(obj1.j);


        // Ananymoue Inner Class implemenmtation

        Phone p = new Phone(){

            public void call(){

                System.out.println("call, calender, mail, video call... " );
            }
        };


        Car c = new Car() {
            @Override
            public void drive() {
                System.out.println("I'm driving the car...");
            }
        };

        c.drive();


        Car d = () -> System.out.println("");


    }
}




 class A  {

   static  int i;

    public void show(){

        int k;

        System.out.println("Show() from class Phone " + i);
    }


   static class B {

        int j;



    }

}


class Phone {

    int i;

    public void call(){

        //int k;

        System.out.println("call... " );
    }

}


interface Car{

    void drive();
}


