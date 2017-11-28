package dl.purchaseBill;
public interface PurchaseBillInterface extends java.io.Serializable
{
public void setId(int id);
public int getId();
public void setPurchaseBillNo(String purchaseBillNo);
public String getPurchaseBillNo();
public void setSupplierId(int supplierId);
public int getSupplierId();
public void setPurchaseBillDate(java.util.Date purchaseBillDate);
public java.util.Date getPurchaseBillDate();
public void setRate(String rate);
public String getRate();
public void setQuantity(String quantity);
public String getQuantity();
public void setDiscount(String discount);
public String getDiscount();
public void setTotalAmount(String totalAmount);
public String getTotalAmount();
public void setPaidAmount(String paidAmount);
public String getPaidAmount();
}