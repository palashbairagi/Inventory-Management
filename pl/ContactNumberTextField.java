package pl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class ContactNumberTextField extends JTextField implements KeyListener
{
public ContactNumberTextField()
{
addKeyListener(this);
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
if ( ((c <'0') || (c >'9')) && (c != evt.VK_BACK_SPACE) && (c!=evt.VK_TAB) && 
(c!=evt.VK_DELETE) )evt.consume();
}
}