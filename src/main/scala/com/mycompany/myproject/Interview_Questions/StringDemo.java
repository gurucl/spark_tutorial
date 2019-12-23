package com.mycompany.myproject.Interview_Questions;

public class StringDemo {

    public static void main(String[] args) {

        String str = "Hello";

        String str1 =  "Hello";

        String str2 = new String("World");

        String str3 = new String("World");

        System.out.println(str);

        System.out.println(str1);

        if(str == str1){
            System.out.println("str  == str1 : True" );
        }else {
            System.out.println("str  == str1 : False" );
        }

        if(str.equals(str1)){
            System.out.println("str.equals(str1) : True" );
        }else {
            System.out.println("str.equals(str1) : False" );
        }

        System.out.println("============================================");

        if(str2 == str3){
            System.out.println("str2  == str3 : True" );
        }else {
            System.out.println("str2  == str3 : False" );
        }

        if(str2.equals(str3)){
            System.out.println("str2.equals(str3) : True" );
        }else {
            System.out.println("str2.equals(str3) : False" );
        }


        if(str2.compareTo(str3)==0){
            System.out.println("str2 compared to str3");
        }else {
            System.out.println("str2 is not compared to str3");
        }

        System.out.println("============================================");

        String str5 = "John, Jennie, Jim, Jack, Joe";

        System.out.println("str5 is:" + str5);

        int length = str5.length();

        System.out.println("Length of str5 is :"+ length);

        System.out.println(str5.charAt(0)+" | "+ str5.charAt(length-1));

        String str6 = str5.toUpperCase();

        String str7 = str5.toLowerCase();

        System.out.println("str5 is :" + str5);
        System.out.println("str6 is :" + str6);
        System.out.println("str7 is :" + str7);

        if (str5.contains("Jack")){
            System.out.println("str5 contains Jack ");
        }else {
            System.out.println("str5 doesn't contains Jack ");
        }

        String s1 =str5.substring(5);

        System.out.println("s1 is :" + s1);

        String s2 = str5.substring(5,10);

        System.out.println("s2 is :" + s2);

        String s3 = str5.concat(" World");

        String s4 = str5.intern();

        String s5 = str5.replace("J","K");

        String s6 = str5.replaceAll("ac","@#");

        Boolean s7 = str5.matches("[J*]");

        byte[] s8 = str5.getBytes();

        Boolean s9 = str5.startsWith("J");

        Boolean s10 = str5.isEmpty();

        Class s11 = str5.getClass();

        String[] s12 = str5.split(",");

        System.out.println("s3 is: "+ s3);
        System.out.println("s4 is: "+ s4);
        System.out.println("s5 is: "+ s5);
        System.out.println("s6 is: "+ s6);
        System.out.println("s7 is: "+ s7);
        System.out.println("s8 is: "+ s8);
        System.out.println("s9 is: "+ s9);
        System.out.println("s10 is: "+ s10);
        System.out.println("s11 is: "+ s11);

        for(String s: s12){
            System.out.println("s12 is: "+ s.trim());
        }


        char[] ch = str5.toCharArray();

        for(char c:ch){
            System.out.print(c + " ");
        }


        System.out.println("==============================");

        String email= "clguru.murthyxyz@gmail.com";
        String phoneNo = "963221***";
        String passWord = "Password@xyz";


        if (!email.isEmpty()){
            if (!(email.contains("@")) || email.contains(".")){

                System.out.println("Email address is valid");
            }else {
                System.out.println("Please provide a valid Email address");
            }

        }else {
            System.out.println("Please provide the Email address");
        }

        if (!phoneNo.isEmpty()){

            if (phoneNo.length()==10){

                System.out.println("Phone number is valid");

            }else {
                System.out.println("Phone Number is not valid");
            }
        }else {
            System.out.println("Please provide Phone Number");
        }




        String plain = new String("Hello");

        plain.contains(" World");

        System.out.printf("\nplain : %s\n",plain);


        // StringBuffer

        StringBuffer buffer = new StringBuffer("Hello");  // Thread Safe

        buffer.append(" World");

        System.out.printf("buffer : %s\n",buffer);


        // StringBuilder


        StringBuilder builder = new StringBuilder("hello");  // Not Thread Safe

        builder.append("  World");


        System.out.printf("builder : %s\n",builder);
























    }
}
