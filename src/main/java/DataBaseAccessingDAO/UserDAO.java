package DataBaseAccessingDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataBaseAccessingModel.User;

//this dao class provides CRUD database operations for the table users in the database.
public class UserDAO {
	
	private String JDBCurl = "jdbc:mysql://localhost:3306/student_reg";
	private String JDBCuser = "root";
	private String JDBCpassword = "12345";
	
	private String selectData = "SELECT * FROM sdata;";
	private String inputData = "INSERT INTO sdata(sname,sroll,saddress) value(?,?,?);";
	private String selectDataByRoll = "SELECT * FROM sdata WHERE sroll=?;";
	private String updateDataById = "UPDATE sdata set sname= ?,sroll = ?,saddress = ? where sid = ?;";
	private String deleteData = "delete from sdata where sid = ?;";
	
	//connecting to the JDBC
	protected Connection getConnection()
	{
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(JDBCurl,JDBCuser,JDBCpassword);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	//Create User or insert user
	public void insertUser(User user) throws SQLException
	{
		//here we used try with resource because 
		//it automatically closes the connection resource.
		try(Connection connection = getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(inputData);
			
	
			statement.setString(1,user.getName());
			statement.setString(2, user.getRoll());
			statement.setString(3,user.getAddress());
			statement.executeUpdate();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Update User
	public boolean updateUser(User user)
	{
		boolean rowUpdated = false;
		try(Connection connection = getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(updateDataById);
			
			statement.setString(1,user.getName());
			statement.setString(2, user.getRoll());
			statement.setString(3,user.getAddress());
			statement.setInt(4,user.getId());
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rowUpdated;
	}
	//Select user by roll number
	public User selectUser(String roll)
	{
		User user = null;
		try(Connection connection = getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(selectDataByRoll);
		
			statement.setString(1, roll);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String sroll = rs.getString("sroll");
				String address = rs.getString("saddress");
				
				user = new User(id,name,sroll,address);
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}
	
	//select all user
	public List<User> selectAllUser()
	{
		List<User> users = new ArrayList<>();
		try(Connection connection = getConnection())
		{
			PreparedStatement statement = connection.prepareStatement(selectData);
	
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String sroll = rs.getString("sroll");
				String address = rs.getString("saddress");
				
				users.add(new User(id,name,sroll,address));
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return users;
	}
	
	//delete user 
	public boolean deleteUser(int id)
	{
		boolean rowDeleted = false;
		try(Connection connection = getConnection(); 
				PreparedStatement statement = connection.prepareStatement(deleteData);)
		{
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowDeleted;
	}
	

}
