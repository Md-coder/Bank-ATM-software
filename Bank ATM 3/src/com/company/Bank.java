package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User>users;
    private ArrayList<Account>accounts;

    public Bank(String name){
        this.name=name;
        this.users=new ArrayList<User>();
        this.accounts=new ArrayList<Account>();
    }
    public String getUUID() {
        String uuid;
        Random rand = new Random();
        int len = 6;
        boolean nonUnique;

        do {
            uuid="";
            for (int i =0;i<len;i++){
                uuid += ((Integer)rand.nextInt(10)).toString();
            }
            nonUnique=false;
            for (User u :this.users){
                if (uuid.compareTo(u.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;
    }

    public String getAcctUUID() {
        String uuid = "";
        int len=10;
        Random rand=new Random();
        boolean unique;

        do {
            for (int i=0;i<len;i++)
                uuid+=((Integer)rand.nextInt(10)).toString();
            unique=false;

            for (Account Acct :this.accounts){
                if (uuid.compareTo(Acct.GetUUID()) == 0) {
                    unique = true;
                    break;
                }
            }
        }while (unique);
        return uuid;
    }

//    creating the user in the bank
    public User addUser(String firstName, String lastName, String pin) {
       User user=new User(firstName,lastName,pin,this);
//       adding the user to the list of users in the bank
       this.users.add(user);

//       creating an account
        Account acct=new Account("Checking",user,this);
//        add account created in the user class
        user.AddAccount(acct);
//        add the account to the list of accounts in the bank
        this.addAccount(acct);

        return user;
    }

    private void addAccount(Account acct) {
        this.accounts.add(acct);
    }

    public void AddAccount(Account acct) {
        this.accounts.add(acct);
    }

    public String  getName() {
        return this.name;
    }

    public User verifyUser(String id, String pin) {
        for (User u:this.users){
            if (u.getUUID().compareTo(id)==0 && u.validatePin(pin)){
                return  u;
            }
        }

        return null;
    }
}
