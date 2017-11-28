import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import pl.*;
import pl.medicine.*;
import pl.customer.*;
import pl.supplier.*;
import pl.user.*;
import pl.saleBill.*;
import pl.saleBill.allSaleBill.*;
import pl.purchaseBill.*;
import pl.help.*;

class MainFrame extends JFrame implements ActionListener, CrossButtonListener
{
private JMenuBar menuBar;
private JMenu sale, purchase, medicine, customer, supplier, user, helpMenu; 
private JMenuItem saleManage, salesByMonth, salesByDate, findSales;
private JMenuItem purchaseManage, purchaseByMonth, purchaseByDate, findPurchase;
private JMenuItem medicineManage, findMedicine;
private JMenuItem customerManage, findCustomer;
private JMenuItem supplierManage, findSupplier;
private JMenuItem userManage, findUser;
private JMenuItem help, aboutUs;
private SaleBillUI saleBillUI;
private Header h;
private Container container;

MainFrame()
{
super("Akshat Medical Store");

container=getContentPane();
h=new Header();
saleBillUI=new SaleBillUI();

sale=new JMenu("Sale Bill"); 
saleManage=new JMenuItem("Manage Sales"); 
salesByMonth=new JMenuItem("Report of the Month");
salesByDate=new JMenuItem("Report of the day"); 
findSales=new JMenuItem("Find Sales");
sale.add(saleManage);
sale.add(salesByMonth);
sale.add(salesByDate);
sale.add(findSales);

purchase=new JMenu("Purchase Bill"); 
purchaseManage=new JMenuItem("Manage Purchase");
purchaseByMonth=new JMenuItem("Report of the Month");
purchaseByDate=new JMenuItem("Report of the day");
findPurchase=new JMenuItem("Find Purchase");
purchase.add(purchaseManage);
purchase.add(purchaseByMonth);
purchase.add(purchaseByDate);
purchase.add(findPurchase);

medicine=new JMenu("Product"); 
medicineManage=new JMenuItem("Manage Product"); 
findMedicine=new JMenuItem("Find Product");
medicine.add(medicineManage);
medicine.add(findMedicine);

customer=new JMenu("Customer");
customerManage=new JMenuItem("Manage Customer");
findCustomer=new JMenuItem("Find Customer");
customer.add(customerManage);
customer.add(findCustomer);

supplier=new JMenu("Supplier"); 
supplierManage=new JMenuItem("Manage Supplier");
findSupplier=new JMenuItem("Find Supplier");
supplier.add(supplierManage);
supplier.add(findSupplier);

user=new JMenu("User");
userManage=new JMenuItem("Manage User");
findUser=new JMenuItem("Find User");
user.add(userManage);
user.add(findUser);

helpMenu=new JMenu("Help");
help=new JMenuItem("Help");
aboutUs=new JMenuItem("About us");
helpMenu.add(help);
helpMenu.add(aboutUs);
 
saleManage.addActionListener(this); 
salesByMonth.addActionListener(this);
salesByDate.addActionListener(this); 
findSales.addActionListener(this);

purchaseManage.addActionListener(this); 
purchaseByMonth.addActionListener(this);
purchaseByDate.addActionListener(this); 
findPurchase.addActionListener(this);

medicineManage.addActionListener(this);
findMedicine.addActionListener(this);

customerManage.addActionListener(this); 
findCustomer.addActionListener(this);

supplierManage.addActionListener(this); 
findSupplier.addActionListener(this);

userManage.addActionListener(this);
findUser.addActionListener(this);

help.addActionListener(this);
aboutUs.addActionListener(this);

menuBar=new JMenuBar();
menuBar.add(sale);
menuBar.add(purchase);
menuBar.add(medicine);
menuBar.add(customer);
menuBar.add(supplier);
menuBar.add(user);
menuBar.add(helpMenu);

Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();

container.setLayout(null);
setJMenuBar(menuBar);
h.setBounds((dimension.width/4),1,(dimension.width/2),100);
saleBillUI.setBounds(10,101,(dimension.width-100),(dimension.height-150));
container.add(h);
container.add(saleBillUI);

CrossButtonHandler cbh=new CrossButtonHandler(this);
addWindowListener(cbh);
setVisible(true);
setSize(dimension.width,dimension.height);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==saleManage){AllSaleBillUI ui=new AllSaleBillUI(); ui.setVisible(true);}
if(ev.getSource()==purchaseManage){PurchaseBillUI ui=new PurchaseBillUI();ui.setVisible(true);}
if(ev.getSource()==medicineManage){MedicineUI ui=new MedicineUI();ui.setVisible(true);}
if(ev.getSource()==customerManage){CustomerUI ui=new CustomerUI();ui.setVisible(true);}
if(ev.getSource()==findCustomer){}
if(ev.getSource()==supplierManage){SupplierUI ui=new SupplierUI();ui.setVisible(true);}
if(ev.getSource()==userManage){UserUI ui=new UserUI();ui.setVisible(true);}
if(ev.getSource()==aboutUs){AboutUsUI ui=new AboutUsUI();ui.setVisible(true);}
}

public void windowClosing(WindowEvent ev)
{
System.exit(0);
}

}