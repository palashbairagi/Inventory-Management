package dl.saleBill;
import java.util.*;
public class SaleBill implements SaleBillInterface
{
	private int billNumber;
	private java.util.Date billDate;
	private String customerName;
	private String customerContactNumber1;
	private String customerContactNumber2;
	private String customerAddress;
	private String totalDiscount;
	private String totalAmount;
	private String paidAmount;
	public int getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}
	public java.util.Date getBillDate() {
		return billDate;
	}
	public void setBillDate(java.util.Date billDate) {
		this.billDate = billDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContactNumber1() {
		return customerContactNumber1;
	}
	public void setCustomerContactNumber1(String customerContactNumber1) {
		this.customerContactNumber1 = customerContactNumber1;
	}
	public String getCustomerContactNumber2() {
		return customerContactNumber2;
	}
	public void setCustomerContactNumber2(String customerContactNumber2) {
		this.customerContactNumber2 = customerContactNumber2;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
		public String getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
}