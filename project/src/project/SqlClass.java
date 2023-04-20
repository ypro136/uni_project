package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


// SQL manager
public class SqlClass {
    
public static Connection con;


// Establishes a connection to SQL.
public static Connection Connect(String url, Properties props) throws SQLException
{ 
	try {

			// Attempts connection.
			con = DriverManager.getConnection(url, props);
			return con;
		} 
		catch (SQLException exception) 
		{
			// creates logger to returns errors from SQL.
			Logger.getLogger(SqlClass.class.getName()).log(Level.SEVERE, null, exception);
			// throws error for convenience.
			System.out.println("Connection ERROR, Exception is : " + exception);
			return con;
		}
	
	}

// closes the connection to SQL.
public static void Close () { 
	try 
	{ 
		con.close();
	}
	catch (SQLException exception) 
	{
		// throws error
		System.out.println("Connection ERROR, Exception is: " + exception); 
	}
}

// to use SQL commands.
public static boolean executeNonquary (String sqlStatement) { 
	try
	{
			// constructs a statement.
			Statement stmt = con.createStatement();
			// executes said statement 
			stmt.execute(sqlStatement);
			return true;
	}
	catch (SQLException exception)
	{
		// throws error
		System.out.println("execution ERROR : " + exception);
		return false;
	}
}
}