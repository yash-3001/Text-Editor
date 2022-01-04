package texteditor;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class doodlesearch extends JFrame {
	JButton[] buttons = new JButton[10];
	ImageIcon[] icons = new ImageIcon[10];
	private Container container;
	private File directoryPath;
	private JFrame frame;
	private JInternalFrame iframe;
	private JLabel look;
	private JButton search;
	private int width, height;
	private JComboBox dataset;
	private Icon searchIcon = new ImageIcon(getClass().getResource("search.png"));
	Canvas canvas;
	doodlesearch(Canvas can){
		this.canvas = can;
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	            try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            break;
	        }
	    }
	    frame = new JFrame("Insert Doodles");
	    iframe = new JInternalFrame();
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		iframe.setLayout(new FlowLayout());
		String category[] = {"airplane","alarm","animals","bee","arrow","girl","birds","birthday","boats","books","boy","bunny","cacti","candies","candles","cars","clothes","clouds","coffee","crown","faces","fishes","flowers","food","football","furniture","gadgets","hearts","house","jars","kites&balloons","letters","party","plants","smiley","sounds","space","stars","stationary"};
		final JComboBox dataset =new JComboBox(category);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		look = new JLabel("Search by Category: ");
		search = new JButton(searchIcon);
		search.setPreferredSize(new Dimension(50, 50));
		search.addActionListener(listener);
		panel.add(look);
		panel.add(dataset);
		panel.add(search);
		dataset.addActionListener(new ActionListener() {  
	        public void actionPerformed(ActionEvent e) {       
	        	directoryPath = new File("C:\\Users\\HP\\eclipse-workspace\\texteditor\\bin\\texteditor\\dataset\\"+(String)dataset.getSelectedItem());
	        }  
	    });
		container.add(panel, BorderLayout.NORTH);
		container.add(iframe, BorderLayout.SOUTH);
		frame.setVisible(true);
        frame.setSize(450,450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			int i = 0;
			if (event.getSource() == search) {
				JPanel panel2 = new JPanel();
				panel2.setLayout(new FlowLayout());
				File filesList[] = directoryPath.listFiles();
				for(File file : filesList) {
					icons[i] = new ImageIcon(file.getAbsolutePath());
					buttons[i] = new JButton(icons[i]);
					buttons[i].setPreferredSize(new Dimension(350, 350));
					buttons[i].addActionListener(listener);
					panel2.add(buttons[i]);	
					i++;
				}
				container.add(panel2,BorderLayout.CENTER);
			}
			if(event.getSource()== buttons[0]) {
				System.out.print("Hi");
				
				JLabel label = new JLabel(icons[0]);
				canvas.add(label);	
			}if(event.getSource()== buttons[1]) {
				System.out.println("Hello");
				JLabel label = new JLabel(icons[1]);
				canvas.add(label);	
		    }if(event.getSource()== buttons[2]) {
		    	System.out.println("Good");
				JLabel label = new JLabel(icons[2]);
				canvas.add(label);	
		    }if(event.getSource()== buttons[3]) {
		    	System.out.println("Morning");
				JLabel label = new JLabel(icons[3]);
				canvas.add(label);	
		    }												
	  }
  };
}


