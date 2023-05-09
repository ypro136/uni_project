package Project;

public class TextField  extends javax.swing.JTextField{
    
    public TextField(String intialTxt, int posX, int posY, int width, int height)
    {
        super(intialTxt);
        this.setBounds(posX, posY, width, height);
        this.setFocusable(true);
    }
    
}