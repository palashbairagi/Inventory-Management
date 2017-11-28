package pl.saleBill;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

import dl.customer.*;

public class FindCustomerPanel extends JPanel implements KeyListener
{
private JLabel findCustomerCaption;
private String name[];
private  Vector<CustomerInterface>customers;
private CustomerDAO customerDAO;
private JComboBox findCustomer;
private TitledBorder titledBorder;

public FindCustomerPanel()
{
initComponents();
}

public void initComponents()
{
getCustomer();
findCustomerCaption=new JLabel("Find Customer");
findCustomer=new JComboBox(name);
findCustomer.setEditable(false);
AutoCompleteDecorator.decorate(findCustomer);
//titledBorder=BorderFactory.createTitledBorder("Search");
//setBorder(titledBorder);

setLayout(null);
findCustomerCaption.setBounds(10,20,90,25);
findCustomer.setBounds(110,20,100,25);
add(findCustomerCaption);
add(findCustomer);
findCustomer.addKeyListener(this);
}

public String getName()
{
return findCustomer.getSelectedItem().toString();
}

public void reset()
{
findCustomer.setSelectedIndex(0);
}

public void enableAll()
{
findCustomerCaption.setText("Find what");
findCustomer.setVisible(true);
findCustomer.setSelectedIndex(0);
super.repaint();
}

public void disableAll()
{
findCustomerCaption.setText("");
findCustomer.setVisible(false);
super.repaint();
}

public void keyPressed(KeyEvent ev){System.out.println("Pressed");}
public void keyReleased(KeyEvent ev){System.out.println("Released");}
public void keyTyped(KeyEvent ev){System.out.println("Typed");}

public void getCustomer()
{
customerDAO=new CustomerDAO();
try
{
customers=customerDAO.getNameBySort();
}
catch(Exception e)
{
System.out.println("Customer Autofill ComboBox");
System.out.println(e);
}
try
{
name=new String[customers.size()+1];
name[0]="";
for(int i=0;i<customers.size();i++)
{
CustomerInterface customer=customers.get(i);
name[i+1]=customer.getName();
}
}catch(Exception e)
{
System.out.println("Customer Autofill ComboBox");
System.out.println(e);
}
}
/*
public boolean validate(MainFrame customerUI)
{
if(InputValidator.isComboBoxEmpty(customerUI,findCustomer,"Search Field required",true))return false;
return true;
}
*/
}