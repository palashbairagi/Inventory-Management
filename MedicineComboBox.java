import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

import dl.medicine.*;

class MedicineComboBox  extends JFrame implements ActionListener
{
private String name[];
private  Vector<MedicineInterface>medicines;
private MedicineDAO medicineDAO;
private JComboBox cb;
private JButton b;

MedicineComboBox()
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
name=new String[medicines.size()];
for(int i=0;i<medicines.size();i++)
{
MedicineInterface medicine=medicines.get(i);
name[i]=medicine.getName();
}
}catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
setLayout(null);
b=new JButton("Get");
cb=new JComboBox(name);
cb.setEditable(false);
AutoCompleteDecorator.decorate(cb);
cb.setBounds(10,10,100,25);
b.setBounds(10,50,100,25);
add(cb);
add(b);
b.addActionListener(this);
setVisible(true);
setSize(400,200);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent ev)
{
MedicineInterface medicine=new Medicine();
try
{
medicine=medicineDAO.findMedicineByName((cb.getSelectedItem()).toString());
}catch(DAOException e)
{
System.out.println("Unable to Get Price");
}
System.out.println(cb.getSelectedItem());
System.out.println(medicine.getId());
System.out.println(medicine.getName());
System.out.println(medicine.getSalePrice());
System.out.println(medicine.getPurchasePrice());
setDate(medicine.getManufacturingDate());
setDate(medicine.getExpiryDate());
}

public void setDate(java.util.Date manufacturingDate)
{
System.out.println(manufacturingDate.getDate()+"-"+(manufacturingDate.getMonth()+1)
+"-"+(manufacturingDate.getYear()+1900));
}

public static void main(String gg[])
{
new MedicineComboBox();
}
}