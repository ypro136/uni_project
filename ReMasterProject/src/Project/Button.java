package Project;

import java.awt.event.ActionListener;

public class Button extends javax.swing.JButton{
    
    
    public Button(String lable, int posX, int posY, int width, int height, ActionListener Listener)
    {
        super(lable);
        setBounds(posX, posY, width, height);
        setFocusable(false);
        addActionListener(Listener);
    }
    
    
}


