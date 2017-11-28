package dl.user;
public interface UserDAOInterface
{
public void addUser(UserInterface user)throws DAOException;
public void updateUser(UserInterface user)throws DAOException;
public void deleteUser(int id)throws DAOException;
public UserInterface get(int id)throws DAOException;
public java.util.ArrayList<UserInterface> get() throws DAOException;
public int getCount() throws DAOException;
public boolean exists(int id)throws DAOException;
}