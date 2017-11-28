package pl.saleBill;
import dl.saleBill.*;
import javax.swing.table.*;
import java.util.*;

public class SaleBillModel extends AbstractTableModel
{
private ArrayList<SaleBillInterface>saleBills;
private SaleBillDAO saleBillDAO;
private String [] columnTitles={"S.No.","Product Id", "Rate", "Quantity", "discount", "total"};
public SaleBillModel()
{
saleBillDAO=new SaleBillDAO();
try
{
saleBills=saleBillDAO.get();
}catch(DAOException daoException)
{
System.out.println("SaleBillModel [SaleBillModel()]:"+daoException);
saleBills=new ArrayList<SaleBillInterface>();
}
}
public int getRowCount()
{
return saleBills.size();
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
if(rowIndex>=0 && rowIndex<=saleBills.size())
{
SaleBillInterface saleBill=saleBills.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return saleBill.getProductId();
if(columnIndex==2)return saleBill.getRate();
if(columnIndex==3)return saleBill.getQuantity();
if(columnIndex==4)return saleBill.getDiscount();
if(columnIndex==5)return saleBill.getTotalAmount();
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
saleBills=saleBillDAO.get();
}catch(DAOException daoException)
{
System.out.println("SaleBillModel [SaleBillModel()]:"+daoException);
saleBills=new ArrayList<SaleBillInterface>();
}
}
public SaleBillInterface get(int index)
{
if(index>=0 && index<saleBills.size())
{
return saleBills.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid saleBill index");
}
}
public int getSaleBillIndex(int billNumber)
{
int e;
e=0;
while(e<saleBills.size())
{
if(saleBills.get(e).getBillNo()==billNumber)return e;
e++;
}
return -1;
}
}