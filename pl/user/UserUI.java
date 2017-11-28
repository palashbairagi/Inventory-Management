package pl.user;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.user.*;
import pl.*;

public class UserUI extends JFrame implements ActionListener, ListSelectionListener, CrossButtonListener
{
private JTable usersTable;
private UserPanel userPanel;
private UserModel userModel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private UserDAO userDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public UserUI()
{
super("User Management");
userDAO=new UserDAO();
initComponents();
setViewStage();
CrossButtonHandler cbh=new CrossButtonHandler(this);
addWindowListener(cbh);
}
public void windowClosing(WindowEvent ev)
{
dispose();
}
public void initComponents()
{
container=getContentPane();
userPanel=new UserPanel();
userModel=new UserModel();
usersTable=new JTable(userModel);
usersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
usersTable.getColumnModel().getColumn(0).setPreferredWidth(30);
usersTable.getColumnModel().getColumn(1).setPreferredWidth(30);
usersTable.getColumnModel().getColumn(2).setPreferredWidth(100);
usersTable.getColumnModel().getColumn(3).setPreferredWidth(30);
usersTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(usersTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=userDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount=new JLabel(String.valueOf(totalNumberOfRecords));
recordCount.setFont(font);
recordCount.setForeground(new Color(14,3,105));
addButton=new JButton("Add");
editButton=new JButton("Edit");
deleteButton=new JButton("Delete");
cancelButton=new JButton("Cancel");
addButton.addActionListener(this);
editButton.addActionListener(this);
deleteButton.addActionListener(this);
cancelButton.addActionListener(this);
container.setLayout(null);
recordCountCaption.setBounds(600,10,100,30);
recordCount.setBounds(705,10,100,30);
jScrollPane.setBounds(10,320,850,210);
userPanel.setBounds(10,40,850,265);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(userPanel);
container.add(addButton);
container.add(editButton);
container.add(deleteButton);
container.add(cancelButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(900,650);
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
try
{
usersTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
usersTable.setEnabled(true);
mode=uiModes.VIEWMODE;
userPanel.disableAll();
addButton.setEnabled(true);
if(totalNumberOfRecords==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
}
cancelButton.setEnabled(false);
addButton.setText("Add");
editButton.setText("Edit");
}
private void setAddModeStage()
{
usersTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
userPanel.reset();
userPanel.enableAll();
userPanel.requestFocusOnName();
}
private void setEditModeStage()
{
usersTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
userPanel.enableAll();
userPanel.requestFocusOnName();
}
private void setDeleteStage()
{
usersTable.setEnabled(false);
mode=uiModes.DELETEMODE;
deleteButton.setEnabled(false);
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
}
public void actionPerformed(ActionEvent event)
{
if(event.getSource()==addButton)
{
if(mode==uiModes.VIEWMODE)
{
setAddModeStage();
}
else
{
addUser();
}
}
if(event.getSource()==editButton)
{
if(mode==uiModes.VIEWMODE)
{
setEditModeStage();
}
else
{
updateUser();
}
}
if(event.getSource()==deleteButton)
{
if(usersTable.getSelectedRow()==-1)
{
JOptionPane.showMessageDialog(this,"Select a row to delete");
return;
}
setDeleteStage();
int selected=JOptionPane.showConfirmDialog(this,"Are you sure?","Confirming delete operation",JOptionPane.YES_NO_OPTION);
if(selected==JOptionPane.YES_OPTION)
{
try 
{
userDAO.deleteUser(userPanel.getId());
userModel.changeNotify();
usersTable.addNotify();
usersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"User Deleted");
userPanel.reset();
if(totalNumberOfRecords>0)
{
usersTable.setRowSelectionInterval(0,0);
usersTable.scrollRectToVisible(usersTable.getCellRect(0,0,false));
valueChanged(null);
}
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
else
{
setViewStage();
}
}
if(event.getSource()==cancelButton)
{
valueChanged(null);
setViewStage();
}
}
private void addUser()
{
if(!userPanel.validate(this))return;
UserInterface user=new User();
user.setName(userPanel.getName());
user.setPassword(userPanel.getPassword());
user.setRole(userPanel.getRole());
try
{
userDAO.addUser(user);
userModel.changeNotify();
usersTable.addNotify();
usersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"User Added");
int newRowIndex=userModel.getUserIndex(user.getName());
usersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
usersTable.scrollRectToVisible(usersTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updateUser()
{
if(!userPanel.validate(this))return;
UserInterface user=new User();
user.setId(userPanel.getId());
user.setName(userPanel.getName());
user.setPassword(userPanel.getPassword());
user.setRole(userPanel.getRole());
try
{
userDAO.updateUser(user);
userModel.changeNotify();
usersTable.addNotify();
usersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"User Update");
int newRowIndex=userModel.getUserIndex(user.getName());
usersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
usersTable.scrollRectToVisible(usersTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void setTotalNumberOfRecords()
{
try
{
totalNumberOfRecords=userDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=usersTable.getSelectedRow();
if(selectedRowIndex>=0)
{
UserInterface user=userModel.get(selectedRowIndex);
userPanel.set(user.getId(),user.getName(), user.getPassword(), user.getRole());
}
else
{
userPanel.reset();
}
}
}