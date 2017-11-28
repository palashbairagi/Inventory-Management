package dl.purchaseBill;
import java.util.*;
public class PurchaseBill implements PurchaseBillInterface
{
private int id;
private String purchaseBillNo;
private int supplierId;
private java.util.Date purchaseBillDate;
private String quantity;
private String rate;
private String discount;
private String totalAmount;
private String paidAmount;

public void setId(int id)
{
this.id=id;
}
public int getId()
{
return this.id;
}
public void setPurchaseBillNo(String purchaseBillNo)
{
this.purchaseBillNo=purchaseBillNo;
} 
public String getPurchaseBillNo()
{
return this.purchaseBillNo;
}
public void setSupplierId(int supplierId)
{
this.supplierId=supplierId;
} 
public int getSupplierId()
{
return this.supplierId;
}

public void setPurchaseBillDate(java.util.Date purchaseBillDate)
{
this.purchaseBillDate=purchaseBillDate;
}
public java.util.Date getPurchaseBillDate()
{
return this.purchaseBillDate;
}
public void setQuantity(String quantity)
{
this.quantity=quantity;
}
public String getQuantity()
{
return this.quantity;
}

public void setRate(String rate)
{
this.rate=rate;
}
public String getRate()
{
return this.rate;
}


public void setDiscount(String discount)
{
this.discount=discount;
}
public String getDiscount()
{
return this.discount;
}
public void setTotalAmount(String totalAmount)
{
this.totalAmount=totalAmount;
}
public String getTotalAmount()
{
return this.totalAmount;
}
public void setPaidAmount(String paidAmount)
{
this.paidAmount=paidAmount;
}
public String getPaidAmount()
{
return this.paidAmount;
}
}