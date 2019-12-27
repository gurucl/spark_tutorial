package com.mycompany.myproject.Interview_Questions;

public class InterfaceDemo {

    public static void main(String[] args) {

        Shape s1 = new Rectangle(3,4);
        Shape s2 = new Circle(5);

        s1.draw();
        s2.draw();

       double areaOfS1 = s1.area();
        double areaOfS2 = s2.area();

        System.out.println("Area of s1 is:"+ areaOfS1);

        System.out.println("Area of s2 is:"+ areaOfS2);
    }

}

interface Shape {

    void draw();
    double area();
}

class  Rectangle implements Shape {

    private int length;
    private int width;

    Rectangle(int length, int width){
        this.length=length;
        this.width=width;
    }


    @Override
    public void draw() {
        System.out.println("Rectangle has drawn...");

    }

    @Override
    public double area() {
        return length * width;
    }
}

class Circle implements Shape {

    final double Pi=3.14;
    private int radius;

    Circle(int radius){
        this.radius=radius;
    }


    @Override
    public void draw() {

        System.out.println("Circle has drawn...");
    }

    @Override
    public double area() {
        return  Pi * (radius  * radius);
    }
}


