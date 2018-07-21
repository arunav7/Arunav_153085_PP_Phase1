package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.mypaymentapp.Exception.InsufficientBalanceException;
import com.cg.mypaymentapp.Exception.InvalidInputException;
import com.cg.mypaymentapp.Exception.MobileNumberNotRegistered;
import com.cg.mypaymentapp.Exception.ZeroBalanceException;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService {
	private WalletRepo walletRepo;

	public WalletServiceImpl(Map<String, Customer> data){
		walletRepo = new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}

	public WalletServiceImpl() {		

	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {
		
		if(amount.compareTo(new BigDecimal("0.00")) <0)	
			throw new InvalidInputException("amount caanot be negative");
			
		Pattern p=Pattern.compile("[0-9]{10}");
		Matcher m=p.matcher(mobileNo);
		
		if(m.find()&& m.group().equals(mobileNo)) {		
		         
			Customer customer1=null;     
			customer1=walletRepo.findOne(mobileNo);
		         
			if(customer1==null) {
		              
				Wallet newWallet = new Wallet(amount);
	            Customer customer=new Customer(name,mobileNo,newWallet);
			    
	            customer1 = customer;
				
	            walletRepo.save(customer);
		        
	            return customer1;
		    }
		                
			else
			    throw new MobileNumberNotRegistered("your mobile number already registered");
		                         
		}
		
		else
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
	
	}

	public Customer showBalance(String mobileNo) {
		
		Pattern p=Pattern.compile("[0-9]{10}");
		Matcher m=p.matcher(mobileNo);
		
		if(m.find()&& m.group().equals(mobileNo)) {	
		               
			Customer customer=null;
			customer = walletRepo.findOne(mobileNo);
		    
			if(customer!=null)
				return customer;
		        
			else
				throw new MobileNumberNotRegistered("your mobile number not registered");
		}
		
		else
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		
		if(amount.compareTo(new BigDecimal("0.00")) ==0)
			throw new ZeroBalanceException("You cannot transfer Zero balance");
			 
		if(amount.compareTo(new BigDecimal("0.00")) <0)	
			throw new InvalidInputException("amount caanot be negative");
			
		Pattern pattern = Pattern.compile("[0-9]{10}");
		Matcher m = pattern.matcher(sourceMobileNo);
		Matcher m1 = pattern.matcher(targetMobileNo);
		
		if(m.find()&& m.group().equals(sourceMobileNo) &&  m1.find()&& m1.group().equals(targetMobileNo)) {
		   
			Customer cust1 = null;
		    Customer cust2 = null;
			
		    cust1 = walletRepo.findOne(sourceMobileNo);
		    cust2 = walletRepo.findOne(targetMobileNo);
	                 
		    if(cust1==null && cust2==null)
		    	throw new MobileNumberNotRegistered("Source mobile Number and target mobile number is not registered");
	              
		    else if(cust2==null)
		    	throw new MobileNumberNotRegistered("Target mobile Number is not registered");
	              
		    else if(cust1==null)
	    	    throw new MobileNumberNotRegistered("Source mobile Number is not registered");
	    
		    else {
		    	      
		    	if(sourceMobileNo.equals(targetMobileNo))
			          throw new InvalidInputException("Source mobile number and target mobile number cannot be same");
			                 
		    	BigDecimal bigDecimal = cust1.getWallet().getBalance();
		    	bigDecimal = bigDecimal.subtract(amount);
		    	
		    	if(bigDecimal.compareTo(new BigDecimal("0.00")) <0)
		    		throw new InsufficientBalanceException("Insufficient balance in source registered mobile number");
		
		    	Wallet newWallet = new Wallet(bigDecimal);
		    	cust1.setWallet(newWallet);
		
		    	BigDecimal newBigDecimal = cust2.getWallet().getBalance();
		    	newBigDecimal = newBigDecimal.add(amount);
				
		    	Wallet newWallet1 = new Wallet( newBigDecimal );
		    	cust2.setWallet(newWallet1);
		
		    	walletRepo.save(cust1);
		    	walletRepo.save(cust2);
	
		    	return cust1;
	    
		    }
		}
	
		else
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		
		if(amount.compareTo(new BigDecimal("0.00")) ==0)
			throw new ZeroBalanceException("You cannot deposit Zero balance in your account");
			
		if(amount.compareTo(new BigDecimal("0.00")) <0)	
			throw new InvalidInputException(" deposited amount caanot be negative or zero");
			
		 
		Pattern pattern = Pattern.compile("[0-9]{10}");
		Matcher m = pattern.matcher(mobileNo);
		
		if(m.find()&& m.group().equals(mobileNo)) {
		
			Customer cust1=null;
			cust1=walletRepo.findOne(mobileNo);
		
			if(cust1==null)
		    	throw new MobileNumberNotRegistered(" mobile Number is not registered");
		 
			else {
		
				BigDecimal begdecimal = cust1.getWallet().getBalance();
				begdecimal = begdecimal.add(amount);
		
				Wallet wallet = new Wallet(begdecimal);
				cust1.setWallet(wallet);
		
				walletRepo.save(cust1);
		
				return cust1;
		 
			}
		}
		
		else
			throw new InvalidInputException("please correctly enter 10 digit mobile number");
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {	
		
		if(amount.compareTo(new BigDecimal("0.00")) ==0)
			throw new ZeroBalanceException("You cannot withdraw Zero balance in your account");
		
		if(amount.compareTo(new BigDecimal("0.00")) <=0)
			throw new InvalidInputException("withdrawl amount caanot be negative ror zero");
		
		Pattern pattern=Pattern.compile("[0-9]{10}");
		Matcher m=pattern.matcher(mobileNo);
		
		if(m.find()&& m.group().equals(mobileNo)) {
		     
			Customer cust1=null;
			cust1=walletRepo.findOne(mobileNo);
		    
			if(cust1==null)
		    	throw new MobileNumberNotRegistered("mobile Number is not registered");
		    
			else {
		      
				BigDecimal bigDecimal = cust1.getWallet().getBalance();
		        bigDecimal = bigDecimal.subtract(amount);
		      
		        if(bigDecimal.compareTo(new BigDecimal("0.00")) <0)
		        	throw new InsufficientBalanceException("Insufficient balance in source registered mobile number");
		     
		        Wallet wallet = new Wallet(bigDecimal);
		      
		        cust1.setWallet(wallet);
		        walletRepo.save(cust1);
		
		        return cust1;
		 
			}	
		}
		
		else
			throw new InvalidInputException("please correctly enter 10 digit mobile number");

	}	
}

