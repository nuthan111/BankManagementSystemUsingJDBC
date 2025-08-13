package com.bank.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.CustomerDAO;
import com.bank.DAO.TransactionDetailsDAO;
import com.bank.DTO.CustomerDetails;
import com.bank.DTO.TransactionDetails;
import com.bank.Exception.CustomerDataInvalidException;

public class CustomerService {
	
	Scanner scan=new Scanner(System.in);
	CustomerDAO customerDAO=new CustomerDAO();
	CustomerDetails customerdetails=new CustomerDetails();
	TransactionDetailsDAO transactiondetailsdao=new TransactionDetailsDAO();
	
	public void customerRegistration()
	
	{
		
		List<CustomerDetails> custdetails=CustomerDAO.getallcustomerdetails();
		
		
		
		
		CustomerDetails customerdetails=new CustomerDetails();
		
		System.out.println("Enter the name");
		String name=scan.next();
		while(true)
		{
			try
			{
				if(name.matches("^[A-Za-z]{2,50}$"))
				{
					customerdetails.setName(name);
					break;
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid Name");
				
				}
			}
			catch(CustomerDataInvalidException e)
			{
				System.out.println("Re-enter valid name");
				name=scan.next();
			}
		}
		System.out.println("Enter the custoer emailid");
		String emailid=scan.next();
		int index=0;
		while(true)
		{
			try
			{
				if(emailid.endsWith("@gmail.com"))
				{
					if(!emailid.equals(custdetails.get(index)))
					{
						customerdetails.setEmailid(emailid);
						index++;
						break;
					}
					else
					{
						System.out.println("Duplicate Emailid,Emailid is present in database");
					}
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid emailid");
					
				}
			}
			catch(CustomerDataInvalidException ee)
			{
				System.out.println("Re-Enter Emailid");
				emailid=scan.next();
			}
		}
		System.out.println("Enter customer mobile number");
		long mobilenumber=scan.nextLong();
		while(true)
		{
			try
			{
				if(mobilenumber>=6000000000l&& mobilenumber<=9999999999l)
				{
					customerdetails.setMobilenumber(mobilenumber);
					break;
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid mobile number");
				}
			}
			catch(CustomerDataInvalidException ee)
			{
				System.out.println("Re-Enter the mobile number");
				mobilenumber=scan.nextLong();
			}
		}
		System.out.println("Enter Customer aadhar number");
		long aadharnumber=scan.nextLong();
		while(true)
		{
			try
			{
				if(aadharnumber>=100000000000l && aadharnumber<=999999999999l)
				{
					customerdetails.setAadharnumber(aadharnumber);
					break;
				}
				else
				{

					throw new CustomerDataInvalidException("Invalid Aadhar number");
				}
			}
			catch(CustomerDataInvalidException ev)
			{
				System.out.println("Re-Enter the Aadharnumber");
				aadharnumber=scan.nextLong();
			}
		}
		System.out.println("Enter customer Gender");
		String gender=scan.next();
		while(true)
		{
			try
			{
				if(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("female")||gender.equalsIgnoreCase("Others"))
				{
					customerdetails.setGender(gender);
					break;
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid Gender");
				}
			}
			catch(CustomerDataInvalidException d)
			{
				System.out.println("Re-Enter the Gender ");
				gender=scan.next();
				
			}
		}
		System.out.println("Enter Customer Amount");
		double amount=scan.nextDouble();
		while(true)
		{
			try
			{
				if(amount>0)
				{
					customerdetails.setAmount(amount);
					break;
				}
				else
				{
					throw new CustomerDataInvalidException("Amount is less than 0");
				}
			}
			catch(CustomerDataInvalidException a)
			{
				System.out.println("Re-Enter the Amount");
				amount=scan.nextDouble();
			}
		}
		
		System.out.println("Enter the Customer address");
		String address=scan.next();
		customerdetails.setAddress(address);
		System.out.println("Enter customer account status");
		String status=scan.next();
		customerdetails.setCustomer_account_status(status);
		
		if(customerDAO.insertCustomerDetails(customerdetails))
		{
			System.out.println("Data Inserted");
		}
		else
		{
			System.out.println("Server Error");
		}
		
	}
	public void customerLogin()
	{
		System.out.println("Enter Customer emilid or accountnumber");
		String emailoraccountnumber=scan.next();
		System.out.println("Enter Customer Pin");
		int pin=scan.nextInt();
		
		if(customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin) != null)
		{
			CustomerDetails customerdetails=customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin);
			Random random = new Random();
			String captcha="";
		    String[] captchaData = {
		        "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
		        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
		        "1","2","3","4", "5","6","7","8","9"
		    };

		    StringBuilder captchaik = new StringBuilder();
		    for (int i = 0; i < 4; i++) {
		        int index = random.nextInt(captchaData.length);
		        captchaik.append(captchaData[index]);
		        captcha=new String(captchaik);
		    }
		    System.out.println("Captcha:"+captcha);
		  
		    System.out.println("Enter Capctha");
		    String captchas=scan.next();
		    if(captchas.equals(captcha))
		    {
		    	if(customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin).getGender().equalsIgnoreCase("Male"))
		    	{
		    		System.out.println("Hello \n Mr :"+customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin).getName());
		    		customerOperation(customerdetails);
		    	}
		    	if(customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin).getGender().equalsIgnoreCase("Female"))
		    	{
		    		System.out.println("Hello \n Miss:"+customerDAO.selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(emailoraccountnumber, pin).getName());
		    	}
		   
		    }
		    else {
		    	System.out.println("Invalid Captchaa");
		    }
		}
			
		else
		{
			System.out.println("Invalid Credentials");
		}
	}
	public void customerOperation(CustomerDetails customerdetails)
	{
		 
		System.out.println("Enter \n 1.for credit \n 2.for debit \n 3.for check statement \n 4.for check balance \n 5.update \n 6.close account");
		switch(scan.nextInt())
		{
		case 1:
			System.out.println("credit");
			credit(customerdetails);
			break;
		case 2:
			System.out.println("debit");
			debit(customerdetails);
			break;
		case 3:
			System.out.println("check statement");
			break;
		case 4:
			System.out.println("check balance");
			System.out.println("Enter the pin");
			int pin1=scan.nextInt();
			int pin2=customerdetails.getPin();
			if(pin2==pin1)
			{
				Double balance=customerdetails.getAmount();
				System.out.println("Available balance:"+balance);
			}
			else
			{
				System.out.println("Invalid pin");
			}
			break;
		case 5:
			System.out.println("update");
			updateCustomerDetails(customerdetails);
			break;
		case 6:
			System.out.println("close acccount");
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}
	public void debit(CustomerDetails customerdetails)
	{
		
		System.out.println("Enter Customer Account Number");
		long accountnumber=scan.nextInt();
		System.out.println("Enter pin");
		int pin=scan.nextInt();
		if(accountnumber==customerdetails.getAccountnumber())
		{
			if(pin==customerdetails.getPin())
			{
		if(customerDAO.selectCustomerDetailsByUsingAccountNumberAndPin(accountnumber, pin))
		{
			System.out.println("Enter amount");
			double amount=scan.nextDouble();
			if(amount>=0)
			{
				
				if(amount<customerdetails.getAmount())
				{
					double updateAmount=customerdetails.getAmount()-amount;
					if(customerDAO.updateAmountAfterDebit(updateAmount, accountnumber, pin)) {
						TransactionDetails transactiondetails=new TransactionDetails();
						transactiondetails.setTransactiontype("DEBIT");
						transactiondetails.setTransactionamount(amount);
						transactiondetails.setBalanceamount(updateAmount);
						transactiondetails.setTransactiondate(LocalDate.now());
						transactiondetails.setTransactiontime(LocalTime.now());
						transactiondetails.setCustomeraccountnumber(accountnumber);
						transactiondetails.setTransactionstatus("Transfered");
						transactiondetailsdao.inserttransactiondetails(transactiondetails);
						System.out.println("Updated");
					}
					System.out.println("Sufficent");
				}
				else
				{
					System.out.println("Insufficent");
				}
			}
			else
			{
				System.out.println("Invalid Amount");
			}
		}
			}else {
				System.out.println("invalid pin");
			}
		}
	}
	public void credit(CustomerDetails customerdetails)
	{
		System.out.println("Enter the customer account number");
		long accountnumber=scan.nextLong();
		System.out.println("Enter the pin");
		int cpin=scan.nextInt();
		
		if(accountnumber==customerdetails.getAccountnumber())
		{
			if(cpin==customerdetails.getPin())
			{
				if(customerDAO.selectCustomerDetailsByUsingAccountNumberAndPin(accountnumber, cpin))
				{
					System.out.println("Enter amount");
					double amount=scan.nextDouble();
					if(amount>0)
					{
						double updateamount=amount+customerdetails.getAmount();
						if(customerDAO.updateAmountAftercredit(updateamount, accountnumber, cpin)) {
							System.out.println("Updated");
						
					}
			}
		}
	}
		}
	}
	public static void updateCustomerDetails(CustomerDetails customerdetails) {
	    Scanner scan = new Scanner(System.in);

	    System.out.println("Choose what to update:");
	   
	    System.out.println("1. Name\n2. Email\n3. Mobile Number");
	    int choice=scan.nextInt();
	    
	    switch (choice) {
	        case 1:
	            System.out.print("Enter new name: ");
	            String name = scan.nextLine();
	            customerdetails.setName(name);
	            System.out.println("Name updated.");
	            break;
	        case 2:
	            System.out.print("Enter new email: ");
	            String email = scan.nextLine();
	            customerdetails.setEmailid(email);
	            System.out.println("Email updated.");
	            break;
	        case 3:
	            System.out.print("Enter new mobile number: ");
	            long mobile = scan.nextLong();
	            customerdetails.setMobilenumber(mobile);
	            System.out.println("Mobile number updated.");
	            break;
	        default:
	            System.out.println("Invalid choice.");
	    }
	}

}
	

