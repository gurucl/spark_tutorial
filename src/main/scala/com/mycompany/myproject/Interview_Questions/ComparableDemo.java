package com.mycompany.myproject.Interview_Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableDemo {


    public static void main(String[] args) {

        List<Movie> list = new ArrayList<>();

        list.add(new Movie("The Shawshank Redemption",9.3, 1994));
        list.add(new Movie("Interstellar",8.6, 2014));
        list.add(new Movie("The Dark Knight",9.0, 2008));
        list.add(new Movie("Forrest Gump",8.8, 1994));
        list.add(new Movie("The Lord of the Rings: The Return of the King",8.9, 2003));
        list.add(new Movie("Joker",8.7, 2019));

        System.out.println("List before sorting...\n");

        for(Movie m:list){
            System.out.println( m.getName() + "\t" + m.getRating() + "\t"+ m.getYear());
        }

        Collections.sort(list);

        System.out.println("\n\nList after sorting...\n");

        for(Movie m:list){
            System.out.println( m.getName() + "\t" + m.getRating() + "\t"+ m.getYear());
        }


    }


}


// By Implementing Comparable Interface and overriding compareTo method we can add a natural way of sorting to a collection of object...

// We can't sort by rating or by name => So use Comparator Interface

class Movie implements Comparable <Movie> {

    private String name;
    private double rating;
    private int year;

    Movie(String name, double rating, int year){
        this.name=name;
        this.rating=rating;
        this.year=year;
    }

    public String getName(){
        return this.name;
    }

    public double getRating(){
        return this.rating;
    }

    public int getYear(){
        return this.year;
    }

    public int compareTo(Movie m){
        if (this.year > m.year) return 1;
        else if (this.year < m.year) return -1;
        else return 0;
    }

}
