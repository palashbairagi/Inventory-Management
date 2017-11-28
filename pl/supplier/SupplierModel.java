package pl.supplier;
import dl.supplier.*;
import javax.swing.table.*;
import java.util.*;

public class SupplierModel extends AbstractTableModel
{
private ArrayList<SupplierInterface>suppliers;
private SupplierDAO supplierDAO;
private String [] columnTitles={"S.No.","Name", "Contact Number", "Contact Number", "Address", "Opening Balance"};
public SupplierModel()
{
supplierDAO=new SupplierDAO();
try
{
suppliers=supplierDAO.get();
}catch(DAOException daoException)
{
System.out.println("SupplierModel [SupplierModel()]:"+daoException);
suppliers=new ArrayList<SupplierInterface>();
}
}
public int getRowCount()
{
return suppliers.size();
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
if(rowIndex>=0 && rowIndex<=suppliers.size())
{
SupplierInterface supplier=suppliers.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return supplier.getName();
if(columnIndex==2)return supplier.getContactNumber1();
if(columnIndex==3)return supplier.getContactNumber2();
if(columnIndex==4)return supplier.getAddress();
if(columnIndex==5)return supplier.getOpeningBalance();
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
public void changeNotify()
{
try
{
suppliers=supplierDAO.get();
}catch(DAOException daoException)
{
System.out.println("SupplierModel [SupplierModel()]:"+daoException);
suppliers=new ArrayList<SupplierInterface>();
}
}
public SupplierInterface get(int index)
{
if(index>=0 && index<suppliers.size())
{
return suppliers.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid supplier index");
}
}
public int getSupplierIndex(String name)
{
int e;
e=0;
while(e<suppliers.size())
{
if((suppliers.get(e).getName()).equals(name))return e;
e++;
}
return -1;
}
}