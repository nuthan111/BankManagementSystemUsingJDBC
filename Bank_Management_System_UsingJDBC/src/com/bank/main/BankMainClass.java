package com.bank.main;

import java.util.Scanner;

import com.bank.DAO.CustomerDAO;
import com.bank.service.AdminService;
import com.bank.service.CustomerService;

public class BankMainClass {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		CustomerService customerservice=new CustomerService();
		CustomerDAO customerdao=new CustomerDAO();
		AdminService adminlogin=new AdminService();
		
		System.out.println("Enter \n 1.Customer Regsitraction \n 2.Customer Login \n 3.Admin Login");
		
		switch(scan.nextInt())
		{
		case 1:
			System.out.println("Customer Regsistraction");
			customerservice.customerRegistration();
			break;
		case 2:
			System.out.println("Customer Login");
			customerservice.customerLogin();		
			break;
			
		case 3:
			System.out.println("Admin Login");
			adminlogin.adminservices();
			break;
		default:
			System.out.println("Invalid Request");
		
		}
		
		
		
	}

}
