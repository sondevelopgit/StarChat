package kr.co.star.starchat.server.db;

import java.sql.*;
import java.io.PrintStream;

public class DatabaseHandler {
	private static DatabaseHandler instance = new DatabaseHandler();
	
	private DatabaseHandler() {
	}
	
	public static DatabaseHandler getInstance() {
		return instance;
	}

	/* DB연결 후 커넥션 반환 */
    public static Connection GetDatabaseConnection() {
    	Connection connection = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		
			//String dbUrl = "jdbc:mysql://localhost:3306/starchat";
	    	String dbUrl = "jdbc:mysql://localhost:3306/starchat?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&autoReconnect=true&useSSL=false";
	        String user = "star";
	        String pass = "password";
	        
            connection = DriverManager.getConnection(dbUrl, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
        
        return connection;
    }
	
    /*  */
    public static boolean CheckLoginUser(String userID, String userPW) { //get input from login system module
    	Connection connection = GetDatabaseConnection();
    	String query = "select user_id from user where login_id = '" + userID + "' and password = '" + userPW + "'";
        //String checkQuery = "select * from registeredUser where user = ? and pass = ? ";
        
        PreparedStatement preparedStatement = null;
        boolean status = false; //initially false

        try {
            preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setString(1, userID); // dynamically sending username
            //preparedStatement.setString(2, userPW); // sending password to checkquery statement

            ResultSet resultSet = preparedStatement.executeQuery();

            /*
            while (!resultSet.isLast()) {
            	status = resultSet.next();
            	System.out.println("result: " + status);
                //return status;
            } 
            */
            
            status = resultSet.next();
            preparedStatement.close();
            
            return status;
        } catch (SQLException e) {
        	e.getLocalizedMessage();
            e.printStackTrace();
        }
        return status;
    }

} // class
