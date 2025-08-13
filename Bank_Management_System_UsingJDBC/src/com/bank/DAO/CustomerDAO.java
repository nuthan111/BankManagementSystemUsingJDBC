package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.DTO.CustomerDetails;
import com.bank.util.DatabaseConnection;

public class CustomerDAO {
	private final static String select_all_customer_details="select * from customer_details";
	private final static String insert="insert into customer_details(customer_Name, customer_Eamilid, customer_MobileNumber, customer_aadhar_Number, customer_address, customer_gender,  customer_Amount,customer_account_status) values(?,?,?,?,?,?,?,?)";
	private final static String select_all_customer_details_by_Using_status="select * from customer_details where status=?";
	private final static String customer_details="select*from customer_details where (customer_Eamilid=? or customer_AccountNumber=?) and customer_pin=?";
	private final String update="update customer_details set customer_AccountNumber=?,customer_pin=?,customer_account_status=?";
	
	private final String select_all_customer_details_by_Using_accountNUmber_and_pin = "select * from customer_details where customer_AccountNumber=? AND customer_pin=?";
	private final String updateAmount="update customer_details set customer_Amount=? where customer_AccountNumber=? and customer_pin=?";
	
	public boolean insertCustomerDetails(CustomerDetails customerdetails)
	{
		
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			
			PreparedStatement preparedstatement=connection.prepareStatement(insert);
			preparedstatement.setString(1,customerdetails.getName());
			preparedstatement.setString(2,customerdetails.getEmailid());
			preparedstatement.setLong(3,customerdetails.getMobilenumber());
			preparedstatement.setLong(4,customerdetails.getAadharnumber());
			preparedstatement.setString(5,customerdetails.getAddress());
			preparedstatement.setString(6,customerdetails.getGender());
			preparedstatement.setDouble(7,customerdetails.getAmount());
			preparedstatement.setString(8, customerdetails.getCustomer_account_status());
			int result=preparedstatement.executeUpdate();
			if(result!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static List<CustomerDetails> getallcustomerdetails()
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			
			PreparedStatement preparedstatement=connection.prepareStatement(select_all_customer_details);
			ResultSet resultset=preparedstatement.executeQuery();
			List<CustomerDetails> listofcutsomerdetails=new ArrayList<CustomerDetails>();
			if(resultset.isBeforeFirst())
			{
				while(resultset.next())
				{
					CustomerDetails customerdetails=new CustomerDetails();
					customerdetails.setEmailid(resultset.getString("customer_Eamilid"));
					customerdetails.setAadharnumber(resultset.getLong("customer_aadhar_Number"));
					customerdetails.setMobilenumber(resultset.getLong("customer_MobileNumber"));
					listofcutsomerdetails.add(customerdetails);
					
				}
				return listofcutsomerdetails;
			}
			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static List<CustomerDetails> getAllCustomerByusingStatus(String status)
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			
			PreparedStatement preparedstatement=connection.prepareStatement("select_all_customer_details_by_Using_status");
			preparedstatement.setString(1, status);
			ResultSet resultset=preparedstatement.executeQuery();
			List<CustomerDetails> listofcutsomerdetails=new ArrayList<CustomerDetails>();
			if(resultset.isBeforeFirst())
			{
				while(resultset.next())
				{
					CustomerDetails customerdetails=new CustomerDetails();
					customerdetails.setEmailid(resultset.getString("customer_Eamilid"));
					customerdetails.setAadharnumber(resultset.getLong("customer_aadhar_Number"));
					customerdetails.setMobilenumber(resultset.getLong("customer_MobileNumber"));
					listofcutsomerdetails.add(customerdetails);
					
				}
				return listofcutsomerdetails;
			}
			else
			{
				return null;
			}
		}
			catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	public void updateAccountAndPinByUsingId(List<CustomerDetails> list)
	{
		try
		{
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(update);
			for(CustomerDetails customerdetails:list)
			{
				preparedstatement.setLong(1, customerdetails.getAccountnumber());
				preparedstatement.setLong(2,customerdetails.getPin());
				preparedstatement.setString(3, "Accepted");
				preparedstatement.setString(4, customerdetails.getEmailid());
				preparedstatement.addBatch();
			}
			System.out.println(preparedstatement);
			int[] batch=preparedstatement.executeBatch();
			System.out.println("Updated");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CustomerDetails selectCustomerDetailsByUsingEmailidOrAccountNUmberAndPin(String emailid,int pin)
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(customer_details);
			preparedstatement.setString(1, emailid);
			preparedstatement.setString(2, emailid);
			preparedstatement.setInt(3, pin);
			ResultSet resultSet=preparedstatement.executeQuery();
			if(resultSet.next())
			{
				String gender=resultSet.getString("Customer_Gender");
				String name=resultSet.getString("customer_name");
				Double amount=resultSet.getDouble("customer_Amount");
				
				int pin1=resultSet.getInt("customer_pin");
				long accountnumber=resultSet.getLong("customer_AccountNumber");
				CustomerDetails customerdetails=new CustomerDetails();
				customerdetails.setName(name);
				customerdetails.setGender(gender);
				customerdetails.setAmount(amount);
				customerdetails.setPin(pin1);
				customerdetails.setAccountnumber(accountnumber);
				return customerdetails;
			}
			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public CustomerDetails getBalanceUsingPin(int pin) {
		Connection connection;
		try {
			connection = DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(customer_details);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public boolean selectCustomerDetailsByUsingAccountNumberAndPin(long accountnumber,int pin)

	{
		try {
			Connection connection= DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(select_all_customer_details_by_Using_accountNUmber_and_pin);
			preparedstatement.setLong(1, accountnumber);
			preparedstatement.setInt(2, pin);
			ResultSet resultset=preparedstatement.executeQuery();
			if(resultset.isBeforeFirst())
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateAmountAfterDebit(double amount , long accnum,int pin) {
		try {
		Connection connection=	DatabaseConnection.forMySqlConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(updateAmount);
		preparedStatement.setDouble(1, amount);
		preparedStatement.setLong(2, accnum);
		preparedStatement.setInt(3, pin);
		int result=preparedStatement.executeUpdate();
		if(result!=0) {
			return true;
		}else {
			return false;
		}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateAmountAftercredit(double amount , long acccountnumber,int pin) {
		try {
		Connection connection=	DatabaseConnection.forMySqlConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(updateAmount);
		preparedStatement.setDouble(1, amount);
		preparedStatement.setLong(2, acccountnumber);
		preparedStatement.setInt(3, pin);
		int result=preparedStatement.executeUpdate();
		if(result!=0) {
			return true;
		}else {
			return false;
		}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateCustomer(CustomerDetails customer) {
	    String sql = "UPDATE customer_details SET customer_Name=?, customer_Eamilid=?, customer_MobileNumber=?";
	    try (Connection connection = DatabaseConnection.forMySqlConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setString(1, customer.getName());
	        ps.setString(2, customer.getEmailid());
	        ps.setLong(3, customer.getMobilenumber());
	        

	        int rows = ps.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    return false;
	}

	

}


