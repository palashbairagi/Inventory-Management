package pl.saleBill.allSaleBill;
import dl.saleBill.*;
import javax.swing.table.*;
import java.util.*;

public class SaleBillDetailModel extends AbstractTableModel
{
private ArrayList<SaleBillDetailInterface>saleBillsDetail;
private SaleBillDAO saleBillDAO;
private String [] columnTitles={"Date", "Customer Name", "Amount","Paid Amount", "Due Amount",};
public SaleBillDetailModel()
{
saleBillDAO=new SaleBillDAO();
try
{
saleBillsDetail=saleBillDAO.getSaleBillDetailInfo(1);
}catch(DAOException daoException)
{
System.out.println("AllSaleBillDetailModel [AllSaleBillDetailModel()]:"+daoException);
saleBillsDetail=new ArrayList<SaleBillDetailInterface>();
}
}
public int getRowCount()
{
return saleBillsDetail.size();
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
if(rowIndex>=0 && rowIndex<=saleBillsDetail.size())
{
SaleBillDetailInterface saleBillDetail=saleBillsDetail.get(rowIndex);

if(columnIndex==0)return saleBillDetail.getProductName();
if(columnIndex==1)return saleBillDetail.getRate();
if(columnIndex==2)return saleBillDetail.getQuantity();
if(columnIndex==3)return saleBillDetail.getDiscount();
if(columnIndex==4)return saleBillDetail.getAmount();
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

public void changeNotify(int billNumber)
{
try
{
saleBillsDetail=saleBillDAO.getSaleBillDetailInfo(billNumber);
System.out.println(billNumber);
}catch(DAOException daoException)
{
System.out.println("SaleBillModel [SaleBillModel()]:"+daoException);
saleBillsDetail=new ArrayList<SaleBillDetailInterface>();
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