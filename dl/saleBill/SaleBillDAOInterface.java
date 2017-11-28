package dl.saleBill;
public interface SaleBillDAOInterface
{
public void addSaleBillInfo(SaleBillInterface saleBill)throws DAOException;
public void addSaleBillDetailInfo(java.util.ArrayList<SaleBillDetailInterface> saleBillDetail)throws DAOException;
public void updateSaleBillInfo(SaleBillInterface saleBill)throws DAOException;
public void deleteSaleBill(int billNumber)throws DAOException;
public int getBillNumber() throws DAOException;
public SaleBillInterface getSaleBillInfo(int billNumber)throws DAOException;
public java.util.ArrayList<SaleBillInterface> getSaleBillInfo() throws DAOException;
public java.util.ArrayList<SaleBillDetailInterface> getSaleBillDetailInfo(int billNumber) throws DAOException;
public int getCount() throws DAOException;
public boolean exists(int billNo)throws DAOException;
}