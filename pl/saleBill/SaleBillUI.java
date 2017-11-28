package pl.saleBill;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import java.util.*;
import dl.saleBill.*;
import dl.medicine.*;
import dl.customer.*;
import pl.*;

public class SaleBillUI extends JPanel implements TableModelListener, ActionListener, KeyListener
{
private JTable saleBillsTable;
private BillInfoPanel billInfoPanel;
private FindMedicinePanel findMedicinePanel;
private CustomerPanel customerPanel;
private FindCustomerPanel findCustomerPanel;
private TotalPanel totalPanel;
private JScrollPane jScrollPane;
private JButton addRowButton,removeRowButton, saveButton, saveAndPrintButton, resetButton, setCustomerNameButton, newBillButton;
private SaleBillDAO saleBillDAO;
private SaleBillModel saleBillModel;
private MedicineDAOInterface medicineDAO;
private MedicineInterface medicine;
private DefaultTableModel model;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
private ArrayList<SaleBillDetailInterface>saleBillsDetail;
private SaleBillDetailInterface saleBillDetail;

public SaleBillUI()
{
saleBillDAO=new SaleBillDAO();
medicineDAO=new MedicineDAO();
initComponents();
}

public void initComponents()
{
billInfoPanel=new BillInfoPanel();
totalPanel=new TotalPanel();
findMedicinePanel=new FindMedicinePanel();
saleBillModel=new SaleBillModel();
customerPanel=new CustomerPanel();
findCustomerPanel=new FindCustomerPanel();
model=new DefaultTableModel();
saleBillsTable=new JTable(saleBillModel);
saleBillModel.addColumn("S.No.");
saleBillModel.addColumn("Product");
saleBillModel.addColumn("Quantity");
saleBillModel.addColumn("Rate");
saleBillModel.addColumn("Discount");
saleBillModel.addColumn("Total");
saleBillModel.addTableModelListener(this);
saleBillsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
saleBillsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
saleBillsTable.getColumnModel().getColumn(3).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(4).setPreferredWidth(30);
saleBillsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
saleBillsTable.getColumn("Product").setCellEditor(new DefaultCellEditor(new FindMedicinePanel()));
saleBillsTable.getColumn("Product").setCellRenderer(new FindMedicinePanel());
saleBillsTable.addKeyListener(this);
jScrollPane=new JScrollPane(saleBillsTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
saleBillsTable.setShowHorizontalLines(false);
saleBillsTable.setShowVerticalLines(false);
Font font=new Font("Arial",Font.BOLD,14);
addRowButton=new JButton("Add Row");
removeRowButton=new JButton("Remove Row");
saveButton=new JButton("Save");
saveAndPrintButton=new JButton("Save & Print");
setCustomerNameButton=new JButton("Ok");
resetButton=new JButton("Reset");
newBillButton=new JButton("New Bill");
addRowButton.addActionListener(this);
removeRowButton.addActionListener(this);
saveButton.addActionListener(this);
saveAndPrintButton.addActionListener(this);
setCustomerNameButton.addActionListener(this);
resetButton.addActionListener(this);
newBillButton.addActionListener(this);
setLayout(null);
jScrollPane.setBounds(10,200,850,210);
totalPanel.setBounds(650,420,200,160);
billInfoPanel.setBounds(20,40,480,80);
customerPanel.setBounds(20,100,480,100);
findCustomerPanel.setBounds(500,100,220,100);
setCustomerNameButton.setBounds(720,120,60,25);
addRowButton.setBounds(10,540,80,25);
removeRowButton.setBounds(110,540,80,25);
saveButton.setBounds(200,540,80,25);
saveAndPrintButton.setBounds(320,540,80,25);
resetButton.setBounds(410,540,80,25);
newBillButton.setBounds(500,540,80,25);
add(jScrollPane);
add(billInfoPanel);
add(customerPanel);
add(findCustomerPanel);
add(totalPanel);
add(addRowButton);
add(removeRowButton);
add(saveButton);
add(saveAndPrintButton);
add(resetButton);
add(newBillButton);
add(setCustomerNameButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize((dimension.width-100),(dimension.height-300));
saleBillModel.addRow(new Object[]{"1", "", "1","", "0", "0"});
try
{
billInfoPanel.setBillNumber((saleBillDAO.getBillNumber()+1));
}catch(dl.saleBill.DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException);
}
billInfoPanel.setBillDate(new java.util.Date());
totalPanel.discount.addKeyListener(this);
totalPanel.paidAmount.addKeyListener(this);
}

public void keyReleased(KeyEvent ev)
{
if(saleBillsTable.getSelectedColumn()==5)
{
if((ev.getKeyChar()==ev.VK_TAB)||(ev.getKeyChar()==ev.VK_ENTER))
{
saleBillModel.addRow(new Object[]{""+(saleBillsTable.getRowCount()+1), "", "1","", "0", "0"});
int newRowIndex=saleBillsTable.getRowCount()-1;
saleBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(newRowIndex,0,false));
}
}
if(totalPanel.discount.hasFocus())
{
totalPanel.setTotal(""+showTotal());
totalPanel.setPaidAmount(""+showTotal());
totalPanel.setRemainingAmount("0.0");
}

if(totalPanel.paidAmount.hasFocus())
{
totalPanel.setTotal(""+showTotal());
float paid=0;
try
{
paid=Float.parseFloat(totalPanel.getPaidAmount());
}catch(Exception de){System.out.println("SaleBillUI [keyReleased(KeyEvent ev)]"+de);paid=0;}
totalPanel.setRemainingAmount(""+(showTotal()-paid));
}
}

public void keyTyped(KeyEvent ev){}
public void keyPressed(KeyEvent ev){}


public void tableChanged (TableModelEvent e)
{
int row=saleBillsTable.getSelectedRow();
int col=saleBillsTable.getSelectedColumn();
if(row==-1)row=0;

if(col==1)
{
String productName=(String)saleBillsTable.getValueAt(row,1);
try
{
medicine=new Medicine();
medicine=medicineDAO.findMedicineByName(productName);

if(!((String)saleBillsTable.getValueAt(row, 3)).equals(medicine.getSalePrice())) 
{
saleBillsTable.setValueAt(medicine.getSalePrice(),row, 3);
}

}catch(dl.medicine.DAOException daoException)
{
System.out.println("SaleBillUI[tableChanged]"+daoException);
JOptionPane.showMessageDialog(this,daoException);
}
catch(Exception ex){System.out.println("SaleBillUI [tableChanged]"+ex);}
}

if(col==2 || col==3 || col==4){}
try
{
int rate=Integer.parseInt((String)saleBillsTable.getValueAt(row,2));
int quantity=Integer.parseInt((String)saleBillsTable.getValueAt(row,3));
int discount=Integer.parseInt((String)saleBillsTable.getValueAt(row,4));
String total=""+(rate*quantity-discount);

if(!((String)saleBillsTable.getValueAt(row, 5)).equals(total)) 
{
saleBillsTable.setValueAt(total,row, 5);
}
}catch(Exception pe)
{
System.out.println("SaleBillUI [tableChanged] "+pe);
}
totalPanel.setTotal(""+showTotal());
totalPanel.setPaidAmount(""+showTotal());
float paid=0;
try
{
paid=Float.parseFloat(totalPanel.getPaidAmount());
}catch(Exception de){paid=0;}
totalPanel.setRemainingAmount(""+(showTotal()-paid));
}

public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==setCustomerNameButton)
{
setCustomerDetails(findCustomerPanel.getName());
}
if(ev.getSource()==addRowButton)
{
addRowToTable();
}
if(ev.getSource()==removeRowButton)
{
removeRowFromTable();
}
if(ev.getSource()==saveButton)
{
saveSaleBill();
}
if(ev.getSource()==resetButton)
{
reset();
}
if(ev.getSource()==newBillButton)
{
reset();
billInfoPanel.setBillNumber(25);
}
}

private void reset()
{
findCustomerPanel.reset();
customerPanel.reset();
totalPanel.reset();
saleBillModel.removeTableModelListener(this);
int count=saleBillsTable.getRowCount();
for(int i=0;i<count;i++)
{
System.out.println(i);
try
{
removeRowFromTable();
}catch(Exception e)
{
System.out.println("SaleBillUI [reset()]"+e);
}//saleBillModel.removeRow(i);
}
addRowToTable();
saleBillsTable.addNotify();
saleBillsTable.repaint();
super.repaint();
saleBillModel.addTableModelListener(this);
}
 
private void addRowToTable()
{
saleBillModel.addRow(new Object[]{""+(saleBillsTable.getRowCount()+1), "", "1","", "0", "0"});
int newRowIndex=saleBillsTable.getRowCount()-1;
saleBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(newRowIndex,0,false));
}

private void removeRowFromTable()
{
if(saleBillsTable.getSelectedRow()==-1)
{
JOptionPane.showMessageDialog(this,"Select a row to Remove");
return;
}
try
{
saleBillModel.removeRow(saleBillsTable.getSelectedRow());
int newRowIndex=0;
saleBillsTable.setRowSelectionInterval(newRowIndex,newRowIndex);
saleBillsTable.scrollRectToVisible(saleBillsTable.getCellRect(newRowIndex,0,false));
setSerialNumber();
}catch(ArrayIndexOutOfBoundsException ae)
{
System.out.println("SaleBillUI [removeRowFromTable] "+ae);
}
}

private void setSerialNumber()
{
for(int i=0;i<saleBillsTable.getRowCount();i++)
{
saleBillsTable.setValueAt(i+1,i,0);
}
}

public float showTotal()
{
float total=0;
float discount=0;
for(int i=0;i<saleBillsTable.getRowCount();i++)
{
total=total+Float.parseFloat((String)saleBillsTable.getValueAt(i,5));
}
try
{
discount=Float.parseFloat(totalPanel.getDiscount());
}catch(Exception e){System.out.println("SaleBillUI [showTotal]"+e);discount=0;}
total=total-discount;
return total;
}

private void setCustomerDetails(String customerName)
{
CustomerDAOInterface customerDAO=new CustomerDAO();
CustomerInterface customer=new Customer();
try
{
customer=customerDAO.findCustomerByName(customerName);
customerPanel.set(customer.getId(),customer.getName(),customer.getContactNumber1(),customer.getContactNumber2(),customer.getAddress());
}catch(dl.customer.DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException);
}
}


private void saveSaleBill()
{
if((totalPanel.getDiscount()).length()==0){totalPanel.setDiscount("0.0");}
if((totalPanel.getPaidAmount()).length()==0){totalPanel.setPaidAmount(totalPanel.getTotal());totalPanel.setRemainingAmount("0.0");}

saleBillsDetail=new ArrayList<SaleBillDetailInterface>();

try
{
for(int row=0;row<saleBillsTable.getRowCount();row++)
{
saleBillDetail=new SaleBillDetail();
saleBillDetail.setBillNumber(billInfoPanel.getBillNumber());
saleBillDetail.setProductName((String)saleBillsTable.getValueAt(row,1));
saleBillDetail.setQuantity((String)saleBillsTable.getValueAt(row,2));
saleBillDetail.setRate((String)saleBillsTable.getValueAt(row,3));
saleBillDetail.setDiscount((String)saleBillsTable.getValueAt(row,4));
saleBillDetail.setAmount((String)saleBillsTable.getValueAt(row,5));
saleBillsDetail.add(saleBillDetail);
}
saleBillDAO.addSaleBillDetailInfo(saleBillsDetail);
try
{
SaleBillInterface saleBill=new SaleBill();
saleBill.setBillNumber(billInfoPanel.getBillNumber());
try{
saleBill.setBillDate(billInfoPanel.getBillDate());
}catch(ParseException pe){JOptionPane.showMessageDialog(this,pe);}
saleBill.setCustomerName(customerPanel.getCustomerName());
saleBill.setCustomerContactNumber1(customerPanel.getCustomerContactNumber1());
saleBill.setCustomerContactNumber2(customerPanel.getCustomerContactNumber2());
saleBill.setCustomerAddress(customerPanel.getCustomerAddress());
saleBill.setTotalDiscount(totalPanel.getDiscount());
saleBill.setTotalAmount(totalPanel.getTotal());
saleBill.setPaidAmount(totalPanel.getPaidAmount());
saleBillDAO.addSaleBillInfo(saleBill); 
JOptionPane.showMessageDialog(this,"Bill Saved");
}catch(dl.saleBill.DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException);
}
}catch(Exception daoException)
{
JOptionPane.showMessageDialog(this,daoException);
}

}

/*

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
//saleBillModel.changeNotify();
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
//saleBillModel.changeNotify();
saleBillsTable.addNotify();
saleBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"SaleBill Added");
//int newRowIndex=saleBillModel.getSaleBillIndex(saleBill.getBillNo());
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
//saleBillModel.changeNotify();
saleBillsTable.addNotify();
saleBillsTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"SaleBill Update");
//int newRowIndex=saleBillModel.getSaleBillIndex(saleBill.getBillNo());
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
//SaleBillInterface saleBill=saleBillModel.get(selectedRowIndex);
saleBillPanel.set(saleBill.getBillNo(), saleBill.getProductId(), saleBill.getCustomerId(), saleBill.getBillDate(), saleBill.getRate(), saleBill.getQuantity(),saleBill.getDiscount(), saleBill.getTotalAmount(), saleBill.getPaidAmount());
}
else
{
saleBillPanel.reset();
}
}*/
}