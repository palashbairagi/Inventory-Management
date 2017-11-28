package pl.saleBill;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.saleBill.*;
import pl.*;

public class SaleBillUI extends JFrame implements ActionListener,ListSelectionListener, CrossButtonListener
{
private JTable saleBillsTable;
private BillInfoPanel billInfoPanel;
//private SaleBillPanel saleBillPanel;
private CustomerPanel customerPanel;
//private SaleBillModel saleBillModel;
private MedicinePanel medicinePanel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton;
private JLabel recordCountCaption,recordCount;
private Container container;
//private SaleBillDAO saleBillDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public SaleBillUI()
{
super("SaleBill Management");
saleBillDAO=new SaleBillDAO();
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
billInfoPanel=new BillInfoPanel();
medicinePanel=new MedicinePanel();
//saleBillPanel=new SaleBillPanel();
customerPanel=new CustomerPanel();
//saleBillModel=new SaleBillModel();
saleBillsTable=new JTable(saleBillModel);
saleBillsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
saleBillsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
saleBillsTable.getColumnModel().getColumn(3).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(4).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
saleBillsTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(saleBillsTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
saleBillsTable.setShowHorizontalLines(false);
saleBillsTable.setShowVerticalLines(false);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=saleBillDAO.getCount();
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
//saleBillPanel.setBounds(10,40,440,265);
billInfoPanel.setBounds(20,40,480,80);
customerPanel.setBounds(20,100,480,100);
medicinePanel.setBounds(20,200,200,80);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(saleBillPanel);
container.add(billInfoPanel);
container.add(medicinePanel);
container.add(customerPanel);
container.add(addButton);
container.add(editButton);
container.add(deleteButton);
container.add(cancelButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(900,650);
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
try
{
saleBillsTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
saleBillsTable.setEnabled(true);
mode=uiModes.VIEWMODE;
saleBillPanel.disableAll();
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
saleBillsTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
saleBillPanel.reset();
saleBillPanel.enableAll();
saleBillPanel.requestFocusOnProductId();
}
private void setEditModeStage()
{
saleBillsTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
saleBillPanel.enableAll();
saleBillPanel.requestFocusOnProductId();
}
private void setDeleteStage()
{
saleBillsTable.setEnabled(false);
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
addSaleBill();
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
updateSaleBill();
}
}
if(event.getSource()==deleteButton)
{
if(saleBillsTable.getSelectedRow()==-1)
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
saleBillDAO.deleteSaleBill(saleBillPanel.getSaleBillNo());
saleBillModel.changeNotify();
saleBillsTable.addNotify();
saleBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"SaleBill Deleted");
saleBillPanel.reset();
if(totalNumberOfRecords>0)
{
saleBillsTable.setRowSelectionInterval(0,0);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(0,0,false));
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
private void addSaleBill()
{
SaleBillInterface saleBill=new SaleBill();
saleBill.setBillNo(saleBillPanel.getSaleBillNo());
saleBill.setProductId(saleBillPanel.getProductId());
saleBill.setCustomerId(saleBillPanel.getCustomerId());
saleBill.setRate(saleBillPanel.getRate());
try
{
saleBill.setBillDate(saleBillPanel.getSaleBillDate());
}catch(ParseException parseException)
{
System.out.println("SaleBillUI [addSaleBill]"+ parseException);
}
saleBill.setQuantity(saleBillPanel.getQuantity());
saleBill.setDiscount(saleBillPanel.getDiscount());
saleBill.setTotalAmount(saleBillPanel.getTotalAmount());
saleBill.setPaidAmount(saleBillPanel.getPaidAmount());
try
{
saleBillDAO.addSaleBill(saleBill);
saleBillModel.changeNotify();
saleBillsTable.addNotify();
saleBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"SaleBill Added");
int newRowIndex=saleBillModel.getSaleBillIndex(saleBill.getBillNo());
saleBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}

private void updateSaleBill()
{
SaleBillInterface saleBill=new SaleBill();
saleBill.setBillNo(saleBillPanel.getSaleBillNo());
saleBill.setProductId(saleBillPanel.getProductId());
saleBill.setCustomerId(saleBillPanel.getCustomerId());
saleBill.setRate(saleBillPanel.getRate());
try
{
saleBill.setBillDate(saleBillPanel.getSaleBillDate());
}catch(ParseException parseException)
{
System.out.println("SaleBillUI [addSaleBill]"+ parseException);
}
saleBill.setQuantity(saleBillPanel.getQuantity());
saleBill.setDiscount(saleBillPanel.getDiscount());
saleBill.setTotalAmount(saleBillPanel.getTotalAmount());
saleBill.setPaidAmount(saleBillPanel.getPaidAmount());
try
{
saleBillDAO.updateSaleBill(saleBill);
saleBillModel.changeNotify();
saleBillsTable.addNotify();
saleBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"SaleBill Update");
int newRowIndex=saleBillModel.getSaleBillIndex(saleBill.getBillNo());
saleBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(newRowIndex,0,false));
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
totalNumberOfRecords=saleBillDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=saleBillsTable.getSelectedRow();
if(selectedRowIndex>=0)
{
SaleBillInterface saleBill=saleBillModel.get(selectedRowIndex);
saleBillPanel.set(saleBill.getBillNo(), saleBill.getProductId(), saleBill.getCustomerId(), saleBill.getBillDate(), saleBill.getRate(), saleBill.getQuantity(),saleBill.getDiscount(), saleBill.getTotalAmount(), saleBill.getPaidAmount());
}
else
{
saleBillPanel.reset();
}
}
}