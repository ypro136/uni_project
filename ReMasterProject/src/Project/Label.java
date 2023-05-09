package Project;

public class Label  extends javax.swing.JLabel
{
    public Label(String lable, int posX, int posY, int width, int height)
    {
        super(lable);

        this.setBounds(posX, posY, width, height);
        
        this.setFocusable(false);

    }
    
}