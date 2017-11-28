package pl.purchaseBill;
import dl.purchaseBill.*;
import javax.swing.table.*;
import java.util.*;

public class PurchaseBillModel extends AbstractTableModel
{
private ArrayList<PurchaseBillInterface>purchaseBills;
private PurchaseBillDAO purchaseBillDAO;
private String [] columnTitles={"S.No.","Product Id", "Rate", "Quantity", "discount", "total"};
public PurchaseBillModel()
{
purchaseBillDAO=new PurchaseBillDAO();
try
{
purchaseBills=purchaseBillDAO.get();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillModel [PurchaseBillModel()]:"+daoException);
purchaseBills=new ArrayList<PurchaseBillInterface>();
}
}
public int getRowCount()
{
return purchaseBills.size();
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
if(rowIndex>=0 && rowIndex<=purchaseBills.size())
{
PurchaseBillInterface purchaseBill=purchaseBills.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return purchaseBill.getSupplierId();
if(columnIndex==2)return purchaseBill.getRate();
if(columnIndex==3)return purchaseBill.getQuantity();
if(columnIndex==4)return purchaseBill.getDiscount();
if(columnIndex==5)return purchaseBill.getTotalAmount();
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
purchaseBills=purchaseBillDAO.get();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillModel [PurchaseBillModel()]:"+daoException);
purchaseBills=new ArrayList<PurchaseBillInterface>();
}
}
public PurchaseBillInterface get(int index)
{
if(index>=0 && index<purchaseBills.size())
{
return purchaseBills.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid purchaseBill index");
}
}
public int getPurchaseBillIndex(String billNo)
{
int e;
e=0;
while(e<purchaseBills.size())
{
if((purchaseBills.get(e).getPurchaseBillNo()).equals(billNo))return e;
e++;
}
return -1;
}
}