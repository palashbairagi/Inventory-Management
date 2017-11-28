package dl.saleBill;
public interface SaleBillInterface extends java.io.Serializable
{
public void setBillNumber(int billNumber);
public int getBillNumber();
public void setBillDate(java.util.Date billDate);
public java.util.Date getBillDate();
public void setCustomerName(String customerName);
public String getCustomerName();
public void setCustomerContactNumber1(String contactNumber1);
public String getCustomerContactNumber1();
public void setCustomerContactNumber2(String contactNumber2);
public String getCustomerContactNumber2();
public void setCustomerAddress(String contactAddress);
public String getCustomerAddress();
public void setTotalDiscount(String discount);
public String getTotalDiscount();
public void setTotalAmount(String totalAmount);
public String getTotalAmount();
public void setPaidAmount(String paidAmount);
public String getPaidAmount();
}