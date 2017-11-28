package pl.saleBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class BillInfoPanel extends JPanel
{
private JLabel billNumberCaption, billDateCaption;
private JTextField billNumber, billDate;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public BillInfoPanel()
{
initComponents();
}
public void initComponents()
{
billNumberCaption=new JLabel("No.");
billDateCaption=new JLabel("Date");

billNumber=new JTextField();
billDate=new JTextField(); 

//titledBorder=BorderFactory.createTitledBorder("Bill");
//setBorder(titledBorder);
setLayout(null);

billNumberCaption.setBounds(15,25,30,25); 
billDateCaption.setBounds(180,25,50,25);
billNumber.setBounds(40,25,100,25);
billDate.setBounds(220,25,100,25);

add(billNumberCaption); 
add(billDateCaption);
add(billNumber); 
add(billDate);

}

public String getBillNumber()
{
return billNumber.getText().trim();
}
public void setBillNumber(String billNumber)
{
this.billNumber.setText(billNumber);
}
public String getBillDate() 
{
return this.billDate.getText().trim();
}
public void setBillDate(String date)
{
this.billDate.setText(date);
}

public void reset()
{
billNumber.setText("");
billDate.setText("");
}

public void set(String billNumber, String billDate)
{
setBillNumber(billNumber); 
setBillDate(billDate);
}

public void enableAll()
{
billNumber.setEditable(true);
billDate.setEditable(true);
super.repaint();
}
public void disableAll()
{
billNumber.setEditable(false);
billDate.setEditable(false);
super.repaint();
}
}