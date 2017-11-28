package dl.medicine;

import java.util.*;
public class Medicine implements MedicineInterface
{
private int id;
private String name;
private String purchasePrice;
private String salePrice;
private java.util.Date manufacturingDate;
private java.util.Date expiryDate;
public void setId(int id)
{
this.id=id;
} 
public int getId()
{
return this.id;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setPurchasePrice(String purchasePrice)
{
this.purchasePrice=purchasePrice;
}
public String getPurchasePrice()
{
return this.purchasePrice;
}
public void setSalePrice(String salePrice)
{
this.salePrice=salePrice;
}
public String getSalePrice()
{
return this.salePrice;
}

public void setManufacturingDate(java.util.Date manufacturingDate)
{
this.manufacturingDate=manufacturingDate;
}
public java.util.Date getManufacturingDate()
{
return this.manufacturingDate;
}

public void setExpiryDate(java.util.Date expiryDate)
{
this.expiryDate=expiryDate;
}
public java.util.Date getExpiryDate()
{
return this.expiryDate;
}
}