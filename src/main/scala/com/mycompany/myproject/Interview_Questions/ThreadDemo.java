package com.mycompany.myproject.Interview_Questions;

public class ThreadDemo {

    public static void main(String[] args) {

        // Job 1
        System.out.println("Application Started...");

        // Job 3
        Runnable obj = new Inter();
        Thread t1 = new Thread(obj);
        t1.start();
        //obj.run();

        // Job 5
        MyClass obj3 = new MyClass();
        obj3.start();


        // Job 2
        for (int doc=1; doc<=10; doc++){
            System.out.println("@@ Printing Document #" + doc + " from Printer1");
        }


        // Job 4
        for (int doc=1; doc<=10; doc++){
            System.out.println("^^ Printing Document #" + doc + " from Printer2");
        }




        // Job 6
        System.out.println("Application Ended...");

    }
}



class MyClass extends Thread {

    @Override
    public void run() {

        for (int doc=1; doc<=10; doc++){
            System.out.println("** Printing Document #" + doc + " from Printer4");
        }
    }
}


class Inter implements Runnable {

    @Override
    public void run() {
        for (int doc=1; doc<=10; doc++){
            System.out.println("&& Printing Document #" + doc + " from Printer3");
        }
    }
}