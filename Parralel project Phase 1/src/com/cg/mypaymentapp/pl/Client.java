package com.cg.mypaymentapp.pl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	
	HashMap<String,Customer> map = new HashMap<String,Customer>();

	WalletService walletService = new WalletServiceImpl(map);

	@SuppressWarnings("resource")
	void menu() throws IOException { 
	
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\nWelcome To Our Personal Wallet Portal");
		System.out.println();
		System.out.println("1.Create An account");
		System.out.println("2.View Balance");
		System.out.println("3.Money Transfer");
		System.out.println("4.Amount to be deposited"); 	
		System.out.println("5.Amount to be withdrawl");
		System.out.println("6.Exit");
		System.out.println("==========================");
		
		System.out.print("Please enter your choice: ");
		
		Scanner scanner = new Scanner(System.in);
		int option=scanner.nextInt();
		
		switch(option) {
		
		case 1:
	       System.out.print("Please enter your name: ");
	       String name = consoleInput.readLine();
		
	       System.out.print("Please enter your mobile no: ");
	       String mob = consoleInput.readLine();
		
	       System.out.print("Please enter amount: ");
		
	       BigDecimal bigdecimal = new BigDecimal(consoleInput.readLine());
		
	       try {

	    	   Customer cust = walletService.createAccount(name, mob, bigdecimal);
	    	   System.out.println(cust);
	       
	       } catch(Exception e) {
	    	   System.out.println(e.getMessage());
	       }
		
	       break;
		
		case 2:
			System.out.print("Please enter your mobile no: ");
			String mob1 = consoleInput.readLine();
		   
			try {
		    
				Customer cust1=walletService.showBalance(mob1);
				System.out.println("Your balance in account is: " + cust1.getWallet().getBalance());
		   
			} catch(Exception e) {   
			  System.out.println(e.getMessage()); 
			}
			
			break;
	
		case 3:
			System.out.print("please enter your source mobile no");
			String smob = consoleInput.readLine();
	
			System.out.print("please enter your target mobile no");
			String tmob = consoleInput.readLine();
	
			System.out.print("please enter amount to be transferred");
			BigDecimal amount = new BigDecimal(consoleInput.readLine());
	
			try {
			
				Customer cust=walletService.fundTransfer(smob, tmob, amount);
				System.out.println("Balance has been successfully transfered ur balance after transaction is: " + cust.getWallet().getBalance());
			
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		
			break;
	
		case 4:
			System.out.print("Please enter your mobile no: ");
			String dmob = consoleInput.readLine();
	
			System.out.print("Please enter amount to be deposited: ");
	
			BigDecimal depositAmount = new BigDecimal(consoleInput.readLine());
	
			try {
	
				Customer cust = walletService.depositAmount(dmob, depositAmount);
				System.out.println("Balance after depositing is: " + cust.getWallet().getBalance());
			
			} catch(Exception e) {
				System.out.println(e.getMessage());	
			}
	
			break;
	
		case 5:
			System.out.print("Please enter your mobile no: ");
			String wmob = consoleInput.readLine();
	
			System.out.print("Please enter amount to be withdrawn: ");
			BigDecimal withdrawAmount = new BigDecimal(consoleInput.readLine());
	
			try {
	
				Customer cust=walletService.withdrawAmount(wmob, withdrawAmount);
				System.out.println("Balance after withdrawal is: " + cust.getWallet().getBalance());

			} catch(Exception e) {
				System.out.println(e.getMessage());	
			}
	
			break;
			
		case 6:
			System.out.println("Have A good day.!!!!!!!");
			System.exit(0);
			
		default:
			System.out.println("wrong choice");
		}
	}
	

	public static void main(String[] args) {

		Client ob=new Client();

		while(true) {

			try {
				ob.menu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	
}
