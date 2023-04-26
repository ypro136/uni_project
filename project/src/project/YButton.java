package project;

import java.awt.Color;
import java.awt.event.ActionListener;

/**
 *
 * @author yasser
 */

// a button with a faster creation method using a constructor.
public class YButton  extends javax.swing.JButton{
	
	final private Color Background = new Color(187, 191, 202);
	final private Color Foreground = new Color(73, 84, 100);
    
    YButton(String lable, int x, int y, int width, int height, ActionListener Listener)
    {
        super(lable);
        
        this.setBackground(Background);
        
        this.setBounds(x, y, width, height);
        
        this.setFocusable(false);
        
        this.addActionListener(Listener);
        
        this.setForeground(Foreground);
       
        /**
         * creates a <code>JButton</code> using coordinates and transforms
         * 
         * 
         * 
         * 
         * 
         * */
    }
    
    
}


