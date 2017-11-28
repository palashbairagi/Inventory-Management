package pl.purchaseBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class PurchaseBillPanel extends JPanel
{
private JLabel supplierIdCaption;
private JLabel rateCaption, quantityCaption;
private JLabel discountCaption, totalAmountCaption;
private JTextField id,supplierId,rate,quantity,discount,totalAmount;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public PurchaseBillPanel()
{
initComponents();
}
 public void initComponents()
{
supplierIdCaption=new JLabel("SupplierId");
rateCaption=new JLabel("Rate");
quantityCaption=new JLabel("Quantity");
discountCaption=new JLabel("Discount");
totalAmountCaption=new JLabel("Total");

id=new JTextField(50);
supplierId=new JTextField(50);
rate=new JTextField(50);
quantity=new JTextField(50);
discount=new JTextField(50);
totalAmount=new JTextField(50);

titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);

supplierIdCaption.setBounds(10,20,100,25);
supplierId.setBounds(50,20,200,25);
rateCaption.setBounds(10,55,100,25);
rate.setBounds(100,55,50,25);
quantityCaption.setBounds(10,90,100,25);
quantity.setBounds(100,90,50,25);
discountCaption.setBounds(10,125,160,25);
discount.setBounds(175,125,100,25);
totalAmountCaption.setBounds(10,160,100,25);
totalAmount.setBounds(100,160,200,25);

add(id);
add(supplierIdCaption);
add(supplierId);
add(rateCaption);
add(rate);
add(quantityCaption);
add(quantity);
add(discountCaption);
add(discount);
add(totalAmountCaption);
add(totalAmount);
}
    
public int getId()
{
return Integer.parseInt(id.getText().trim());
}
public void setId(int id)
{
this.id.setText(String.valueOf(id));
}
public int getSupplierId()
{
return Integer.parseInt(supplierId.getText().trim());
}
public void setSupplierId(int supplierId)
{
this.supplierId.setText(String.valueOf(supplierId));
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

public void requestFocusOnSupplierId()
{
supplierId.requestFocus();
}
public void reset()
{
supplierId.setText("");
rate.setText("");
quantity.setText("");
discount.setText("");
totalAmount.setText("");
}

public void set(int id, int supplierId, String rate, String quantity,
String discount, String totalAmount)
{
setId(id);
setSupplierId(supplierId);
setRate(rate);
setQuantity(quantity);
setDiscount(discount);
setTotalAmount(totalAmount);
}

public void enableAll()
{
supplierId.setEditable(true);
rate.setEditable(true);
quantity.setEditable(true);
discount.setEditable(true);
totalAmount.setEditable(true);
super.repaint();
}
public void disableAll()
{
supplierId.setEditable(false);
rate.setEditable(false);
quantity.setEditable(false);
discount.setEditable(false);
totalAmount.setEditable(false);
super.repaint();
}
}