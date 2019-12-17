package com.mycompany.myproject.Interview_Questions;

public class SyncApp {

    public static void main(String[] args) {

        System.out.println("=== Application Started ===");

        Printer printerObj = new Printer();

        MyThread obj = new MyThread(printerObj);

        YourThread obj2 = new YourThread(printerObj);

        obj.start();

        obj2.start();


        System.out.println("=== Application Finished ====");
    }
}


class Printer {

   // synchronized void printDocuments(int numDocs, String document){


    void printDocuments(int numDocs, String document){

        for(int doc=1; doc<=numDocs; doc++){

            System.out.println("Printing "+ document + " #" +doc);
        }

    }
}

class MyThread extends Thread {

    Printer pRef;

    MyThread(Printer p){
        this.pRef=p;
    }

    @Override
    public void run() {

        synchronized (pRef){

            pRef.printDocuments(10, "Gurumurthy_Profile.pdf" );

        }



    }
}


class YourThread extends Thread {

    Printer pRef;

    YourThread(Printer p){
        this.pRef=p;
    }

    @Override
    public void run() {

        synchronized (pRef){

            pRef.printDocuments(10, "Harish's_Profile.pdf" );
        }



    }
}