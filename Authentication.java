import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class Authentication extends JFrame implements ActionListener
{
private JLabel userNameCaption,passwordCaption;
private JTextField userName,password;
private JButton loginButton;
Authentication()
{
super("Login");
userNameCaption=new JLabel("User Name");
passwordCaption=new JLabel("Password");
userName=new JTextField();
password=new JTextField();
loginButton=new JButton("Login");
setLayout(null);
userNameCaption.setBounds(100,50,100,25);
userName.setBounds(210,50,120,25);
passwordCaption.setBounds(100,85,100,25);
password.setBounds(210,85,120,25);
loginButton.setBounds(160,140,80,25);
loginButton.addActionListener(this);
add(userNameCaption);
add(passwordCaption);
add(userName);
add(password);
add(loginButton);
setSize(430,250);
setVisible(true);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent ev) 
{
this.dispose();
MainFrame frame=new MainFrame();
frame.setVisible(true);
}

public static void main(String gg[])
{
Authentication a=new Authentication();
a.setVisible(true);
}
}