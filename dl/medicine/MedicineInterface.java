package dl.medicine;

public interface MedicineInterface extends java.io.Serializable
{
public void setId(int id);
public int getId();
public void setName(String name);
public String getName();
public void setPurchasePrice(String purchasePrice);
public String getPurchasePrice();
public void setSalePrice(String salePrice);
public String getSalePrice();
public void setManufacturingDate(java.util.Date manufacturingDate);
public java.util.Date getManufacturingDate();
public void setExpiryDate(java.util.Date expiryDate);
public java.util.Date getExpiryDate();
}