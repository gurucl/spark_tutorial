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
            System.out.println("New Checking Account balance is :"+ getCheckingBalance());
        }else {
            System.out.println("Withdraw amount can't be greater than Balance amount");
        }
    }

    public void calcCheckingWithDraw(double amount){

        if (amount>=0){
            checkingBalance = checkingBalance-amount;
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
        }else {
            System.out.println("Please Deposit a valid amount: amount should be greater than 0");
        }
    }






    public void calcSavingsWithDraw(double amount){

        if (amount>=0){
            checkingBalance = checkingBalance-amount;
        }else {
            System.out.println("Please Enter a valid amount to withdraw...");
        }
    }


    public double getCurrentAccountBalance(){

        return savingsBalance;
    }


    public void getCurrentAccountWithdrawInput(){

        System.out.println("Your Current Account Balance is : "+ getCurrentAccountBalance());

        System.out.println("Enter the amount you want to withdraw :");

        double amount = sc.nextDouble();

        if (savingsBalance - amount >0){
            calcSavingsWithDraw(amount);
            System.out.println("New Savings account balance is :"+ getCurrentAccountBalance());

        }else {

            System.out.println("Withdraw amount can't be greater than balance amount");
        }




    }




}
