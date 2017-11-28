package pl.saleBill.allSaleBill;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import pl.*;
import pl.saleBill.*;
import dl.saleBill.*;

public class AllSaleBillUI extends JFrame implements CrossButtonListener, ListSelectionListener, ActionListener
{
private Container container;
private JButton editButton,deleteButton;
private BillInfoPanel billInfoPanel;
private CustomerPanel customerPanel;
private TotalPanel totalPanel;
private AllSaleBillModel allSaleBillModel;
private SaleBillDetailModel saleBillDetailModel;
private JTable allSaleBillsTable,saleBillsDetailTable;
private JScrollPane allSaleBillScrollPane,saleBillDetailScrollPane;
private SaleBillDAOInterface saleBillDAO;

public AllSaleBillUI()
{
super("Sale Bill");
initComponents();
CrossButtonHandler cbh=new CrossButtonHandler(this);
addWindowListener(cbh);
}

public void windowClosing(WindowEvent ev)
{
dispose();
}

private void initComponents()
{
container=getContentPane();
editButton=new JButton("Edit");
deleteButton=new JButton("Delete");
allSaleBillModel=new AllSaleBillModel();
saleBillDetailModel=new SaleBillDetailModel();
billInfoPanel=new BillInfoPanel();
customerPanel=new CustomerPanel();
totalPanel=new TotalPanel();
allSaleBillsTable=new JTable(allSaleBillModel);
allSaleBillsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//TableColumn tc;
allSaleBillsTable.getColumnModel().getColumn(0).setPreferredWidth(15);
allSaleBillsTable.getColumnModel().getColumn(1).setPreferredWidth(10);
allSaleBillsTable.getColumnModel().getColumn(2).setPreferredWidth(60);
allSaleBillsTable.getColumnModel().getColumn(3).setPreferredWidth(10);
allSaleBillsTable.getColumnModel().getColumn(4).setPreferredWidth(10);
allSaleBillsTable.getColumnModel().getColumn(5).setPreferredWidth(10); allSaleBillsTable.getSelectionModel().addListSelectionListener(this);

allSaleBillScrollPane=new JScrollPane(allSaleBillsTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);

saleBillsDetailTable=new JTable(saleBillDetailModel);
saleBillsDetailTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//TableColumn tc;
saleBillsDetailTable.getColumnModel().getColumn(0).setPreferredWidth(15);
saleBillsDetailTable.getColumnModel().getColumn(1).setPreferredWidth(10);
saleBillsDetailTable.getColumnModel().getColumn(2).setPreferredWidth(60);
saleBillsDetailTable.getColumnModel().getColumn(3).setPreferredWidth(10);
saleBillsDetailTable.getColumnModel().getColumn(4).setPreferredWidth(10);
saleBillsDetailTable.getSelectionModel().addListSelectionListener(this);

saleBillDetailScrollPane=new JScrollPane(saleBillsDetailTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);

editButton.addActionListener(this);
deleteButton.addActionListener(this);
container.setLayout(null);
saleBillDetailScrollPane.setBounds(10,320,450,200);
allSaleBillScrollPane.setBounds(500,250,450,350);
billInfoPanel.setBounds(10,10,400,100);
customerPanel.setBounds(10,120,600,200);
totalPanel.setBounds(300,520,250,400);
editButton.setBounds(100,550,50,25);
deleteButton.setBounds(200,550,50,25);
container.add(allSaleBillScrollPane);
container.add(saleBillDetailScrollPane);
container.add(billInfoPanel);
container.add(customerPanel);
container.add(totalPanel);
container.add(editButton);
container.add(deleteButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(dimension.width,dimension.height);
try
{
allSaleBillsTable.setRowSelectionInterval(0,0);
saleBillsDetailTable.setRowSelectionInterval(0,0);
}catch(Exception e){}
}

public void deleteSaleBill()
{
saleBillDAO=new SaleBillDAO();
int selectedRowIndex=allSaleBillsTable.getSelectedRow();
if(selectedRowIndex>=0)
{
int billNumber=(int)allSaleBillsTable.getValueAt(selectedRowIndex,0);
try
{
saleBillDAO.deleteSaleBill(billNumber);
JOptionPane.showMessageDialog(this,"Bill Deleted");
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException);
}
allSaleBillModel.changeNotify();
try
{
allSaleBillsTable.setRowSelectionInterval(0,0);
billNumber=(int)allSaleBillsTable.getValueAt(selectedRowIndex,0);
saleBillDetailModel.changeNotify(billNumber);
saleBillsDetailTable.setRowSelectionInterval(0,0);
}catch(Exception e)
{
saleBillDetailModel.changeNotify(1);
}
allSaleBillsTable.addNotify();
allSaleBillsTable.repaint();
saleBillsDetailTable.addNotify();
saleBillsDetailTable.repaint();
super.repaint();
}
}

public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==editButton);
if(ev.getSource()==deleteButton)
{
int selected=JOptionPane.showConfirmDialog(this,"Are you sure?","Confirming delete operation",JOptionPane.YES_NO_OPTION);
if(selected==JOptionPane.YES_OPTION)deleteSaleBill();
}
}

public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=allSaleBillsTable.getSelectedRow();
if(selectedRowIndex>=0)
{
int billNumber=(int)allSaleBillsTable.getValueAt(selectedRowIndex,0);
saleBillDetailModel.changeNotify(billNumber);
allSaleBillsTable.addNotify();
allSaleBillsTable.repaint();
saleBillsDetailTable.addNotify();
saleBillsDetailTable.repaint();
super.repaint();
}
}

}