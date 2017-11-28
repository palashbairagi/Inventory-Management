package dl.supplier;
public interface SupplierDAOInterface
{
public void addSupplier(SupplierInterface supplier)throws DAOException;
public void updateSupplier(SupplierInterface supplier)throws DAOException;
public void deleteSupplier(int id)throws DAOException;
public SupplierInterface get(int id)throws DAOException;
public java.util.ArrayList<SupplierInterface> get() throws DAOException;
public int getCount() throws DAOException;
public SupplierInterface findSupplierByName(String name)throws DAOException;
public java.util.Vector<SupplierInterface> getNameBySort() throws DAOException;
}