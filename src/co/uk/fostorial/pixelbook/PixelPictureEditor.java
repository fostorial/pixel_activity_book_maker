package co.uk.fostorial.pixelbook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class PixelPictureEditor extends JDialog implements ActionListener {

	private static final long serialVersionUID = -3597892350124852243L;

	private PixelPicture picture;
	private PixelBook book;
	private PixelBookMaker pixelBookMaker;
	
	private JScrollPane scroll;
	private JLayeredPane pane;
	
	private JToolBar colourBar;
	private JButton chooseColour;
	private JButton pickColour;
	private JLabel currentColourLabel;
	
	private JToolBar buttonsBar;
	private JButton save;
	private JButton cancel;
	
	private Color currentColor = Color.white;
	
	private JLabel[][] labels;
	private boolean pickColourMode = false;
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem editUndo = new JMenuItem("Undo");
	private JMenuItem editChooseColour = new JMenuItem("Choose Colour");
	private JMenuItem editPickColour = new JMenuItem("Pick Colour");
	
	private JMenu displayMenu;
	private JCheckBoxMenuItem displayShowGrid;
	
	private List<Object[]> undoStack = new ArrayList<Object[]>();
	
	public PixelPictureEditor(PixelPicture picture, PixelBook book, boolean showGrid, PixelBookMaker pixelBookMaker) {
		this.setPicture(picture);
		this.setBook(book);
		this.pixelBookMaker = pixelBookMaker;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize((picture.getWidth() * book.getPixelSize()) + 30, (picture.getHeight() * book.getPixelSize()) + 100);
		setTitle("Pixel Picture Editor - " + picture.getName());
		
		editUndo.addActionListener(this);
		KeyStroke key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		editUndo.setAccelerator(key);
		editMenu.add(editUndo);
		editMenu.addSeparator();
		
		editChooseColour.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		editChooseColour.setAccelerator(key);
		editMenu.add(editChooseColour);
		
		editPickColour.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		editPickColour.setAccelerator(key);
		editMenu.add(editPickColour);
		
		menubar.add(editMenu);
		
		displayMenu = new JMenu("View");
		
		displayShowGrid = new JCheckBoxMenuItem("Show Grid in Editor");
		displayShowGrid.addActionListener(this);
		displayShowGrid.setSelected(pixelBookMaker.isShowGridInEditor());
		displayMenu.add(displayShowGrid);
		
		menubar.add(displayMenu);
		
		this.setJMenuBar(menubar);
		
		scroll = new JScrollPane();
		
		pane = new JLayeredPane();
		pane.setLayout(null);
		pane.setPreferredSize(new Dimension(picture.getWidth() * book.getPixelSize(), picture.getHeight() * book.getPixelSize()));
		
		labels = new JLabel[picture.getWidth()][picture.getHeight()];
		for (int i = 0; i < picture.getWidth(); i++)
		{
			for (int j = 0; j < picture.getHeight(); j++)
			{
				JLabel l = new JLabel("");
				l.setOpaque(true);
				if (showGrid)
				{
					l.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				}
				l.setSize(new Dimension(book.getPixelSize(), book.getPixelSize()));
				l.setPreferredSize(new Dimension(book.getPixelSize(), book.getPixelSize()));
				l.setBounds(i * book.getPixelSize(), j * book.getPixelSize(), book.getPixelSize(), book.getPixelSize());
				try
				{
					l.setBackground(picture.getPixelColours()[i][j]);
				}
				catch(Exception e)
				{
					l.setBackground(Color.white);
				}
				
				l.addMouseListener ( new MouseAdapter ()
	            {
	                public void mousePressed ( MouseEvent e )
	                {
	                	if (pickColourMode)
	                	{
	                		pickColourMode = false;
	                		JLabel label = (JLabel)e.getSource();
	                		currentColor = label.getBackground();
	                		currentColourLabel.setBackground(currentColor);
	                	}
	                	else
	                	{
	                		JLabel label = (JLabel)e.getSource();
	                		Object[] undo = new Object[2];
	                		undo[0] = label;
	                		undo[1] = label.getBackground();
	                		undoStack.add(undo);
	                    	label.setBackground(currentColor);
	                	}
	                }
	            } );
				
				pane.add(l);
				l.validate();
				pane.validate();
				labels[i][j] = l;
			}
		}
		
		scroll.setViewportView(pane);
		
		this.add(scroll, BorderLayout.CENTER);
		
		colourBar = new JToolBar();
		colourBar.setFloatable(false);
		
		chooseColour = new JButton("Choose Colour");
		chooseColour.addActionListener(this);
		colourBar.add(chooseColour);
		
		pickColour = new JButton("Pick Colour");
		pickColour.addActionListener(this);
		colourBar.add(pickColour);
		
		currentColourLabel = new JLabel("  Current Colour  ");
		currentColourLabel.setOpaque(true);
		currentColourLabel.setBackground(currentColor);
		colourBar.add(currentColourLabel);
		
		this.add(colourBar, BorderLayout.PAGE_START);
		
		buttonsBar = new JToolBar();
		buttonsBar.setFloatable(false);
		
		save = new JButton("Save");
		save.addActionListener(this);
		buttonsBar.add(save);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		buttonsBar.add(cancel);
		
		this.add(buttonsBar, BorderLayout.PAGE_END);
	}

	public PixelPicture getPicture() {
		return picture;
	}

	public void setPicture(PixelPicture picture) {
		this.picture = picture;
	}

	public PixelBook getBook() {
		return book;
	}

	public void setBook(PixelBook book) {
		this.book = book;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(chooseColour) || e.getSource().equals(editChooseColour))
		{
			pickColourMode = false;
			
			JColorChooser chooser = new JColorChooser();
			chooser.addChooserPanel(new PixelBookColorsPanel(book, labels));
			ListAcceptActionListener listListener = new ListAcceptActionListener();
			listListener.setChooser(chooser);
			JDialog d = JColorChooser.createDialog(this, "Choose a colour...", true, chooser, listListener, null);
			d.setVisible(true);
			d.dispose();
		}
		
		if (e.getSource().equals(pickColour) || e.getSource().equals(editPickColour))
		{
			pickColourMode = true;
		}
		
		if (e.getSource().equals(editUndo))
		{
			/* Undo */
			if (undoStack.size() > 0)
			{
				Object[] undo = undoStack.get(undoStack.size() - 1);
				((JLabel)undo[0]).setBackground((Color)undo[1]);
				undoStack.remove(undo);
			}
		}
		
		if (e.getSource().equals(cancel))
		{
			pickColourMode = false;
			this.setVisible(false);
		}
		
		if (e.getSource().equals(save))
		{
			pickColourMode = false;
			for (int i = 0; i < picture.getWidth(); i++)
			{
				for (int j = 0; j < picture.getHeight(); j++)
				{
					picture.getPixelColours()[i][j] = labels[i][j].getBackground();
				}
			}
			this.setVisible(false);
		}
		
		if (e.getSource().equals(displayShowGrid))
		{
			pixelBookMaker.updateStatus(" ");
			displayShowGrid.setSelected(!pixelBookMaker.isShowGridInEditor());
			pixelBookMaker.setShowGridInEditor(displayShowGrid.isSelected());
			
			if (displayShowGrid.isSelected())
			{
				for (int i = 0; i < picture.getWidth(); i++)
				{
					for (int j = 0; j < picture.getHeight(); j++)
					{
						labels[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
					}
				}
			}
			else
			{
				for (int i = 0; i < picture.getWidth(); i++)
				{
					for (int j = 0; j < picture.getHeight(); j++)
					{
						labels[i][j].setBorder(null);
					}
				}
			}
		}
	}

	public BufferedImage getPictureImage()
	{		
		int w = picture.getWidth() * book.getPixelSize();
        int h = picture.getHeight() * book.getPixelSize();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(w, h, type);
        Graphics2D g2 = image.createGraphics();
        pane.paintAll(g2);
        pane.printComponents(g2);
        g2.dispose();
        
        return image;
	}
	
	public class ListAcceptActionListener implements ActionListener {
		private JColorChooser chooser;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currentColor = chooser.getColor();
			currentColourLabel.setBackground(chooser.getColor());
		}

		public JColorChooser getChooser() {
			return chooser;
		}

		public void setChooser(JColorChooser chooser) {
			this.chooser = chooser;
		}
	};
}
