package pl.saleBill;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import dl.medicine.*;

public class FindMedicinePanel extends JComboBox implements TableCellRenderer
{
private String name[];
private  Vector<MedicineInterface>medicines;
private MedicineDAO medicineDAO;

public FindMedicinePanel()
{
initComponents();
}

public void initComponents()
{
getMedicine();
setEditable(false);
AutoCompleteDecorator.decorate(this);
}

public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
{
	if(value!=null)setSelectedItem(value);
            return this;
}


/*
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
findMedicine.setVisible(true);
findMedicine.setSelectedIndex(0);
super.repaint();
}

public void disableAll()
{
findMedicine.setVisible(false);
super.repaint();
}
*/
public String[] getMedicine()
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
addItem(name[i+1]);
}
}catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
return name;
}
/*
public boolean validate(MedicineUI medicineUI)
{
if(InputValidator.isComboBoxEmpty(medicineUI,findMedicine,"Search Field required",true))return false;
return true;
}
*/
}