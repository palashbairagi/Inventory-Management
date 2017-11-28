package dl.user;
import java.sql.*;
import java.util.*;
public class UserDAO implements UserDAOInterface
{
public static Connection getConnection() throws DAOException
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println("UserDAO[Connection getConnection()]:"+classNotFoundException);
throw new DAOException("JdbcOdbcDriver not found.");
}
Connection connection=null;
try
{
connection=DriverManager.getConnection("jdbc:odbc:medicaldsn","medical","newerasoftech");
}catch(SQLException sqlException)
{
System.out.println("UserDAO[Connection getConnection()]:"+sqlException);
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
System.out.println("UserDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from user_info");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("UserDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}
public boolean exists(int id) throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO[boolean exists(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select count(*) as total_records from user_info where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("total_records");
}catch(SQLException sqlException)
{
System.out.println("UserDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to check existence.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count!=0;
}
public UserInterface get(int id) throws DAOException
{
UserInterface user=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO[UserInterface get(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from user_info where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
user=new User();
user.setId(id);
user.setName(resultSet.getString("name").trim());
user.setPassword(resultSet.getString("purchasePrice").trim());
user.setRole(resultSet.getString("salePrice").trim());
}
}catch(SQLException sqlException)
{
System.out.println("UserDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO[UserInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(user==null)throw new DAOException("Invalid ID.");
return user;
}
public java.util.ArrayList<UserInterface> get() throws DAOException
{
ArrayList<UserInterface> users=new ArrayList<UserInterface>();
UserInterface user;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO[ArrayList<UserInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from user_info");
while(resultSet.next())
{
user=new User();
user.setId(resultSet.getInt("id"));
user.setName(resultSet.getString("user_name").trim());
user.setPassword(resultSet.getString("password").trim());
user.setRole(resultSet.getString("user_role").trim());
users.add(user);
}
}catch(SQLException sqlException)
{
System.out.println("UserDAO[ArrayList<UserInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO[ArrayList<UserInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(users.size()==0) throw new DAOException("No Users.");
return users;
}
public void addUser(UserInterface user) throws DAOException
{
if(exists(user.getId())) throw new DAOException("ID. Exists.");
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO[void addUser(UserInterface user)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into user_info values(?,?,?,?)");
preparedStatement.setInt(1,user.getId());
preparedStatement.setString(2,user.getName());
preparedStatement.setString(3,user.getPassword());
preparedStatement.setString(4,user.getRole());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void addUser(UserInterface user_info)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void addUser(UserInterface user)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
public void updateUser(UserInterface user) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO [void updateUser(UserInterface user)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update user_info set user_name=?,password=?,user_role=? where id=?");
preparedStatement.setString(1,user.getName());
preparedStatement.setString(2,user.getPassword());
preparedStatement.setString(3,user.getRole());
preparedStatement.setInt(4,user.getId());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void updateUser(UserInterface user)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void updateUser(UserInterface user)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");

}
}
}




public void deleteUser(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("UserDAO [void deleteUser(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from user_info where id=?"); preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void deleteUser(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("UserDAO [void deleteUser(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
}