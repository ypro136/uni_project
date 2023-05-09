package Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.*;


import javax.swing.table.*;

public class Table extends javax.swing.JTable implements ActionListener{
	
    final private int buttonWidth = 85;
    final private int buttonHeight = 25;
    final private int WindowWidth = 900;
    final private int WindowHeight = 600;
    
    Object[] rowDataList;
    String[] columnsName;
    JTextField[] TFList;
    JLabel[] LabelList;
    
    String currentTableName;
    int columnCount;
    int rowCount;
    
    Connection connect;
    Statement stat;
    ResultSet rs;
    DefaultTableModel model;
    JFrame frame;
    
    
    Button insertButton = new Button("Insert", WindowWidth - (buttonWidth + 15), WindowHeight - (buttonHeight * 5 + 10) + buttonHeight * 0, buttonWidth, buttonHeight, this);
    Button updateButton = new Button("Update", WindowWidth - (buttonWidth + 15), WindowHeight - (buttonHeight * 5 + 10) + buttonHeight * 1, buttonWidth, buttonHeight, this);
    Button deleteButton = new Button("Delete", WindowWidth - (buttonWidth + 15), WindowHeight - (buttonHeight * 5 + 10) + buttonHeight * 2, buttonWidth, buttonHeight, this);
    Button searchButton = new Button("Search", WindowWidth - (buttonWidth + 15), WindowHeight - (buttonHeight * 5 + 10) + buttonHeight * 3, buttonWidth, buttonHeight, this);
    Button debugButton = new Button("Debug", WindowWidth - (buttonWidth + 15), WindowHeight - (buttonHeight * 5 + 10) + buttonHeight * 4, buttonWidth, buttonHeight, this);
        
    
    public Table(int rowCount, int columnCount, int fullWidth, String URL, Properties prop, String tableName)
    {
	super(rowCount,columnCount);
        
        
        
	setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        currentTableName = tableName;
        
        
        
        DataBaseConnection(URL,prop);
        
        frame = GuiElements(fullWidth);
        
        frame.setName(tableName);
        rowDataList=new Object[rowCount];
        
    }
    
    Object[] AddTableRow(ResultSet rs) throws SQLException{
        
        
        Object[] Data = new Object[columnCount];
        String columnName="" , columnType="";
            
        for (byte i = 0;i < columnCount; i++) 
        {
            columnName = columnsName[i];
            columnType = rs.getMetaData().getColumnTypeName(i+1);
            switch(columnType)
            {
            	case"money":
                case "int": Data[i] = rs.getInt(columnName); break;
                case "varchar":Data[i] = rs.getString(columnName); break;
            }
        }
        return Data;
    }
    
    
    public void DataBaseConnection(String URL,Properties prop){
        try{
            
            //Connecting To DataBase
            connect = DriverManager.getConnection(URL,prop);
            stat = connect.createStatement();
            rs = stat.executeQuery("select * from "+currentTableName+"");
            
            //Intializing Variables and Model
            
            columnCount = rs.getMetaData().getColumnCount();
            
            columnsName = new String[columnCount];
            for (Byte i = 0; i < columnCount; i++) 
                columnsName[i] = rs.getMetaData().getColumnName(i+1);
            model = new DefaultTableModel(null,columnsName);
            setModel(model);
            
            //Retriving Data
            while(rs.next()){
                rowDataList = AddTableRow(rs);
                model.addRow(rowDataList);
            }
            rowCount = getRowCount();

            //System.out.println(rs.getMetaData().getColumnTypeName(5));
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    void InsertButtonAction(){
        
        if(TFList[0].getText().isEmpty()) return;
        int ID = Integer.parseInt(TFList[0].getText());
        
        for (byte i = 0; i < rowCount; i++) {
            if(ID == Integer.parseInt(getValueAt(i, 0).toString())){
                JOptionPane.showMessageDialog(this, "Data Already Exist!");
                return;
            }    
        }
        
        try{
            //Create Statement
            String Statement = "insert into " + currentTableName + " values(";
            for (Byte i = 0; i < columnCount; i++) 
            { 
                Statement += " '"+TFList[i].getText()+"' ";
                rowDataList[i] = TFList[i].getText();
                if(i < columnCount - 1)
                    Statement+=" ,";
            }
            
           
            System.out.println(Statement+")");
            stat.executeUpdate(Statement+")");
            
            model.addRow(rowDataList);
            
        }catch(SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE,null,ex);  
        }
        
    }
    void UpdateButtonAction() {
        int index = getSelectedRow();
        
        try{
            String Statement = "update "+currentTableName+" set ";
            for (Byte i = 1; i < columnCount; i++) 
            { 
                switch(rs.getMetaData().getColumnTypeName(i+1))
                {
                    case "int": Statement += " "+ columnsName[i] +" = "+getValueAt(index,i)+" "; break;
                    case "varchar": Statement += " "+ columnsName[i] +" = '"+getValueAt(index,i)+"' "; break;
                }
                if(i < columnCount - 1)
                    Statement+=" , ";
            
            }
            Statement+=" where "+columnsName[0]+"="+getValueAt(index,0)+"";
            stat.executeUpdate(Statement);
            
            JOptionPane.showMessageDialog(this, "Update successfuly");
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
    }
    void DeleteButtonAction(){
        int index = getSelectedRow();
            System.out.println("delete from "+currentTableName+" where " + columnsName[0] +"= "+getValueAt(index,0)+" ");
        try{
            stat.executeUpdate("delete from "+currentTableName+" where " + columnsName[0] +" = "+getValueAt(index,0)+" ");
            
        }catch(SQLException ex){
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE,null,ex);  
        }
        model.removeRow(index);
    }
    void SearchButtonAction(){
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertButton) InsertButtonAction();
        if(e.getSource() == deleteButton) DeleteButtonAction();
        if(e.getSource() == updateButton) UpdateButtonAction();
        if(e.getSource() == searchButton) SearchButtonAction();
    }
    
    JFrame GuiElements(int fullWidth){
        JFrame frame = new JFrame(currentTableName);
        
        TFList = new TextField[columnCount];
        LabelList = new Label[columnCount];
        
        JScrollPane pane = new JScrollPane(this);
        pane.setBounds(13, 60, fullWidth, 400);
        
        
        
        frame.add(pane);
        frame.add(insertButton);
        frame.add(updateButton);
        frame.add(deleteButton);
        frame.add(searchButton);
        frame.add(debugButton);
        
        frame.setLayout(null);
        frame.setBounds(400, 100, WindowWidth, WindowHeight);
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        for (byte i = 0; i < columnCount; i++)
        {
            LabelList[i] = new Label(columnsName[i]+": ", 13 + (90 * i), 470, 85, 25);
            TFList[i] = new TextField("",  13 + (90 * i),  500, 85, 25);
            
            frame.add(LabelList[i]);
            frame.add(TFList[i]);
        }
        return frame;
    }
}