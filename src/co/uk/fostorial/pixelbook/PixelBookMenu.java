package co.uk.fostorial.pixelbook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PixelBookMenu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 4084211008842874523L;
	
	private PixelBookMaker pixelBookMaker;
	
	JFileChooser chooser = new JFileChooser();
	
	private JMenu fileMenu;
	private JMenuItem fileNew;
	private JMenuItem fileLoad;
	private JMenuItem fileSaveAs;
	private JMenuItem fileSave;
	private JMenuItem fileExportBook;
	private JMenuItem fileExportApp;
	private JMenuItem fileExit;
	
	private JMenu settingsMenu;
	private JMenuItem settingsBookName;
	private JMenuItem settingsPixelSize;
	private JMenuItem settingsImageWidth;
	private JMenuItem settingsImageHeight;
	private JMenuItem settingsCoverFontSize;
	private JMenuItem settingsCoverPixelSize;
	private JMenuItem settingsImageBufferSize;
	private JMenu bookType;
	private JMenuItem typeStickerBook;
	private JMenuItem typeColouringBook;
	private JMenuItem typeBWColouringBook;
	private JMenuItem typeNumberColouringBook;
	private JMenuItem typeNumberColouringSinglePageBook;
	private JMenu coverType;
	private JMenuItem coverMosaic;
	private JMenuItem coverBorder;
	private JMenuItem coverStrip;
	
	private JMenu imagesMenu;
	private JMenuItem imagesAddImage;
	private JMenuItem imagesDeleteImage;
	private JMenuItem imagesRenameImage;
	private JMenuItem imagesEditImage;
	
	private JMenu preview;
	private JMenuItem previewCover;
	private JMenuItem previewSticker;
	private JMenuItem previewGridColouring;
	private JMenuItem previewBWColouring;
	private JMenuItem previewNumberColouring;
	private JMenuItem previewNumberColouringSinglePage;

	public PixelBookMenu(PixelBookMaker maker) {
		this.pixelBookMaker = maker;
		
		fileMenu = new JMenu("File");
		
		fileNew = new JMenuItem("New Pixel Book");
		fileNew.addActionListener(this);
		KeyStroke key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		fileNew.setAccelerator(key);
		fileMenu.add(fileNew);
		
		fileLoad = new JMenuItem("Load Pixel Book...");
		fileLoad.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		fileLoad.setAccelerator(key);
		fileMenu.add(fileLoad);
		
		fileMenu.addSeparator();
		
		fileSave = new JMenuItem("Save");
		fileSave.addActionListener(this);
		fileSave.setEnabled(false);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		fileSave.setAccelerator(key);
		fileMenu.add(fileSave);
		
		fileSaveAs = new JMenuItem("Save As...");
		fileSaveAs.addActionListener(this);
		fileMenu.add(fileSaveAs);
		
		fileMenu.addSeparator();
		
		fileExportBook = new JMenuItem("Export Book...");
		fileExportBook.addActionListener(this);
		fileMenu.add(fileExportBook);
		
		fileExportApp = new JMenuItem("Export Book for App...");
		fileExportApp.addActionListener(this);
		fileMenu.add(fileExportApp);
		
		fileMenu.addSeparator();
		
		fileExit = new JMenuItem("Quit Pixel Book Maker");
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		fileExit.setAccelerator(key);
		fileExit.addActionListener(this);
		fileMenu.add(fileExit);
		
		this.add(fileMenu);
		
		
		settingsMenu = new JMenu("Settings");
		
		settingsBookName = new JMenuItem("Set Book Name...");
		settingsBookName.addActionListener(this);
		settingsMenu.add(settingsBookName);
		
		settingsPixelSize = new JMenuItem("Set Pixel Size...");
		settingsPixelSize.addActionListener(this);
		settingsMenu.add(settingsPixelSize);
		
		settingsImageWidth = new JMenuItem("Set Image Width...");
		settingsImageWidth.addActionListener(this);
		settingsMenu.add(settingsImageWidth);
		
		settingsImageHeight = new JMenuItem("Set Image Height...");
		settingsImageHeight.addActionListener(this);
		settingsMenu.add(settingsImageHeight);
		
		settingsImageBufferSize = new JMenuItem("Set Image Buffer Size...");
		settingsImageBufferSize.addActionListener(this);
		settingsMenu.add(settingsImageBufferSize);
		
		settingsCoverFontSize = new JMenuItem("Set Cover Font Size...");
		settingsCoverFontSize.addActionListener(this);
		settingsMenu.add(settingsCoverFontSize);
		
		settingsCoverPixelSize = new JMenuItem("Set Cover Pixel Size...");
		settingsCoverPixelSize.addActionListener(this);
		settingsMenu.add(settingsCoverPixelSize);
		
		bookType = new JMenu("Book Type");
		
		typeStickerBook = new JCheckBoxMenuItem("Sticker Book");
		typeStickerBook.addActionListener(this);
		typeStickerBook.setSelected(pixelBookMaker.getPixelBook().isStickerBook());
		bookType.add(typeStickerBook);

		typeColouringBook = new JCheckBoxMenuItem("Grid Colouring Book");
		typeColouringBook.addActionListener(this);
		typeColouringBook.setSelected(pixelBookMaker.getPixelBook().isColouringBook());
		bookType.add(typeColouringBook);
		
		typeBWColouringBook = new JCheckBoxMenuItem("Black and White Colouring Book");
		typeBWColouringBook.addActionListener(this);
		typeBWColouringBook.setSelected(pixelBookMaker.getPixelBook().isBWColouringBook());
		bookType.add(typeBWColouringBook);
		
		typeNumberColouringBook = new JCheckBoxMenuItem("Colouring By Numbers Book");
		typeNumberColouringBook.addActionListener(this);
		typeNumberColouringBook.setSelected(pixelBookMaker.getPixelBook().isNumberColouringBook());
		bookType.add(typeNumberColouringBook);

		typeNumberColouringSinglePageBook = new JCheckBoxMenuItem("Colouring By Numbers Book (Single Page)");
		typeNumberColouringSinglePageBook.addActionListener(this);
		typeNumberColouringSinglePageBook.setSelected(pixelBookMaker.getPixelBook().isNumberColouringBook());
		bookType.add(typeNumberColouringSinglePageBook);
		
		settingsMenu.add(bookType);
		
		coverType = new JMenu("Cover Type");
		
		coverBorder = new JCheckBoxMenuItem("Border");
		coverBorder.addActionListener(this);
		coverBorder.setSelected(pixelBookMaker.getPixelBook().isCoverBorder());
		coverType.add(coverBorder);
		
		coverMosaic = new JCheckBoxMenuItem("Mosaic");
		coverMosaic.addActionListener(this);
		coverMosaic.setSelected(pixelBookMaker.getPixelBook().isCoverMosaic());
		coverType.add(coverMosaic);
		
		coverStrip = new JCheckBoxMenuItem("Strip");
		coverStrip.addActionListener(this);
		coverStrip.setSelected(pixelBookMaker.getPixelBook().isCoverStrip());
		coverType.add(coverStrip);
		
		settingsMenu.add(coverType);
		
		this.add(settingsMenu);
		
		
		imagesMenu = new JMenu("Images");
		
		imagesAddImage = new JMenuItem("Add Image");
		imagesAddImage.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		imagesAddImage.setAccelerator(key);
		imagesMenu.add(imagesAddImage);
		
		imagesDeleteImage = new JMenuItem("Delete Image");
		imagesDeleteImage.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		imagesDeleteImage.setAccelerator(key);
		imagesMenu.add(imagesDeleteImage);
		
		imagesRenameImage = new JMenuItem("Rename Image");
		imagesRenameImage.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		imagesRenameImage.setAccelerator(key);
		imagesMenu.add(imagesRenameImage);
		
		imagesEditImage = new JMenuItem("Edit Image");
		imagesEditImage.addActionListener(this);
		key = KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		imagesEditImage.setAccelerator(key);
		imagesMenu.add(imagesEditImage);
		
		this.add(imagesMenu);
		
		preview = new JMenu("Preview");
		
		previewCover = new JMenuItem("Preview Cover...");
		previewCover.addActionListener(this);
		preview.add(previewCover);
		
		previewSticker = new JMenuItem("Preview Sticker Book...");
		previewSticker.addActionListener(this);
		preview.add(previewSticker);
		
		previewGridColouring = new JMenuItem("Preview Grid Colouring Book...");
		previewGridColouring.addActionListener(this);
		preview.add(previewGridColouring);
		
		previewBWColouring = new JMenuItem("Preview Black and White Colouring Book...");
		previewBWColouring.addActionListener(this);
		preview.add(previewBWColouring);
		
		previewNumberColouring = new JMenuItem("Preview Colouring by Numbers Book...");
		previewNumberColouring.addActionListener(this);
		preview.add(previewNumberColouring);

		previewNumberColouringSinglePage = new JMenuItem("Preview Colouring by Numbers (Single Page) Book...");
		previewNumberColouringSinglePage.addActionListener(this);
		preview.add(previewNumberColouringSinglePage);
		
		this.add(preview);
	}

	public PixelBookMaker getPixelBookMaker() {
		return pixelBookMaker;
	}

	public void setPixelBookMaker(PixelBookMaker pixelBookMaker) {
		this.pixelBookMaker = pixelBookMaker;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(fileExit))
		{
			System.exit(0);
		}
		
		if (e.getSource().equals(settingsPixelSize))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the size of the pixels (currently " + pixelBookMaker.getPixelBook().getPixelSize() + ")", "Set Pixel Size", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					pixelBookMaker.getPixelBook().setPixelSize(i.intValue());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(settingsImageWidth))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the image width (currently " + pixelBookMaker.getPixelBook().getPixelsWide() + ")", "Set Image Width", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					pixelBookMaker.getPixelBook().setPixelsWide(i.intValue());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(settingsImageHeight))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the image height (currently " + pixelBookMaker.getPixelBook().getPixelsHigh() + ")", "Set Image Height", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					pixelBookMaker.getPixelBook().setPixelsHigh(i.intValue());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(settingsBookName))
		{
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the book name", "Set Book Name", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				pixelBookMaker.updateStatus(" ");
				pixelBookMaker.getPixelBook().setName(s);
				pixelBookMaker.setTitle("Pixel Book Maker - " + pixelBookMaker.getPixelBook().getName());
			}
			else
			{
				JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid name!", "Name Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource().equals(settingsCoverFontSize))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the cover font size (currently " + pixelBookMaker.getPixelBook().getCoverFontSize() + ")", "Set Cover Font Size", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					pixelBookMaker.getPixelBook().setCoverFontSize(i.intValue());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(settingsCoverPixelSize))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the cover pixel size (currently " + pixelBookMaker.getPixelBook().getCoverImagePixelSize()+ ")", "Set Cover Pixel Size", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					pixelBookMaker.getPixelBook().setCoverImagePixelSize(i.intValue());
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(settingsImageBufferSize))
		{
			pixelBookMaker.updateStatus(" ");
			String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the image buffer size (currently " + pixelBookMaker.getPixelBook().getImageBufferSize()+ ")", "Set Image Buffer Size", JOptionPane.PLAIN_MESSAGE);
			if (s != null && s.isEmpty() == false)
			{
				try
				{
					Integer i = Integer.parseInt(s);
					if ((double)(i.doubleValue() % 1d) != 0d)
					{
						JOptionPane.showMessageDialog(pixelBookMaker, "Not an even number!", "Number Error!", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						pixelBookMaker.getPixelBook().setImageBufferSize(i.intValue());
					}
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid number!", "Number Format Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (e.getSource().equals(imagesAddImage))
		{
			PixelPicture pic = pixelBookMaker.getPixelBook().addNewPixelPicture();
			pixelBookMaker.getImageListModel().addElement(pic);
		}
		
		if (e.getSource().equals(imagesDeleteImage))
		{
			try
			{
				pixelBookMaker.updateStatus(" ");
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
				pixelBookMaker.getPixelBook().getPictures().remove(pic);
				pixelBookMaker.getImageListModel().removeElement(pic);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource().equals(imagesRenameImage))
		{
			try
			{
				pixelBookMaker.updateStatus(" ");
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
				
				String s = JOptionPane.showInputDialog(pixelBookMaker, "Enter the book name", "Set Book Name", JOptionPane.PLAIN_MESSAGE);
				if (s != null && s.isEmpty() == false)
				{
					pic.setName(s);
				}
				else
				{
					JOptionPane.showMessageDialog(pixelBookMaker, "Not a valid name!", "Name Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource().equals(imagesEditImage))
		{
			try
			{
				pixelBookMaker.updateStatus(" ");
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
				PixelPictureEditor ed = new PixelPictureEditor(pic, pixelBookMaker.getPixelBook(), pixelBookMaker.isShowGridInEditor(), pixelBookMaker);
				ed.setVisible(true);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource().equals(fileNew))
		{
			int outcome = JOptionPane.showConfirmDialog(pixelBookMaker, "All changes to current book will be lost. Are you sure?", "New Pixel Book", JOptionPane.YES_NO_OPTION);
			if (outcome == JOptionPane.YES_OPTION)
			{
				pixelBookMaker.updateStatus("Creating Book...");
				pixelBookMaker.setPixelBook(new PixelBook());
				pixelBookMaker.getImageListModel().clear();
				pixelBookMaker.setTitle("Pixel Book Maker - " + pixelBookMaker.getPixelBook().getName());
				fileSave.setEnabled(false);
				pixelBookMaker.setCurrentFile(null);
				pixelBookMaker.updateStatus("Book Created");
				
				pixelBookMaker.getPixelBook().setCoverType(PixelBook.COVER_MOSAIC);
				coverBorder.setSelected(false);
				coverMosaic.setSelected(true);
				coverStrip.setSelected(false);
				
				pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_STICKER_BOOK);
				typeStickerBook.setSelected(true);
				typeColouringBook.setSelected(false);
			}
		}
		
		if (e.getSource().equals(fileSave))
		{
			if (pixelBookMaker.getCurrentFile() != null)
			{
				try
				{
					pixelBookMaker.updateStatus("Saving...");
					savePixelBook(pixelBookMaker.getCurrentFile());
					pixelBookMaker.updateStatus("Saved");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);
					pixelBookMaker.updateStatus("Saving Failed");
				}
			}
		}
		
		if (e.getSource().equals(fileSaveAs))
		{
			int outcome = chooser.showSaveDialog(pixelBookMaker);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					pixelBookMaker.updateStatus("Saving...");
					savePixelBook(chooser.getSelectedFile());
					pixelBookMaker.setCurrentFile(chooser.getSelectedFile());
					fileSave.setEnabled(true);
					pixelBookMaker.updateStatus("Saved");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);
					pixelBookMaker.updateStatus("Saving Failed");
				}
			}
		}
		
		if (e.getSource().equals(fileLoad))
		{
			int outcome = chooser.showOpenDialog(pixelBookMaker);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					pixelBookMaker.updateStatus("Loading...");
					PixelBook book = loadPixelBook(chooser.getSelectedFile());
					pixelBookMaker.setPixelBook(book);
					pixelBookMaker.getImageListModel().clear();
					for (PixelPicture p : book.getPictures())
					{
						pixelBookMaker.getImageListModel().addElement(p);
					}
					pixelBookMaker.setCurrentFile(chooser.getSelectedFile());
					pixelBookMaker.setTitle("Pixel Book Maker - " + pixelBookMaker.getPixelBook().getName());
					fileSave.setEnabled(true);
					pixelBookMaker.updateStatus("Book Loaded");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Load Error!", JOptionPane.ERROR_MESSAGE);
					pixelBookMaker.updateStatus("Loading Failed");
				}
			}
		}
		
		if (e.getSource().equals(fileExportBook))
		{
			int outcome = chooser.showSaveDialog(pixelBookMaker);
			if (outcome == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					pixelBookMaker.updateStatus("Exporting...");
					pixelBookMaker.exportBook(chooser.getCurrentDirectory());
					pixelBookMaker.updateStatus("Book Exported");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);
					pixelBookMaker.updateStatus("Export Failed");
				}
			}
		}
		
		if (e.getSource().equals(fileExportApp))
		{
			try
			{
				savePixelBookApp(chooser.getSelectedFile());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
//			int outcome = chooser.showSaveDialog(pixelBookMaker);
//			if (outcome == JFileChooser.APPROVE_OPTION)
//			{
//				try
//				{
//					pixelBookMaker.updateStatus("Exporting...");
//					savePixelBookApp(chooser.getSelectedFile());
//					pixelBookMaker.updateStatus("Exported");
//				}
//				catch (Exception ex)
//				{
//					ex.printStackTrace();
//					JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Save Error!", JOptionPane.ERROR_MESSAGE);
//					pixelBookMaker.updateStatus("Saving Failed");
//				}
//			}
		}
		
		if (e.getSource().equals(previewCover))
		{
			pixelBookMaker.updateStatus(" ");
			
	        try
	        {
	        	JDialog d = new JDialog();
				d.setTitle("Cover Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				int w = ((pixelBookMaker.getPixelBook().getPixelsWide() * pixelBookMaker.getPixelBook().getPixelSize()) + (pixelBookMaker.getPixelBook().getImageBufferSize() * pixelBookMaker.getPixelBook().getPixelSize()));
		        int h = (pixelBookMaker.getPixelBook().getPixelsHigh() * pixelBookMaker.getPixelBook().getPixelSize()) + (pixelBookMaker.getPixelBook().getImageBufferSize() * pixelBookMaker.getPixelBook().getPixelSize());
		        int type = BufferedImage.TYPE_INT_RGB;
		        BufferedImage image = new BufferedImage(w, h, type);
		        
	        	ImageIcon ii = new ImageIcon(pixelBookMaker.generateCoverImage(image, 0, 0));
	        	d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
	        	d.add(new JLabel(ii), BorderLayout.CENTER);
	        	
	        	d.setVisible(true);
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
				JOptionPane.showMessageDialog(pixelBookMaker, ex.getMessage(), "Preview Error!", JOptionPane.ERROR_MESSAGE);
	        }
		}
		
		if (e.getSource().equals(previewSticker))
		{
			pixelBookMaker.updateStatus(" ");
			
	        try
	        {
	        	JDialog d = new JDialog();
				d.setTitle("Sticker Book Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
		        
	        	ImageIcon ii = new ImageIcon(pixelBookMaker.generatePicturePageForSticker(pic));
	        	d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
	        	d.add(new JLabel(ii), BorderLayout.CENTER);
	        	
	        	d.setVisible(true);
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        	JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
	        }
		}
		
		if (e.getSource().equals(previewGridColouring))
		{
			pixelBookMaker.updateStatus(" ");
			
	        try
	        {
	        	JDialog d = new JDialog();
				d.setTitle("Grid Colouring Book Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
		        
	        	ImageIcon ii = new ImageIcon(pixelBookMaker.generatePicturePageForGridColouring(pic));
	        	d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
	        	d.add(new JLabel(ii), BorderLayout.CENTER);
	        	
	        	d.setVisible(true);
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        	JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
	        }
		}
		
		if (e.getSource().equals(previewBWColouring))
		{
			pixelBookMaker.updateStatus(" ");
			
	        try
	        {
	        	JDialog d = new JDialog();
				d.setTitle("Black and White Colouring Book Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
		        
	        	ImageIcon ii = new ImageIcon(pixelBookMaker.generatePicturePageForBWColouring(pic));
	        	d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
	        	d.add(new JLabel(ii), BorderLayout.CENTER);
	        	
	        	d.setVisible(true);
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        	JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
	        }
		}
		
		if (e.getSource().equals(previewNumberColouring))
		{
			pixelBookMaker.updateStatus(" ");
			
	        try
	        {
	        	JDialog d = new JDialog();
				d.setTitle("Colouring By Numbers Book Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());
		        
	        	ImageIcon ii = new ImageIcon(pixelBookMaker.generatePicturePageForNumberColouring(pic));
	        	d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
	        	d.add(new JLabel(ii), BorderLayout.CENTER);
	        	
	        	d.setVisible(true);
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        	JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
	        }
		}

		if (e.getSource().equals(previewNumberColouringSinglePage))
		{
			pixelBookMaker.updateStatus(" ");

			try
			{
				JDialog d = new JDialog();
				d.setTitle("Colouring By Numbers Book Preview");
				d.setModal(true);
				d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

				PixelPicture pic = (PixelPicture)pixelBookMaker.getImageListModel().get(pixelBookMaker.getImageList().getSelectedIndex());

				ImageIcon ii = new ImageIcon(pixelBookMaker.generatePicturePageForNumberColouringSinglePage(pic));
				d.setSize(ii.getIconWidth() + 20, ii.getIconHeight() + 30);
				d.add(new JLabel(ii), BorderLayout.CENTER);

				d.setVisible(true);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(pixelBookMaker, "No image selected!", "Selection Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource().equals(typeStickerBook))
		{
			pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_STICKER_BOOK);
			typeColouringBook.setSelected(false);
			typeStickerBook.setSelected(true);
			typeBWColouringBook.setSelected(false);
			typeNumberColouringBook.setSelected(false);
			typeNumberColouringSinglePageBook.setSelected(false);
		}
		
		if (e.getSource().equals(typeColouringBook))
		{
			pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_COLOURING_BOOK);
			typeColouringBook.setSelected(true);
			typeStickerBook.setSelected(false);
			typeBWColouringBook.setSelected(false);
			typeNumberColouringBook.setSelected(false);
			typeNumberColouringSinglePageBook.setSelected(false);
		}
		
		if (e.getSource().equals(typeBWColouringBook))
		{
			pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_BW_COLOURING_BOOK);
			typeColouringBook.setSelected(false);
			typeStickerBook.setSelected(false);
			typeBWColouringBook.setSelected(true);
			typeNumberColouringBook.setSelected(false);
			typeNumberColouringSinglePageBook.setSelected(false);
		}
		
		if (e.getSource().equals(typeNumberColouringBook))
		{
			pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_NUMBER_COLOURING_BOOK);
			typeColouringBook.setSelected(false);
			typeStickerBook.setSelected(false);
			typeBWColouringBook.setSelected(false);
			typeNumberColouringBook.setSelected(true);
			typeNumberColouringSinglePageBook.setSelected(false);
		}

		if (e.getSource().equals(typeNumberColouringSinglePageBook))
		{
			pixelBookMaker.getPixelBook().setBookType(PixelBook.TYPE_NUMBER_COLOURING_SINGLE_PAGE_BOOK);
			typeColouringBook.setSelected(false);
			typeStickerBook.setSelected(false);
			typeBWColouringBook.setSelected(false);
			typeNumberColouringBook.setSelected(false);
			typeNumberColouringSinglePageBook.setSelected(true);
		}
		
		if (e.getSource().equals(coverBorder))
		{
			pixelBookMaker.getPixelBook().setCoverType(PixelBook.COVER_BORDER);
			coverBorder.setSelected(true);
			coverMosaic.setSelected(false);
			coverStrip.setSelected(false);
		}
		
		if (e.getSource().equals(coverMosaic))
		{
			pixelBookMaker.getPixelBook().setCoverType(PixelBook.COVER_MOSAIC);
			coverBorder.setSelected(false);
			coverMosaic.setSelected(true);
			coverStrip.setSelected(false);
		}
		
		if (e.getSource().equals(coverStrip))
		{
			pixelBookMaker.getPixelBook().setCoverType(PixelBook.COVER_STRIP);
			coverBorder.setSelected(false);
			coverMosaic.setSelected(false);
			coverStrip.setSelected(true);
		}
	}
	
	private void savePixelBook(File outfile) throws Exception
	{
		String xml = "<?xml version= \"1.0\"?>\n<xml>\n";
		xml += "<bookname>" + pixelBookMaker.getPixelBook().getName() + "</bookname>\n";
		xml += "<pixelsize>" + pixelBookMaker.getPixelBook().getPixelSize() + "</pixelsize>\n";
		xml += "<imageheight>" + pixelBookMaker.getPixelBook().getPixelsHigh() + "</imageheight>\n";
		xml += "<imagewidth>" + pixelBookMaker.getPixelBook().getPixelsWide() + "</imagewidth>\n";
		xml += "<coverfontsize>" + pixelBookMaker.getPixelBook().getCoverFontSize() + "</coverfontsize>\n";
		xml += "<coverpixelsize>" + pixelBookMaker.getPixelBook().getCoverImagePixelSize() + "</coverpixelsize>\n";
		xml += "<imagebuffer>" + pixelBookMaker.getPixelBook().getCoverImagePixelSize() + "</imagebuffer>\n";
		xml += "<booktype>" + pixelBookMaker.getPixelBook().getBookType() + "</booktype>\n";
		xml += "<covertype>" + pixelBookMaker.getPixelBook().getCoverType() + "</covertype>\n";
		xml += "<pixelimages>\n";
		
		for (PixelPicture pic : pixelBookMaker.getPixelBook().getPictures())
		{
			xml += "<pixelimage>\n";
			xml += "<picturename>" + pic.getName() + "</picturename>\n";
			xml += "<pixelcolumns>\n";
			for (int i = 0; i < pic.getWidth(); i++)
			{
				xml += "<pixelcolumn>";
				String col = "";
				for (int j = 0; j < pic.getHeight(); j++)
				{
					col += pic.getPixelColours()[i][j].getRGB() + ",";
				}
				if (col.endsWith(","))
				{
					col = col.substring(0, col.length() - 1);
				}
				xml += col;
				xml += "</pixelcolumn>\n";
			}
			xml += "</pixelcolumns>\n";
			xml += "</pixelimage>\n";
		}
		
		xml += "</pixelimages>\n";
		xml += "</xml>";
		
		FileWriter fstream = new FileWriter(outfile);
		BufferedWriter out = new BufferedWriter(fstream);
		
		out.write(xml);
		
		out.close();
		fstream.close();
	}
	
	private void savePixelBookApp(File outfile) throws Exception
	{
		boolean solvable = pixelBookMaker.getPixelBook().isAppSolvable();
		
//		String xml = "<?xml version= \"1.0\"?>\n<xml>\n";
//		xml += "<bookname>" + pixelBookMaker.getPixelBook().getName() + "</bookname>\n";
//		xml += "<pixelsize>" + pixelBookMaker.getPixelBook().getPixelSize() + "</pixelsize>\n";
//		xml += "<imageheight>" + pixelBookMaker.getPixelBook().getPixelsHigh() + "</imageheight>\n";
//		xml += "<imagewidth>" + pixelBookMaker.getPixelBook().getPixelsWide() + "</imagewidth>\n";
//		xml += "<coverfontsize>" + pixelBookMaker.getPixelBook().getCoverFontSize() + "</coverfontsize>\n";
//		xml += "<coverpixelsize>" + pixelBookMaker.getPixelBook().getCoverImagePixelSize() + "</coverpixelsize>\n";
//		xml += "<imagebuffer>" + pixelBookMaker.getPixelBook().getCoverImagePixelSize() + "</imagebuffer>\n";
//		xml += "<booktype>" + pixelBookMaker.getPixelBook().getBookType() + "</booktype>\n";
//		xml += "<covertype>" + pixelBookMaker.getPixelBook().getCoverType() + "</covertype>\n";
//		xml += "<pixelimages>\n";
//		
//		for (PixelPicture pic : pixelBookMaker.getPixelBook().getPictures())
//		{
//			xml += "<pixelimage>\n";
//			xml += "<picturename>" + pic.getName() + "</picturename>\n";
//			xml += "<pixelcolumns>\n";
//			for (int i = 0; i < pic.getWidth(); i++)
//			{
//				xml += "<pixelcolumn>";
//				String col = "";
//				for (int j = 0; j < pic.getHeight(); j++)
//				{
//					col += pic.getPixelColours()[i][j].getRGB() + ",";
//				}
//				if (col.endsWith(","))
//				{
//					col = col.substring(0, col.length() - 1);
//				}
//				xml += col;
//				xml += "</pixelcolumn>\n";
//			}
//			xml += "</pixelcolumns>\n";
//			xml += "</pixelimage>\n";
//		}
//		
//		xml += "</pixelimages>\n";
//		xml += "</xml>";
//		
//		FileWriter fstream = new FileWriter(outfile);
//		BufferedWriter out = new BufferedWriter(fstream);
//		
//		out.write(xml);
//		
//		out.close();
//		fstream.close();
	}
	
	public PixelBook loadPixelBook(File file) throws Exception
	{
		PixelBook book = new PixelBook();
		
		Document document = Jsoup.parse(file, null);

		Elements els = document.select("bookname");
		for (Element el : els) {
			book.setName(el.text());
		}
		
		els = document.select("pixelsize");
		for (Element el : els) {
			try
			{
				book.setPixelSize(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("imagewidth");
		for (Element el : els) {
			try
			{
				book.setPixelsWide(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("imageheight");
		for (Element el : els) {
			try
			{
				book.setPixelsHigh(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("coverfontsize");
		for (Element el : els) {
			try
			{
				book.setCoverFontSize(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("coverpixelsize");
		for (Element el : els) {
			try
			{
				book.setCoverImagePixelSize(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("imagebuffer");
		for (Element el : els) {
			try
			{
				book.setCoverImagePixelSize(Integer.parseInt(el.text()));
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}
		}
		
		els = document.select("booktype");
		for (Element el : els) {
			book.setBookType(el.text());
			if (PixelBook.TYPE_COLOURING_BOOK.equals(el.text()))
			{
				typeColouringBook.setSelected(true);
				typeStickerBook.setSelected(false);
				typeBWColouringBook.setSelected(false);
				typeNumberColouringBook.setSelected(false);
			}
			if (PixelBook.TYPE_STICKER_BOOK.equals(el.text()))
			{
				typeColouringBook.setSelected(false);
				typeStickerBook.setSelected(true);
				typeBWColouringBook.setSelected(false);
				typeNumberColouringBook.setSelected(false);
			}
			if (PixelBook.TYPE_BW_COLOURING_BOOK.equals(el.text()))
			{
				typeColouringBook.setSelected(false);
				typeStickerBook.setSelected(false);
				typeBWColouringBook.setSelected(true);
				typeNumberColouringBook.setSelected(false);
			}
			if (PixelBook.TYPE_NUMBER_COLOURING_BOOK.equals(el.text()))
			{
				typeColouringBook.setSelected(false);
				typeStickerBook.setSelected(false);
				typeBWColouringBook.setSelected(false);
				typeNumberColouringBook.setSelected(true);
			}
		}
		
		els = document.select("covertype");
		for (Element el : els) {
			book.setCoverType(el.text());
			if (PixelBook.COVER_BORDER.equals(el.text()))
			{
				coverBorder.setSelected(true);
				coverMosaic.setSelected(false);
				coverStrip.setSelected(false);
			}
			if (PixelBook.COVER_MOSAIC.equals(el.text()))
			{
				coverBorder.setSelected(false);
				coverMosaic.setSelected(true);
				coverStrip.setSelected(false);
			}
			if (PixelBook.COVER_STRIP.equals(el.text()))
			{
				coverBorder.setSelected(false);
				coverMosaic.setSelected(false);
				coverStrip.setSelected(true);
			}
		}
		
		els = document.select("pixelimages pixelimage");
		for (Element el : els) {
			PixelPicture pic = new PixelPicture(book.getPixelsWide(), book.getPixelsHigh());
			for (Element c : el.children())
			{
				if (c.tagName().equals("picturename"))
				{
					pic.setName(c.text());
				}
				
				if (c.tagName().equals("pixelcolumns"))
				{
					int i = 0;
					for (Element col : c.children())
					{
						if (col.tagName().equals("pixelcolumn"))
						{
							String column = col.text();
							String[] rgbs = column.split(",");
							int j = 0;
							for (String s : rgbs)
							{
								Color colour = new Color(Integer.parseInt(s.replace(",", "")));
								pic.getPixelColours()[i][j] = colour;
								j++;
							}
							i++;
						}
					}
				}
			}
			book.getPictures().add(pic);
		}
		
		return book;
	}

	public JMenuItem getFileSave() {
		return fileSave;
	}

	public void setFileSave(JMenuItem fileSave) {
		this.fileSave = fileSave;
	}
	
	
}
