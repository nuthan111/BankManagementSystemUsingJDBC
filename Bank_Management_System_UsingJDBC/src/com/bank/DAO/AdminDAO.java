package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.util.DatabaseConnection;

public class AdminDAO {
	public static final String admin_login="Select * from admin_details where admin_emailid=? and admin_password=?";
	
	public boolean selectAdminLoginByUsingEmailidAndPassword(String emailid,String password)
	{
		try {
			Connection connection=DatabaseConnection.forMySqlConnection();
			PreparedStatement preparedstatement=connection.prepareStatement(admin_login);
			preparedstatement.setString(1, emailid);
			preparedstatement.setString(2, password);
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

}
