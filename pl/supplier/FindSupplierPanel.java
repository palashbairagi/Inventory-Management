package pl.supplier;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

import dl.supplier.*;

public class FindSupplierPanel extends JPanel 
{
private JLabel findSupplierCaption;
private String name[];
private  Vector<SupplierInterface>suppliers;
private SupplierDAO supplierDAO;
private JComboBox findSupplier;
private TitledBorder titledBorder;

public FindSupplierPanel()
{
initComponents();
}

public void initComponents()
{
getSupplier();
findSupplierCaption=new JLabel("Find what");
findSupplier=new JComboBox(name);
findSupplier.setEditable(false);
AutoCompleteDecorator.decorate(findSupplier);
titledBorder=BorderFactory.createTitledBorder("Search");
setBorder(titledBorder);

setLayout(null);
findSupplierCaption.setBounds(10,20,80,25);
findSupplier.setBounds(100,20,100,25);
add(findSupplierCaption);
add(findSupplier);
}

public String getName()
{
return findSupplier.getSelectedItem().toString();
}

public void reset()
{
findSupplier.setSelectedIndex(0);
}

public void enableAll()
{
findSupplierCaption.setText("Find what");
findSupplier.setVisible(true);
findSupplier.setSelectedIndex(0);
super.repaint();
}

public void disableAll()
{
findSupplierCaption.setText("");
findSupplier.setVisible(false);
super.repaint();
}

public void getSupplier()
{
supplierDAO=new SupplierDAO();
try
{
suppliers=supplierDAO.getNameBySort();
}
catch(Exception e)
{
System.out.println("Supplier Autofill ComboBox");
System.out.println(e);
}
try
{
name=new String[suppliers.size()+1];
name[0]="";
for(int i=0;i<suppliers.size();i++)
{
SupplierInterface supplier=suppliers.get(i);
name[i+1]=supplier.getName();
}
}catch(Exception e)
{
System.out.println("Supplier Autofill ComboBox");
System.out.println(e);
}
}

public boolean validate(SupplierUI supplierUI)
{
if(InputValidator.isComboBoxEmpty(supplierUI,findSupplier,"Search Field required",true))return false;
return true;
}

}