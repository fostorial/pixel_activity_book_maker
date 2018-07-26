package co.uk.fostorial.pixelbook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalToolTipUI;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

public class PixelBookMaker extends JFrame {

	private static final long serialVersionUID = -6283271840055531534L;

	private PixelBookMenu pixelBookMenu;
	
	private PixelBook pixelBook = new PixelBook();
	
	private JScrollPane imageScroll;
	private JList imageList;
	private DefaultListModel imageListModel;
	
	private File currentFile;
	
	private boolean showGridInEditor = true;
	
	private JLabel previewLabel;
	private JLabel statusLabel;
	
	public static PixelBookMaker maker;
	
	public PixelBookMaker() {
		maker = this;
		
		setTitle("Pixel Book Maker - " + pixelBook.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 600);
		
		pixelBookMenu = new PixelBookMenu(this);
		setJMenuBar(pixelBookMenu);
		
		imageScroll = new JScrollPane();
		imageListModel = new DefaultListModel();
		
		imageList = new JList(imageListModel);
		imageList.setToolTipText("");
		    
		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		imageScroll.setViewportView(imageList);
		this.add(imageScroll, BorderLayout.CENTER);
		
		previewLabel = new JLabel("Hover to preview the selected picture") {
		    private static final long serialVersionUID = 1L;

			public JToolTip createToolTip() {
		        return new ImageToolTip();
		      }
		    };
		previewLabel.setToolTipText("");
		this.add(previewLabel, BorderLayout.PAGE_START);
		
		statusLabel = new JLabel(" ");
		this.add(statusLabel, BorderLayout.PAGE_END);
		
		this.setVisible(true);
		
		Application app = Application.getApplication();
		app.addApplicationListener(new ApplicationAdapter() {
		    public void handleOpenFile(ApplicationEvent evt) {
		      System.out.println("Pixel Book Maker - opening " + evt.getFilename());
		      String file = evt.getFilename();
		      try {
		    	  File fileToLoad = new File(file);
		    	 pixelBook = getPixelBookMenu().loadPixelBook(fileToLoad);
		    	 
		    	 getImageListModel().clear();
					for (PixelPicture p : pixelBook.getPictures())
					{
						getImageListModel().addElement(p);
					}
					setCurrentFile(fileToLoad);
					setTitle("Pixel Book Maker - " + getPixelBook().getName());
					getPixelBookMenu().getFileSave().setEnabled(true);
		      } catch (Exception e) {
		    	  System.err.println("Failed to load Pixel Book");
		    	  e.printStackTrace();
		      }
		    }
		});

	}
	
	public static void main(String[] args) throws Exception
	{
		for (String arg : args)
		{
			System.err.println("SYSARG = " + arg);
		}
		PixelBookMaker maker = new PixelBookMaker();
//
//		try {
//			File fileToLoad = new File("C:\\Users\\karl.foster\\Documents\\Personal\\Pixelbooks\\Pixel Spiderman");
//			PixelBook pixelBook = maker.getPixelBookMenu().loadPixelBook(fileToLoad);
//
//			maker.getImageListModel().clear();
//			for (PixelPicture p : pixelBook.getPictures())
//			{
//				maker.getImageListModel().addElement(p);
//			}
//			maker.setCurrentFile(fileToLoad);
//			maker.setTitle("Pixel Book Maker - " + maker.getPixelBook().getName());
//			maker.getPixelBookMenu().getFileSave().setEnabled(true);
//		} catch (Exception e) {
//			System.err.println("Failed to load Pixel Book");
//			e.printStackTrace();
//		}
	}

	public PixelBook getPixelBook() {
		return pixelBook;
	}

	public void setPixelBook(PixelBook pixelBook) {
		this.pixelBook = pixelBook;
	}

	public JList getImageList() {
		return imageList;
	}

	public void setImageList(JList imageList) {
		this.imageList = imageList;
	}

	public DefaultListModel getImageListModel() {
		return imageListModel;
	}

	public void setImageListModel(DefaultListModel imageListModel) {
		this.imageListModel = imageListModel;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public boolean isShowGridInEditor() {
		return showGridInEditor;
	}

	public void setShowGridInEditor(boolean showGridInEditor) {
		this.showGridInEditor = showGridInEditor;
	}
	
	public void exportBook(File directory) throws Exception
	{
		/* Create cover */
		int w = ((pixelBook.getPixelsWide() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
        int h = (pixelBook.getPixelsHigh() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(w, h, type);
        
        ImageIO.write(generateCoverImage(image, (w / 2), 0), "png", new File(directory.getAbsolutePath() + File.separator + "cover.png"));
		
        /* Create pages */
		if (pixelBook.isBWColouringBook())
		{
			createPicturePagesForBWColouring(directory);
		}
		else if (pixelBook.isNumberColouringBook())
		{
			createPicturePagesForNumberColouring(directory);
		}
		else if (pixelBook.isNumberColouringSinglePageBook())
		{
			createPicturePagesForNumberColouringSinglePage(directory);
		}
		else if (pixelBook.isStickerBook())
		{
			createPicturePagesForSticker(directory);
		}
		else
		{
			createPicturePagesForGridColouring(directory);
		}
		
		/* Create Stickers */
		if (pixelBook.isStickerBook())
		{
			createStickers(directory);
		}
	}
	
	public void exportBookApp(File directory) throws Exception
	{
		/* Create cover */
		int w = ((pixelBook.getPixelsWide() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
        int h = (pixelBook.getPixelsHigh() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(w, h, type);
        
        ImageIO.write(generateCoverImage(image, (w / 2), 0), "png", new File(directory.getAbsolutePath() + File.separator + "cover.png"));
		
        /* Create pages */
		if (pixelBook.isBWColouringBook())
		{
			createPicturePagesForBWColouring(directory);
		}
		else if (pixelBook.isNumberColouringBook())
		{
			createPicturePagesForNumberColouring(directory);
		}
		else if (pixelBook.isStickerBook())
		{
			createPicturePagesForSticker(directory);
		}
		else
		{
			createPicturePagesForGridColouring(directory);
		}
		
		/* Create Stickers */
		if (pixelBook.isStickerBook())
		{
			createStickers(directory);
		}
	}
	
	public JLabel getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	public class SortByColor implements Comparator<Color>{

	    public int compare(Color o1, Color o2) {
	        return o1.getRGB() - o2.getRGB();
	    }
	}
	
	class ImageToolTip extends JToolTip {
		private static final long serialVersionUID = 1624274960147992098L;

		public ImageToolTip() {
		    setUI(new ImageToolTipUI());
		  }
		}

		class ImageToolTipUI extends MetalToolTipUI {
		  public void paint(Graphics g, JComponent c) {
			  try
			  {
				  PixelPicture pic = (PixelPicture)getImageListModel().get(getImageList().getSelectedIndex());
					if (pic != null)
					{
						g.setColor(c.getForeground());
						Image image = pic.getImage(getPixelBook().getPixelSize(), false);
				    	g.drawImage(image, 0, 0, c);
					}
			  }
			  catch (Exception e)
			  {
				  FontMetrics metrics = c.getFontMetrics(g.getFont());
				  String tipText = "No Pixel Picture selected. ";
				    g.setColor(c.getForeground());
				    g.drawString(tipText, 1, metrics.getHeight());
			  }
		  }

		  public Dimension getPreferredSize(JComponent c) {
			try
			{
				PixelPicture pic = (PixelPicture)getImageListModel().get(getImageList().getSelectedIndex());
				if (pic != null)
				{
					Image image = pic.getImage(getPixelBook().getPixelSize(), false);
			    	int width = image.getWidth(c);
			    	int height = image.getHeight(c);
			    	return new Dimension(width, height);
				}
				return new Dimension(0,0);
			}
			catch (Exception e)
			{
				FontMetrics metrics = c.getFontMetrics(c.getFont());
			    String tipText = "No Pixel Picture selected. ";
			    int width = SwingUtilities.computeStringWidth(metrics, tipText);
			    int height = metrics.getHeight() * 2;

			    return new Dimension(width, height);
			}
		  }
		}
		
	public void updateStatus(String text)
	{
		statusLabel.setText(" " + text);
	}
	
	public BufferedImage generateCoverImage(BufferedImage image, int xOffset, int yOffset) throws Exception
	{
		if (pixelBook.isCoverBorder())
		{
			return generateBorderCoverImage(image, xOffset, yOffset);
		}
		
		if (pixelBook.isCoverMosaic())
		{
			return generateMosaicCoverImage(image, xOffset, yOffset);
		}
		
		if (pixelBook.isCoverStrip())
		{
			return generateStripCoverImage(image, xOffset, yOffset);
		}
		
		return generateMosaicCoverImage(image, xOffset, yOffset);
	}
	
	public BufferedImage generateMosaicCoverImage(BufferedImage image, int xOffset, int yOffset) throws Exception
	{
		Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        List<BufferedImage> smallImages = new ArrayList<BufferedImage>();
        for (PixelPicture pic : pixelBook.getPictures())
		{
        	smallImages.add(pic.getImage(pixelBook.getCoverImagePixelSize(), false));
		}
        
        int xSpace =  smallImages.get(0).getWidth() / 2;
        int picsPerRow = (int)(((int)((image.getWidth() - xOffset) + xSpace) / (smallImages.get(0).getWidth() + xSpace)));
        xSpace = ((image.getWidth() - xOffset) - (picsPerRow * smallImages.get(0).getWidth())) / (picsPerRow + 1);
        int xStartBuffer = (((image.getWidth() - xOffset) - (((smallImages.get(0).getWidth() + xSpace) * picsPerRow) + xSpace)) / 2);
        
        int ySpace =  smallImages.get(0).getHeight() / 2;
        int picsPerColumn = (int)(((int)((image.getHeight() - yOffset) + ySpace) / (smallImages.get(0).getHeight() + ySpace)));
        ySpace = ((image.getHeight() - yOffset) - (picsPerColumn * smallImages.get(0).getHeight())) / (picsPerColumn + 1);
        int yStartBuffer = (((image.getHeight() - yOffset) - (((smallImages.get(0).getHeight() + ySpace) * picsPerRow) + ySpace)) / 2);
        
        int xVal = xSpace + xOffset + xStartBuffer;
        int yVal = ySpace + yOffset + yStartBuffer;
        int index = 0;
        for (int j = 0; j < picsPerColumn; j++)
        {
        	for (int i = 0; i < picsPerRow; i++)
        	{
        		g2.drawImage(smallImages.get(index), xVal, yVal, this);
        		xVal += smallImages.get(index).getWidth() + xSpace;
        		index++;
        		if (index >= smallImages.size())
        		{
        			index = 0;
        		}
        	}
        	xVal = xSpace + xOffset + xStartBuffer;
        	yVal += smallImages.get(index).getHeight() + ySpace;
        }
        g2.fillRect(0, 0, xOffset, image.getHeight());
        
        InputStream fin = new FileInputStream("kongtext.ttf");
        Font pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)pixelBook.getCoverFontSize());
        fin.close();
        
        int fontSize = pixelBook.getCoverFontSize();
        int maxWidth = (image.getWidth() - xOffset) - ((smallImages.get(0).getWidth() + xSpace + xStartBuffer) * 2);
        
        boolean fontCalculated = false;
        while(!fontCalculated)
        {	
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
        	int fontWidth = 0;
        	if (pixelBook.getName().contains(" "))
            {
            	String[] words = pixelBook.getName().split(" ");
            	int longest = 0;
            	String longestWord = "";
            	for (String s : words)
            	{
            		if (s.length() > longest)
            		{
            			longest = s.length();
            			longestWord = s;
            		}
            	}
            	fontWidth = SwingUtilities.computeStringWidth(metrics, longestWord);
            }
        	else
        	{
        		fontWidth = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
        	}
        	
        	if (fontWidth >= maxWidth)
        	{
        		fontSize--;
        		fin = new FileInputStream("kongtext.ttf");
        		pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)fontSize);
        		fin.close();
        	}
        	else
        	{
        		fontCalculated = true;
        	}
        }
        
        g2.setFont(pixelFont);
        
        if (pixelBook.getName().contains(" "))
        {
        	String[] words = pixelBook.getName().split(" ");
        	int longest = 0;
        	String longestWord = "";
        	for (String s : words)
        	{
        		if (s.length() > longest)
        		{
        			longest = s.length();
        			longestWord = s;
        		}
        	}
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, longestWord);
    	    int height = metrics.getHeight() * words.length;
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    g2.setColor(Color.black);
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 3);
    	    for (String s : words)
    	    {
    	    	int wordWidth = SwingUtilities.computeStringWidth(metrics, s);
    	    	g2.drawString(s, xOffset + ((image.getWidth() - xOffset) / 2) - (wordWidth / 2), y);
    	    	y += metrics.getHeight();
    	    }

        }
        else
        {
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
    	    int height = metrics.getHeight();
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 4);
    	    g2.setColor(Color.black);
    	    g2.drawString(pixelBook.getName(), xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), y);
        }
        
        g2.dispose();
        
		return image;
	}
	
	public BufferedImage generateStripCoverImage(BufferedImage image, int xOffset, int yOffset) throws Exception
	{
		Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        List<BufferedImage> smallImages = new ArrayList<BufferedImage>();
        for (PixelPicture pic : pixelBook.getPictures())
		{
        	smallImages.add(pic.getImage(pixelBook.getCoverImagePixelSize(), false));
		}
  
        int xSpace =  smallImages.get(0).getWidth() / 3;
        
        int picsPerRow = (int)(((int)((image.getWidth() - xOffset) + xSpace) / (smallImages.get(0).getWidth() + xSpace)));
        xSpace = ((image.getWidth() - xOffset) - (picsPerRow * smallImages.get(0).getWidth())) / (picsPerRow + 1);
        int xStartBuffer = (((image.getWidth() - xOffset) - (((smallImages.get(0).getWidth() + xSpace) * picsPerRow) + xSpace)) / 2);
        
        int ySpace =  smallImages.get(0).getHeight() / 3;
        int picsPerColumn = (int)(((int)((image.getHeight() - yOffset) + ySpace) / (smallImages.get(0).getHeight() + ySpace)));
        ySpace = ((image.getHeight() - yOffset) - (picsPerColumn * smallImages.get(0).getHeight())) / (picsPerColumn + 1);
        
        int xVal = xSpace + xOffset + xStartBuffer;
        int yVal = ySpace + yOffset;      
        int index = 0;
        int rowsInStrip = 2;
        for (int j = 0; j < rowsInStrip; j++)
        {
        	for (int i = 0; i < picsPerRow; i++)
        	{
        		g2.drawImage(smallImages.get(index), xVal, yVal, this);
        		xVal += smallImages.get(index).getWidth() + xSpace;
        		index++;
        		if (index >= smallImages.size())
        		{
        			index = 0;
        		}
        	}
        	xVal = xSpace + xOffset + xStartBuffer;
        	yVal += smallImages.get(index).getHeight() + ySpace;
        }
        yVal = (image.getHeight() - yOffset) - ((smallImages.get(0).getHeight() + ySpace) * 2);
        for (int j = 0; j < rowsInStrip; j++)
        {
        	for (int i = 0; i < picsPerRow; i++)
        	{
        		g2.drawImage(smallImages.get(index), xVal, yVal, this);
        		xVal += smallImages.get(index).getWidth() + xSpace;
        		index++;
        		if (index >= smallImages.size())
        		{
        			index = 0;
        		}
        	}
        	xVal = xSpace + xOffset;
        	yVal += smallImages.get(index).getHeight() + ySpace;
        }
        g2.fillRect(0, 0, xOffset, image.getHeight());
        
        InputStream fin = new FileInputStream("kongtext.ttf");
        Font pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)pixelBook.getCoverFontSize());
        fin.close();
        
        int fontSize = pixelBook.getCoverFontSize();
        int maxWidth = (image.getWidth() - xOffset) - ((image.getWidth() - xOffset) / 10);
        
        boolean fontCalculated = false;
        while(!fontCalculated)
        {	
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
        	int fontWidth = 0;
        	if (pixelBook.getName().contains(" "))
            {
            	String[] words = pixelBook.getName().split(" ");
            	int longest = 0;
            	String longestWord = "";
            	for (String s : words)
            	{
            		if (s.length() > longest)
            		{
            			longest = s.length();
            			longestWord = s;
            		}
            	}
            	fontWidth = SwingUtilities.computeStringWidth(metrics, longestWord);
            }
        	else
        	{
        		fontWidth = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
        	}
        	
        	if (fontWidth >= maxWidth)
        	{
        		fontSize--;
        		fin = new FileInputStream("kongtext.ttf");
        		pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)fontSize);
        		fin.close();
        	}
        	else
        	{
        		fontCalculated = true;
        	}
        }
        
        g2.setFont(pixelFont);
        
        if (pixelBook.getName().contains(" "))
        {
        	String[] words = pixelBook.getName().split(" ");
        	int longest = 0;
        	String longestWord = "";
        	for (String s : words)
        	{
        		if (s.length() > longest)
        		{
        			longest = s.length();
        			longestWord = s;
        		}
        	}
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, longestWord);
    	    int height = metrics.getHeight() * words.length;
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    g2.setColor(Color.black);
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 3);
    	    for (String s : words)
    	    {
    	    	int wordWidth = SwingUtilities.computeStringWidth(metrics, s);
    	    	g2.drawString(s, xOffset + ((image.getWidth() - xOffset) / 2) - (wordWidth / 2), y);
    	    	y += metrics.getHeight();
    	    }

        }
        else
        {
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
    	    int height = metrics.getHeight();
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 4);
    	    g2.setColor(Color.black);
    	    g2.drawString(pixelBook.getName(), xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), y);
        }
        
        g2.dispose();
        
		return image;
	}
	
	public BufferedImage generateBorderCoverImage(BufferedImage image, int xOffset, int yOffset) throws Exception
	{
		Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        List<BufferedImage> smallImages = new ArrayList<BufferedImage>();
        for (PixelPicture pic : pixelBook.getPictures())
		{
        	smallImages.add(pic.getImage(pixelBook.getCoverImagePixelSize(), false));
		}
  
        int xSpace =  smallImages.get(0).getWidth() / 3;
        
        int picsPerRow = (int)(((int)((image.getWidth() - xOffset) + xSpace) / (smallImages.get(0).getWidth() + xSpace)));
        xSpace = ((image.getWidth() - xOffset) - (picsPerRow * smallImages.get(0).getWidth())) / (picsPerRow + 1);
        int xStartBuffer = (((image.getWidth() - xOffset) - (((smallImages.get(0).getWidth() + xSpace) * picsPerRow) + xSpace)) / 2);
        
        int ySpace =  smallImages.get(0).getHeight() / 3;
        int picsPerColumn = (int)(((int)((image.getHeight() - yOffset) + ySpace) / (smallImages.get(0).getHeight() + ySpace)));
        ySpace = ((image.getHeight() - yOffset) - (picsPerColumn * smallImages.get(0).getHeight())) / (picsPerColumn + 1);
        int yStartBuffer = (((image.getHeight() - yOffset) - (((smallImages.get(0).getHeight() + ySpace) * picsPerRow) + ySpace)) / 2);
        
        int xVal = xSpace + xOffset + xStartBuffer;
        int yVal = ySpace + yOffset + yStartBuffer;      
        int index = 0;
        for (int j = 0; j < picsPerColumn; j++)
        {
        	for (int i = 0; i < picsPerRow; i++)
        	{
        		if (j == 0 || j == (picsPerColumn - 1) || i == 0 || i == (picsPerRow - 1))
        		{
        			g2.drawImage(smallImages.get(index), xVal, yVal, this);
            		
            		index++;
            		if (index >= smallImages.size())
            		{
            			index = 0;
            		}
        		}
        		xVal += smallImages.get(index).getWidth() + xSpace;
        	}
        	xVal = xSpace + xOffset + xStartBuffer;
        	yVal += smallImages.get(index).getHeight() + ySpace;
        }
        g2.fillRect(0, 0, xOffset, image.getHeight());
        
        InputStream fin = new FileInputStream("kongtext.ttf");
        Font pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)pixelBook.getCoverFontSize());
        fin.close();
        
        int fontSize = pixelBook.getCoverFontSize();
        int maxWidth = (image.getWidth() - xOffset) - ((smallImages.get(0).getWidth() + xSpace + xStartBuffer) * 2);
        
        boolean fontCalculated = false;
        while(!fontCalculated)
        {	
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
        	int fontWidth = 0;
        	if (pixelBook.getName().contains(" "))
            {
            	String[] words = pixelBook.getName().split(" ");
            	int longest = 0;
            	String longestWord = "";
            	for (String s : words)
            	{
            		if (s.length() > longest)
            		{
            			longest = s.length();
            			longestWord = s;
            		}
            	}
            	fontWidth = SwingUtilities.computeStringWidth(metrics, longestWord);
            }
        	else
        	{
        		fontWidth = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
        	}
        	
        	if (fontWidth >= maxWidth)
        	{
        		fontSize--;
        		fin = new FileInputStream("kongtext.ttf");
        		pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)fontSize);
        		fin.close();
        	}
        	else
        	{
        		fontCalculated = true;
        	}
        }
        
        g2.setFont(pixelFont);
        
        if (pixelBook.getName().contains(" "))
        {
        	String[] words = pixelBook.getName().split(" ");
        	int longest = 0;
        	String longestWord = "";
        	for (String s : words)
        	{
        		if (s.length() > longest)
        		{
        			longest = s.length();
        			longestWord = s;
        		}
        	}
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, longestWord);
    	    int height = metrics.getHeight() * words.length;
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    g2.setColor(Color.black);
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 3);
    	    for (String s : words)
    	    {
    	    	int wordWidth = SwingUtilities.computeStringWidth(metrics, s);
    	    	g2.drawString(s, xOffset + ((image.getWidth() - xOffset) / 2) - (wordWidth / 2), y);
    	    	y += metrics.getHeight();
    	    }

        }
        else
        {
        	FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, pixelBook.getName());
    	    int height = metrics.getHeight();
    	    
    	    g2.setColor(Color.white);
    	    g2.fillRect(xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), (image.getHeight() / 2) - (height / 2), width, height);
    	    
    	    int y = (image.getHeight() / 2) - (metrics.getHeight() / 2) + (metrics.getHeight() / 4);
    	    g2.setColor(Color.black);
    	    g2.drawString(pixelBook.getName(), xOffset + ((image.getWidth() - xOffset) / 2) - (width / 2), y);
        }
        
        g2.dispose();
        
		return image;
	}
	
	public void createPicturePagesForSticker(File directory) throws Exception
	{
		int pageNumber = 1;
		for (PixelPicture pic : pixelBook.getPictures())
		{
	        ImageIO.write(generatePicturePageForSticker(pic), "png", new File(directory.getAbsolutePath() + File.separator + "page" + pageNumber + ".png"));
	        
	        pageNumber++;
		}
	}
	
	public BufferedImage generatePicturePageForSticker(PixelPicture pic) throws Exception
	{
			int w = ((pic.getWidth() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
	        int h = (pic.getHeight() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics2D g2 = image.createGraphics();
	        g2.setColor(Color.white);
	        g2.fillRect(0, 0, w, h);
	        
	        /* Paint the Image */
	        int xOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        int yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					g2.setColor(pic.getPixelColours()[i][j]);
					g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
				}
			}
	        
	        /* Paint the blank grid */
	        xOffset = ((pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize()) + (w / 2);
	        yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        g2.setColor(Color.lightGray);
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					if (!pic.getPixelColours()[i][j].equals(Color.white))
					{
						g2.setColor(new Color(230,230,230));
						g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
					}
					g2.setColor(Color.lightGray);
					g2.drawRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
				}
			}
	        g2.dispose();
	        
	        return image;
	}
	
	public void createPicturePagesForGridColouring(File directory) throws Exception
	{
		int pageNumber = 1;
		for (PixelPicture pic : pixelBook.getPictures())
		{
	        ImageIO.write(generatePicturePageForGridColouring(pic), "png", new File(directory.getAbsolutePath() + File.separator + "page" + pageNumber + ".png"));
	        
	        pageNumber++;
		}
	}
	
	public BufferedImage generatePicturePageForGridColouring(PixelPicture pic) throws Exception
	{
			int w = ((pic.getWidth() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
	        int h = (pic.getHeight() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics2D g2 = image.createGraphics();
	        g2.setColor(Color.white);
	        g2.fillRect(0, 0, w, h);
	        
	        /* Paint the Image */
	        int xOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        int yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					g2.setColor(pic.getPixelColours()[i][j]);
					g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
				}
			}
	        
	        /* Paint the blank grid */
	        xOffset = ((pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize()) + (w / 2);
	        yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        g2.setColor(Color.lightGray);
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					g2.drawRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
				}
			}
	        g2.dispose();
	        
	        return image;
	}
	
	public void createPicturePagesForBWColouring(File directory) throws Exception
	{
		int pageNumber = 1;
		for (PixelPicture pic : pixelBook.getPictures())
		{
	        ImageIO.write(generatePicturePageForBWColouring(pic), "png", new File(directory.getAbsolutePath() + File.separator + "page" + pageNumber + ".png"));
	        
	        pageNumber++;
		}
	}
	
	public BufferedImage generatePicturePageForBWColouring(PixelPicture pic) throws Exception
	{
		
			int w = ((pic.getWidth() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
	        int h = (pic.getHeight() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics2D g2 = image.createGraphics();
	        g2.setColor(Color.white);
	        g2.fillRect(0, 0, w, h);
	        
	        /* Paint the Image */
	        int xOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        int yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					g2.setColor(pic.getPixelColours()[i][j]);
					g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
				}
			}
	        
	        /* Paint the blank grid with coloring lines */
	        xOffset = ((pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize()) + (w / 2);
	        yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        g2.setColor(Color.lightGray);
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					//g2.drawRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
					
					/* Draw borders */
					g2.setColor(Color.black);
					
					/* Draw Left */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (i == 0 || !pic.getPixelColours()[i - 1][j].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Right */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (i == (pixelBook.getPixelsWide() - 1) || !pic.getPixelColours()[i + 1][j].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Top */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (j == 0 || !pic.getPixelColours()[i][j - 1].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset);
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Bottom */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (j == pic.getPixelColours()[i].length -1 || !pic.getPixelColours()[i][j + 1].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize(), (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					g2.setColor(Color.lightGray);
				}
			}
	        g2.dispose();
	        
	        return image;
		
	}
	
	public void createPicturePagesForNumberColouring(File directory) throws Exception
	{
		int pageNumber = 1;
		for (PixelPicture pic : pixelBook.getPictures())
		{
	        ImageIO.write(generatePicturePageForNumberColouring(pic), "png", new File(directory.getAbsolutePath() + File.separator + "page" + pageNumber + ".png"));
	        
	        pageNumber++;
		}
	}

	public void createPicturePagesForNumberColouringSinglePage(File directory) throws Exception
	{
		int pageNumber = 1;
		for (PixelPicture pic : pixelBook.getPictures())
		{
			ImageIO.write(generatePicturePageForNumberColouringSinglePage(pic), "png", new File(directory.getAbsolutePath() + File.separator + "page" + pageNumber + ".png"));

			pageNumber++;
		}
	}
	
	public BufferedImage generatePicturePageForNumberColouring(PixelPicture pic) throws Exception
	{
			int w = ((pic.getWidth() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
	        int h = (pic.getHeight() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics2D g2 = image.createGraphics();
	        g2.setColor(Color.white);
	        g2.fillRect(0, 0, w, h);
	        
	        /* Calculate Numbers for Colours */
	        int currentNumber = 0;
	        HashMap<Color, Integer> colours = new HashMap<Color, Integer>();
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					if (pic.getPixelColours()[i][j] != null && !pic.getPixelColours()[i][j].equals(Color.white) && !colours.containsKey(pic.getPixelColours()[i][j]))
					{
						colours.put(pic.getPixelColours()[i][j], new Integer(currentNumber));
						currentNumber++;
					}
				}
			}
	        
	        /* Paint the Key */
	        InputStream fin = new FileInputStream("kongtext.ttf");
	        Font pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)pixelBook.getCoverFontSize());
	        fin.close();

	        int fontSize = pixelBook.getCoverFontSize();
	        int maxWidth = (image.getWidth() / 2) - ((image.getWidth() / 2) / 10);

	        boolean fontCalculated = false;
	        while(!fontCalculated)
	        {
	        	FontMetrics metrics = this.getFontMetrics(pixelFont);
	        	int fontWidth = 0;
	        	fontWidth = SwingUtilities.computeStringWidth(metrics, pic.getName());

	        	if (fontWidth >= maxWidth)
	        	{
	        		fontSize--;
	        		fin = new FileInputStream("kongtext.ttf");
	        		pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont((float)fontSize);
	        		fin.close();
	        	}
	        	else
	        	{
	        		fontCalculated = true;
	        	}
	        }

	        g2.setFont(pixelFont);

	        FontMetrics metrics = this.getFontMetrics(pixelFont);
    	    int width = SwingUtilities.computeStringWidth(metrics, pic.getName());

    	    int y = metrics.getHeight() + (image.getHeight() / 10);
    	    g2.setColor(Color.black);
    	    g2.drawString(pic.getName(), ((image.getWidth()) / 4) - (width / 2), y);

    	    /* print out key, calculate x and y before changing font */
    	    int xVal = image.getWidth() / 10;
	        int yVal = metrics.getHeight() + ((image.getHeight() / 10) * 2);
	        fin = new FileInputStream("kongtext.ttf");
	        pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont(15f);
	        fin.close();
	        g2.setFont(pixelFont);

	        /* Sort Colours for display */
	        SortedSet<Map.Entry<Color, Integer>> results = new TreeSet<Map.Entry<Color, Integer>>(new Comparator<Map.Entry<Color, Integer>>() {
				@Override
				public int compare(Entry<Color, Integer> o1,
						Entry<Color, Integer> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
	        	});
	        results.addAll(colours.entrySet());

	        Iterator<Entry<Color, Integer>> it = results.iterator();
	        while (it.hasNext())
	        {
	        	Entry<Color, Integer> entry = it.next();
	        	String colourString = "" + entry.getValue() + ". " + ColorUtil.getBestColorName(entry.getKey());
	        	metrics = this.getFontMetrics(pixelFont);

	    	    g2.setColor(Color.black);
	    	    g2.drawString(colourString, xVal, yVal);
	    	    yVal += metrics.getHeight();
	        }
	        
	        
	        /* Paint the blank picture with coloring lines */
	        int xOffset = ((pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize()) + (w / 2);
	        int yOffset = (pixelBook.getImageBufferSize() / 2) * pixelBook.getPixelSize();
	        
	        fin = new FileInputStream("kongtext.ttf");
	        pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont(8f);
	        metrics = this.getFontMetrics(pixelFont);
	        fin.close();
	        g2.setFont(pixelFont);
	        
	        for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					/* Draw borders */
					g2.setColor(Color.black);
					
					boolean top = false;
					boolean left = false;
					/* Draw Left */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (i == 0 || !pic.getPixelColours()[i - 1][j].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
							left = true;
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Right */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (i == (pixelBook.getPixelsWide() - 1) || !pic.getPixelColours()[i + 1][j].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Top */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (j == 0 || !pic.getPixelColours()[i][j - 1].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset);
							top = true;
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					/* Draw Bottom */
					try { 
						if (!pic.getPixelColours()[i][j].equals(Color.white) && (j == pic.getPixelColours()[i].length -1 || !pic.getPixelColours()[i][j + 1].equals(pic.getPixelColours()[i][j])))
						{
							g2.drawLine((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize(), (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize());
						}
					}
					catch (Exception e)
					{
						/* Do Nothing */
					}
					
					if (top && left)
					{
						Integer integer = colours.get(pic.getPixelColours()[i][j]);
						g2.drawString("" + integer, (i * pixelBook.getPixelSize()) + xOffset + metrics.getWidths()[0], (j * pixelBook.getPixelSize()) + yOffset + metrics.getHeight());
					}

				}
			}
	        g2.dispose();
	        
	        return image;
		
	}

	public BufferedImage generatePicturePageForNumberColouringSinglePage(PixelPicture pic) throws Exception {
		int w = ((pic.getWidth() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize()));
		int h = (pic.getHeight() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage image = new BufferedImage(w, h, type);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, w, h);

		/* Calculate Numbers for Colours */
		int currentNumber = 1;
		HashMap<Color, Integer> colours = new HashMap<Color, Integer>();
		for (int i = 0; i < pic.getWidth(); i++) {
			for (int j = 0; j < pic.getHeight(); j++) {
				if (!pic.getPixelColours()[i][j].equals(Color.white) && !colours.containsKey(pic.getPixelColours()[i][j])) {
					colours.put(pic.getPixelColours()[i][j], new Integer(currentNumber));
					currentNumber++;
				}
			}
		}

		/* Paint the Key */
		InputStream fin = new FileInputStream("kongtext.ttf");
		Font pixelFont = Font.createFont(Font.PLAIN, fin).deriveFont((float) pixelBook.getCoverFontSize());
		fin.close();

		int fontSize = pixelBook.getCoverFontSize();
		int maxWidth = (image.getWidth() / 2) - ((image.getWidth() / 2) / 10);

		boolean fontCalculated = false;
		while (!fontCalculated) {
			FontMetrics metrics = this.getFontMetrics(pixelFont);
			int fontWidth = 0;
			fontWidth = SwingUtilities.computeStringWidth(metrics, pic.getName());

			if (fontWidth >= maxWidth) {
				fontSize--;
				fin = new FileInputStream("kongtext.ttf");
				pixelFont = Font.createFont(Font.PLAIN, fin).deriveFont((float) fontSize);
				fin.close();
			} else {
				fontCalculated = true;
			}
		}

		g2.setFont(pixelFont);

		FontMetrics metrics = this.getFontMetrics(pixelFont);
		int width = SwingUtilities.computeStringWidth(metrics, pic.getName());

		int y = metrics.getHeight() + (image.getHeight() / 10);
		g2.setColor(Color.black);
		//g2.drawString(pic.getName(), ((image.getWidth()) / 4) - (width / 2), y);

		/* print out key, calculate x and y before changing font */
		int xVal = image.getWidth() / 10;
		int yVal = metrics.getHeight() + ((image.getHeight() / 10) * 2);
		fin = new FileInputStream("kongtext.ttf");
		pixelFont = Font.createFont(Font.PLAIN, fin).deriveFont(15f);
		fin.close();
		g2.setFont(pixelFont);

		/* Sort Colours for display */
		SortedSet<Map.Entry<Color, Integer>> results = new TreeSet<Map.Entry<Color, Integer>>(new Comparator<Map.Entry<Color, Integer>>() {
			@Override
			public int compare(Entry<Color, Integer> o1,
							   Entry<Color, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		results.addAll(colours.entrySet());

		int keyW = results.size() * pixelBook.getPixelSize();
		int keyX = (w / 2) - (keyW / 2);
		int keyY = (pic.getHeight() * pixelBook.getPixelSize()) - (pixelBook.getPixelSize() / 2);

		int c = 0;
		Iterator<Entry<Color, Integer>> it = results.iterator();
		while (it.hasNext())
		{
			Entry<Color, Integer> entry = it.next();

			int x1 = keyX+(c * pixelBook.getPixelSize());
			int y1 = keyY+(1 * pixelBook.getPixelSize());
			int x2 = keyX+(c * pixelBook.getPixelSize()) + pixelBook.getPixelSize();
			int y2 = keyY+(1 * pixelBook.getPixelSize()) + pixelBook.getPixelSize();

			g2.setColor(entry.getKey());
			g2.fillRect(x1, y1, x2-x1, y2-y1);

			/* Draw borders */
			g2.setColor(Color.black);
			g2.drawLine(x1, y1, x2, y1);
			g2.drawLine(x1, y2, x2, y2);
			g2.drawLine(x1, y1, x1, y2);
			g2.drawLine(x2, y1, x2, y2);

			if (colours.get(entry.getKey()) != null) {
				g2.setColor(Color.white);
				if (ColorUtil.invertText(entry.getKey()))
				{
					g2.setColor(Color.black);
				}
				int fontY = y1 + pixelBook.getPixelSize();
				int fontW = SwingUtilities.computeStringWidth(metrics, ""+colours.get(entry.getKey()).intValue());
				int fontX = (pixelBook.getPixelSize() / 2) - (fontW / 2) + x1;
				g2.drawString("" + colours.get(entry.getKey()).intValue(), fontX, fontY);
			}

			c++;
		}

//		Iterator<Entry<Color, Integer>> it = results.iterator();
//		while (it.hasNext())
//		{
//			Entry<Color, Integer> entry = it.next();
//			String colourString = "" + entry.getValue() + ". " + ColorUtil.getBestColorName(entry.getKey());
//			metrics = this.getFontMetrics(pixelFont);
//
//			g2.setColor(Color.black);
//			g2.drawString(colourString, xVal, yVal);
//			yVal += metrics.getHeight();
//		}


		/* Paint the blank picture with coloring lines */
		int xOffset = (pixelBook.getPixelSize());
		int yOffset = (pixelBook.getPixelSize() / 4);

		fin = new FileInputStream("kongtext.ttf");
		pixelFont = Font.createFont (Font.PLAIN, fin).deriveFont(8f);
		metrics = this.getFontMetrics(pixelFont);
		fin.close();
		g2.setFont(pixelFont);

		for (int i = 0; i < pic.getWidth(); i++)
		{
			for (int j = 0; j < pic.getHeight(); j++)
			{
				/* Draw borders */
				g2.setColor(Color.black);

				int x1 = (i * pixelBook.getPixelSize()) + xOffset;
				int y1 = (j * pixelBook.getPixelSize()) + yOffset;
				int x2 = (i * pixelBook.getPixelSize()) + xOffset + pixelBook.getPixelSize();
				int y2 = (j * pixelBook.getPixelSize()) + yOffset + pixelBook.getPixelSize();
				g2.drawLine(x1, y1, x2, y1);
				g2.drawLine(x1, y2, x2, y2);
				g2.drawLine(x1, y1, x1, y2);
				g2.drawLine(x2, y1, x2, y2);

				if (colours.get(pic.getPixelColours()[i][j]) != null) {
					int fontY = y1 + metrics.getHeight();
					int fontW = SwingUtilities.computeStringWidth(metrics, ""+colours.get(pic.getPixelColours()[i][j]).intValue());
					int fontX = (pixelBook.getPixelSize() / 2) - (fontW / 2) + x1;
					g2.drawString("" + colours.get(pic.getPixelColours()[i][j]).intValue(), fontX, fontY);
				}
			}
		}
		g2.dispose();

		return image;

	}
	
	public void createStickers(File directory) throws Exception
	{
		int pageNumber = 1;
		List<Color> stickerList = new ArrayList<Color>();
		for (PixelPicture pic : pixelBook.getPictures())
		{
			for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					if (!pic.getPixelColours()[i][j].equals(Color.white))
					{
						stickerList.add(pic.getPixelColours()[i][j]);
					}
				}
			}
		}
		Collections.sort(stickerList, new SortByColor());
		
		int index = 0;
		List<Color> processedStickers = new ArrayList<Color>();
		while(processedStickers.size() < stickerList.size() - 1)
		{
			int w = ((pixelBook.getPixelsWide() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize())) * 2;
	        int h = (pixelBook.getPixelsHigh() * pixelBook.getPixelSize()) + (pixelBook.getImageBufferSize() * pixelBook.getPixelSize());
	        int type = BufferedImage.TYPE_INT_RGB;
	        BufferedImage image = new BufferedImage(w, h, type);
	        Graphics2D g2 = image.createGraphics();
	        g2.setColor(Color.white);
	        g2.fillRect(0, 0, w, h);
	        
	        /* Paint the Image */
	        int xOffset = 1 * pixelBook.getPixelSize();
	        int yOffset = 1 * pixelBook.getPixelSize();
	        for (int i = 0; i < (pixelBook.getPixelsWide()) + (pixelBook.getImageBufferSize() - 2); i++)
			{
				for (int j = 0; j < pixelBook.getPixelsHigh() + (pixelBook.getImageBufferSize() - 2); j++)
				{
					if (index < stickerList.size())
					{
						g2.setColor(stickerList.get(index));
						g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
						processedStickers.add(stickerList.get(index));
						index++;
					}
				}
			}
	        
	        xOffset = ((pixelBook.getPixelsWide()) + (pixelBook.getImageBufferSize() + 1)) * pixelBook.getPixelSize();
	        for (int i = 0; i < (pixelBook.getPixelsWide()) + (pixelBook.getImageBufferSize() - 2); i++)
			{
				for (int j = 0; j < pixelBook.getPixelsHigh() + (pixelBook.getImageBufferSize() - 2); j++)
				{
					if (index < stickerList.size())
					{
						g2.setColor(stickerList.get(index));
						g2.fillRect((i * pixelBook.getPixelSize()) + xOffset, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
						processedStickers.add(stickerList.get(index));
						index++;
					}
				}
			}
	        
	        /* Draw the guide markers */
	        for (int i = 0; i < (pixelBook.getPixelsWide() * 2) + (pixelBook.getImageBufferSize() * 2); i++)
			{
	        	g2.setColor(Color.black);
	        	g2.drawRect((i * pixelBook.getPixelSize()), 0, pixelBook.getPixelSize(), pixelBook.getPixelSize());
	        	g2.drawRect((i * pixelBook.getPixelSize()), (pixelBook.getPixelsHigh() + pixelBook.getImageBufferSize() - 1) * pixelBook.getPixelSize(), pixelBook.getPixelSize(), pixelBook.getPixelSize());
			}
	        
	        for (int j = 0; j < pixelBook.getPixelsHigh() + pixelBook.getImageBufferSize(); j++)
			{
	        	g2.setColor(Color.black);
	        	g2.drawRect(0, (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
	        	g2.drawRect(((pixelBook.getPixelsWide()) + (pixelBook.getImageBufferSize() - 1)) * pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
	        	g2.drawRect(((pixelBook.getPixelsWide()) + (pixelBook.getImageBufferSize())) * pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
	        	g2.drawRect(((pixelBook.getPixelsWide() * 2) + ((pixelBook.getImageBufferSize() - 1) * 2) + 1) * pixelBook.getPixelSize(), (j * pixelBook.getPixelSize()) + yOffset, pixelBook.getPixelSize(), pixelBook.getPixelSize());
			}
	        
	        g2.dispose();
	        
	        ImageIO.write(image, "png", new File(directory.getAbsolutePath() + File.separator + "stickerpage" + pageNumber + ".png"));
	        
	        pageNumber++;
		}
	}

	public PixelBookMenu getPixelBookMenu() {
		return pixelBookMenu;
	}

	public void setPixelBookMenu(PixelBookMenu pixelBookMenu) {
		this.pixelBookMenu = pixelBookMenu;
	}
	
	
}
