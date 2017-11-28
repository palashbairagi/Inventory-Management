package pl.user;
import dl.user.*;
import javax.swing.table.*;
import java.util.*;

public class UserModel extends AbstractTableModel
{
private ArrayList<UserInterface>users;
private UserDAO userDAO;
private String [] columnTitles={"S.No.","Name", "Password", "Rights"};
public UserModel()
{
userDAO=new UserDAO();
try
{
users=userDAO.get();
}catch(DAOException daoException)
{
System.out.println("UserModel [UserModel()]:"+daoException);
users=new ArrayList<UserInterface>();
}
}
public int getRowCount()
{
return users.size();
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
if(rowIndex>=0 && rowIndex<=users.size())
{
UserInterface user=users.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return user.getName();
if(columnIndex==2)return user.getPassword();
if(columnIndex==3)return user.getRole();
}
return "";
}
public Class getColumnClass(int columnIndex)
{
try
{
if(columnIndex==0 || columnIndex==1)return Class.forName("java.lang.Integer");
if(columnIndex==2 && columnIndex==3)return Class.forName("java.lang.String");
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
users=userDAO.get();
}catch(DAOException daoException)
{
System.out.println("UserModel [UserModel()]:"+daoException);
users=new ArrayList<UserInterface>();
}
}
public UserInterface get(int index)
{
if(index>=0 && index<users.size())
{
return users.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid user index");
}
}
public int getUserIndex(String name)
{
int e;
e=0;
while(e<users.size())
{
if((users.get(e).getName()).equals(name))return e;
e++;
}
return -1;
}
}