package pl.user;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;

public class UserPanel extends JPanel
{
private JLabel nameCaption;
private JLabel passwordCaption, roleCaption;
private JTextField id,name,password,nonEditableRole;
private JComboBox roleComboBox;
private TitledBorder titledBorder;
//private Color enabledColor=Color.black;
//private Color disabledColor=Color.red;

public UserPanel()
{
initComponents();
}
 public void initComponents()
{
nameCaption=new JLabel("Name");
passwordCaption=new JLabel("Password");
roleCaption=new JLabel("Type");

id=new JTextField(50);
name=new JTextField(50);
password=new JTextField(50);
nonEditableRole=new JTextField(50);
nonEditableRole.setEditable(false);
roleComboBox=new JComboBox();
roleComboBox.addItem("Admin");
roleComboBox.addItem("User");

titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);

nameCaption.setBounds(10,20,100,25);
name.setBounds(50,20,200,25);
passwordCaption.setBounds(10,55,100,25);
password.setBounds(100,55,50,25);
roleCaption.setBounds(10,90,100,25);
nonEditableRole.setBounds(100,90,100,25);
roleComboBox.setBounds(100,90,100,25);
add(id);
add(nameCaption);
add(name);
add(passwordCaption);
add(password);
add(roleCaption);
add(nonEditableRole);
add(roleComboBox);
}
    
public int getId()
{
return Integer.parseInt(id.getText().trim());
}
public void setId(int id)
{
this.id.setText(String.valueOf(id));
}
public String getName()
{
return name.getText().trim();
}
public void setName(String name)
{
this.name.setText(name);
}
public String getPassword()
{
return this.password.getText().trim();
}
public void setPassword(String password)
{
this.password.setText(password);
}
public String getRole()
{
nonEditableRole.setText(roleComboBox.getSelectedItem().toString());
return roleComboBox.getSelectedItem().toString();
}
public void setRole(String role)
{
nonEditableRole.setText(role);
this.roleComboBox.setSelectedItem(role);
}

public void requestFocusOnName()
{
name.requestFocus();
}
public void reset()
{
name.setText("");
password.setText("");
roleComboBox.setSelectedIndex(0);
}

public void set(int id, String name, String password, String role)
{
setId(id);
setName(name);
setPassword(password);
setRole(role);
}

public void enableAll()
{
name.setEditable(true);
password.setEditable(true);
roleComboBox.setVisible(true);
nonEditableRole.setVisible(false);
super.repaint();
}
public void disableAll()
{
name.setEditable(false);
password.setEditable(false);
nonEditableRole.setVisible(true);
roleComboBox.setVisible(false);
super.repaint();
}

public boolean validate(UserUI userUI)
{
if(InputValidator.isTextFieldEmpty(userUI,name,"Name required",true))return false;
if(InputValidator.isTextFieldEmpty(userUI,password,"Password required",true))return false;
return true;
}

}