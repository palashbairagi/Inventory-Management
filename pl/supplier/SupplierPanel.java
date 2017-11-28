package pl.supplier;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
import pl.*;

public class SupplierPanel extends JPanel
{
private JLabel nameCaption;
private JLabel contactNumber1Caption, contactNumber2Caption;
private JLabel addressCaption, openingBalanceCaption;
private JTextField id,name,address,openingBalance;
private ContactNumberTextField contactNumber1,contactNumber2;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public SupplierPanel()
{
initComponents();
}
 public void initComponents()
{
nameCaption=new JLabel("Name");
contactNumber1Caption=new JLabel("Mobile Number");
contactNumber2Caption=new JLabel("Phone Number");
addressCaption=new JLabel("Address");
openingBalanceCaption=new JLabel("Opening Balance");

id=new JTextField(50);
name=new JTextField(50);
contactNumber1=new ContactNumberTextField();
contactNumber2=new ContactNumberTextField();
address=new JTextField(50);
openingBalance=new JTextField(50);

titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);

nameCaption.setBounds(10,20,100,25);
name.setBounds(50,20,200,25);
contactNumber1Caption.setBounds(10,55,100,25);
contactNumber1.setBounds(100,55,50,25);
contactNumber2Caption.setBounds(10,90,100,25);
contactNumber2.setBounds(100,90,50,25);
addressCaption.setBounds(10,125,160,25);
address.setBounds(175,125,100,25);
openingBalanceCaption.setBounds(10,160,100,25);
openingBalance.setBounds(100,160,200,25);

add(id);
add(nameCaption);
add(name);
add(contactNumber1Caption);
add(contactNumber1);
add(contactNumber2Caption);
add(contactNumber2);
add(addressCaption);
add(address);
add(openingBalanceCaption);
add(openingBalance);
}
    
public int getId()
{
return Integer.parseInt(id.getText().trim());
}
public void setId(int id)
{
this.id.setText(String.valueOf(id));
}
public String getName()
{
return name.getText().trim();
}
public void setName(String name)
{
this.name.setText(name);
}
public String getContactNumber1()
{
return this.contactNumber1.getText().trim();
}
public void setContactNumber1(String contactNumber1)
{
this.contactNumber1.setText(contactNumber1);
}
public String getContactNumber2()
{
return this.contactNumber2.getText().trim();
}
public void setContactNumber2(String contactNumber2)
{
this.contactNumber2.setText(contactNumber2);
}
public String getAddress() 
{
return this.address.getText().trim();
}
public void setAddress(String address)
{
this.address.setText(address);
}
public String getOpeningBalance() 
{
return this.openingBalance.getText().trim();
}
public void setOpeningBalance(String openingBalance)
{
this.openingBalance.setText(openingBalance);
}

public void requestFocusOnName()
{
name.requestFocus();
}
public void reset()
{
name.setText("");
contactNumber1.setText("");
contactNumber2.setText("");
address.setText("");
openingBalance.setText("");
}

public void set(int id, String name, String contactNumber1, String contactNumber2,
String address, String openingBalance)
{
setId(id);
setName(name);
setContactNumber1(contactNumber1);
setContactNumber2(contactNumber2);
setAddress(address);
setOpeningBalance(openingBalance);
}

public void enableAll()
{
name.setEditable(true);
contactNumber1.setEditable(true);
contactNumber2.setEditable(true);
address.setEditable(true);
openingBalance.setEditable(true);
super.repaint();
}
public void disableAll()
{
name.setEditable(false);
contactNumber1.setEditable(false);
contactNumber2.setEditable(false);
address.setEditable(false);
openingBalance.setEditable(false);
super.repaint();
}

public boolean validate(SupplierUI supplierUI)
{
if(InputValidator.isTextFieldEmpty(supplierUI,name,"Name required",true))return false;
if(InputValidator.isTextFieldEmpty(supplierUI,openingBalance,"Opening Balance required",true))return false;
return true;
}

}