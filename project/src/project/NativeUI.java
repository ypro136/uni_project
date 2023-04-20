package project;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.event.EventListenerList;

/**
 *
 * @author yasser
 */


public class NativeUI extends Frame implements ActionListener{
    
    final private int buttonWidth = 85;
    final private int buttonHeight = 25;
    final private Color theam = new Color(80, 140, 150);
    Connection connection;
    
    // Event Listener List for connection and SQL Events.
    EventListenerList eventListenerList = new EventListenerList();

    // constructor.
    public NativeUI(String title, Connection conn)
    {
        // implements a Frame constructor that generates a initially invisible Frame object with a title.
        super(title);
        
        // saves connection (not necessary).
        connection = conn;
        
        // a short-cut to use the close button.
        addWindowListener (new WindowAdapter() {@Override public void windowClosing (WindowEvent event){dispose();}});    
        
        // defines a size and position for the frame.
        this.setBounds(10, 10, 600, 600);
        
        prepareGUI();

        this.setVisible(true);
    }
    
    // Contains all the creation and modeling of buttons , labels, TextField and Action Listeners. 
    public void prepareGUI()
    {
        
        // set a new clean layout.
        setLayout(null);

        // creates and positions status Label.
        Label statusLabel = new Label();
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350,100);
        
        // preparing Action Listeners for the buttons using lambda expressions.
        ActionListener okActionListener = (ActionEvent event) -> {
            statusLabel.setText("OK Button clicked.");
            fireConnectionEvent(new ConnectionEvent(this, connection));
        };
        ActionListener submitActionListener = (ActionEvent event) -> {
            statusLabel.setText("submit Button clicked.");
        };
        ActionListener cancelActionListener = (ActionEvent event) -> {
            statusLabel.setText("cancel Button clicked.");
        };
        
        // creates buttons and adds buttons and labels.
        YButton okButton = new YButton("ok", 10, 40, buttonWidth, buttonHeight, okActionListener, theam);
        YButton submitButton = new YButton("Submit", 10, 40 + buttonHeight * 1, buttonWidth, buttonHeight, submitActionListener, theam);
        YButton cancelButton = new YButton("Cancel", 10, 40 + buttonHeight * 2, buttonWidth, buttonHeight, cancelActionListener, theam);
        add(okButton);
        add(submitButton);
        add(cancelButton);
        add(statusLabel);
        
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
