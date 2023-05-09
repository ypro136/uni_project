package Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.*;

public class StartInterface extends javax.swing.JFrame implements ActionListener{
    final private int width = 85;
    final private int height = 25;
    final private int WindowWidth = 600;
    final private int WindowHeight = 200;
    Properties props = new Properties();
    
        
    // preparing user interface.
    private JTextField URL_textField = new TextField("jdbc:sqlserver://localhost;databaseName=Project;integratedSecurity=true;encrypt=true;trustServerCertificate=true;", 105,  20, width * 5, height);
    private JLabel URL_Label = new Label("URL:",  20,  20,  width,  height);
    
    private JTextField userName_textField = new TextField("mohamed",  105,  50, width, height);
    private JLabel userName_Label = new Label("Username:",  20,  50,  width,  height);
    
    private JTextField password_textField = new TextField("football",  105,  80, width, height);
    private JLabel password_Label = new Label("Password:",  20,  80,  width,  height);
    
    private JTextField table_textField = new TextField("Books",  115 + width + 50,  50, width, height);
    private JLabel table_Label = new Label("table:",  115 + width,  50,  width,  height);
    
    private JButton connectButton=new Button("connect",  20,  WindowHeight - (height + 50),  width,  height,  this);
    
    private JLabel statusLabel = new Label("Disconnected",  WindowWidth - (width + 30),  WindowHeight - (height + 50),  width,  height); 

    public StartInterface(){
        setLayout(null);
        
        add(URL_Label);
        add(URL_textField);
        add(userName_Label);
        add(userName_textField);
        add(password_Label);
        add(password_textField);
        add(connectButton);
        add(table_textField);
        add(table_Label);
        add(statusLabel);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        
        setSize(WindowWidth, WindowHeight);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==connectButton)
        {
            props.setProperty("User", userName_textField.getText());
            props.setProperty("Password", password_textField.getText());
            Table t = new Table(5, 5, 700,URL_textField.getText(),props,table_textField.getText());
        }
    }
    
}
