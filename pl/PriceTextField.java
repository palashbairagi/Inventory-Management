import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PriceTextField extends Canvas implements KeyListener
{
private JTextField tf1,tf2;
private JLabel l1;
private Container container;

public PriceTextField()
{
container=getContentPane();
tf1=new JTextField(50);
tf2=new JTextField(50);
l1=new JLabel();
container.setLayout(null);
tf1.setBounds(1,1,49,20);
l1.setBounds(50,1,4,20);
tf2.setBounds(54,1,20,20);
tf1.setHorizontalAlignment(JTextField.RIGHT);
tf1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
tf2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
l1.setBackground(Color.white);
l1.setOpaque(true);
l1.setText(".");
tf1.addKeyListener(this);
tf2.addKeyListener(this);
container.add(tf1);
container.add(tf2);
container.add(l1);
add(container);
//add(tf1);
//add(tf2);
//add(l1);
setSize(200,200);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
PriceTextField p=new PriceTextField();
p.setVisible(true);
}

public void keyReleased(KeyEvent ev)
{
}
public void keyPressed(KeyEvent ev)
{
}
public void keyTyped(KeyEvent evt)
{
char c = evt.getKeyChar();
System.out.println((tf2.getText()).length());
if ( ((c <'0') || (c >'9')) && (c != evt.VK_BACK_SPACE) && (c!=evt.VK_TAB) && 
(c!=evt.VK_DELETE) )evt.consume();

}

}
/*
class Frame1 extends JFrame 
{
private PriceTextField t;
private JTextField jt;
Frame1()
{
t=new PriceTextField();
jt=new JTextField();
setLayout(null);
t.setBounds(4,4,80,25);
jt.setBounds(10,90,100,25);
add(jt);
add(t);
setSize(200,200);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
Frame1 p=new Frame1();
}

}*/