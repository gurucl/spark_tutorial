package com.mycompany.myproject.Interview_Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorDemo {

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

        Collections.sort(list, new RatingCompare());


        System.out.println("\n\nList after sorting using Rating...\n");

        for(Movie m:list){
            System.out.println( m.getName() + "\t" + m.getRating() + "\t"+ m.getYear());
        }


        Collections.sort(list, new NameCompare());


        System.out.println("\n\nList after sorting using Name...\n");

        for(Movie m:list){
            System.out.println( m.getName() + "\t" + m.getRating() + "\t"+ m.getYear());
        }

    }
}

// Using Comparator interface we can sort the collection of objects using custom fields and no code change needed inside the Object class

class RatingCompare  implements Comparator<Movie> {

    @Override
    public int compare(Movie m1, Movie m2) {

        if (m1.getRating() > m2.getRating()) return 1;
        else if (m1.getRating() < m2.getRating()) return -1;
        else return 0;
    }
}

class NameCompare implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getName().compareTo(o2.getName());
    }
}


