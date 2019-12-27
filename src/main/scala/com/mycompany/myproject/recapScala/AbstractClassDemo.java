package com.mycompany.myproject.recapScala;

public class AbstractClassDemo {

    public static void main(String[] args) {

        Shape s1 = new Rectangle(3,4,"Parallel Rectangle");
        Shape s2 = new Circle(5,"Nehru Circle");

        s1.draw();
        s2.draw();

        System.out.println("s1 is : " + s1.toString());
        System.out.println("s2 is : " + s2.toString());


        double areaOfS1 = s1.area();
        double areaOfS2 = s2.area();

        System.out.println("Area of s1 is :" + areaOfS1);

        System.out.println("Area of s2 is :" + areaOfS2);

    }
}

abstract class Shape {

    private String name;

    Shape(String name){

        this.name=name;

    }

    abstract void draw();

    abstract double area();

    public String toString(){

        return "Shape with name : " + name;

    }

}

class Rectangle extends Shape {

    private int length;
    private int width;

    Rectangle(int length, int width, String name){

        super(name);
        this.length=length;
        this.width=width;
    }


    @Override
    void draw() {

        System.out.println("Rectangle has drawn...");
    }

    @Override
    double area() {
        return length * width;
    }
}

class Circle extends Shape {

    final double Pi = 3.14;
    private int radius;

    Circle(int radius, String name){

        super(name);

        this.radius=radius;
    }


    @Override
    void draw() {

        System.out.println("Circle has drwan...");
    }

    @Override
    double area() {
        return Pi * radius * radius;
    }
}


