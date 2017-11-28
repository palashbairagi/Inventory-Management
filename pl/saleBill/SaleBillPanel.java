package pl.saleBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class SaleBillPanel extends JPanel
{
private JLabel saleBillNoCaption, productIdCaption, customerIdCaption, saleBillDateCaption;
private JLabel rateCaption, quantityCaption;
private JLabel discountCaption, totalAmountCaption, paidAmountCaption;
private JTextField saleBillNo, productId, customerId, saleBillDate, rate, quantity, discount, totalAmount, paidAmount;
private SimpleDateFormat simpleDateFormat;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public SaleBillPanel()
{
initComponents();
}
public void initComponents()
{
simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
saleBillNoCaption=new JLabel("Sale Bill Number");
productIdCaption=new JLabel("ProductId");
customerIdCaption=new JLabel("CustomerId");
saleBillDateCaption=new JLabel("Date");
rateCaption=new JLabel("Rate");
quantityCaption=new JLabel("Quantity");
discountCaption=new JLabel("Discount");
totalAmountCaption=new JLabel("Amount");
paidAmountCaption=new JLabel("Paid Amount");

saleBillNo=new JTextField(50);
productId=new JTextField(50);
customerId=new JTextField(50);
saleBillDate=new JTextField(50);
rate=new JTextField(50);
quantity=new JTextField(50);
discount=new JTextField(50);
totalAmount=new JTextField(50);
paidAmount=new JTextField(50);

titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);

saleBillNoCaption.setBounds(220,20,100,25);
saleBillNo.setBounds(300,20,100,25);
productIdCaption.setBounds(10,20,100,25);
productId.setBounds(120,20,100,25);
customerIdCaption.setBounds(10,50,100,25);
customerId.setBounds(120,50,100,25);
saleBillDateCaption.setBounds(10,80,100,25);
saleBillDate.setBounds(120,80,100,25);
rateCaption.setBounds(10,110,100,25);
rate.setBounds(120,110,100,25);
quantityCaption.setBounds(10,140,100,25);
quantity.setBounds(120,140,100,25);
discountCaption.setBounds(10,170,100,25);
discount.setBounds(120,170,100,25);
totalAmountCaption.setBounds(10,200,100,25);
totalAmount.setBounds(120,200,100,25);
paidAmountCaption.setBounds(10,230,100,25);
paidAmount.setBounds(120,230,100,25);


add(saleBillNoCaption);
add(saleBillNo);
add(productIdCaption);
add(productId);
add(customerIdCaption);
add(customerId);
add(saleBillDateCaption);
add(saleBillDate);
add(rateCaption);
add(rate);
add(quantityCaption);
add(quantity);
add(discountCaption);
add(discount);
add(totalAmountCaption);
add(totalAmount);
add(paidAmountCaption);
add(paidAmount);
}

private boolean isConvertedDateValid(java.util.Date date,String string)
{
String dt=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
return string.equals(dt);
}
    
public int getSaleBillNo()
{
return Integer.parseInt(saleBillNo.getText().trim());
}
public void setSaleBillNo(int saleBillNo)
{
this.saleBillNo.setText(String.valueOf(saleBillNo));
}

public int getProductId()
{
return Integer.parseInt(productId.getText().trim());
}
public void setProductId(int productId)
{
this.productId.setText(String.valueOf(productId));
}

public void setCustomerId(int customerId)
{
this.customerId.setText(String.valueOf(customerId));
}
public int getCustomerId()
{
return Integer.parseInt(customerId.getText().trim());
}

public java.util.Date getSaleBillDate() throws ParseException
{
java.util.Date saleBillDate =simpleDateFormat.parse(this.saleBillDate.getText().trim());
if(isConvertedDateValid(saleBillDate,this.saleBillDate.getText().trim()))
{
return saleBillDate;
}
else
{
throw new ParseException("Invalid sale bill date date",0);
}
}
public void setSaleBillDate(java.util.Date saleBillDate)
{
this.saleBillDate.setText(saleBillDate.getDate()+"-"+(saleBillDate.getMonth()+1)
+"-"+(saleBillDate.getYear()+1900));
}

public String getRate()
{
return this.rate.getText().trim();
}
public void setRate(String rate)
{
this.rate.setText(rate);
}

public String getQuantity()
{
return this.quantity.getText().trim();
}
public void setQuantity(String quantity)
{
this.quantity.setText(quantity);
}

public String getDiscount() 
{
return this.discount.getText().trim();
}
public void setDiscount(String discount)
{
this.discount.setText(discount);
}

public String getTotalAmount() 
{
return this.totalAmount.getText().trim();
}
public void setTotalAmount(String totalAmount)
{
this.totalAmount.setText(totalAmount);
}

public String getPaidAmount() 
{
return this.paidAmount.getText().trim();
}
public void setPaidAmount(String paidAmount)
{
this.paidAmount.setText(paidAmount);
}


public void requestFocusOnProductId()
{
productId.requestFocus();
}
public void reset()
{
saleBillNo.setText("");
customerId.setText("");
saleBillDate.setText("");
productId.setText("");
rate.setText("");
quantity.setText("");
discount.setText("");
totalAmount.setText("");
paidAmount.setText("");
}

public void set(int billNo, int productId, int customerId, java.util.Date billDate, String rate, String quantity, String discount, String totalAmount, String paidAmount)
{
setSaleBillNo(billNo);
setProductId(productId);
setCustomerId(customerId);
setSaleBillDate(billDate);
setRate(rate);
setQuantity(quantity);
setDiscount(discount);
setTotalAmount(totalAmount);
setPaidAmount(paidAmount);
}

public void enableAll()
{
saleBillNo.setEditable(true);
customerId.setEditable(true);
saleBillDate.setEditable(true);
productId.setEditable(true);
rate.setEditable(true);
quantity.setEditable(true);
discount.setEditable(true);
totalAmount.setEditable(true);
paidAmount.setEditable(true);
super.repaint();
}
public void disableAll()
{
saleBillNo.setEditable(false);
customerId.setEditable(false);
saleBillDate.setEditable(false);
productId.setEditable(false);
rate.setEditable(false);
quantity.setEditable(false);
discount.setEditable(false);
totalAmount.setEditable(false);
paidAmount.setEditable(false);
super.repaint();
}
}