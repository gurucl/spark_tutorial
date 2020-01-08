package com.mycompany.myproject.java_projects.atm;

import java.util.Scanner;

public class Account {

    private int customerNumber;
    private int pinNumber;

    private double checkingBalance;
    private double savingsBalance;


    Scanner sc = new Scanner(System.in);

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getCheckingBalance(){

        return checkingBalance;
    }



    public void getCheckingWithDrawInput(){

        System.out.println("Your Checking Account balance is: "+ getCheckingBalance());
        System.out.println("Enter amount to withdraw :");

        double amount = sc.nextDouble();

        if ((checkingBalance-amount)>=0){

            calcCheckingWithDraw(amount);
            // System.out.println("New Checking Account balance is :"+ getCheckingBalance());
        }else {
            System.out.println("Withdraw amount can't be greater than Balance amount");
        }
    }

    public void calcCheckingWithDraw(double amount){

        if (amount>=0){
            checkingBalance = checkingBalance-amount;
            System.out.println("Transaction successfull. Please collect the cash : $$$$ " + amount);
        }else {
            System.out.println("Please Enter a valid amount to withdraw...");
        }
    }


    public void getCheckingDepositInput(){

        System.out.println("Please enter the amount you want to Deposit: ");

        double amount = sc.nextDouble();
        calcCheckingDeposit(amount);

    }

    public void calcCheckingDeposit(double amount){

        if (amount>=0){
            checkingBalance = checkingBalance +amount;
            System.out.println("New Checking Account balance is :"+ getCheckingBalance());
        }else {
            System.out.println("Please Deposit a valid amount: amount should be greater than 0");
        }
    }





    public double getSavingsAccountBalance(){

        return savingsBalance;
    }


    public void getSavingsWithdrawInput(){

        System.out.println("Your Savings Account Balance is : "+ getSavingsAccountBalance());

        System.out.println("Enter the amount you want to withdraw :");

        double amount = sc.nextDouble();

        if (savingsBalance - amount >0){
            calcSavingsWithDraw(amount);
           // System.out.println("New Savings account balance is :"+ getSavingsAccountBalance());

        }else {

            System.out.println("Withdraw amount can't be greater than balance amount");
        }
    }

    public void calcSavingsWithDraw(double amount){

        if (amount>=0){
            savingsBalance = savingsBalance-amount;
            System.out.println("Transaction successfull. Please collect the cash : $$$$ " + amount);
        }else {
            System.out.println("Please Enter a valid amount to withdraw...");
        }
    }



    public void getSavingsDepositInput(){

        System.out.println("Enter the amount you want to Deposit :");

        double amount = sc.nextDouble();

        calcSavingsDeposit(amount);

    }

    public void calcSavingsDeposit(double amount){

        if(amount>0){
            savingsBalance = savingsBalance + amount;
            System.out.println("New Savings account balance is :"+ getSavingsAccountBalance());
        }else {
            System.out.println("Please enter a valid amount to Deposit - Amount must be greate than 0");
        }

    }




}
