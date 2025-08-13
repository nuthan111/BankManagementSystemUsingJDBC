package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import com.bank.DTO.TransactionDetails;
import com.bank.util.DatabaseConnection;

public class TransactionDetailsDAO {
	
	private final static String insert_transaction_details="insert into transaction_details(transaction_type, transaction_amount, transaction_time, transaction_date, balance_amount, transaction_status, customer_account_number)values(?,?,?,?,?,?,?)";
	
	public boolean inserttransactiondetails(TransactionDetails transactiondetails)
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement ps=connection.prepareStatement(insert_transaction_details);
			ps.setString(1,transactiondetails.getTransactiontype());
			ps.setDouble(2,transactiondetails.getTransactionamount());
			ps.setTime(3,Time.valueOf(transactiondetails.getTransactiontime()));
			ps.setDate(4, Date.valueOf(transactiondetails.getTransactiondate()));
			ps.setDouble(5, transactiondetails.getBalanceamount());
			ps.setString(6, transactiondetails.getTransactionstatus());
			ps.setLong(7, transactiondetails.getCustomeraccountnumber());
			int result=ps.executeUpdate();
			if(result!=0)
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
			

}
