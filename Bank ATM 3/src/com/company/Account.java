package com.company;

import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction>transactions;

    public Account(String name,User holder,Bank bank){
//        initialization.
        this.name=name;
        this.holder=holder;
        this.uuid=bank.getAcctUUID();

        this.transactions=new ArrayList<Transaction>();
    }

    public String GetUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        double balance=this.getBalance();

        if (balance>=0){
            return String.format("\n %s : $%.02f : %s",this.uuid,balance,this.name);
        }else {
            return String.format("\n %s : $(%.02f) : %s",this.uuid,balance,this.name);
        }
    }

     public double getBalance() {
         double balance=0;
         for (Transaction t:this.transactions){
             balance += t.getAmount();
         }
         return balance;
    }

//    deriving the accounts in which we have to get the history from the Transaction Class.
    public void transactionHistory() {
        System.out.printf("\n the transaction history for %s",this.uuid);
//        creating the loop to get the list of transactions from an account.
        for (int i=this.transactions.size()-1;i>=0;i--)
            System.out.println(this.transactions.get(i).printTransaction());
    }

    public void addTransaction(double amount, String memo) {
        Transaction trans =new Transaction(amount,memo,this);
        this.transactions.add(trans);
    }
}
