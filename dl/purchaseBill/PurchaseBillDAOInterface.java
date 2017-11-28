package dl.purchaseBill;
public interface PurchaseBillDAOInterface
{
public void addPurchaseBill(PurchaseBillInterface purchaseBill)throws DAOException;
public void updatePurchaseBill(PurchaseBillInterface purchaseBill)throws DAOException;
public void deletePurchaseBill(int id)throws DAOException;
public PurchaseBillInterface get(int id)throws DAOException;
public java.util.ArrayList<PurchaseBillInterface> get() throws DAOException;
public int getCount() throws DAOException;
}