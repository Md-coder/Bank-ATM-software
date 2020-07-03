package com.company;

import java.util.Date;

public class Transaction {
    private double amount;
    private String memo;
    private Date timeStamp;
    private Account account;

    public Transaction(double amount,Account account){
        this.amount=amount;
        this.memo="";
        this.timeStamp=new Date();
        this.account=account;
    }

    public Transaction(double amount,String memo,Account account){
        this(amount,account);
        this.memo=memo;
    }

    public double getAmount() {
        return this.amount;
    }

//    printing the transaction history
    public String printTransaction() {
        if (this.amount>0)
            return String.format("\n $%.02f : %s : %s",this.amount,this.timeStamp.toString(),this.memo);
        else
            return String.format("\n $(%.02f) : %s : %s",this.amount,this.timeStamp.toString(),this.memo);
    }
}
