package com.mycompany.myproject.Interview_Questions;

import java.util.Objects;

public class StaticBlocks {

    public static void main(String[] args) {

        Employee e2 = new Employee();

        System.out.println( "e2 :" + e2);

        Employee e1 = new Employee(101,"John",(double)10000,"Minnetonka");

        Double out = e1.getIncrement((double) 15);

        System.out.println("e1: "+ e1);


        System.out.println("Total Increment is: "+ out);

        Double out2 = e2.getIncrement((double) 15);

        System.out.println("Total Increment is: "+ out2);


    }
}



class Employee {

    private int id;
    private String name;
    private Double sal;
    static  String location;// = "UK";

    public  Employee(int id, String name, Double sal, String location){

        this.id=id;
        this.name=name;
        this.sal=sal;
        //Employee.location=location;

        System.out.println("Employee Constructor Executed...");
    }

    public Employee(){
        this.id=0;
        this.name="Govinda";
        this.sal=(double)100;
//        this.location="IN";

        System.out.println("Employee Empty Constructor Executed...");

    }

    static {

        location = "USA";
        System.out.println("Static Block Executed...");
    }

     {
        // location ="NCB";

        System.out.println("Non-Static Block Executed...");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public String getLocation() {
        return location;
    }

//    public void setLocation(String location) {
//        this.location = location;
//    }


    public Double getIncrement(Double percentage){

        return sal * percentage/100;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sal=" + sal +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(name, employee.name) &&
                Objects.equals(sal, employee.sal) &&
                Objects.equals(location, employee.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sal, location);
    }
}
