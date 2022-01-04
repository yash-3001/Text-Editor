package texteditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class TextEditor extends JFrame implements ActionListener {
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;//allow user to adjust the size of the text
	JLabel fontLabel;
	JButton fontColorButton,drawButton;
	JComboBox fontBox;
	JMenuBar menuBar,optionsBar;
	JMenu fileMenu;
	JMenu viewMenu;
	JMenu editMenu;
	JMenuItem undoItem;
	JMenuItem redoItem;
    JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem printItem;
	JMenuItem findItem;
	JMenuItem spellcheck;
	UndoManager editManager = new UndoManager();
	private Icon drawIcon = new ImageIcon(getClass().getResource("Draw.png"));
	public Object um;
	
	TextEditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//used for closing the program
		this.setTitle("D-Pad");//title
		this.setSize(500 , 500);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null); //when we run the application the window should open in the center.
		textArea = new JTextArea();
		JPanel panel = new JPanel();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial" ,Font.PLAIN , 20));
		
		scrollPane =new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450, 400));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fontLabel=new JLabel("Font: ");
		fontSizeSpinner=new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue()));
				
			}
			
		});
		drawButton = new JButton(drawIcon);
		drawButton.setPreferredSize(new Dimension(40, 40));
		drawButton.addActionListener(this);
		fontColorButton=new JButton("Color");
		fontColorButton.addActionListener(this);
		//creating an array of strings for all the available fonts in java
		//let's call this Strings[]
		String[] fonts =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		//Menu Bar
		menuBar=new JMenuBar();
		optionsBar= new JMenuBar();
		fileMenu=new JMenu("File");
		editMenu = new JMenu("Edit");
		openItem=new JMenuItem("open");
		saveItem=new JMenuItem("save");
		exitItem=new JMenuItem("exit");
		printItem=new JMenuItem("print");
		viewMenu=new JMenu("View");
		findItem =new JMenuItem("Find");
		spellcheck = new JMenuItem("Spell Check");
		editMenu = new JMenu("Edit");
		undoItem = new JMenuItem("undo");
		redoItem = new JMenuItem("redo");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		printItem.addActionListener(this);
		findItem.addActionListener(this);
		spellcheck.addActionListener(this);
		
		undoItem.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(editManager.canUndo()) {
					editManager.undo();
				}
			}
		});
		redoItem.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(editManager.canRedo()) {
					editManager.redo();
				}
			}
		});
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		fileMenu.add(printItem);
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(editMenu);
		viewMenu.add(findItem);
		viewMenu.add(spellcheck);
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		
		
		
		//closing menu bar
		this.setJMenuBar(menuBar);
		panel.add(fontLabel);
		panel.add(fontSizeSpinner);
		panel.add(fontColorButton);
		panel.add(fontBox);
		panel.add(drawButton);
		this.add(panel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);
		this.setVisible(true);
		
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==fontColorButton) {
			JColorChooser colorChooser = new JColorChooser();
			Color color=colorChooser.showDialog(null, "Choose a color", Color.black);
			textArea.setForeground(color);
		}
		if(e.getSource()==fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		
		if(e.getSource()==openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter =new FileNameExtensionFilter("Text Files" , "txt , HTML files");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			if(response ==JFileChooser.APPROVE_OPTION) {
				File file =new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn =null;
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				}catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
				
			}
		}
			
		
		if(e.getSource()==saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response = fileChooser.showSaveDialog(null);
			if(response== JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut=new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
				fileOut.close();
			}
		}
		}
		if(e.getSource()==exitItem) {
			System.exit(0);
		}
//		if(e.getSource()==printItem) {
//			textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
//		}
		if(e.getSource()==undoItem) {
			if(editManager.canUndo()) {
				undoItem.setEnabled(true);
			}
			else
				undoItem.setEnabled(false);
			
		}
		if(e.getSource()==undoItem) {
			if(editManager.canUndo()) {
				undoItem.setEnabled(true);
			}
			else
				undoItem.setEnabled(false);
			//			um.redo();
		}
		if(e.getSource()==findItem) {
			new Find(textArea);
		}
		if(e.getSource()==spellcheck) {
			new SpellChecker(textArea);
		}
		if(e.getSource()==drawButton) {
			new Input();
		}
		
	}

}