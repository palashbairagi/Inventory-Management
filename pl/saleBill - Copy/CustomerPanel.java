package pl.saleBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class CustomerPanel extends JPanel
{
private JLabel customerNameCaption, customerContactNumber1Caption, customerContactNumber2Caption, customerAddressCaption;
private JTextField customerId, customerName, customerContactNumber1, customerContactNumber2, customerAddress;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public CustomerPanel()
{
initComponents();
}
public void initComponents()
{
customerNameCaption=new JLabel("Name");
customerContactNumber1Caption=new JLabel("Mobile Number"); 
customerContactNumber2Caption=new JLabel("Phone Number");
customerAddressCaption=new JLabel("Address");

customerId=new JTextField(); 
customerName=new JTextField();
customerContactNumber1=new JTextField(); 
customerContactNumber2=new JTextField();
customerAddress=new JTextField();

//titledBorder=BorderFactory.createTitledBorder("Customer");
//setBorder(titledBorder);
setLayout(null);

customerNameCaption.setBounds(15,25,100,25); 
customerContactNumber1Caption.setBounds(255,25,100,25); customerContactNumber2Caption.setBounds(15,55,100,25);
customerAddressCaption.setBounds(255,55,100,25);
customerName.setBounds(115,25,100,25);
customerContactNumber1.setBounds(355,25,100,25);
customerContactNumber2.setBounds(115,55,100,25); 
customerAddress.setBounds(355,55,100,25);

add(customerId);
add(customerNameCaption); 
add(customerContactNumber1Caption);
add(customerContactNumber2Caption); 
add(customerAddressCaption);
add(customerName); 
add(customerContactNumber1); 
add(customerContactNumber2); 
add(customerAddress);

}

public int getCustomerId()
{
return Integer.parseInt(customerId.getText().trim());
}
public void setCustomerId(int id)
{
this.customerId.setText(String.valueOf(id));
}
public String getCustomerName()
{
return customerName.getText().trim();
}
public void setCustomerName(String name)
{
this.customerName.setText(name);
}
public String getCustomerContactNumber1()
{
return this.customerContactNumber1.getText().trim();
}
public void setCustomerContactNumber1(String contactNumber1)
{
this.customerContactNumber1.setText(contactNumber1);
}
public String getCustomerContactNumber2()
{
return this.customerContactNumber2.getText().trim();
}
public void setCustomerContactNumber2(String contactNumber2)
{
this.customerContactNumber2.setText(contactNumber2);
}
public String getCustomerAddress() 
{
return this.customerAddress.getText().trim();
}
public void setCustomerAddress(String address)
{
this.customerAddress.setText(address);
}

public void reset()
{
customerName.setText("");
customerContactNumber1.setText("");
customerContactNumber2.setText("");
customerAddress.setText("");
}

public void set(int id, String name, String contactNumber1, String contactNumber2,
String address)
{
setCustomerId(id);
setCustomerName(name); 
setCustomerContactNumber1(contactNumber1);
setCustomerContactNumber2(contactNumber2);
setCustomerAddress(address);
}

public void enableAll()
{
customerName.setEditable(true);
customerContactNumber1.setEditable(true);
customerContactNumber2.setEditable(true);
customerAddress.setEditable(true);
super.repaint();
}
public void disableAll()
{
customerName.setEditable(false);
customerContactNumber1.setEditable(false);
customerContactNumber2.setEditable(false);
customerAddress.setEditable(false);
super.repaint();
}
}