package project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.*;

public class Ytable extends javax.swing.JTable{
	
	private int rowCount;
    private int columnCount;
	
	private DefaultTableModel tableModel = new DefaultTableModel();
	
	
	Ytable(int row_Count, int column_Count, int fullWidth)
	{
		super(row_Count, column_Count);
		
		rowCount = row_Count;
		columnCount = column_Count;
    
		tableModel.addColumn("ISBN");
		tableModel.addColumn("title");
		tableModel.addColumn("type");
		tableModel.addColumn("page count");
		tableModel.addColumn("price");
    
		tableModel.setColumnCount(columnCount);

		this.setBounds(13, 60, fullWidth, 400);
    	
		this.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
    
    	
		this.getColumnModel().getColumn(0).setWidth((fullWidth / 100) * 15);
		this.getColumnModel().getColumn(1).setPreferredWidth((fullWidth / 100) * 30);
		this.getColumnModel().getColumn(2).setPreferredWidth((fullWidth / 100) * 10);
		this.getColumnModel().getColumn(3).setPreferredWidth((fullWidth / 100) * 10);
		this.getColumnModel().getColumn(4).setPreferredWidth((fullWidth / 100) * 35);
		
		tableModel.addRow(new Object[] {"ISBN", "title", "type", "page count", "price"});
	}
	
	public void addData(Connection connection, String table_name) 
	{
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + table_name);
			while(resultSet.next())
			{
				tableModel.addRow(new Object[] {resultSet.getInt("ISBN"), resultSet.getString("title"), resultSet.getString("type"), resultSet.getInt("page count"), resultSet.getInt("price")});
			}
			this.setModel(tableModel);

			
		} catch (SQLException exception) {
			System.out.print(exception);
		}
	}
	
	public void updateData(Connection connection, String table_name) 
	{
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + table_name);
			
			// clears the table model and adds data back.
			tableModel = new DefaultTableModel();
			tableModel.addColumn("ISBN");
			tableModel.addColumn("title");
			tableModel.addColumn("type");
			tableModel.addColumn("page count");
			tableModel.addColumn("price");
			tableModel.setColumnCount(columnCount);
			tableModel.addRow(new Object[] {"ISBN", "title", "type", "page count", "price"});
			
			

			while(resultSet.next())
			{
				tableModel.addRow(new Object[] {resultSet.getInt("ISBN"), resultSet.getString("title"), resultSet.getString("type"), resultSet.getInt("page count"), resultSet.getInt("price")});
				
			}
			this.setModel(tableModel);

			
		} catch (SQLException exception) {
			System.out.print(exception);
		}

		
	}
	
	public void searchData(Connection connection, String table_name, String ISBN) 
	{
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where ISBN =" + ISBN);
			
			// clears the table model and adds data back.
			tableModel = new DefaultTableModel();
			tableModel.addColumn("ISBN");
			tableModel.addColumn("title");
			tableModel.addColumn("type");
			tableModel.addColumn("page count");
			tableModel.addColumn("price");
			tableModel.setColumnCount(columnCount);
			tableModel.addRow(new Object[] {"ISBN", "title", "type", "page count", "price"});
			
			

			while(resultSet.next())
			{
				tableModel.addRow(new Object[] {resultSet.getInt("ISBN"), resultSet.getString("title"), resultSet.getString("type"), resultSet.getInt("page count"), resultSet.getInt("price")});
				
			}
			this.setModel(tableModel);

			
		} catch (SQLException exception) {
			System.out.print(exception);
		}

		
	}
}
