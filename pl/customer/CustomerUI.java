package pl.customer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.customer.*;
import pl.*;

public class CustomerUI extends JFrame implements ActionListener,ListSelectionListener, CrossButtonListener
{
private JTable customersTable;
private CustomerPanel customerPanel;
private CustomerModel customerModel;
private FindCustomerPanel findCustomerPanel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton,findButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private CustomerDAO customerDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public CustomerUI()
{
super("Customer Management");
customerDAO=new CustomerDAO();
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
customerPanel=new CustomerPanel();
findCustomerPanel=new FindCustomerPanel();
customerModel=new CustomerModel();
customersTable=new JTable(customerModel);
customersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
customersTable.getColumnModel().getColumn(0).setPreferredWidth(30);
customersTable.getColumnModel().getColumn(1).setPreferredWidth(30);
customersTable.getColumnModel().getColumn(2).setPreferredWidth(100);
customersTable.getColumnModel().getColumn(3).setPreferredWidth(30);
customersTable.getColumnModel().getColumn(4).setPreferredWidth(30);
customersTable.getColumnModel().getColumn(5).setPreferredWidth(100);
customersTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(customersTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=customerDAO.getCount();
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
findButton=new JButton("Find");
addButton.addActionListener(this);
editButton.addActionListener(this);
deleteButton.addActionListener(this);
cancelButton.addActionListener(this);
findButton.addActionListener(this);
container.setLayout(null);
recordCountCaption.setBounds(600,10,100,30);
recordCount.setBounds(705,10,100,30);
jScrollPane.setBounds(10,320,850,210);
customerPanel.setBounds(10,40,450,265);
findCustomerPanel.setBounds(500,50,220,70);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
findButton.setBounds(350,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(customerPanel);
container.add(findCustomerPanel);
container.add(addButton);
container.add(editButton);
container.add(deleteButton);
container.add(cancelButton);
container.add(findButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(900,650);
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
try
{
customersTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
customersTable.setEnabled(true);
mode=uiModes.VIEWMODE;
customerPanel.disableAll();
findCustomerPanel.enableAll();
addButton.setEnabled(true);
if(totalNumberOfRecords==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
findButton.setVisible(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
findButton.setVisible(true);
}
cancelButton.setEnabled(false);
addButton.setText("Add");
editButton.setText("Edit");
}
private void setAddModeStage()
{
customersTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
findButton.setVisible(false);
customerPanel.reset();
customerPanel.enableAll();
findCustomerPanel.disableAll();
customerPanel.requestFocusOnName();
}
private void setEditModeStage()
{
customersTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
findButton.setVisible(false);
customerPanel.enableAll();
findCustomerPanel.disableAll();
customerPanel.requestFocusOnName();
}
private void setDeleteStage()
{
customersTable.setEnabled(false);
mode=uiModes.DELETEMODE;
deleteButton.setEnabled(false);
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
findButton.setVisible(false);
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
addCustomer();
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
updateCustomer();
}
}
if(event.getSource()==deleteButton)
{
if(customersTable.getSelectedRow()==-1)
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
customerDAO.deleteCustomer(customerPanel.getId());
customerModel.changeNotify();
customersTable.addNotify();
customersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Customer Deleted");
customerPanel.reset();
if(totalNumberOfRecords>0)
{
customersTable.setRowSelectionInterval(0,0);
customersTable.scrollRectToVisible(customersTable.getCellRect(0,0,false));
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
if(event.getSource()==findButton)
{
if(!findCustomerPanel.validate(this))return;
int newRowIndex=customerModel.getCustomerIndex(findCustomerPanel.getName());
customersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
customersTable.scrollRectToVisible(customersTable.getCellRect(newRowIndex,0,false));
}
}
private void addCustomer()
{
if(!customerPanel.validate(this))return;
CustomerInterface customer=new Customer();
customer.setName(customerPanel.getName());
customer.setContactNumber1(customerPanel.getContactNumber1());
customer.setContactNumber2(customerPanel.getContactNumber2());
customer.setAddress(customerPanel.getAddress());
customer.setOpeningBalance(customerPanel.getOpeningBalance());
try
{
customerDAO.addCustomer(customer);
customerModel.changeNotify();
customersTable.addNotify();
customersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Customer Added");
int newRowIndex=customerModel.getCustomerIndex(customer.getName());
customersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
customersTable.scrollRectToVisible(customersTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updateCustomer()
{
if(!customerPanel.validate(this))return;
CustomerInterface customer=new Customer();
customer.setId(customerPanel.getId());
customer.setName(customerPanel.getName());
customer.setContactNumber1(customerPanel.getContactNumber1());
customer.setContactNumber2(customerPanel.getContactNumber2());
customer.setAddress(customerPanel.getAddress());
customer.setOpeningBalance(customerPanel.getOpeningBalance());
try
{
customerDAO.updateCustomer(customer);
customerModel.changeNotify();
customersTable.addNotify();
customersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Customer Update");
int newRowIndex=customerModel.getCustomerIndex(customer.getName());
customersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
customersTable.scrollRectToVisible(customersTable.getCellRect(newRowIndex,0,false));
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
totalNumberOfRecords=customerDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=customersTable.getSelectedRow();
if(selectedRowIndex>=0)
{
CustomerInterface customer=customerModel.get(selectedRowIndex);
customerPanel.set(customer.getId(),customer.getName(), customer.getContactNumber1(), customer.getContactNumber2(),customer.getAddress(), customer.getOpeningBalance());
}
else
{
customerPanel.reset();
}
}
}