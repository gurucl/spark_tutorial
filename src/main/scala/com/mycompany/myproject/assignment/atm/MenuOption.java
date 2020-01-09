package com.mycompany.myproject.assignment.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuOption extends Account {

    Scanner sc = new Scanner(System.in);

    Map<Integer, Integer> database = new HashMap<>();


    public void getLogin(){

        int x = 1;

        do{
            database.put(64026483, 5678);
            database.put(64100522, 1234);


            try {

                System.out.println("================ Welcome to the ATM Project ================ ");

                System.out.println("Enter customer Account number:");

                setCustomerNumber(sc.nextInt());

                System.out.println("Enter Pin number:");

                setPinNumber(sc.nextInt());


            }catch (Exception e){

                System.out.println("\nInvalid Charecters, Please Enter numbers only : Exiting \n");

                x=2;
            }

            for (Map.Entry entry : database.entrySet()){

                if (getCustomerNumber()==(int)entry.getKey() && getPinNumber()==(int)entry.getValue()){

                    getAccountType();
                }

            }

            System.out.println("Customer number or Pin number is invalid.");

        }while(x==1);

    }


    public void getAccountType(){

        System.out.println("========== Please select your account of choice ===========");

        System.out.println("Type 1 : Savings Account");
        System.out.println("Type 2 : Current Account");
        System.out.println("Type 3 : Exit");

        int button = sc.nextInt();

        switch(button){

            case 1: getSavingsAccount();
                    break;


            case 2: //getCurrentAccount();
                    break;

            case 3:
                    System.out.println("Thank you for using this ATM... Bye....");
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;

        }




    }


    public void getSavingsAccount(){

        System.out.println("Your Savings Account is here :");

        System.out.println("Type 1 : View Balance");
        System.out.println("Type 2 : WithDraw Funds");
        System.out.println("Type 3 : Deposit Funds");
        System.out.println("Type 4 : Exit");

        System.out.println("Enter your choice: ");

        int button = sc.nextInt();

        switch (button){


            case  1:
                    System.out.println("Your Savings Account Balance is :"+ getSavingsBalance());
                    getAccountType();
                    break;

            case 2: getSavingsWithDrawInput();
                    getAccountType();
                    break;

            case 3: getSavingsDepositInput();
                    getAccountType();
                    break;


            case 4:
                    System.out.println("Thank you for using this ATM... Bye...");
                    break;

                default:
                    System.out.println("Invalid choice");
                    getAccountType();
                    break;

        }



    }



}
