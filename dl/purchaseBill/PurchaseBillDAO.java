package dl.purchaseBill;
import java.sql.*;
import java.util.*;
public class PurchaseBillDAO implements PurchaseBillDAOInterface
{
public static Connection getConnection() throws DAOException
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println("userDAO[Connection getConnection()]:"+classNotFoundException);
throw new DAOException("JdbcOdbcDriver not found.");
}
Connection connection=null;
try
{
connection=DriverManager.getConnection("jdbc:odbc:medicaldsn","medical","newerasoftech");
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[Connection getConnection()]:"+sqlException);
throw new DAOException("Unable to connect using DSN:tmdsn.");
}
return connection;
}

private java.util.Date convertSQLDateToUtilDate(java.sql.Date sqlDate)
{
return new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
}
private java.sql.Date convertUtilDateToSQLDate(java.util.Date utilDate)
{
return new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate());
}

public int getCount() throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from purchase_bill_info");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}
public PurchaseBillInterface get(int id) throws DAOException
{
PurchaseBillInterface purchaseBill=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO[PurchaseBillInterface get(String PurchaseBillNo)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from purchase_bill_info where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
purchaseBill=new PurchaseBill();
purchaseBill.setPurchaseBillNo(resultSet.getString("purchase_Bill_Number").trim());
purchaseBill.setSupplierId(resultSet.getInt("supplier_Id"));
purchaseBill.setPurchaseBillDate(convertSQLDateToUtilDate(resultSet.getDate("purchase_Bill_Date")));
purchaseBill.setRate(resultSet.getString("Rate").trim());
purchaseBill.setQuantity(resultSet.getString("quantity").trim());
purchaseBill.setDiscount(resultSet.getString("discount").trim());
purchaseBill.setTotalAmount(resultSet.getString("total_Amount").trim());
purchaseBill.setPaidAmount(resultSet.getString("paid_Amount").trim());
}
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[PurchaseBillInterface get(String PurchaseBillNo)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[PurchaseBillInterface get(String PurchaseBillNo)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(purchaseBill==null)throw new DAOException("Invalid ID.");
return purchaseBill;
}
public java.util.ArrayList<PurchaseBillInterface> get() throws DAOException
{
ArrayList<PurchaseBillInterface> purchaseBills=new ArrayList<PurchaseBillInterface>();
PurchaseBillInterface purchaseBill;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO[ArrayList<PurchaseBillInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from purchase_bill_info");
while(resultSet.next())
{
purchaseBill=new PurchaseBill();
purchaseBill.setPurchaseBillNo(resultSet.getString("purchase_Bill_Number").trim());
purchaseBill.setSupplierId(resultSet.getInt("supplier_Id"));
purchaseBill.setPurchaseBillDate(convertSQLDateToUtilDate(resultSet.getDate("purchase_Bill_Date")));
purchaseBill.setRate(resultSet.getString("Rate").trim());
purchaseBill.setQuantity(resultSet.getString("quantity").trim());
purchaseBill.setDiscount(resultSet.getString("discount").trim());
purchaseBill.setTotalAmount(resultSet.getString("total_Amount").trim());
purchaseBill.setPaidAmount(resultSet.getString("paid_Amount").trim());
purchaseBills.add(purchaseBill);
}
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[ArrayList<PurchaseBillInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO[ArrayList<PurchaseBillInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(purchaseBills.size()==0) throw new DAOException("No PurchaseBill.");
return purchaseBills;
}
public void addPurchaseBill(PurchaseBillInterface purchaseBill) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO[void addPurchaseBill(PurchaseBillInterface purchaseBill)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into purchase_bill_info values(?,?,?,?,?,?,?,?,?)");
preparedStatement.setInt(1,purchaseBill.getId());
preparedStatement.setString(2,purchaseBill.getPurchaseBillNo());
preparedStatement.setInt(3,purchaseBill.getSupplierId());
preparedStatement.setDate(4,convertUtilDateToSQLDate(purchaseBill.getPurchaseBillDate()));
preparedStatement.setString(5,purchaseBill.getRate());
preparedStatement.setString(6,purchaseBill.getQuantity());
preparedStatement.setString(7,purchaseBill.getDiscount());
preparedStatement.setString(8,purchaseBill.getTotalAmount());
preparedStatement.setString(9,purchaseBill.getPaidAmount());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void addPurchaseBill(PurchaseBillInterface purchaseBill)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void addPurchaseBill(PurchaseBillInterface purchaseBill)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
public void updatePurchaseBill(PurchaseBillInterface purchaseBill) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO [void updatePurchaseBill(PurchaseBillInterface purchaseBill)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update purchase_bill_info set supplier_Id=?,purchase_Bill_Date=?,rate=?,quantity=?,discount=?,total_Amount=?,paid_Amount=?, purchase_Bill_No=? where id=?");
preparedStatement.setInt(1,purchaseBill.getSupplierId());
preparedStatement.setDate(2,convertUtilDateToSQLDate(purchaseBill.getPurchaseBillDate()));
preparedStatement.setString(3,purchaseBill.getRate());
preparedStatement.setString(4,purchaseBill.getQuantity());
preparedStatement.setString(5,purchaseBill.getDiscount());
preparedStatement.setString(6,purchaseBill.getTotalAmount());
preparedStatement.setString(7,purchaseBill.getPaidAmount());
preparedStatement.setString(8,purchaseBill.getPurchaseBillNo());
preparedStatement.setInt(9,purchaseBill.getId());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void updatePurchaseBill(PurchaseBillInterface purchaseBill)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void updatePurchaseBill(PurchaseBillInterface purchaseBill)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");

}
}
}



public void deletePurchaseBill(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("PurchaseBillDAO [void deletePurchaseBill(String PurchaseBillNo)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from purchase_bill_info where id=?");
preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void deletePurchaseBill(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("PurchaseBillDAO [void deletePurchaseBill(String PurchaseBillNo)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
}