package com.mycompany.myproject.java_projects.atm;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OptionMenu extends Account {

    Scanner sc = new Scanner(System.in);

    DecimalFormat decimalFormat = new DecimalFormat("'$'###,##0.00");

    Map<Integer, Integer> accountDatabase = new HashMap<>();


    public void getLogin(){

        int x=1;

        do {
            try {

                accountDatabase.put(640264,5678);
                accountDatabase.put(641005,1234);

                System.out.println("=========== Welcome to the ATM project =============");

                System.out.println("Enter your Account Number :");

                setCustomerNumber(sc.nextInt());


                System.out.println("Enter your 4 digit Pin Number :");

                setPinNumber(sc.nextInt());

            }catch (Exception e){

                System.out.println("Invalid Charecter(s): Enter Only Numbers \n");
                x=2;
            }

            for(Map.Entry entry: accountDatabase.entrySet()){

                if ((getCustomerNumber()== (int)entry.getKey()) &&  (getPinNumber()==(int)entry.getValue())){

                    getAccountType();
                }

            }

            System.out.println("Wrong Customer Number or Pin Number. \n");

        }while(x==1);

    }

    public void getAccountType(){

        System.out.println("Enter the Account type you want to access");

        System.out.println("Type 1 :  Savings Account");
        System.out.println("Type 2 :  Current Account");
        System.out.println("Type 3 :  Exit");

        System.out.print("Enter Your Choice: ");

        int button = sc.nextInt();

        switch (button){

            case 1 : getCheckingAccount();
                        break;

            case 2 : getCurrentAccount();
                        break;

            case 3 :
                System.out.println("Thank you for using this ATM...Bye...");
                        break;

            default :
                System.out.println("Invalid Choice. ");
                getAccountType();

        }

    }

    public void getCheckingAccount(){

        System.out.println("Your Checking Account is here : ");

        System.out.println("Type 1: View Balance");
        System.out.println("Type 2: WithDraw Funds");
        System.out.println("Type 3: Deposit Funds");
        System.out.println("Type 4: Exit");

        System.out.print("Enter your choice: ");


        int button = sc.nextInt();

        switch (button){

            case 1 :
                System.out.println("Your Checking Account Balance :" + getCheckingBalance());
                getAccountType();
                break;

            case 2:
                getCheckingWithDrawInput();
                getAccountType();
                break;

            case 3:
                getCheckingDepositInput();
                getAccountType();
                break;

            case 4:
                System.out.println("Thank you for using the ATM... Bye...");
                break;

                default:
                    System.out.println("\nInvalid choice: \n");
                    getAccountType();

        }

    }


    public void getCurrentAccount(){

        System.out.println("Your Current Account is here : ");

        System.out.println("Type 1: View Balance ");
        System.out.println("Type 2: Withdraw Funds ");
        System.out.println("Type 3: Deposit Funds ");
        System.out.println("Type 4: Exit ");


        System.out.println("Enter Your choice: ");

        int button = sc.nextInt();

        switch(button){

            case 1:
                System.out.println("Your Current Account Balance is : "+ getCurrentAccountBalance());
                getAccountType();
                break;

            case 2: getCurrentAccountWithdrawInput();
                getAccountType();
                break;

            case 3: //getCurrentAccountDepositInput();
                getAccountType();
                break;

            case 4:
                System.out.println("Thank you for using this ATM... Bye...");
                break;

                default:
                    System.out.println("\n Invalid Choice... \n");

        }




    }


}
