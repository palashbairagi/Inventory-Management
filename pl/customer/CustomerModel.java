package pl.customer;
import dl.customer.*;
import javax.swing.table.*;
import java.util.*;

public class CustomerModel extends AbstractTableModel
{
private ArrayList<CustomerInterface>customers;
private CustomerDAO customerDAO;
private String [] columnTitles={"S.No.","Name", "Contact Number", "Contact Number", "Address", "Opening Balance"};
public CustomerModel()
{
customerDAO=new CustomerDAO();
try
{
customers=customerDAO.get();
}catch(DAOException daoException)
{
System.out.println("CustomerModel [CustomerModel()]:"+daoException);
customers=new ArrayList<CustomerInterface>();
}
}
public int getRowCount()
{
return customers.size();
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
if(rowIndex>=0 && rowIndex<=customers.size())
{
CustomerInterface customer=customers.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return customer.getName();
if(columnIndex==2)return customer.getContactNumber1();
if(columnIndex==3)return customer.getContactNumber2();
if(columnIndex==4)return customer.getAddress();
if(columnIndex==5)return customer.getOpeningBalance();
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
customers=customerDAO.get();
}catch(DAOException daoException)
{
System.out.println("CustomerModel [CustomerModel()]:"+daoException);
customers=new ArrayList<CustomerInterface>();
}
}
public CustomerInterface get(int index)
{
if(index>=0 && index<customers.size())
{
return customers.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid customer index");
}
}
public int getCustomerIndex(String name)
{
int e;
e=0;
while(e<customers.size())
{
if((customers.get(e).getName()).equals(name))return e;
e++;
}
return -1;
}
}