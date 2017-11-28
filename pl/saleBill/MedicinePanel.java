package pl.saleBill;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

import dl.medicine.*;

public class MedicinePanel extends JPanel 
{
private JLabel medicineCaption;
private String name[];
private  Vector<MedicineInterface>medicines;
private MedicineDAO medicineDAO;
private JComboBox medicine;
private JLabel rateCaption,quantityCaption,discountCaption,totalCaption;
private JTextField rate,quantity,discount,total;
private TitledBorder titledBorder;

public MedicinePanel()
{
initComponents();
}

public void initComponents()
{
getMedicine();
medicineCaption=new JLabel("Product");
rateCaption=new JLabel("Rate");
quantityCaption=new JLabel("Quantity");
discountCaption=new JLabel("Discount");

medicine=new JComboBox(name);
rate=new JTextField();
quantity=new JTextField();
discount=new JTextField();
total=new JTextField();

medicine.setEditable(false);
AutoCompleteDecorator.decorate(medicine);

//titledBorder=BorderFactory.createTitledBorder("Search");
//setBorder(titledBorder);

setLayout(null);
medicineCaption.setBounds(20,20,80,25);
medicine.setBounds(100,20,100,25);
//rateCaption.setBounds();
//rate.setBounds();
//quantityCaption.setBounds();
//quantity.setBounds();
//discountCaption.setBounds();
//discount.setBounds();
//totalCaption.setBounds();
//total.setBounds();
add(medicineCaption);
add(medicine);
add(rateCaption);
add(quantityCaption);
add(discountCaption);
add(totalCaption);
add(rate);
add(quantity);
add(discount);
add(total);
}

public String getName()
{
return medicine.getSelectedItem().toString();
}

public void reset()
{
medicine.setSelectedIndex(0);
}

public void enableAll()
{
medicineCaption.setText("Find what");
medicine.setVisible(true);
medicine.setSelectedIndex(0);
super.repaint();
}

public void disableAll()
{
medicineCaption.setText("");
medicine.setVisible(false);
super.repaint();
}

public void getMedicine()
{
medicineDAO=new MedicineDAO();
try
{
medicines=medicineDAO.getNameBySort();
}
catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
try
{
name=new String[medicines.size()+1];
name[0]="";
for(int i=0;i<medicines.size();i++)
{
MedicineInterface medicine=medicines.get(i);
name[i+1]=medicine.getName();
}
}catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
}
/*
public boolean validate(MedicineUI medicineUI)
{
if(InputValidator.isComboBoxEmpty(medicineUI,medicine,"Search Field required",true))return false;
return true;
}
*/
}