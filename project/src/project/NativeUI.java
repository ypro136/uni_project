package project;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

/**
 *
 * @author yasser
 */


public class NativeUI extends Frame implements ActionListener{
    
    final private int buttonWidth = 85;
    final private int buttonHeight = 25;
    final private int labelWidth = 200;
    final private int labelHeight = 25;
    final private int WindowWidth = 900;
    final private int WindowHeight = 600;
    final private Color theam = new Color(80, 140, 150);
    Connection connection;
    
    // preparing user interface.
    private JTextField ISBN_textField = new YTextField("",  13,  470, 85, 25);
    private JTextField title_textField = new YTextField("",  13 + 85,  470, 85, 25);
    private JTextField type_textField = new YTextField("",  13 + 85 + 85,  470, 85, 25);
    private JTextField page_count_textField = new YTextField("",  13 + 85 + 85 + 85,  470, 85, 25);
    private JTextField price_textField = new YTextField("",  13 + 85 + 85 + 85 + 85,  470, 85, 25);
    
    // Event Listener List for connection and SQL Events.
    EventListenerList eventListenerList = new EventListenerList();

    // constructor.
    public NativeUI(String title, Connection conn, String table)
    {
        // implements a Frame constructor that generates a initially invisible Frame object with a title.
        super(title);
        
        // saves connection (not necessary).
        connection = conn;
        
        // a short-cut to use the close button.
        addWindowListener (new WindowAdapter() {@Override public void windowClosing (WindowEvent event){dispose();}});    
        
        // defines a size and position for the frame.
        this.setBounds(10, 10, WindowWidth, WindowHeight);
        
        prepareGUI(table);

        this.setVisible(true);
    }
    
    // Contains all the creation and modeling of buttons , labels, TextField and Action Listeners. 
    public void prepareGUI(String table_name)
    {
        
        // set a new clean layout.
        setLayout(null);

        // creates and positions status Label.
        Label statusLabel = new Label();
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setBounds((WindowWidth / 2) - (labelWidth / 2), WindowHeight - 30, labelWidth, labelHeight);
        
        // preparing Action Listeners for the buttons using lambda expressions.
        ActionListener insertActionListener = (ActionEvent event) -> {
        	SqlClass.executeNonquary("insert into " + table_name + " values(" + ISBN_textField.getText() + ",'" + title_textField.getText() + "'," + type_textField.getText() + "'," + page_count_textField.getText() +"'," + price_textField.getText() + ")");
            statusLabel.setText("insert Button clicked.");
        };
        ActionListener updateActionListener = (ActionEvent event) -> {
            statusLabel.setText("update Button clicked.");
        };
        ActionListener deleteActionListener = (ActionEvent event) -> {
            statusLabel.setText("delete Button clicked.");
        };
        ActionListener searchActionListener = (ActionEvent event) -> {
            statusLabel.setText("search Button clicked.");
        };
        ActionListener debugActionListener = (ActionEvent event) -> {
            statusLabel.setText("debug Button clicked.");
            fireConnectionEvent(new ConnectionEvent(this, connection));
        };
        
        // creates buttons and adds buttons and labels.
        YButton insertButton = new YButton("insert", WindowWidth - (buttonWidth + 10), 600 - (buttonHeight * 5 + 10) + buttonHeight * 0, buttonWidth, buttonHeight, insertActionListener, theam);
        YButton updateButton = new YButton("update", WindowWidth - (buttonWidth + 10), 600 - (buttonHeight * 5 + 10) + buttonHeight * 1, buttonWidth, buttonHeight, updateActionListener, theam);
        YButton deleteButton = new YButton("delete", WindowWidth - (buttonWidth + 10), 600 - (buttonHeight * 5 + 10) + buttonHeight * 2, buttonWidth, buttonHeight, deleteActionListener, theam);
        YButton searchButton = new YButton("search", WindowWidth - (buttonWidth + 10), 600 - (buttonHeight * 5 + 10) + buttonHeight * 3, buttonWidth, buttonHeight, searchActionListener, theam);
        YButton debugButton = new YButton("debug", WindowWidth - (buttonWidth + 10), 600 - (buttonHeight * 5 + 10) + buttonHeight * 4, buttonWidth, buttonHeight, debugActionListener, theam);
        add(insertButton);
        add(updateButton);
        add(deleteButton);
        add(searchButton);
        add(debugButton);
        add(statusLabel);
        
        // adds text Fields.
        add(ISBN_textField);
        add(title_textField);
        add(type_textField);
        add(page_count_textField);
        add(price_textField);
        
        int rowCount = 3;
        int columnCount = 5;
        
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel();
        
        
        tableModel.addColumn("ISBN");
        tableModel.addColumn("title");
        tableModel.addColumn("type");
        tableModel.addColumn("page count");
        tableModel.addColumn("price");
        
        tableModel.setColumnCount(columnCount);
        //tableModel.setRowCount(rowCount);

        javax.swing.JTable table = new javax.swing.JTable(rowCount, columnCount);
        
        table.setModel(tableModel);
        
        table.setBounds(13, 60, WindowWidth - 25, 400);
        
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        
        	
        table.getColumnModel().getColumn(0).setWidth(((WindowWidth - 25) / 100) * 15);
        table.getColumnModel().getColumn(1).setPreferredWidth(((WindowWidth - 25) / 100) * 30);
        table.getColumnModel().getColumn(2).setPreferredWidth(((WindowWidth - 25) / 100) * 10);
        table.getColumnModel().getColumn(3).setPreferredWidth(((WindowWidth - 25) / 100) * 5);
        table.getColumnModel().getColumn(4).setPreferredWidth(((WindowWidth - 25) / 100) * 40);
        	
        
        setResizable(false);
        
        add(table);
        
        try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from " + table_name);
			while(resultSet.next())
			{
				tableModel.addRow(new Object[] {resultSet.getInt("ISBN"), resultSet.getString("title"), resultSet.getString("type"), resultSet.getInt("page count"), resultSet.getInt("price")});
			}
			
		} catch (SQLException exception) {
			System.out.print(exception);
		}
        
        setVisible(true);
   }
    
  
    // Override of actionPerformed function because lambda expressions don't count.
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not used."); 
    }
    
    // preparing connection and SQL event system.
    public void addConnectionEventListener(ConnectionEventListener listener) {
		eventListenerList.add(ConnectionEventListener.class, listener);
    }
    public void removeConnectionEventListener(ConnectionEventListener listener) {
    	eventListenerList.remove(ConnectionEventListener.class, listener);
    }
    protected void fireConnectionEvent(ConnectionEvent event) {
        Object[] listeners = eventListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == ConnectionEventListener.class) {
                ((ConnectionEventListener)listeners[i+1]).connectionEstablished(event);
            }
        }
    }
    
    
    
    
}
