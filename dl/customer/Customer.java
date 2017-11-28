package dl.customer;
import java.util.*;
public class Customer implements CustomerInterface
{
private int id;
private String name;
private String contactNumber1;
private String contactNumber2;
private String address;
private String openingBalance;
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
public void setContactNumber1(String contactNumber1)
{
this.contactNumber1=contactNumber1;
}
public String getContactNumber1()
{
return this.contactNumber1;
}

public void setContactNumber2(String contactNumber2)
{
this.contactNumber2=contactNumber2;
}
public String getContactNumber2()
{
return this.contactNumber2;
}


public void setAddress(String address)
{
this.address=address;
}
public String getAddress()
{
return this.address;
}
public void setOpeningBalance(String openingBalance)
{
this.openingBalance=openingBalance;
}
public String getOpeningBalance()
{
return this.openingBalance;
}
}