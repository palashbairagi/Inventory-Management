package pl.saleBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
import java.awt.event.*;

public class TotalPanel extends JPanel 
{
private JLabel discountCaption, totalCaption, paidAmountCaption, remainingAmountCaption;
public JTextField discount, paidAmount;
private JLabel total, remainingAmount;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public TotalPanel()
{
initComponents();
}

public void initComponents()
{
discountCaption=new JLabel("Discount");
totalCaption=new JLabel("Total");
paidAmountCaption=new JLabel("Paid");
remainingAmountCaption=new JLabel("Due Amount");
total=new JLabel("0");
discount=new JTextField();
paidAmount=new JTextField();
remainingAmount=new JLabel("0");

setLayout(null);
discountCaption.setBounds(10,10,80,25);
discount.setBounds(100,10,80,25);
totalCaption.setBounds(10,50,80,25);
total.setBounds(100,50,80,25);
paidAmountCaption.setBounds(10,90,80,25);
paidAmount.setBounds(100,90,80,25);
remainingAmountCaption.setBounds(10,130,80,25);
remainingAmount.setBounds(100,130,80,25);

add(discountCaption);
add(discount);
add(totalCaption);
add(total);
add(paidAmountCaption);
add(paidAmount);
add(remainingAmountCaption);
add(remainingAmount);

setDiscount("0.0");
setTotal("0.0");
setPaidAmount("0.0");
setRemainingAmount("0.0");
}

public void setDiscount(String discount)
{
this.discount.setText(discount);
}
public String getDiscount()
{
return this.discount.getText().trim();
}
public void setTotal(String total)
{
this.total.setText(total);
}
public String getTotal()
{
return this.total.getText().trim();
}
public void setPaidAmount(String paidAmount)
{
this.paidAmount.setText(paidAmount);
}
public String getPaidAmount()
{
return this.paidAmount.getText().trim();
}
public void setRemainingAmount(String remainingAmount)
{
this.remainingAmount.setText(remainingAmount);
}
public String getRemainingAmount()
{
return this.remainingAmount.getText().trim();
}

public void reset()
{
setDiscount("0.0");
setTotal("0.0");
setPaidAmount("0.0");
setRemainingAmount("0.0");
}
}
