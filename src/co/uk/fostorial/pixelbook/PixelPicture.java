package co.uk.fostorial.pixelbook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import co.uk.fostorial.pixelbook.PixelBook.SortByPixelColor;

public class PixelPicture {

	private String name = "New Pixel Picture";
	private Color[][] pixelColours;
	private int width;
	private int height;
	
	private PixelColor[][] pixelColorArray;
	private int[] columnCounts;
	private int[] rowCounts;
	private int[] columnSizes;
	private int[] rowSizes;
	private int[] columnColours;
	private int[] rowColours;
	private PixelColor[][] columnColourSelection;
	private PixelColor[][] rowColourSelection;
	
	private PixelColorCount[][] columnNonogram;
	private PixelColorCount[][] rowNonogram;
	
	private List<String> defaultFill = new ArrayList<String>();
	
	public PixelPicture(int width, int height) {
		setupDefaultFill();
		
		pixelColours = new Color[width][height];
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				if (defaultFill.contains(""+i+","+j))
				{
					pixelColours[i][j] = Color.lightGray;
				}
				else
				{
					pixelColours[i][j] = Color.white;
				}
			}
		}
		
		this.width = width;
		this.height = height;
	}
	
	public PixelPicture(int width, int height, Color[][] originalColours) {
		pixelColours = new Color[width][height];
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				try
				{
					pixelColours[i][j] = originalColours[i][j];
				}
				catch(Exception e)
				{
					pixelColours[i][j] = Color.white;
				}
			}
		}
		
		this.width = width;
		this.height = height;
	}

	public Color[][] getPixelColours() {
		return pixelColours;
	}

	public void setPixelColours(Color[][] pixelColours) {
		this.pixelColours = pixelColours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setupDefaultFill()
	{
		defaultFill.add("1,11");
		defaultFill.add("1,12");
		defaultFill.add("2,9");
		defaultFill.add("2,10");
		defaultFill.add("2,11");
		defaultFill.add("2,12");
		defaultFill.add("3,8");
		defaultFill.add("3,9");
		defaultFill.add("3,10");
		defaultFill.add("3,11");
		defaultFill.add("3,12");
		defaultFill.add("3,15");
		defaultFill.add("4,2");
		defaultFill.add("4,3");
		defaultFill.add("4,4");
		defaultFill.add("4,8");
		defaultFill.add("4,9");
		defaultFill.add("4,11");
		defaultFill.add("4,13");
		defaultFill.add("4,14");
		defaultFill.add("4,15");
		defaultFill.add("5,1");
		defaultFill.add("5,2");
		defaultFill.add("5,3");
		defaultFill.add("5,4");
		defaultFill.add("5,5");
		defaultFill.add("5,7");
		defaultFill.add("5,8");
		defaultFill.add("5,9");
		defaultFill.add("5,10");
		defaultFill.add("5,11");
		defaultFill.add("5,12");
		defaultFill.add("5,13");
		defaultFill.add("5,14");
		defaultFill.add("5,15");
		defaultFill.add("6,0");
		defaultFill.add("6,1");
		defaultFill.add("6,2");
		defaultFill.add("6,3");
		defaultFill.add("6,4");
		defaultFill.add("6,5");
		defaultFill.add("6,6");
		defaultFill.add("6,7");
		defaultFill.add("6,8");
		defaultFill.add("6,9");
		defaultFill.add("6,10");
		defaultFill.add("6,11");
		defaultFill.add("6,12");
		defaultFill.add("6,13");
		defaultFill.add("7,0");
		defaultFill.add("7,1");
		defaultFill.add("7,2");
		defaultFill.add("7,3");
		defaultFill.add("7,4");
		defaultFill.add("7,5");
		defaultFill.add("7,6");
		defaultFill.add("7,7");
		defaultFill.add("7,8");
		defaultFill.add("7,9");
		defaultFill.add("7,10");
		defaultFill.add("7,11");
		defaultFill.add("7,12");
		defaultFill.add("8,0");
		defaultFill.add("8,1");
		defaultFill.add("8,2");
		defaultFill.add("8,3");
		defaultFill.add("8,4");
		defaultFill.add("8,5");
		defaultFill.add("8,6");
		defaultFill.add("8,7");
		defaultFill.add("8,8");
		defaultFill.add("8,9");
		defaultFill.add("8,10");
		defaultFill.add("8,11");
		defaultFill.add("8,12");
		defaultFill.add("9,0");
		defaultFill.add("9,1");
		defaultFill.add("9,2");
		defaultFill.add("9,3");
		defaultFill.add("9,4");
		defaultFill.add("9,5");
		defaultFill.add("9,6");
		defaultFill.add("9,7");
		defaultFill.add("9,8");
		defaultFill.add("9,9");
		defaultFill.add("9,10");
		defaultFill.add("9,11");
		defaultFill.add("9,12");
		defaultFill.add("9,13");
		defaultFill.add("10,1");
		defaultFill.add("10,2");
		defaultFill.add("10,3");
		defaultFill.add("10,4");
		defaultFill.add("10,5");
		defaultFill.add("10,7");
		defaultFill.add("10,8");
		defaultFill.add("10,9");
		defaultFill.add("10,10");
		defaultFill.add("10,11");
		defaultFill.add("10,12");
		defaultFill.add("10,13");
		defaultFill.add("10,14");
		defaultFill.add("10,15");
		defaultFill.add("11,2");
		defaultFill.add("11,3");
		defaultFill.add("11,4");
		defaultFill.add("11,8");
		defaultFill.add("11,9");
		defaultFill.add("11,11");
		defaultFill.add("11,13");
		defaultFill.add("11,14");
		defaultFill.add("11,15");
		defaultFill.add("12,8");
		defaultFill.add("12,9");
		defaultFill.add("12,10");
		defaultFill.add("12,11");
		defaultFill.add("12,12");
		defaultFill.add("12,15");
		defaultFill.add("13,9");
		defaultFill.add("13,10");
		defaultFill.add("13,11");
		defaultFill.add("13,12");
		defaultFill.add("14,11");
		defaultFill.add("14,12");
	}
	
	public BufferedImage getImage(int pixelSize, boolean showGrid)
	{
		JLayeredPane pane = new JLayeredPane();
		pane.setSize(new Dimension(width * pixelSize, height * pixelSize));
		pane.setPreferredSize(new Dimension(width * pixelSize, height * pixelSize));
		
		JLabel[][] labels = new JLabel[width][height];
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				JLabel l = new JLabel("");
				l.setOpaque(true);
				if (showGrid)
				{
					l.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				}
				l.setPreferredSize(new Dimension(pixelSize, pixelSize));
				l.setBounds(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
				try
				{
					l.setBackground(pixelColours[i][j]);
				}
				catch(Exception e)
				{
					l.setBackground(Color.white);
				}
				
				pane.add(l);
				labels[i][j] = l;
			}
		}
		
		int w = (int)pane.getPreferredSize().getWidth();
        int h = (int)pane.getPreferredSize().getHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(w, h, type);
        Graphics2D g2 = image.createGraphics();
        
        for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				g2.setColor(labels[i][j].getBackground());
				g2.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
			}
		}
        g2.dispose();
		
		return image;
	}
	
	public BufferedImage getBlankGrid(int pixelSize, boolean showGrid)
	{
		JLayeredPane pane = new JLayeredPane();
		pane.setSize(new Dimension(width * pixelSize, height * pixelSize));
		pane.setPreferredSize(new Dimension(width * pixelSize, height * pixelSize));
		
		JLabel[][] labels = new JLabel[width][height];
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				JLabel l = new JLabel("");
				l.setOpaque(true);
				if (showGrid)
				{
					l.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
				}
				l.setPreferredSize(new Dimension(pixelSize, pixelSize));
				l.setBounds(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
				l.setBackground(Color.white);
				
				pane.add(l);
				labels[i][j] = l;
			}
		}
		
		int w = (int)pane.getPreferredSize().getWidth();
        int h = (int)pane.getPreferredSize().getHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(w, h, type);
        Graphics2D g2 = image.createGraphics();
        
        for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				g2.setColor(labels[i][j].getBackground());
				g2.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
			}
		}
        g2.dispose();
		
		return image;
	}

	public PixelColor[][] getPixelColorArray() {
		return pixelColorArray;
	}

	public void setPixelColorArray(PixelColor[][] pixelColorArray) {
		this.pixelColorArray = pixelColorArray;
	}
	
	public void resetPixelColorArray(PixelColor systemColour, List<PixelColor> colours, PixelColor[][] array)
	{
		pixelColorArray = array;
		
		columnCounts = new int[width];
		rowCounts = new int[height];
		columnSizes = new int[width];
		rowSizes = new int[height];
		columnColours = new int[width];
		rowColours = new int[height];
		columnColourSelection = new PixelColor[height][];
		rowColourSelection = new PixelColor[height][];
		
		//Calc col counts
		for (int i = 0; i < width; i++)
		{
			int colScore = 0;
			int colSize = 0;
			int colColours = 0;
			List<PixelColor> known = new ArrayList<PixelColor>();
			for (int j = 0; j < height; j++)
			{
				if (pixelColours[i][j].getRGB() == systemColour.getColor().getRGB())
				{
					pixelColorArray[i][j] = systemColour;
				}
				else
				{
					colSize++;
				}
				
				for (PixelColor pc : colours)
				{
					if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
					{
						colScore += pc.getId();
						if (!known.contains(pc) && pixelColours[i][j].getRGB() != systemColour.getColor().getRGB())
						{
							colColours++;
							known.add(pc);
						}
					}
				}
				
				PixelColor[] values = new PixelColor[known.size()];
				for (int k = 0; k < known.size(); k++)
				{
					values[k] = known.get(k);
				}
				columnColourSelection[i] = values;
				
				columnCounts[i] = colScore;
				columnSizes[i] = colSize;
				columnColours[i] = colColours;
			}
		}
		
		//calc row scores
		for (int j = 0; j < height; j++)
		{
			int rowScore = 0;
			int rowSize = 0;
			int rColours = 0;
			List<PixelColor> known = new ArrayList<PixelColor>();
			for (int i = 0; i < width; i++)
			{				
				if (pixelColours[i][j].getRGB() == systemColour.getColor().getRGB())
				{
					pixelColorArray[i][j] = systemColour;
				}
				else
				{
					rowSize++;
				}
				
				for (PixelColor pc : colours)
				{
					if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
					{
						rowScore += pc.getId();
						if (!known.contains(pc) && pixelColours[i][j].getRGB() != systemColour.getColor().getRGB())
						{
							rColours++;
							known.add(pc);
						}
					}
				}
				
				PixelColor[] values = new PixelColor[known.size()];
				for (int k = 0; k < known.size(); k++)
				{
					values[k] = known.get(k);
				}
				rowColourSelection[j] = values;
				
				rowCounts[j] = rowScore;
				rowSizes[j] = rowSize;
				rowColours[j] = rColours;
			}
		}
	}

	public int[] getColumnCounts() {
		return columnCounts;
	}

	public void setColumnCounts(int[] columnCounts) {
		this.columnCounts = columnCounts;
	}

	public int[] getRowCounts() {
		return rowCounts;
	}

	public void setRowCounts(int[] rowCounts) {
		this.rowCounts = rowCounts;
	}

	public int[] getRowSizes() {
		return rowSizes;
	}

	public void setRowSizes(int[] rowSizes) {
		this.rowSizes = rowSizes;
	}

	public int[] getColumnSizes() {
		return columnSizes;
	}

	public void setColumnSizes(int[] columnSizes) {
		this.columnSizes = columnSizes;
	}

	public int[] getColumnColours() {
		return columnColours;
	}

	public void setColumnColours(int[] columnColours) {
		this.columnColours = columnColours;
	}

	public int[] getRowColours() {
		return rowColours;
	}

	public void setRowColours(int[] rowColours) {
		this.rowColours = rowColours;
	}
	
	public PixelPicture buildSolvedPixelPicture(PixelColor systemColour)
	{
		PixelPicture pp = new PixelPicture(width, height);
		
		pp.setName(name + " Solved");
		
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				if (pixelColorArray[i][j] != null)
				{
					pp.getPixelColours()[i][j] = pixelColorArray[i][j].getColor();
				}
				else
				{
					pp.getPixelColours()[i][j] = systemColour.getColor();
				}
			}
		}
		
		return pp;
	}

	public PixelColor[][] getColumnColourSelection() {
		return columnColourSelection;
	}

	public void setColumnColourSelection(PixelColor[][] columnColourSelection) {
		this.columnColourSelection = columnColourSelection;
	}

	public PixelColor[][] getRowColourSelection() {
		return rowColourSelection;
	}

	public void setRowColourSelection(PixelColor[][] rowColourSelection) {
		this.rowColourSelection = rowColourSelection;
	}

	public boolean isColourInRow(PixelColor pc, int row)
	{
		for (int i = 0; i < width; i++)
		{
			if (pixelColours[i][row].getRGB() == pc.getColor().getRGB())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isColourInColumn(PixelColor pc, int col)
	{
		for (int i = 0; i < height; i++)
		{
			if (pixelColours[col][i].getRGB() == pc.getColor().getRGB())
			{
				return true;
			}
		}
		return false;
	}
	
	public List<PixelColor> getPixelColourList(PixelColor systemColor)
	{
		List<PixelColor> colours = new ArrayList<PixelColor>();
		List<Color> known = new ArrayList<Color>();
		
		int id = 1;
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				if (pixelColours[i][j].getRGB() != systemColor.getColor().getRGB())
				{
					PixelColor pc = new PixelColor(id, pixelColours[i][j]);
					if (!known.contains(pixelColours[i][j]))
					{
						known.add(pixelColours[i][j]);
						colours.add(pc);
						id++;
					}
				}
			}
		}
		
		Collections.sort(colours, new SortByPixelColor());
		
		return colours;
	}

	public PixelColorCount[][] getColumnNonogram() {
		return columnNonogram;
	}

	public void setColumnNonogram(PixelColorCount[][] columnNonogram) {
		this.columnNonogram = columnNonogram;
	}

	public PixelColorCount[][] getRowNonogram() {
		return rowNonogram;
	}

	public void setRowNonogram(PixelColorCount[][] rowNonogram) {
		this.rowNonogram = rowNonogram;
	}
	
	public void calculateNonogram(PixelColor systemColour, List<PixelColor> colours)
	{
		columnNonogram = new PixelColorCount[width][];
		rowNonogram = new PixelColorCount[height][];
		
		PixelColor previousColour = null;
		
		//Calc col counts
		for (int i = 0; i < width; i++)
		{
			int c = 0;
			previousColour = null;
			
			List<PixelColorCount> countList = new ArrayList<PixelColorCount>();
			for (int j = 0; j < height; j++)
			{
				if (pixelColours[i][j].getRGB() != systemColour.getColor().getRGB())
				{
					
					
					PixelColor colour = null;
					for (PixelColor pc : colours)
					{
						if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
						{
							colour = pc;
						}
					}
					
					if (colour.getColor().getRGB() != systemColour.getColor().getRGB())
					{
						if (previousColour != null && !previousColour.equals(colour))
						{
							countList.add(new PixelColorCount(previousColour, c));
							c = 0;
							
						}
						
						
					}
					
					previousColour = colour;
					
					c++;
				}
				else if (pixelColours[i][j].getRGB() == systemColour.getColor().getRGB() && c > 0)
				{
					countList.add(new PixelColorCount(previousColour, c));
					c = 0;
					
					PixelColor colour = null;
					for (PixelColor pc : colours)
					{
						if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
						{
							colour = pc;
						}
					}
					previousColour = colour;
				}
			}
			
			if (c > 0)
			{
				countList.add(new PixelColorCount(previousColour, c));
			}
			
			columnNonogram[i] = new PixelColorCount[countList.size()];
			int k = 0;
			for (PixelColorCount pc : countList)
			{
				columnNonogram[i][k] = pc;
				k++;
			}
		}
		
		previousColour = null;
		
		//calc row scores
		for (int j = 0; j < height; j++)
		{
			int c = 0;
			previousColour = null;
			
			List<PixelColorCount> countList = new ArrayList<PixelColorCount>();
			for (int i = 0; i < width; i++)
			{				
				if (pixelColours[i][j].getRGB() != systemColour.getColor().getRGB())
				{
					
					
					PixelColor colour = null;
					for (PixelColor pc : colours)
					{
						if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
						{
							colour = pc;
							break;
						}
					}
					
					if (colour.getColor().getRGB() != systemColour.getColor().getRGB())
					{
						if (previousColour != null && !previousColour.equals(colour))
						{
							countList.add(new PixelColorCount(previousColour, c));
							c = 0;
						}
					}
					
					previousColour = colour;
					
					c++;
				}
				else if (pixelColours[i][j].getRGB() == systemColour.getColor().getRGB() && c > 0)
				{
					countList.add(new PixelColorCount(previousColour, c));
					c = 0;
					
					PixelColor colour = null;
					for (PixelColor pc : colours)
					{
						if (pixelColours[i][j].getRGB() == pc.getColor().getRGB())
						{
							colour = pc;
						}
					}
					previousColour = null;
				}
			}
			
			if (c > 0)
			{
				countList.add(new PixelColorCount(previousColour, c));
			}
			
			rowNonogram[j] = new PixelColorCount[countList.size()];
			int k = 0;
			for (PixelColorCount pc : countList)
			{
				rowNonogram[j][k] = pc;
				k++;
			}
		}
	}
	
	public void printPixelPictureNonogramValues()
	{
		System.out.println("Pixel Picture: " + this.getName());
		
		String str = "Column Counts: ";
		for (int i = 0; i < this.getColumnNonogram().length; i++)
		{
			int total = 0;
			str += "[";
			for (int j = 0; j < this.getColumnNonogram()[i].length; j++)
			{
				str += this.getColumnNonogram()[i][j] + ",";
				total += this.getColumnNonogram()[i][j].getCount();
			}
			str += "Total="+total+"], ";
		}
		System.out.println(str);
		
		
		str = "Row Counts: ";
		for (int i = 0; i < this.getRowNonogram().length; i++)
		{
			int total = 0;
			str += "[";
			for (int j = 0; j < this.getRowNonogram()[i].length; j++)
			{
				str += this.getRowNonogram()[i][j] + ",";
				total += this.getRowNonogram()[i][j].getCount();
			}
			str += "Total="+total+"], ";
		}
		System.out.println(str);
	}
	
	public boolean isPictureNonogramSolvable(PixelColor systemColour, List<PixelColor> colours)
	{
		PixelPicture pp = this;
		
		pp.calculateNonogram(systemColour, colours);
		pp.printPixelPictureNonogramValues();
		
		pp.setPixelColorArray(new PixelColor[pp.getWidth()][pp.getHeight()]);
		
		int iterations = 0;
		boolean done = false;
		while (!done)
		{
			iterations++;
			System.out.println("Iteration: " + iterations);
			
			boolean pixelMatched = false;
			
			// Check columns
			for (int i = 0; i < width; i++)
			{
				//Check for complete sets
				int total = 0;
				for (int k = 0; k < columnNonogram[i].length; k++)
				{
					total += columnNonogram[i][k].getCount();
				}
				if (total == height)
				{
					int currentValue = 0;
					int currentColour = 0;
					for (int j = 0; j < height; j++)
					{
						if (pp.getPixelColorArray()[i][j] == null)
						{
							pixelMatched = true;
						}
						
						pp.getPixelColorArray()[i][j] = columnNonogram[i][currentColour].getPixelColour();
						currentValue++;
						if (currentValue >= columnNonogram[i][currentColour].getCount())
						{
							currentValue = 0;
							currentColour++;
						}
						
					}
				}
				
				//Resolve single regions when region size matches number of rows with colour
				for (PixelColor pc : colours)
				{
					if (getRegionsInColumn(pc, i).size() == 1)
					{
						int count = 0;
						for (int k = 0; k < height; k++)
						{
							if (isColourInRow(pc, k))
							{
								count++;
							}
						}
						if (count == getRegionsInColumn(pc, i).get(0).getCount())
						{
							for (int j = 0; j < height; j++)
							{
								if (isColourInRow(pc, j))
								{
									if (pp.getPixelColorArray()[i][j] == null)
									{
										pixelMatched = true;
									}
									
									pp.getPixelColorArray()[i][j] = pc;
								}
							}
						}
					}					
				}
				
				for (int j = 0; j < height; j++)
				{
					
				}
				
			
			}
			
			// Check rows
			for (int j = 0; j < height; j++)
			{
				//Check for complete sets
				int total = 0;
				for (int k = 0; k < rowNonogram[j].length; k++)
				{
					total += rowNonogram[j][k].getCount();
				}
				if (total == width)
				{
					int currentValue = 0;
					int currentColour = 0;
					for (int i = 0; i < width; i++)
					{
						if (pp.getPixelColorArray()[i][j] == null)
						{
							pixelMatched = true;
						}
						
						pp.getPixelColorArray()[i][j] = rowNonogram[j][currentColour].getPixelColour();
						currentValue++;
						if (currentValue >= rowNonogram[j][currentColour].getCount())
						{
							currentValue = 0;
							currentColour++;
						}
					}
				}
				
				//Resolve single regions when region size matches number of columns with colour
				for (PixelColor pc : colours)
				{
					if (getRegionsInRow(pc, j).size() == 1)
					{
						int count = 0;
						for (int k = 0; k < width; k++)
						{
							if (isColourInColumn(pc, k))
							{
								count++;
							}
						}
						if (count == getRegionsInRow(pc, j).get(0).getCount())
						{
							for (int i = 0; i < width; i++)
							{
								if (isColourInColumn(pc, i))
								{
									if (pp.getPixelColorArray()[i][j] == null)
									{
										pixelMatched = true;
									}
									
									pp.getPixelColorArray()[i][j] = pc;
								}
							}
						}
					}					
				}
				
				//Check for single colours against columns
				for (int i = 0; i < width; i++)
				{
					PixelColor pc = getPixelColour(colours, pixelColours[i][j]);
					
					if (getColourInRowCount(pc, j) == 1)
					{
						//go through each column and see if colour exists
						//if only one match found, then you can colour it
						//need to expand to cover regions as could resolve single region here
						
						boolean found1 = false;
						boolean found2 = false;
						int id = -1;
						for (int k = 0; k < height; k++)
						{
							if (getColourInColumnCount(pc, k) > 0)
							{
								id = k;
								if (found1 == true) found2 = true;
								found1 = true;
							}
						}
						if (found1 && !found2)
						{
							if (pp.getPixelColorArray()[i][id] == null)
							{
								pixelMatched = true;
							}
							pp.getPixelColorArray()[i][id] = pc;
						}
					}
					
					
				}
				
				
			}
			
			if (!pixelMatched)
			{
				done = true;
			}
		}
		
		boolean solved = true;
		//Print image
		for (int j = 0; j < height; j++)
		{
			String str = "";
			for (int i = 0; i < width; i++)
			{
				if (pp.getPixelColorArray()[i][j] != null && pp.getPixelColorArray()[i][j].getColor().getRGB() == systemColour.getColor().getRGB())
				{
					str += "0,"; // System Colour
				}
				else if (pp.getPixelColorArray()[i][j] != null)
				{
					str += ""+pp.getPixelColorArray()[i][j].getId()+","; //Matched
				}
				else
				{
					str += "-,"; //Not Matched
					solved = false;
				}
			}
			System.out.println(str);
		}
		
		//Add solved to app for confirmation - REMOVE IN FINAL
		PixelBookMaker.maker.getImageListModel().addElement(pp.buildSolvedPixelPicture(systemColour));
		
		return solved;
	}
	
	public int getColourInRowCount(PixelColor pc, int row)
	{
		int t = 0;
		for (int i = 0; i < width; i++)
		{
			if (pc != null && pixelColours[i][row].getRGB() == pc.getColor().getRGB())
			{
				t++;
			}
		}
		return t;
	}
	
	public int getColourInColumnCount(PixelColor pc, int col)
	{
		int t = 0;
		for (int i = 0; i < height; i++)
		{
			if (pc != null && pixelColours[col][i].getRGB() == pc.getColor().getRGB())
			{
				t++;
			}
		}
		return t;
	}
	
	public List<PixelColorCount> getRegionsInRow(PixelColor pc, int row)
	{
		List<PixelColorCount> cols = new ArrayList<PixelColorCount>();
		if (pc != null)
		{
			for (PixelColorCount c : rowNonogram[row])
			{
				if (c.getPixelColour().equals(pc))
				{
					cols.add(c);
				}
			}
		}
		return cols;
	}
	
	public List<PixelColorCount> getRegionsInColumn(PixelColor pc, int col)
	{
		List<PixelColorCount> cols = new ArrayList<PixelColorCount>();
		if (pc != null)
		{
			for (PixelColorCount c : columnNonogram[col])
			{
				if (c.getPixelColour().equals(pc))
				{
					cols.add(c);
				}
			}
		}
		return cols;
	}
	
	public PixelColor getPixelColour(List<PixelColor> colours, Color color)
	{
		for (PixelColor c : colours)
		{
			if (c.getColor().getRGB() == color.getRGB())
			{
				return c;
			}
		}
		return null;
	}
}
