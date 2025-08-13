package com.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.CustomerDAO;
import com.bank.DTO.CustomerDetails;

public class AdminService {
	Scanner scan=new Scanner(System.in);
	CustomerDAO customerdao=new CustomerDAO();

	AdminDAO admindao=new AdminDAO();
	List<CustomerDetails> l=CustomerDAO.getallcustomerdetails();
	
	
	public void adminservices()
	{
		System.out.println("Enter emailid");
		String emailid=scan.next();
		System.out.println("Enter password");
		String password=scan.next();
		
		if(admindao.selectAdminLoginByUsingEmailidAndPassword(emailid,password))
		{
			System.out.println("Enter \n 1.To get customer details \n 2.To get all account request details \n 3.To get all acoount closing details ");
			switch(scan.nextInt())
			{
			case 1:
				System.out.println("To get all customer details");
				
				System.out.println(l);
				break;
			case 2:
				System.out.println("To get all account request details");
				List<CustomerDetails> allcustomerbyusingstatus=
				CustomerDAO.getAllCustomerByusingStatus("pending");
				 allcustomerbyusingstatus.stream().forEachOrdered((customer->{
					 System.out.println("Customer Name:-"+customer.getName());
					 System.out.println("Customer emailid-"+customer.getEmailid());
					 long mobilenumber=customer.getMobilenumber();
					 String mb=""+mobilenumber;
					 long aadharnumber=customer.getAadharnumber();
					 String aa=""+aadharnumber;
					 System.out.println(
							 "Customer mobile number:-"+mb.substring(0, 3)+"*****"+mb.substring(7, 10));
					 System.out.println(
							 "Customer Aadhar Number:-"+aa.substring(0,3)+"*****"+mb.substring(9, 12));
					 System.out.println("**------*****------**");
							 
				 }));
				 System.out.println("Enter 1.To Generate Account Number for One Person"
						 +"\n 2.To select all to Generate account numbers");
				 switch(scan.nextInt())
				 {
				 case 1:
					 break;
				 case 2:
					 List<CustomerDetails> accepteddetails=new ArrayList<CustomerDetails>();
					 for(int i=0;i<allcustomerbyusingstatus.size();i++)
					 {
						 CustomerDetails customerdetails=allcustomerbyusingstatus.get(i);
						 Random random=new Random();
						 int ac=random.nextInt();
						 if(ac<100000)
						 {
							 ac+=100000;
						 }
						 customerdetails.setAccountnumber(ac);
						 int pin=random.nextInt(100000);
						 if(pin<1000)
						 {
							 pin+=1000;
						 }
						 customerdetails.setPin(pin);
						 accepteddetails.add(customerdetails);
						 
					 }
					 
					 customerdao.updateAccountAndPinByUsingId(l);
					 System.out.println("accepteddetails");
					break;
						 
					default:
						break;
						 
					 }
				break;
				 
				 
				
			case 3:
				System.out.println("To get all account closing details");
				break;
			default:
				System.out.println("Invalid Request");
				break;
			}
		}
		else
		{
			System.out.println("Invalid Email and password ");
		}
	
		
	
}
}
