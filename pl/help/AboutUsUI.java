package pl.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import pl.*;

public class AboutUsUI extends JFrame implements ActionListener, CrossButtonListener
{
public AboutUsUI()
{
super("About Us");
setLayout(null);
CrossButtonHandler cbh=new CrossButtonHandler(this);
addWindowListener(cbh);
setVisible(true);
setSize(400,200);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
}

public void actionPerformed(ActionEvent ev)
{
}

public void windowClosing(WindowEvent ev)
{
dispose();
}

}
