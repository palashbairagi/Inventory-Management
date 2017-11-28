package dl.supplier;
import java.sql.*;
import java.util.*;
public class SupplierDAO implements SupplierDAOInterface
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
System.out.println("SupplierDAO[Connection getConnection()]:"+sqlException);
throw new DAOException("Unable to connect using DSN:tmdsn.");
}
return connection;
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
System.out.println("SupplierDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from supplier_info");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("SupplierDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}

public SupplierInterface get(int id) throws DAOException
{
SupplierInterface supplier=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO[SupplierInterface get(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from supplier_info where supplier_id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
supplier=new Supplier();
supplier.setId(id);
supplier.setName(resultSet.getString("supplier_name").trim());
supplier.setContactNumber1(resultSet.getString("supplier_contact_Number_1").trim());
supplier.setContactNumber2(resultSet.getString("supplier_contact_Number_2").trim());
supplier.setAddress(resultSet.getString("supplier_address"));
supplier.setOpeningBalance(resultSet.getString("opening_Balance"));
}
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[ArrayList<SupplierInterface>get()]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[SupplierInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(supplier==null)throw new DAOException("Invalid ID.");
return supplier;
}
public java.util.ArrayList<SupplierInterface> get() throws DAOException
{
ArrayList<SupplierInterface> suppliers=new ArrayList<SupplierInterface>();
SupplierInterface supplier;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO[ArrayList<SupplierInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from supplier_info");
while(resultSet.next())
{
supplier=new Supplier();
supplier.setId(resultSet.getInt("supplier_id"));
supplier.setName(resultSet.getString("supplier_name").trim());
supplier.setContactNumber1(resultSet.getString("supplier_contact_Number_1").trim());
supplier.setContactNumber2(resultSet.getString("supplier_contact_Number_2").trim());
supplier.setAddress(resultSet.getString("supplier_address"));
supplier.setOpeningBalance(resultSet.getString("opening_Balance"));
suppliers.add(supplier);
}
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[ArrayList<SupplierInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[ArrayList<SupplierInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(suppliers.size()==0) throw new DAOException("No Supplier.");
return suppliers;
}
public void addSupplier(SupplierInterface supplier) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO[void addSupplier(SupplierInterface supplier)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into supplier_info values(?,?,?,?,?,?)");
preparedStatement.setInt(1,supplier.getId());
preparedStatement.setString(2,supplier.getName());
preparedStatement.setString(3,supplier.getContactNumber1());
preparedStatement.setString(4,supplier.getContactNumber2());
preparedStatement.setString(5,supplier.getAddress());
preparedStatement.setString(6,supplier.getOpeningBalance());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void addSupplier(SupplierInterface supplier)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void addSupplier(SupplierInterface supplier)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
public void updateSupplier(SupplierInterface supplier) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO [void updateSupplier(SupplierInterface supplier)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update supplier_info set supplier_name=?, supplier_contact_Number_1=?, supplier_contact_Number_2=?, supplier_address=?, opening_Balance=? where supplier_id=?");
preparedStatement.setString(1,supplier.getName());
preparedStatement.setString(2,supplier.getContactNumber1());
preparedStatement.setString(3,supplier.getContactNumber2());
preparedStatement.setString(4,supplier.getAddress());
preparedStatement.setString(5,supplier.getOpeningBalance());
preparedStatement.setInt(6,supplier.getId());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void updateSupplier(SupplierInterface supplier)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void updateSupplier(SupplierInterface supplier)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");

}
}
}



public void deleteSupplier(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO [void deleteSupplier(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from supplier_info where supplier_id=?"); preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void deleteSupplier(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO [void deleteSupplier(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}




public java.util.Vector<SupplierInterface> getNameBySort() throws DAOException
{
Vector<SupplierInterface> suppliers=new Vector<SupplierInterface>();
SupplierInterface supplier;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO[Vector<SupplierInterface>getNameBySort()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from supplier_info order by supplier_name");
while(resultSet.next())
{
supplier=new Supplier();
supplier.setName(resultSet.getString("supplier_name").trim());
suppliers.add(supplier);
}
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[Vector<SupplierInterface> getNameBySort()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[Vector<SupplierInterface> getNameBySort()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(suppliers.size()==0) throw new DAOException("No Suppliers.");
return suppliers;
}



public SupplierInterface findSupplierByName(String name) throws DAOException
{
SupplierInterface supplier=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("SupplierDAO[SupplierInterface findSupplierByName(String name)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from supplier_info where supplier_name=?");
preparedStatement.setString(1,name);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
supplier=new Supplier();
supplier.setId(resultSet.getInt("id"));
supplier.setName(resultSet.getString("supplier_name").trim());
supplier.setContactNumber1(resultSet.getString("contact_Number_1").trim());
supplier.setContactNumber2(resultSet.getString("contact_Number_2").trim());
supplier.setAddress(resultSet.getString("address"));
supplier.setOpeningBalance(resultSet.getString("opening_Balance"));
}
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[SupplierInterface findSupplierByName(String name)]:"+sqlException);
throw new DAOException("Unable to findSupplierByName record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("SupplierDAO[SupplierInterface findSupplierByName(String name)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(supplier==null)throw new DAOException("Invalid Name.");
return supplier;
}

}