package pl.purchaseBill;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.purchaseBill.*;
import pl.*;

public class PurchaseBillUI extends JFrame implements ActionListener,ListSelectionListener, CrossButtonListener
{
private JTable purchaseBillsTable;
private PurchaseBillPanel purchaseBillPanel;
private PurchaseBillModel purchaseBillModel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private PurchaseBillDAO purchaseBillDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public PurchaseBillUI()
{
super("PurchaseBill Management");
purchaseBillDAO=new PurchaseBillDAO();
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
purchaseBillPanel=new PurchaseBillPanel();
purchaseBillModel=new PurchaseBillModel();
purchaseBillsTable=new JTable(purchaseBillModel);
purchaseBillsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
purchaseBillsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
purchaseBillsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
purchaseBillsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
purchaseBillsTable.getColumnModel().getColumn(3).setPreferredWidth(30);
purchaseBillsTable.getColumnModel().getColumn(4).setPreferredWidth(30);
purchaseBillsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
purchaseBillsTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(purchaseBillsTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=purchaseBillDAO.getCount();
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
purchaseBillPanel.setBounds(10,40,850,265);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(purchaseBillPanel);
container.add(addButton);
container.add(editButton);
container.add(deleteButton);
container.add(cancelButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(900,650);
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
try
{
purchaseBillsTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
purchaseBillsTable.setEnabled(true);
mode=uiModes.VIEWMODE;
purchaseBillPanel.disableAll();
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
purchaseBillsTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
purchaseBillPanel.reset();
purchaseBillPanel.enableAll();
purchaseBillPanel.requestFocusOnSupplierId();
}
private void setEditModeStage()
{
purchaseBillsTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
purchaseBillPanel.enableAll();
purchaseBillPanel.requestFocusOnSupplierId();
}
private void setDeleteStage()
{
purchaseBillsTable.setEnabled(false);
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
addPurchaseBill();
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
updatePurchaseBill();
}
}
if(event.getSource()==deleteButton)
{
if(purchaseBillsTable.getSelectedRow()==-1)
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
purchaseBillDAO.deletePurchaseBill(purchaseBillPanel.getId());
purchaseBillModel.changeNotify();
purchaseBillsTable.addNotify();
purchaseBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"PurchaseBill Deleted");
purchaseBillPanel.reset();
if(totalNumberOfRecords>0)
{
purchaseBillsTable.setRowSelectionInterval(0,0);
purchaseBillsTable.scrollRectToVisible(purchaseBillsTable.getCellRect(0,0,false));
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
private void addPurchaseBill()
{
PurchaseBillInterface purchaseBill=new PurchaseBill();
purchaseBill.setSupplierId(purchaseBillPanel.getSupplierId());
purchaseBill.setRate(purchaseBillPanel.getRate());
purchaseBill.setQuantity(purchaseBillPanel.getQuantity());
purchaseBill.setDiscount(purchaseBillPanel.getDiscount());
purchaseBill.setTotalAmount(purchaseBillPanel.getTotalAmount());
try
{
purchaseBillDAO.addPurchaseBill(purchaseBill);
purchaseBillModel.changeNotify();
purchaseBillsTable.addNotify();
purchaseBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"PurchaseBill Added");
int newRowIndex=purchaseBillModel.getPurchaseBillIndex(purchaseBill.getPurchaseBillNo());
purchaseBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
purchaseBillsTable.scrollRectToVisible(purchaseBillsTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updatePurchaseBill()
{
PurchaseBillInterface purchaseBill=new PurchaseBill();
purchaseBill.setId(purchaseBillPanel.getId());
purchaseBill.setSupplierId(purchaseBillPanel.getSupplierId());
purchaseBill.setRate(purchaseBillPanel.getRate());
purchaseBill.setQuantity(purchaseBillPanel.getQuantity());
purchaseBill.setDiscount(purchaseBillPanel.getDiscount());
purchaseBill.setTotalAmount(purchaseBillPanel.getTotalAmount());
try
{
purchaseBillDAO.updatePurchaseBill(purchaseBill);
purchaseBillModel.changeNotify();
purchaseBillsTable.addNotify();
purchaseBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"PurchaseBill Update");
int newRowIndex=purchaseBillModel.getPurchaseBillIndex(purchaseBill.getPurchaseBillNo());
purchaseBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
purchaseBillsTable.scrollRectToVisible(purchaseBillsTable.getCellRect(newRowIndex,0,false));
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
totalNumberOfRecords=purchaseBillDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=purchaseBillsTable.getSelectedRow();
if(selectedRowIndex>=0)
{
PurchaseBillInterface purchaseBill=purchaseBillModel.get(selectedRowIndex);
purchaseBillPanel.set(purchaseBill.getId(),purchaseBill.getSupplierId(), purchaseBill.getRate(), purchaseBill.getQuantity(),purchaseBill.getDiscount(), purchaseBill.getTotalAmount());
}
else
{
purchaseBillPanel.reset();
}
}
}