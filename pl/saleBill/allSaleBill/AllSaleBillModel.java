package pl.saleBill.allSaleBill;
import dl.saleBill.*;
import javax.swing.table.*;
import java.util.*;

public class AllSaleBillModel extends AbstractTableModel
{
private ArrayList<SaleBillInterface>saleBills;
private SaleBillDAO saleBillDAO;
private String [] columnTitles={"Bill Number","Date", "Customer Name", "Amount","Paid Amount", "Due Amount",};
public AllSaleBillModel()
{
saleBillDAO=new SaleBillDAO();
try
{
saleBills=saleBillDAO.getSaleBillInfo();
}catch(DAOException daoException)
{
System.out.println("AllSaleBillModel [AllSaleBillModel()]:"+daoException);
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
float total=0,paid=0;
if(rowIndex>=0 && rowIndex<=saleBills.size())
{
SaleBillInterface saleBill=saleBills.get(rowIndex);

try{total=Float.parseFloat(saleBill.getTotalAmount());}catch(Exception e){total=0;}
try{paid=Float.parseFloat(saleBill.getPaidAmount());}catch(Exception e){paid=0;}

if(columnIndex==0)return saleBill.getBillNumber();
if(columnIndex==1)return convertDateToString(saleBill.getBillDate());
if(columnIndex==2)return saleBill.getCustomerName();
if(columnIndex==3)return saleBill.getTotalAmount();
if(columnIndex==4)return saleBill.getPaidAmount();
if(columnIndex==5)return (total-paid);
}
return "";
}
public Class getColumnClass(int columnIndex)
{
try
{
//if(columnIndex==0 || columnIndex==1)return Class.forName("java.lang.Integer");
//if(columnIndex>=2 && columnIndex<=5)return Class.forName("java.lang.String");
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
saleBills=saleBillDAO.getSaleBillInfo();
}catch(DAOException daoException)
{
System.out.println("AllSaleBillModel [changeNotify()]:"+daoException);
saleBills=new ArrayList<SaleBillInterface>();
}
}

/*
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
}*/
}