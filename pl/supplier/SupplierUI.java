package pl.supplier;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.supplier.*;
import pl.*;

public class SupplierUI extends JFrame implements ActionListener,ListSelectionListener, CrossButtonListener
{
private JTable suppliersTable;
private SupplierPanel supplierPanel;
private FindSupplierPanel findSupplierPanel;
private SupplierModel supplierModel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton,findButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private SupplierDAO supplierDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public SupplierUI()
{
super("Supplier Management");
supplierDAO=new SupplierDAO();
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
supplierPanel=new SupplierPanel();
findSupplierPanel=new FindSupplierPanel();
supplierModel=new SupplierModel();
suppliersTable=new JTable(supplierModel);
suppliersTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
suppliersTable.getColumnModel().getColumn(0).setPreferredWidth(30);
suppliersTable.getColumnModel().getColumn(1).setPreferredWidth(30);
suppliersTable.getColumnModel().getColumn(2).setPreferredWidth(100);
suppliersTable.getColumnModel().getColumn(3).setPreferredWidth(30);
suppliersTable.getColumnModel().getColumn(4).setPreferredWidth(30);
suppliersTable.getColumnModel().getColumn(5).setPreferredWidth(100);
suppliersTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(suppliersTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=supplierDAO.getCount();
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
supplierPanel.setBounds(10,40,450,265);
findSupplierPanel.setBounds(500,50,220,70);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
findButton.setBounds(350,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(supplierPanel);
container.add(findSupplierPanel);
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
suppliersTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
suppliersTable.setEnabled(true);
mode=uiModes.VIEWMODE;
supplierPanel.disableAll();
findSupplierPanel.enableAll();
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
suppliersTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
findButton.setVisible(false);
supplierPanel.reset();
findSupplierPanel.disableAll();
supplierPanel.enableAll();
supplierPanel.requestFocusOnName();
}
private void setEditModeStage()
{
suppliersTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
findButton.setVisible(false);
supplierPanel.enableAll();
findSupplierPanel.disableAll();
supplierPanel.requestFocusOnName();
}
private void setDeleteStage()
{
suppliersTable.setEnabled(false);
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
addSupplier();
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
updateSupplier();
}
}
if(event.getSource()==deleteButton)
{
if(suppliersTable.getSelectedRow()==-1)
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
supplierDAO.deleteSupplier(supplierPanel.getId());
supplierModel.changeNotify();
suppliersTable.addNotify();
suppliersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Supplier Deleted");
supplierPanel.reset();
if(totalNumberOfRecords>0)
{
suppliersTable.setRowSelectionInterval(0,0);
suppliersTable.scrollRectToVisible(suppliersTable.getCellRect(0,0,false));
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
if(!findSupplierPanel.validate(this))return;
int newRowIndex=supplierModel.getSupplierIndex(findSupplierPanel.getName());
suppliersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
suppliersTable.scrollRectToVisible(suppliersTable.getCellRect(newRowIndex,0,false));
}
}
private void addSupplier()
{
if(!supplierPanel.validate(this))return;
SupplierInterface supplier=new Supplier();
supplier.setName(supplierPanel.getName());
supplier.setContactNumber1(supplierPanel.getContactNumber1());
supplier.setContactNumber2(supplierPanel.getContactNumber2());
supplier.setAddress(supplierPanel.getAddress());
supplier.setOpeningBalance(supplierPanel.getOpeningBalance());
try
{
supplierDAO.addSupplier(supplier);
supplierModel.changeNotify();
suppliersTable.addNotify();
suppliersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Supplier Added");
int newRowIndex=supplierModel.getSupplierIndex(supplier.getName());
suppliersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
suppliersTable.scrollRectToVisible(suppliersTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updateSupplier()
{
if(!supplierPanel.validate(this))return;
SupplierInterface supplier=new Supplier();
supplier.setId(supplierPanel.getId());
supplier.setName(supplierPanel.getName());
supplier.setContactNumber1(supplierPanel.getContactNumber1());
supplier.setContactNumber2(supplierPanel.getContactNumber2());
supplier.setAddress(supplierPanel.getAddress());
supplier.setOpeningBalance(supplierPanel.getOpeningBalance());
try
{
supplierDAO.updateSupplier(supplier);
supplierModel.changeNotify();
suppliersTable.addNotify();
suppliersTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Supplier Update");
int newRowIndex=supplierModel.getSupplierIndex(supplier.getName());
suppliersTable.setRowSelectionInterval(newRowIndex,newRowIndex);
suppliersTable.scrollRectToVisible(suppliersTable.getCellRect(newRowIndex,0,false));
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
totalNumberOfRecords=supplierDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=suppliersTable.getSelectedRow();
if(selectedRowIndex>=0)
{
SupplierInterface supplier=supplierModel.get(selectedRowIndex);
supplierPanel.set(supplier.getId(),supplier.getName(), supplier.getContactNumber1(), supplier.getContactNumber2(),supplier.getAddress(), supplier.getOpeningBalance());
}
else
{
supplierPanel.reset();
}
}
}