package project;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.event.EventListenerList;

class Login_and_connection extends JFrame {
	
	// Visual customization.
	final private int width = 85;
    final private int height = 25;
    final private int WindowWidth = 600;
    final private int WindowHeight = 200;
    final private Color theam = new Color(80, 140, 150);

    // preparing user interface.
    private JTextField URL_textField = new YTextField("jdbc:sqlserver://localhost;databaseName=Project;integratedSecurity=true;encrypt=true;trustServerCertificate=true;", 105,  20, width * 5, height);
    private JLabel URL_Label = new YLabel("URL:",  20,  20,  width,  height);
    private JTextField userName_textField = new YTextField("",  105,  50, width, height);;
    private JLabel userName_Label = new YLabel("Username:",  20,  50,  width,  height);
    private JTextField password_textField = new YTextField("",  105,  80, width, height);
    private JLabel password_Label = new YLabel("Password:",  20,  80,  width,  height);
    private JButton connectButton;
    private JLabel statusLabel = new YLabel("Disconnected",  WindowWidth - (width + 30),  WindowHeight - (height + 50),  width,  height);    
    
    // Event Listener List for connection and SQL Events.
    EventListenerList eventListenerList = new EventListenerList();

    // constructor.
    public Login_and_connection(String title) {
    	// implements a Frame constructor that generates a initially invisible Frame object with a title.
        super(title);
        
        // preparing Action Listener for the connect button using lambda expression.
        ActionListener connectButtonActionListener = (ActionEvent e) -> {
            // gets URL, user name and password to store them.
            String url = URL_textField.getText().trim();
            String username = userName_textField.getText().trim();
            String password = password_textField.getText().trim();
            
            // Prepares Properties to input user name and password if necessary.
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            
            Connection conn = null;
            
            try {
                conn = SqlClass.Connect(url, props);
                fireConnectionEvent(new ConnectionEvent(this, conn));
                statusLabel.setText("Connected");
                
                // creates main program frame.
                NativeUI frame = new NativeUI("Native java UI", conn);
                frame.addConnectionEventListener(new ConnectionEventListener() {
                    @Override
                    public void connectionEstablished(ConnectionEvent event) {
                        System.out.println("Connection active: " + event.getConnection());
                    }
                });
                this.dispose();
                // exits the login window but not the program.
                /**<code>this.dispose();</code>**/
            } catch (SQLException ex) {
            	// throws error
                statusLabel.setText("Connection failed: " + ex.getMessage());
            }
        
    };
    
    

        // Create connection button.
        connectButton = new YButton("connect",  20,  WindowHeight - (height + 50),  width,  height,  connectButtonActionListener,  theam);
        
        
        // set a new clean layout.
        setLayout(null);
        
        // adds UI elements.
        this.add(URL_Label);
        this.add(URL_textField);
        this.add(userName_Label);
        this.add(userName_textField);
        this.add(password_Label);
        this.add(password_textField);
        this.add(connectButton);
        this.add(statusLabel);
        
        // Set window properties
        addWindowListener (new WindowAdapter() {@Override public void windowClosing (WindowEvent e){dispose();}});    
        setSize(WindowWidth, WindowHeight);
        setLocationRelativeTo(null);
        setVisible(true);
    }

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