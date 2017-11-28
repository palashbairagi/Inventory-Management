package dl.medicine;

public interface MedicineDAOInterface
{
public void addMedicine(MedicineInterface medicine)throws DAOException;
public void updateMedicine(MedicineInterface medicine)throws DAOException;
public void deleteMedicine(int id)throws DAOException;
public MedicineInterface get(int id)throws DAOException;
public java.util.ArrayList<MedicineInterface> get() throws DAOException;
public java.util.Vector<MedicineInterface> getNameBySort() throws DAOException;
public int getCount() throws DAOException;
public boolean exists(String name)throws DAOException;
public MedicineInterface findMedicineByName(String name) throws DAOException;
}