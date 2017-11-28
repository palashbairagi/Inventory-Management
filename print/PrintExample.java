import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.geom.*;

public class PrintExample extends JFrame implements ActionListener {
	public static void main(String[] args) {
		new PrintExample();
	}

	public PrintExample() {
		super("Printing Swing Components");
		WindowShow.setNativeLookAndFeel();
		//addWindowListener(this);
		Container content = getContentPane();
		JButton printButton = new JButton("Print");
		printButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		buttonPanel.add(printButton);
		content.add(buttonPanel, BorderLayout.SOUTH);
		DrawingPane drawingPanel = new DrawingPane();
		content.add(drawingPanel, BorderLayout.CENTER);
		setSize(800,300);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		PrintableDocument.printComponent(this);
	}
	
	class DrawingPane extends JPanel {
		private int fontSize = 90;
		private String message = "Roseindia.net";
		private int messageWidth;
		
		public DrawingPane() {
			setBackground(Color.white);
			Font font = new Font("Serif", Font.PLAIN, fontSize);
			setFont(font);
			FontMetrics metrics = getFontMetrics(font);
			messageWidth = metrics.stringWidth(message);
			int width = messageWidth*5/3;
			int height = fontSize*3;
			setPreferredSize(new Dimension(width, height));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graph = (Graphics2D)g;
			int x = messageWidth/10;
			int y = fontSize*5/2;
			graph.translate(x, y);
			graph.setPaint(Color.lightGray);
			
			AffineTransform origTransform = graph.getTransform();
			graph.shear(-0.95, 0);
			graph.scale(1, 3);
			graph.drawString(message, 0, 0);
			graph.setTransform(origTransform);
			graph.setPaint(Color.black);
			graph.drawString(message, 0, 0); 
		}
	}
}

    
