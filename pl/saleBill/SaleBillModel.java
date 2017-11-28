package pl.saleBill;
import dl.saleBill.*; 
import javax.swing.table.*;
import java.util.*;

public class SaleBillModel extends DefaultTableModel
{
/*private String [] columnTitles={"S.No.","Product", "Rate", "Quantity", "Discount", "Total"};
private Object data[][];
//private ArrayList<ArrayList<Object>> data;

public SaleBillModel()
{
data=new Object[10][6];
//data = new ArrayList<ArrayList<Object>>();
}
public int getRowCount()
{
//return 5;
return data.length;
}
public int getColumnCount()
{
//return 6;
return columnTitles.length;
} 
 */
public boolean isCellEditable(int row, int col)
 {
 if (col ==5 || col==3)return false;
 else return true;
 }
    

/*
public Class getColumnClass(int columnIndex)
{
try
{
return Class.forName("java.lang.String");
}catch(ClassNotFoundException classNotFoundException)
{
return "".getClass();
}
}

public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}

public void setValueAt(Object value, int row, int col) {
            
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
         
	data[row][col]=value;
	//data.get(row).get(col) = value;
              fireTableCellUpdated(row,col);
}

public Object getValueAt(int row, int col) 
{
                return data[row][col];

            }


public void addRow1()
{
  Vector<Object> data = new Vector<Object>();
data.add("");        
data.add("Project Title");
        data.add("Start");
        data.add("Stop");
        data.add("Pause");
        data.add("Status");
        System.out.println("test");
        addRow(data);
}

public void changeNotify()
{
try
{
saleBills=saleBillDAO.get();
}catch(DAOException daoException)
{
System.out.println("SaleBillModel [SaleBillModel()]:"+daoException);
saleBills=new ArrayList<SaleBillInterface>();
}
}
public SaleBillInterface get(int index)
{
if(index>=0 && index<saleBills.size())
{
return saleBills.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid saleBill index");
}
}
public int getSaleBillIndex(int billNumber)
{
int e;
e=0;
while(e<saleBills.size())
{
if(saleBills.get(e).getBillNo()==billNumber)return e;
e++;
}
return -1;
}*/
}