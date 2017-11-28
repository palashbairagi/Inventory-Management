package dl.user;
public interface UserInterface extends java.io.Serializable
{
public void setId(int id);
public int getId();
public void setName(String name);
public String getName();
public void setPassword(String password);
public String getPassword();
public void setRole(String role);
public String getRole();
}