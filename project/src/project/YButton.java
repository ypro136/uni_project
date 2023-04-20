package project;

import java.awt.Color;
import java.awt.event.ActionListener;

/**
 *
 * @author yasser
 */

// a button with a faster creation method using a constructor.
public class YButton  extends javax.swing.JButton{
    
    YButton(String lable, int x, int y, int width, int height, ActionListener Listener, Color theam)
    {
        super(lable);
        
        this.setBackground(theam);
        
        this.setBounds(x, y, width, height);
        
        this.setFocusable(false);
        
        this.addActionListener(Listener);
       
    }
    /**
     * creates a <code>JButton</code> using coordinates and transforms
     * 
     * 
     * 
     * 
     * 
     * */
    
}


