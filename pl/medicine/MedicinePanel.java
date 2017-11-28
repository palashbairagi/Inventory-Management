package pl.medicine;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class MedicinePanel extends JPanel
{
private JLabel nameCaption;
private JLabel purchasePriceCaption,salePriceCaption;
private JLabel manufacturingDateCaption,expiryDateCaption;
private JTextField id,name,purchasePrice,salePrice,manufacturingDate,expiryDate;
private SimpleDateFormat simpleDateFormat;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;

public MedicinePanel()
{
initComponents();
}
public void initComponents()
{
simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
nameCaption=new JLabel("Name");
purchasePriceCaption=new JLabel("Purchase Price");
salePriceCaption=new JLabel("Sale Price");
manufacturingDateCaption=new JLabel("Manufacturing Date");
expiryDateCaption=new JLabel("Expiry Date");

id=new JTextField(50);
name=new JTextField(50);
purchasePrice=new JTextField(50);
salePrice=new JTextField(50);
manufacturingDate=new JTextField(50);
expiryDate=new JTextField(50);

titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);

nameCaption.setBounds(10,20,100,25);
name.setBounds(50,20,200,25);
purchasePriceCaption.setBounds(10,55,100,25);
purchasePrice.setBounds(100,55,50,25);
salePriceCaption.setBounds(10,90,100,25);
salePrice.setBounds(100,90,50,25);
manufacturingDateCaption.setBounds(10,125,160,25);
manufacturingDate.setBounds(175,125,100,25);
expiryDateCaption.setBounds(10,160,100,25);
expiryDate.setBounds(100,160,200,25);

add(id);
add(nameCaption);
add(name);
add(purchasePriceCaption);
add(purchasePrice);
add(salePriceCaption);
add(salePrice);
add(manufacturingDateCaption);
add(manufacturingDate);
add(expiryDateCaption);
add(expiryDate);
}
private boolean isConvertedDateValid(java.util.Date date,String string)
{
String dt=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
return string.equals(dt);
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
public String getPurchasePrice()
{
return this.purchasePrice.getText().trim();
}
public void setPurchasePrice(String purchasePrice)
{
this.purchasePrice.setText(purchasePrice);
}
public String getSalePrice()
{
return this.salePrice.getText().trim();
}
public void setSalePrice(String salePrice)
{
this.salePrice.setText(salePrice);
}
public java.util.Date getManufacturingDate() throws ParseException
{
java.util.Date manufacturingDate =simpleDateFormat.parse(this.manufacturingDate.getText().trim());
if(isConvertedDateValid(manufacturingDate,this.manufacturingDate.getText().trim()))
{
return manufacturingDate;
}
else
{
throw new ParseException("Invalid manufacturing date",0);
}
}
public void setManufacturingDate(java.util.Date manufacturingDate)
{
this.manufacturingDate.setText(manufacturingDate.getDate()+"-"+(manufacturingDate.getMonth()+1)
+"-"+(manufacturingDate.getYear()+1900));
}
public java.util.Date getExpiryDate() throws ParseException
{
java.util.Date expiryDate=simpleDateFormat.parse(this.expiryDate.getText().trim());
if(isConvertedDateValid(expiryDate,this.expiryDate.getText().trim()))
{
return expiryDate;
}
else
{
throw new ParseException("Invalid expiry Date",0);
}
}
public void setExpiryDate(java.util.Date expiryDate)
{
this.expiryDate.setText(expiryDate.getDate()+"-"+(expiryDate.getMonth()+1)
+"-"+(expiryDate.getYear()+1900));
}

public void requestFocusOnName()
{
name.requestFocus();
}
public void reset()
{
name.setText("");
purchasePrice.setText("");
salePrice.setText("");
manufacturingDate.setText("");
expiryDate.setText("");
}

public void set(int id, String name, String purchasePrice, String salePrice,
java.util.Date manufacturingDate, java.util.Date expiryDate)
{
setId(id);
setName(name);
setPurchasePrice(purchasePrice);
setSalePrice(salePrice);
setManufacturingDate(manufacturingDate);
setExpiryDate(expiryDate);
}

public void enableAll()
{
name.setEditable(true);
purchasePrice.setEditable(true);
salePrice.setEditable(true);
manufacturingDate.setEditable(true);
expiryDate.setEditable(true);
super.repaint();
}
public void disableAll()
{
name.setEditable(false);
purchasePrice.setEditable(false);
salePrice.setEditable(false);
manufacturingDate.setEditable(false);
expiryDate.setEditable(false);
super.repaint();
}

public boolean validate(MedicineUI medicineUI)
{
if(InputValidator.isTextFieldEmpty(medicineUI,name,"Name required",true))return false;
if(InputValidator.isTextFieldEmpty(medicineUI,purchasePrice,"Purchase Price required",true))return false;
if(InputValidator.isTextFieldEmpty(medicineUI,salePrice,"Sale Price required",true))return false;
if(InputValidator.isTextFieldEmpty(medicineUI,manufacturingDate,"Manufacturing Date required",true))return false;
if(InputValidator.isTextFieldEmpty(medicineUI,expiryDate,"Expiry Date required",true))return false;
if(!InputValidator.isValidDDMMYYYYDate(medicineUI,manufacturingDate,"Invalid Manufacturing Date",true,false))return false;
if(!InputValidator.isValidDDMMYYYYDate(medicineUI,expiryDate,"Invalid Expiry Date",true,false))return false;
return true;
}

}