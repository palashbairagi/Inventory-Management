import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.geom.*;

class Header extends JPanel {
		private int fontSize = 90;
		private String message = "Akshat Medical";
		private int messageWidth;
		
		public Header() {
			//setBackground(Color.white);
			Font font = new Font("Serif", Font.PLAIN, fontSize);
			setFont(font);
			FontMetrics metrics = getFontMetrics(font);
			messageWidth = metrics.stringWidth(message);
			int width = messageWidth;
			int height = fontSize;
			setPreferredSize(new Dimension(width, height));
		}

public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graph = (Graphics2D)g;
			int x = messageWidth/10;
			int y = fontSize;
			graph.translate(x, y);
			graph.setPaint(Color.lightGray);
			
			AffineTransform origTransform = graph.getTransform();
			graph.shear(-0.70, 0);
			graph.scale(1, 1.5);
			graph.drawString(message, 0, 0);
			graph.setTransform(origTransform);
			graph.setPaint(Color.black);
			graph.drawString(message, 0, 0); 
		}
		}