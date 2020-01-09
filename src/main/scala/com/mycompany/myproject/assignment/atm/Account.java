package com.mycompany.myproject.assignment.atm;

import java.util.Scanner;

public class Account {

    private int customerNumber;
    private int pinNumber;

    private double savingsBalance;
    private double checkingbalance;

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

    public double getSavingsBalance(){
        return savingsBalance;
    }

    public double getCheckingbalance(){
        return checkingbalance;
    }

    public void getSavingsWithDrawInput(){

        System.out.println("Your Savings account balance is: "+ getSavingsBalance());
        System.out.println("Enter the Amount you want to withdraw :");
        double amount = sc.nextDouble();

        if(savingsBalance-amount>=0){
            calcSavingsWithdraw(amount);
            System.out.println("New savings account balance is :"+ getSavingsBalance());
        }
    }


    public void calcSavingsWithdraw(double amount){

        if (amount>0){
            savingsBalance = savingsBalance -amount;
            System.out.println("Transaction is successful. Please collect the cash.");
        }else {
            System.out.println("Please enter a valid amount  to withdraw. amount should be greater than 0");
        }
    }

    public void getSavingsDepositInput(){

        System.out.println("Enter a amount you want to deposit:");
        double amount = sc.nextDouble();
        calcSavingsDeposit(amount);
    }

    public void calcSavingsDeposit(double amount){
        if (amount>0){
            savingsBalance = savingsBalance + amount;
            System.out.println("Transaction is successful.");
        }else {
            System.out.println("Please enter a valid amount to deposit, amount must be breater than 0");
        }
    }




























}
