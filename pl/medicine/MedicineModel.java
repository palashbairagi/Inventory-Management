package pl.medicine;
import dl.medicine.*;
import javax.swing.table.*;
import java.util.*;

public class MedicineModel extends AbstractTableModel
{
private ArrayList<MedicineInterface>medicines;
private MedicineDAO medicineDAO;
private String [] columnTitles={"S.No.","Name","Purchase Price","Sale Price","Manufacturing Date","Expiry Date"};
public MedicineModel()
{
medicineDAO=new MedicineDAO();
try
{
medicines=medicineDAO.get();
}catch(DAOException daoException)
{
System.out.println("MedicineModel [MedicineModel()]:"+daoException);
medicines=new ArrayList<MedicineInterface>();
}
}
public int getRowCount()
{
return medicines.size();
}
public int getColumnCount()
{
return columnTitles.length;
} 
public boolean isCellEditable()
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(rowIndex>=0 && rowIndex<=medicines.size())
{
MedicineInterface medicine=medicines.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return medicine.getName();
if(columnIndex==2)return medicine.getPurchasePrice();
if(columnIndex==3)return medicine.getSalePrice();
if(columnIndex==4)return convertDateToString(medicine.getManufacturingDate());
if(columnIndex==5)return convertDateToString(medicine.getExpiryDate());
}
return "";
}
public Class getColumnClass(int columnIndex)
{
try
{
if(columnIndex==0 || columnIndex==1)return Class.forName("java.lang.Integer");
if(columnIndex>=2 && columnIndex<=5)return Class.forName("java.lang.String");
return Class.forName("java.lang.String");
}catch(ClassNotFoundException classNotFoundException)
{
return "".getClass();
}
}
public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}
private String convertDateToString(java.util.Date date)
{
return date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
}
public void changeNotify()
{
try
{
medicines=medicineDAO.get();
}catch(DAOException daoException)
{
System.out.println("MedicineModel [MedicineModel()]:"+daoException);
medicines=new ArrayList<MedicineInterface>();
}
}
public MedicineInterface get(int index)
{
if(index>=0 && index<medicines.size())
{
return medicines.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid medicine index");
}
}
public int getMedicineIndex(String name)
{
int e;
e=0;
while(e<medicines.size())
{
if((medicines.get(e).getName()).equals(name))return e;
e++;
}
return -1;
}
}