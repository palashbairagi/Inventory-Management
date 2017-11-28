package dl.medicine;

import java.sql.*;
import java.util.*;

public class MedicineDAO implements MedicineDAOInterface
{
public static Connection getConnection() throws DAOException
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println("MedicineDAO[Connection getConnection()]:"+classNotFoundException);
throw new DAOException("JdbcOdbcDriver not found.");
}
Connection connection=null;
try
{
connection=DriverManager.getConnection("jdbc:odbc:medicaldsn","medical","newerasoftech");
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[Connection getConnection()]:"+sqlException);
throw new DAOException("Unable to connect using DSN:medicaldsn");
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
System.out.println("MedicineDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from medicine");
resultSet.next();
count=resultSet.getInt("total_records");
} catch(SQLException sqlException)
{
System.out.println("MedicineDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[int getCount()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}
public boolean exists(String name) throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[boolean exists(String name)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select count(*) as total_records from medicine where name=?");
preparedStatement.setString(1,name);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("total_records");
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to check existence.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[boolean exists(String name)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count!=0;
}
public MedicineInterface get(int id) throws DAOException
{
MedicineInterface medicine=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[MedicineInterface get(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from medicine where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
medicine=new Medicine();
medicine.setId(id);
medicine.setName(resultSet.getString("name").trim());
medicine.setPurchasePrice(resultSet.getString("purchase_Price").trim());
medicine.setSalePrice(resultSet.getString("sale_Price").trim());
medicine.setManufacturingDate(convertSQLDateToUtilDate(resultSet.getDate("manufacturing_Date")));
medicine.setExpiryDate(convertSQLDateToUtilDate(resultSet.getDate("expiry_Date")));
}
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[MedicineInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[MedicineInterface get(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(medicine==null)throw new DAOException("Invalid ID.");
return medicine;
}

public java.util.ArrayList<MedicineInterface> get() throws DAOException
{
ArrayList<MedicineInterface> medicines=new ArrayList<MedicineInterface>();
MedicineInterface medicine;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from medicine");
while(resultSet.next())
{
medicine=new Medicine();
medicine.setId(resultSet.getInt("id"));
medicine.setName(resultSet.getString("name").trim());
medicine.setPurchasePrice(resultSet.getString("purchase_Price").trim());
medicine.setSalePrice(resultSet.getString("sale_Price").trim());
medicine.setManufacturingDate(convertSQLDateToUtilDate(resultSet.getDate("manufacturing_Date")));
medicine.setExpiryDate(convertSQLDateToUtilDate(resultSet.getDate("expiry_Date")));
medicines.add(medicine);
}
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(medicines.size()==0) throw new DAOException("No Medicines.");
return medicines;
}

public void addMedicine(MedicineInterface medicine) throws DAOException
{
if(exists(medicine.getName())) throw new DAOException("Product Exists");
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[void add(MedicineInterface medicine)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into medicine values(?,?,?,?,?,?)");
preparedStatement.setInt(1,medicine.getId());
preparedStatement.setString(2,medicine.getName());
preparedStatement.setString(3,medicine.getPurchasePrice());
preparedStatement.setString(4,medicine.getSalePrice());
preparedStatement.setDate(5,convertUtilDateToSQLDate(medicine.getManufacturingDate()));
preparedStatement.setDate(6,convertUtilDateToSQLDate(medicine.getExpiryDate()));
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void add(MedicineInterface medicine)]:"+sqlException);
throw new DAOException("Unable to insert record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void add(MedicineInterface medicine)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}
public void updateMedicine(MedicineInterface medicine) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO [void update(MedicineInterface medicine)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update medicine set name=?,purchase_Price=?,sale_Price=?,manufacturing_Date=?,expiry_Date=? where id=?");
preparedStatement.setString(1,medicine.getName());
preparedStatement.setString(2,medicine.getPurchasePrice());
preparedStatement.setString(3,medicine.getSalePrice());
preparedStatement.setDate(4,convertUtilDateToSQLDate(medicine.getManufacturingDate()));
preparedStatement.setDate(5,convertUtilDateToSQLDate(medicine.getExpiryDate()));
preparedStatement.setInt(6,medicine.getId());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void update(MedicineInterface medicine)]:"+sqlException);
throw new DAOException("Unable to update record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void update(MedicineInterface medicine)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");

}
}
}

public void deleteMedicine(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO [void delete(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from medicine where id=?"); preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void delete(int id)]:"+sqlException);
throw new DAOException("Unable to delete record.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO [void delete(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
}


public java.util.Vector<MedicineInterface> getNameBySort() throws DAOException
{
Vector<MedicineInterface> medicines=new Vector<MedicineInterface>();
MedicineInterface medicine;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface>get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from medicine order by name");
while(resultSet.next())
{
medicine=new Medicine();
medicine.setName(resultSet.getString("name").trim());
medicines.add(medicine);
}
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[Vector<MedicineInterface> get()]:"+sqlException);
throw new DAOException("Unable to get records.");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[Vector<MedicineInterface> get()]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(medicines.size()==0) throw new DAOException("No Medicines.");
return medicines;
}


public MedicineInterface findMedicineByName(String name) throws DAOException
{
MedicineInterface medicine=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[MedicineInterface findMedicineByName(String name)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from medicine where name=?");
preparedStatement.setString(1,name);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
medicine=new Medicine();
medicine.setId(resultSet.getInt("id"));
medicine.setName(resultSet.getString("name").trim());
medicine.setPurchasePrice(resultSet.getString("purchase_Price").trim());
medicine.setSalePrice(resultSet.getString("sale_Price").trim());
medicine.setManufacturingDate(convertSQLDateToUtilDate(resultSet.getDate("manufacturing_Date")));
medicine.setExpiryDate(convertSQLDateToUtilDate(resultSet.getDate("expiry_Date")));
}
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[MedicineInterface findMedicineByName(String name)]:"+sqlException);
throw new DAOException("Unable to findMedicineByName record");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[MedicineInterface findMedicineByName(String name)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(medicine==null)throw new DAOException("Invalid Name.");
return medicine;
}



/*
public java.util.ArrayList<MedicineInterface> findMedicineByName(String name) throws DAOException
{
ArrayList<MedicineInterface> medicines=new ArrayList<MedicineInterface>();
MedicineInterface medicine;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface> findMedicineByName(String name)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
name=name+"%";
System.out.println(name);
preparedStatement=connection.prepareStatement("select * from medicine where name like ? order by name"); 
preparedStatement.setString(1,name);
resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
medicine=new Medicine();
medicine.setId(resultSet.getInt("id"));
medicine.setName(resultSet.getString("name").trim());
medicine.setPurchasePrice(resultSet.getString("purchase_Price").trim());
medicine.setSalePrice(resultSet.getString("sale_Price").trim());
medicine.setManufacturingDate(convertSQLDateToUtilDate(resultSet.getDate("manufacturing_Date")));
medicine.setExpiryDate(convertSQLDateToUtilDate(resultSet.getDate("expiry_Date")));
medicines.add(medicine);
}
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[ArrayList<MedicineInterface> findMedicineByName(String name)]:"+sqlException);
throw new DAOException("Unable to findMedicineByName");
}
finally
{
try
{
if(!connection.isClosed()) connection.close();
}catch(SQLException sqlException)
{
System.out.println("MedicineDAO[MedicineInterface findMedicineByName(String name)]:"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
if(medicines.size()==0) throw new DAOException("No Medicines.");
return medicines;
}
*/

}