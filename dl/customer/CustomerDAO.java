package dl.customer;
import java.sql.*;
import java.util.*;
public class CustomerDAO implements CustomerDAOInterface
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
System.out.println("CustomerDAO[Connection getConnection()]:"+sqlException);
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
System.out.println("CustomerDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from customer_info");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("CustomerDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}
public CustomerInterface get(int id) throws DAOException
{
CustomerInterface customer=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO[CustomerInterface get(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from customer_info where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
customer=new Customer();
customer.setId(id);
customer.setName(resultSet.getString("customer_name").trim());
customer.setContactNumber1(resultSet.getString("customer_contact_Number_1").trim());
customer.setContactNumber2(resultSet.getString("customer_contact_Number_2").trim());
customer.setAddress(resultSet.getString("customer_address"));
customer.setOpeningBalance(resultSet.getString("opening_Balance"));
}
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[CustomerInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[CustomerInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(customer==null)throw new DAOException("Invalid ID.");
return customer;
}

public java.util.ArrayList<CustomerInterface> get() throws DAOException
{
ArrayList<CustomerInterface> customers=new ArrayList<CustomerInterface>();
CustomerInterface customer;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO[ArrayList<CustomerInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from customer_info");
while(resultSet.next())
{
customer=new Customer();
customer.setId(resultSet.getInt("customer_id"));
customer.setName(resultSet.getString("customer_name").trim());
customer.setContactNumber1(resultSet.getString("customer_contact_Number_1").trim());
customer.setContactNumber2(resultSet.getString("customer_contact_Number_2").trim());
customer.setAddress(resultSet.getString("customer_address"));
customer.setOpeningBalance(resultSet.getString("opening_Balance"));
customers.add(customer);
}
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[ArrayList<CustomerInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[ArrayList<CustomerInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(customers.size()==0) throw new DAOException("No Customer.");
return customers;
}
public void addCustomer(CustomerInterface customer) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO[void addCustomer(CustomerInterface customer)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into customer_info values(?,?,?,?,?,?)");
preparedStatement.setInt(1,customer.getId());
preparedStatement.setString(2,customer.getName());
preparedStatement.setString(3,customer.getContactNumber1());
preparedStatement.setString(4,customer.getContactNumber2());
preparedStatement.setString(5,customer.getAddress());
preparedStatement.setString(6,customer.getOpeningBalance());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void addCustomer(CustomerInterface customer)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void addCustomer(CustomerInterface customer)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
public void updateCustomer(CustomerInterface customer) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO [void updateCustomer(CustomerInterface customer)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update customer_info set customer_name=?, customer_contact_Number_1=?, customer_contact_Number_2=?, customer_address=?, opening_Balance=? where customer_id=?");
preparedStatement.setString(1,customer.getName());
preparedStatement.setString(2,customer.getContactNumber1());
preparedStatement.setString(3,customer.getContactNumber2());
preparedStatement.setString(4,customer.getAddress());
preparedStatement.setString(5,customer.getOpeningBalance());
preparedStatement.setInt(6,customer.getId());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void updateCustomer(CustomerInterface customer)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void updateCustomer(CustomerInterface customer)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");

}
}
}



public void deleteCustomer(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO [void deleteCustomer(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from customer_info where customer_id=?"); preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void deleteCustomer(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO [void deleteCustomer(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}


public java.util.Vector<CustomerInterface> getNameBySort() throws DAOException
{
Vector<CustomerInterface> customers=new Vector<CustomerInterface>();
CustomerInterface customer;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO[Vector<CustomerInterface>getNameBySort()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from customer_info order by customer_name");
while(resultSet.next())
{
customer=new Customer();
customer.setName(resultSet.getString("customer_name").trim());
customers.add(customer);
}
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[Vector<CustomerInterface> getNameBySort()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[Vector<CustomerInterface> getNameBySort()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(customers.size()==0) throw new DAOException("No Customers.");
return customers;
}



public CustomerInterface findCustomerByName(String name) throws DAOException
{
CustomerInterface customer=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("CustomerDAO[CustomerInterface findCustomerByName(String name)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from customer_info where customer_name=?");
preparedStatement.setString(1,name);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
customer=new Customer();
customer.setId(resultSet.getInt("customer_id"));
customer.setName(resultSet.getString("customer_name").trim());
customer.setContactNumber1(resultSet.getString("customer_contact_Number_1").trim());
customer.setContactNumber2(resultSet.getString("customer_contact_Number_2").trim());
customer.setAddress(resultSet.getString("customer_address"));
customer.setOpeningBalance(resultSet.getString("opening_Balance"));
}
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[CustomerInterface findCustomerByName(String name)]:"+sqlException);
throw new DAOException("Unable to find customer");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("CustomerDAO[CustomerInterface findCustomerByName(String name)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(customer==null)throw new DAOException("Invalid Name.");
return customer;
}

}