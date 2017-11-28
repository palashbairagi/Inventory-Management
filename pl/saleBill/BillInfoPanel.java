package pl.saleBill;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class BillInfoPanel extends JPanel
{
private JLabel billNumberCaption, billDateCaption;
private JLabel billNumber, billDate;
private SimpleDateFormat simpleDateFormat;

public BillInfoPanel()
{
initComponents();
}
public void initComponents()
{
simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
billNumberCaption=new JLabel("Bill No.");
billDateCaption=new JLabel("Date");

billNumber=new JLabel("");
billDate=new JLabel(""); 

setLayout(null);

billNumberCaption.setBounds(15,25,50,25); 
billDateCaption.setBounds(350,25,50,25);
billNumber.setBounds(60,25,100,25);
billDate.setBounds(400,25,100,25);

add(billNumberCaption); 
add(billDateCaption);
add(billNumber); 
add(billDate);

}

private boolean isConvertedDateValid(java.util.Date date,String string)
{
String dt=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
return string.equals(dt);
}

public void setBillNumber(int billNumber)
{
this.billNumber.setText(String.valueOf(billNumber));
}
public void setBillDate(java.util.Date date)
{
this.billDate.setText(date.getDate()+"-"+(date.getMonth()+1)
+"-"+(date.getYear()+1900));
}

public int getBillNumber()
{
return Integer.parseInt(billNumber.getText());
}
public java.util.Date getBillDate() throws ParseException 
{
java.util.Date billDate =simpleDateFormat.parse(this.billDate.getText().trim());
if(isConvertedDateValid(billDate,this.billDate.getText().trim()))
{
return billDate;
}
else
{
throw new ParseException("Invalid bill date",0);
}
}

public void reset()
{
billNumber.setText("");
billDate.setText("");
}

public void set(int billNumber, java.util.Date billDate)
{
setBillNumber(billNumber); 
setBillDate(billDate);
}

}