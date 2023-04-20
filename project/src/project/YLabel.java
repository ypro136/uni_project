package project;

import java.awt.Color;
import java.awt.event.ActionListener;


public class YLabel  extends javax.swing.JLabel{
    

	YLabel(String lable, int x, int y, int width, int height)
    {
        super(lable);

        this.setBounds(x, y, width, height);
        
        this.setFocusable(false);

    }
    /**
     * creates a <code>JLabel</code> using coordinates and transforms
     * 
     * 
     * 
     * 
     * 
     * */
    
}