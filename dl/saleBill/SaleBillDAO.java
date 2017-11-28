package dl.saleBill;
import java.sql.*;
import java.util.*;

public class SaleBillDAO implements SaleBillDAOInterface
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
System.out.println("SaleBillDAO[Connection getConnection()]:"+sqlException);
throw new DAOException("Unable to connect using DSN:medical");
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
System.out.println("SaleBillDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from sale_bill_info");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}

public boolean exists(int billNo) throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[boolean exists(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select count(*) as total_records from sale_bill_info where sale_bill_number=?");
preparedStatement.setInt(1,billNo);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("total_records");
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to check existence.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count!=0;
}


public int getBillNumber() throws DAOException
{
int billNumber=1;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[int getBillNumber()]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select max(sale_bill_number) as new_bill_number from sale_bill_info");
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
billNumber=resultSet.getInt("new_bill_number");
}
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[int getBillNumber()]:"+sqlException);
throw new DAOException("Unable to get Bill Number");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[int getBillNumber()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return billNumber;
}

public SaleBillInterface getSaleBillInfo(int billNo) throws DAOException
{
SaleBillInterface saleBill=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[SaleBillInterface getSaleBillInfo(int billNo)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from sale_bill_info where sale_bill_number=?");
preparedStatement.setInt(1,billNo);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
saleBill=new SaleBill();
saleBill.setBillNumber(resultSet.getInt("sale_bill_number"));
saleBill.setBillDate(convertSQLDateToUtilDate(resultSet.getDate("sale_bill_Date")));
saleBill.setCustomerName(resultSet.getString("customer_name").trim());
saleBill.setCustomerContactNumber1(resultSet.getString("customer_contact_number_1").trim());
saleBill.setCustomerContactNumber2(resultSet.getString("customer_contact_number_2").trim());
saleBill.setCustomerAddress(resultSet.getString("customer_address").trim());
saleBill.setTotalDiscount(resultSet.getString("total_Discount").trim());
saleBill.setTotalAmount(resultSet.getString("total_Amount").trim());
saleBill.setPaidAmount(resultSet.getString("paid_Amount").trim());
}
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[SaleBillInterface getSaleBillInfo(int billNo)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[SaleBillInterface getSaleBillInfo(int billNo)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(saleBill==null)throw new DAOException("Invalid Bill Number");
return saleBill;
}

public java.util.ArrayList<SaleBillInterface> getSaleBillInfo() throws DAOException
{
ArrayList<SaleBillInterface> saleBills=new ArrayList<SaleBillInterface>();
SaleBillInterface saleBill;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillInterface>getSaleBillInfo()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from sale_bill_info");
while(resultSet.next())
{
saleBill=new SaleBill();
saleBill.setBillNumber(resultSet.getInt("sale_bill_number"));
saleBill.setBillDate(convertSQLDateToUtilDate(resultSet.getDate("sale_bill_Date")));
saleBill.setCustomerName(resultSet.getString("customer_name").trim());
saleBill.setCustomerContactNumber1(resultSet.getString("customer_contact_number_1").trim());
saleBill.setCustomerContactNumber2(resultSet.getString("customer_contact_number_2").trim());
saleBill.setCustomerAddress(resultSet.getString("customer_address").trim());
saleBill.setTotalDiscount(resultSet.getString("total_Discount").trim());
saleBill.setTotalAmount(resultSet.getString("total_Amount").trim());
saleBill.setPaidAmount(resultSet.getString("paid_Amount").trim());
saleBills.add(saleBill);
}
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillInterface> getSaleBillInfo()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillInterface> getSaleBillInfo()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(saleBills.size()==0) throw new DAOException("No Bill");
return saleBills;
}

public java.util.ArrayList<SaleBillDetailInterface> getSaleBillDetailInfo(int billNumber) throws DAOException
{
ArrayList<SaleBillDetailInterface> saleBillsDetail=new ArrayList<SaleBillDetailInterface>();
SaleBillDetailInterface saleBillDetail;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillDetailInterface>getSaleBillDetailInfo()]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from sale_bill_details where sale_bill_number=?");
preparedStatement.setInt(1,billNumber);
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
saleBillDetail=new SaleBillDetail();
saleBillDetail.setProductName(resultSet.getString("product_name"));
saleBillDetail.setRate(resultSet.getString("product_price"));
saleBillDetail.setQuantity(resultSet.getString("quantity"));
saleBillDetail.setDiscount(resultSet.getString("discount"));
saleBillDetail.setAmount(resultSet.getString("total"));
saleBillsDetail.add(saleBillDetail);
}
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillDetailInterface> getSaleBillDetailInfo()]:"+sqlException);
throw new DAOException("Unable to get records.");
}

finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[ArrayList<SaleBillDetailInterface> getSaleBillDetailInfo()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(saleBillsDetail.size()==0) throw new DAOException("No Item");
return saleBillsDetail;
}


public void addSaleBillInfo(SaleBillInterface saleBill) throws DAOException
{
if(exists(saleBill.getBillNumber())) throw new DAOException("Bill Number Exists");
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[void addSaleBillInfo(SaleBillInterface saleBill)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into sale_bill_info values(?,?,?,?,?,?,?,?,?)");
preparedStatement.setInt(1,saleBill.getBillNumber());
preparedStatement.setDate(2,convertUtilDateToSQLDate(saleBill.getBillDate()));
preparedStatement.setString(3,saleBill.getCustomerName());
preparedStatement.setString(4,saleBill.getCustomerContactNumber1());
preparedStatement.setString(5,saleBill.getCustomerContactNumber2());
preparedStatement.setString(6,saleBill.getCustomerAddress());
preparedStatement.setString(7,saleBill.getTotalDiscount());
preparedStatement.setString(8,saleBill.getTotalAmount());
preparedStatement.setString(9,saleBill.getPaidAmount());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void addSaleBillInfo(SaleBillInterface saleBill)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void addSaleBillInfo(SaleBillInterface saleBill)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}

public void addSaleBillDetailInfo(java.util.ArrayList<SaleBillDetailInterface> saleBillDetail)throws DAOException
{
if(exists(saleBillDetail.get(0).getBillNumber())) throw new DAOException("Bill Number Exists");

Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO[void addSaleBillDetailInfo(java.util.ArrayList<SaleBillDetailInterface> saleBillDetail)]:"+daoException);
throw daoException;
}

for(int i=0;i<saleBillDetail.size();i++)
{
PreparedStatement preparedStatement=null;

try
{
SaleBillDetailInterface saleBill=saleBillDetail.get(i);

preparedStatement=connection.prepareStatement("insert into sale_bill_details values(?,?,?,?,?,?)");
preparedStatement.setInt(1,saleBill.getBillNumber());
preparedStatement.setString(2,saleBill.getProductName());
preparedStatement.setString(3,saleBill.getRate());
preparedStatement.setString(4,saleBill.getQuantity());
preparedStatement.setString(5,saleBill.getDiscount());
preparedStatement.setString(6,saleBill.getAmount());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[void addSaleBillDetailInfo(java.util.ArrayList<SaleBillDetailInterface> saleBillDetail)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
}

try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO[void addSaleBillDetailInfo(java.util.ArrayList<SaleBillDetailInterface> saleBillDetail)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}

}

public void updateSaleBillInfo(SaleBillInterface saleBill) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO [void updateSaleBillInfo(SaleBillInterface saleBill)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update sale_bill_info set  sale_bill_Date=?, customer_name=?, customer_contact_number_1=?, customer_contact_number_2=?, customer_address=?, total_discount=?, total_Amount=?, paid_Amount=? where sale_bill_number=?");
preparedStatement.setDate(1,convertUtilDateToSQLDate(saleBill.getBillDate()));
preparedStatement.setString(2,saleBill.getCustomerName());
preparedStatement.setString(3,saleBill.getCustomerContactNumber1());
preparedStatement.setString(4,saleBill.getCustomerContactNumber2());
preparedStatement.setString(5,saleBill.getCustomerAddress());
preparedStatement.setString(6,saleBill.getTotalDiscount());
preparedStatement.setString(7,saleBill.getTotalAmount());
preparedStatement.setString(8,saleBill.getPaidAmount());
preparedStatement.setInt(9,saleBill.getBillNumber());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void updateSaleBillInfo(SaleBillInterface saleBill)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void updateSaleBillInfo(SaleBillInterface saleBill)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}

public void deleteSaleBill(int billNumber) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SaleBillDAO [void deleteSaleBill(int billNumber)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from sale_bill_info where sale_bill_number=?"); 
preparedStatement.setInt(1,billNumber);
preparedStatement.executeUpdate();

preparedStatement=connection.prepareStatement("delete from sale_bill_details where sale_bill_number=?"); 
preparedStatement.setInt(1,billNumber);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void deleteSaleBill(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SaleBillDAO [void deleteSaleBill(int billNumber)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
}