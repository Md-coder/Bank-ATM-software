package com.company;

import java.io.Console;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);

         Bank bank=new Bank("Bank of The Rising Sun");

//         user created.
         User user=bank.addUser("John","Doe","1234");

//         Savings account created
         Account acct=new Account("Savings",user,bank);
//         account added to the list of bank accounts
         bank.AddAccount(acct);
//         account added to tbe list of user accounts
         user.AddAccount(acct);

         User curUser;
         while (true){
        curUser= ATM.printUserMenu(bank,scanner);
         ATM.transactionMenu(curUser,scanner);

         }
    }
    
            /**User menu method containing the bank and the scanner class.
             * Scanner class to take password input from the user
             * i tried using the Console class to mask the password input to no avail.
             */
    private static User printUserMenu(Bank bank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\nWelcome to %s\n\n",bank.getName());
            System.out.println("Enter user ID:");
            userID=scanner.nextLine();
            System.out.print("Enter pin:");
            pin=scanner.next();

            authUser=bank.verifyUser(userID,pin);
            if (authUser==null){
                System.out.println("Incorrect user ID/pin combination."+"please try again.");
            }
        }while (authUser==null);
        return authUser;
    }

    //    Transaction Menu method containing the user and scanner class
    private static void transactionMenu(User curUser, Scanner scanner) {
        curUser.printAccountSummary();
        int choice;

        do {
            System.out.println("1) Transaction History.\n 2) Deposit.\n 3) Withdrawal\n 4) Transfer\n 5) Quit");
            System.out.println("Enter Choice:");
            choice=scanner.nextInt();

//            creating conditions and limit of input.
            if (choice<0||choice>5)
                System.out.println("invalid selection. Try again");

        }while (choice<0||choice>5);

//        switch statement for the next customer process
            switch (choice){
            case 1:
                ATM.transactionHistory(curUser,scanner);
                break;
            case 2:
                ATM.Deposit(curUser,scanner);
                break;
            case 3:
                ATM.Withdraw(curUser,scanner);
                break;
            case 4:
                ATM.Transfer(curUser,scanner);
                break;
            }
          if (choice!=5)
              ATM.transactionMenu(curUser,scanner);

    }

/*   Transfer method containing the User and scanner class.
     This is done first as it helps in the withdrawal and deposit method.
 */
    private static void Transfer(User curUser, Scanner scanner) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

//       creating a do-while loop for the transfer menu
        do {
            System.out.printf("Select the account between 1-%d you want to transfer from:",curUser.getAccount());
//           subtracting the input by 1,because the account index starts from 0.
            fromAcct=scanner.nextInt()-1;
            if (fromAcct<0||fromAcct>curUser.getAccount())
                System.out.println("invalid account input");
        }while (fromAcct<0 || fromAcct>curUser.getAccount());

//        get the account balance of the "fromAcct" selected
        acctBal=curUser.getAccountBalance(fromAcct);

        do {
            System.out.printf("Select the account between 1-%d you want to transfer to:",curUser.getAccount());
            //           subtracting the input by 1,because the account index starts from 0.
           toAcct=scanner.nextInt()-1;
            if (toAcct<0||toAcct>curUser.getAccount())
                System.out.println("invalid account input");
        }while (toAcct<0 || toAcct>curUser.getAccount());

        do {
            System.out.printf("Enter the amount ( max=($%.02f) ):",acctBal);
            amount=scanner.nextDouble();
            if (amount<0 || amount>acctBal)
                System.out.println("invalid amount,Amount must not be less than 0 or more than max.");
        }while (amount<0 || amount>acctBal);

//        making the transfer transaction
        curUser.transferTransaction(fromAcct,-1*amount,String.format("transfer Account to %s",curUser.getAccountUUID(toAcct)));

        curUser.transferTransaction(toAcct,amount,String.format("Transfer received for %s",curUser.getAccountUUID(fromAcct)));

    }

    // Withdrawal transaction method with the User and Scanner class
    private static void Withdraw(User curUser, Scanner scanner) {
        int fromAcct;
        double amount;
        double acctBalance;
        String memo;

        do {
            System.out.printf("Select between 1-%d the account you want to withdraw from:",curUser.getAccount());
            fromAcct=scanner.nextInt()-1;
            if (fromAcct<0 || fromAcct>curUser.getAccount())
                System.out.println("invalid selection");
        }while (fromAcct<0 || fromAcct>curUser.getAccount());

        acctBalance=curUser.getAccountBalance(fromAcct);

        do {
            System.out.printf("Enter amount to withdraw max ($%.02f) :",acctBalance);
            amount=scanner.nextDouble();
            if (amount<0 || amount>acctBalance)
                System.out.println("invalid amount"+"Cant enter (-ve) amount or above max");
        }while (amount<=0 || amount>acctBalance);

//        create the memo prompt
        System.out.println("Transaction Details:");
        memo=scanner.next();

//        doing the withdrawal
        curUser.transferTransaction(fromAcct,-1*amount,memo);
    }

//    Deposit method containing the User and the scanner class
    private static void Deposit(User curUser, Scanner scanner) {
        int toAcct;
        double amount;
        String memo;
        do {
            System.out.printf("Select the account between 1-%d you want to make deposit in:",curUser.getAccount());
            System.out.println("Enter selection:");
            toAcct=scanner.nextInt()-1;
            if (toAcct<0 || toAcct>curUser.getAccount())
                System.out.println("invalid input");
        }while (toAcct<0 || toAcct>curUser.getAccount());
//        acctBalance=curUser.getAccountBalance(toAcct);

        do {
            System.out.println("Enter the amount:");
            amount=scanner.nextDouble();
            if (amount<0)
                System.out.println("invalid amount"+"Cant enter (-ve) amount");
        }while (amount<0);

//        creating the memo prompt
        System.out.println("Transaction Details:");
        memo=scanner.next();

//        doing the actual deposit
        curUser.transferTransaction(toAcct,amount,memo);
    }

// Transaction History method containing the user and the scanner class
    private static void transactionHistory(User curUser, Scanner scanner) {
        int theAcct;
        do {
        System.out.printf("\n select account between 1-%d account you want to view",curUser.getAccount());
        theAcct=scanner.nextInt()-1;
        if (theAcct<0||theAcct>curUser.getAccount())
            System.out.println("invalid selection, try again");

        }while (theAcct<0 || theAcct>curUser.getAccount());
        curUser.printTransactionHistory(theAcct);
    }

}
