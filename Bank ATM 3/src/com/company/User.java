package com.company;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private  String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account>accounts;

    public User(String firstName,String lastName,String pin,Bank bank){
//       initialization
        this.firstName=firstName;
        this.lastName=lastName;

//        creating the pin
        try {
            MessageDigest md =MessageDigest.getInstance("MD5");
             this.pinHash=md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error message");
            e.printStackTrace();
            System.exit(1);
        }
        this.uuid=bank.getUUID();
        this.accounts=new ArrayList<Account>();

        System.out.printf("Welcome %s %s,here is your user ID %s",firstName,lastName,this.uuid);
    }

    public String getUUID() {
        return this.uuid;
    }

    public void AddAccount(Account acct) {
        this.accounts.add(acct);
    }

//   this method validates both the pin inputed by the user and the pin passed or set.
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error message");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountSummary() {
        System.out.printf("\n %s,accounts summary",this.firstName);
        for (int i=0;i<accounts.size();i++){
            System.out.printf("%d) %s\n",i+1,this.accounts.get(i).getSummaryLine());
        }

    }
// getting the number of accounts
    public int getAccount() {
        return this.accounts.size();
    }

//    from the list of the user accounts to get the transactions
    public void printTransactionHistory(int theAcct) {
        this.accounts.get(theAcct).transactionHistory();
    }

    public double getAccountBalance(int fromAcct) {
        return this.accounts.get(fromAcct).getBalance();
    }

    public String getAccountUUID(int toAcct) {
        return this.accounts.get(toAcct).GetUUID();
    }

    public void transferTransaction(int acctIndex, double amount, String memo) {
        this.accounts.get(acctIndex).addTransaction(amount,memo);
    }
}
