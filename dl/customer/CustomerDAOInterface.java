package dl.customer;
public interface CustomerDAOInterface
{
public void addCustomer(CustomerInterface customer)throws DAOException;
public void updateCustomer(CustomerInterface customer)throws DAOException;
public void deleteCustomer(int id)throws DAOException;
public CustomerInterface get(int id)throws DAOException;
public java.util.ArrayList<CustomerInterface> get() throws DAOException;
public int getCount() throws DAOException;
public CustomerInterface findCustomerByName(String name)throws DAOException;
public java.util.Vector<CustomerInterface> getNameBySort() throws DAOException;
}