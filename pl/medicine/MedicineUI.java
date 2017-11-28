package pl.medicine;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import dl.medicine.*;
import pl.*;

public class MedicineUI extends JFrame implements ActionListener,ListSelectionListener, CrossButtonListener
{
private JTable medicinesTable;
private MedicinePanel medicinePanel;
private FindMedicinePanel findMedicinePanel;
private MedicineModel medicineModel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton,findButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private MedicineDAO medicineDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public MedicineUI()
{
super("Medicine Management");
medicineDAO=new MedicineDAO();
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
medicinePanel=new MedicinePanel();
findMedicinePanel=new FindMedicinePanel();
medicineModel=new MedicineModel();
medicinesTable=new JTable(medicineModel);
medicinesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
medicinesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
medicinesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
medicinesTable.getColumnModel().getColumn(2).setPreferredWidth(100);
medicinesTable.getColumnModel().getColumn(3).setPreferredWidth(30);
medicinesTable.getColumnModel().getColumn(4).setPreferredWidth(30);
medicinesTable.getColumnModel().getColumn(5).setPreferredWidth(100);
medicinesTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(medicinesTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=medicineDAO.getCount();
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
medicinePanel.setBounds(10,40,450,265);
findMedicinePanel.setBounds(500,50,220,70);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
findButton.setBounds(350,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(medicinePanel);
container.add(findMedicinePanel);
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
medicinesTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}
private void setViewStage()
{
medicinesTable.setEnabled(true);
mode=uiModes.VIEWMODE;
medicinePanel.disableAll();
findMedicinePanel.enableAll();
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
medicinesTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
findButton.setVisible(false);
medicinePanel.reset();
medicinePanel.enableAll();
findMedicinePanel.disableAll();
medicinePanel.requestFocusOnName();
}
private void setEditModeStage()
{
medicinesTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
findButton.setVisible(false);
medicinePanel.enableAll();
findMedicinePanel.disableAll();
medicinePanel.requestFocusOnName();
}
private void setDeleteStage()
{
medicinesTable.setEnabled(false);
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
addMedicine();
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
updateMedicine();
}
}
if(event.getSource()==deleteButton)
{
if(medicinesTable.getSelectedRow()==-1)
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
medicineDAO.deleteMedicine(medicinePanel.getId());
medicineModel.changeNotify();
medicinesTable.addNotify();
medicinesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Medicine Deleted");
medicinePanel.reset();
if(totalNumberOfRecords>0)
{
medicinesTable.setRowSelectionInterval(0,0);
medicinesTable.scrollRectToVisible(medicinesTable.getCellRect(0,0,false));
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
if(!findMedicinePanel.validate(this))return;
int newRowIndex=medicineModel.getMedicineIndex(findMedicinePanel.getName());
medicinesTable.setRowSelectionInterval(newRowIndex,newRowIndex);
medicinesTable.scrollRectToVisible(medicinesTable.getCellRect(newRowIndex,0,false));
}
}
private void addMedicine()
{
if(!medicinePanel.validate(this))return;
MedicineInterface medicine=new Medicine();
medicine.setName(medicinePanel.getName());
medicine.setPurchasePrice(medicinePanel.getPurchasePrice());
medicine.setSalePrice(medicinePanel.getSalePrice());
try
{
medicine.setManufacturingDate(medicinePanel.getManufacturingDate());
}catch(ParseException parseException)
{
System.out.println("Medicine UI "+parseException);
}
try
{
medicine.setExpiryDate(medicinePanel.getExpiryDate());
}catch(ParseException parseException)
{
System.out.println("Medicine UI "+parseException);
}
try
{
medicineDAO.addMedicine(medicine);
medicineModel.changeNotify();
medicinesTable.addNotify();
medicinesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Medicine Added");
int newRowIndex=medicineModel.getMedicineIndex(medicine.getName());
medicinesTable.setRowSelectionInterval(newRowIndex,newRowIndex);
medicinesTable.scrollRectToVisible(medicinesTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updateMedicine()
{
if(!medicinePanel.validate(this))return;
MedicineInterface medicine=new Medicine();
medicine.setId(medicinePanel.getId());
medicine.setName(medicinePanel.getName());
medicine.setPurchasePrice(medicinePanel.getPurchasePrice());
medicine.setSalePrice(medicinePanel.getSalePrice());
try
{
medicine.setManufacturingDate(medicinePanel.getManufacturingDate());
}catch(ParseException parseException)
{

}
try
{
medicine.setExpiryDate(medicinePanel.getExpiryDate());
}catch(ParseException parseException)
{

}
try
{
medicineDAO.updateMedicine(medicine);
medicineModel.changeNotify();
medicinesTable.addNotify();
medicinesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Medicine Update");
int newRowIndex=medicineModel.getMedicineIndex(medicine.getName());
medicinesTable.setRowSelectionInterval(newRowIndex,newRowIndex);
medicinesTable.scrollRectToVisible(medicinesTable.getCellRect(newRowIndex,0,false));
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
totalNumberOfRecords=medicineDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=medicinesTable.getSelectedRow();
if(selectedRowIndex>=0)
{
MedicineInterface medicine=medicineModel.get(selectedRowIndex);
medicinePanel.set(medicine.getId(),medicine.getName(), medicine.getPurchasePrice(), medicine.getSalePrice(),medicine.getManufacturingDate(), medicine.getExpiryDate());
}
else
{
medicinePanel.reset();
}
}
}