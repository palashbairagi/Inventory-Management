package pl.customer.find;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import pl.*;

public class FindCustomerFrame extends JFrame implements ActionListener, CrossButtonListener
{
public FindCustomerFrame()
{
super("Find Customer");
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
