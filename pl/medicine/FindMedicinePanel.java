package pl.medicine;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

import dl.medicine.*;

public class FindMedicinePanel extends JPanel 
{
private JLabel findMedicineCaption;
private String name[];
private  Vector<MedicineInterface>medicines;
private MedicineDAO medicineDAO;
private JComboBox findMedicine;
private TitledBorder titledBorder;

public FindMedicinePanel()
{
initComponents();
}

public void initComponents()
{
getMedicine();
findMedicineCaption=new JLabel("Find what");
findMedicine=new JComboBox(name);
findMedicine.setEditable(false);
AutoCompleteDecorator.decorate(findMedicine);
titledBorder=BorderFactory.createTitledBorder("Search");
setBorder(titledBorder);

setLayout(null);
findMedicineCaption.setBounds(10,20,80,25);
findMedicine.setBounds(100,20,100,25);
add(findMedicineCaption);
add(findMedicine);
}

public String getName()
{
return findMedicine.getSelectedItem().toString();
}

public void reset()
{
findMedicine.setSelectedIndex(0);
}

public void enableAll()
{
findMedicineCaption.setText("Find what");
findMedicine.setVisible(true);
findMedicine.setSelectedIndex(0);
super.repaint();
}

public void disableAll()
{
findMedicineCaption.setText("");
findMedicine.setVisible(false);
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

public boolean validate(MedicineUI medicineUI)
{
if(InputValidator.isComboBoxEmpty(medicineUI,findMedicine,"Search Field required",true))return false;
return true;
}

}