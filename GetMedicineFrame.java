import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.JList.*;

import dl.medicine.*;

class GetMedicineFrame extends JFrame implements KeyListener
{
private StringBuffer name;
private JComboBox cb;
private ArrayList<MedicineInterface>medicines;
private MedicineDAO medicineDAO;
JTextField editor;

GetMedicineFrame()
{
setLayout(null);
name=new StringBuffer();
medicineDAO=new MedicineDAO();
cb=new JComboBox();
editor = (JTextField) cb.getEditor().getEditorComponent();
cb.setBounds(10,10,100,25);
editor.setBounds(10,10,100,25);
add(cb);
add(editor);
editor.addKeyListener(this);
cb.setEditable(true);
setVisible(true);
setSize(200,200);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void keyReleased(KeyEvent evt)
{
cb.showPopup();
cb.setPopupVisible(true);
}

public void keyPressed(KeyEvent evt)
{
}

public void keyTyped(KeyEvent evt) 
{
char c = evt.getKeyChar();
if(c == evt.VK_BACK_SPACE)name.deleteCharAt(name.length()-1);
else name.append(c);
try
{
medicines=medicineDAO.findMedicineByName(name.toString());
}
catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
try
{
for(int i=0;i<medicines.size();i++)
{
MedicineInterface medicine=medicines.get(i);
System.out.println("Found "+medicine.getName());
cb.insertItemAt(medicine.getName(),i);
}
}catch(Exception e)
{
System.out.println("Medicine Autofill ComboBox");
System.out.println(e);
}
}


public static void main(String gg[])
{
GetMedicineFrame f=new GetMedicineFrame();
f.setVisible(true);
}
}