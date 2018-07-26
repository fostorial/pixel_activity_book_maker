package co.uk.fostorial.pixelbook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PixelBook {
	
	public static final String TYPE_STICKER_BOOK = "Sticker Book";
	public static final String TYPE_COLOURING_BOOK = "Colouring Book";
	public static final String TYPE_BW_COLOURING_BOOK = "Black and White Colouring Book";
	public static final String TYPE_NUMBER_COLOURING_BOOK = "Number Colouring Book";
	public static final String TYPE_NUMBER_COLOURING_SINGLE_PAGE_BOOK = "Number Colouring Book (Single Page)";
	
	public static final String COVER_MOSAIC = "Mosaic";
	public static final String COVER_BORDER = "Border";
	public static final String COVER_STRIP = "Strip";
	
	private String name = "New Pixel Book";
	private int pixelSize = 25;
	private int pixelsWide = 16;
	private int pixelsHigh = 16;
	
	private int coverImagePixelSize = 2;
	private int coverFontSize = 100;
	private int imageBufferSize = 2;
	
	private String bookType = TYPE_STICKER_BOOK;
	private String coverType = COVER_MOSAIC;
	
	private List<PixelPicture> pictures = new ArrayList<PixelPicture>();
	
	private List<Color> solvableColours = null;
	private List<PixelPicture> solvablePictures = null;

	public PixelBook() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
	}

	public int getPixelsWide() {
		return pixelsWide;
	}

	public void setPixelsWide(int pixelsWide) {
		this.pixelsWide = pixelsWide;
	}

	public int getPixelsHigh() {
		return pixelsHigh;
	}

	public void setPixelsHigh(int pixelsHigh) {
		this.pixelsHigh = pixelsHigh;
	}

	public List<PixelPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<PixelPicture> pictures) {
		this.pictures = pictures;
	}

	public PixelPicture addNewPixelPicture()
	{
		PixelPicture pic = new PixelPicture(pixelsWide, pixelsHigh);
		pictures.add(pic);
		return pic;
	}
	
	public static PixelBook getPreviewBook(PixelBook book)
	{
		PixelBook b = new PixelBook();
		b.setName(book.getName());
		b.setPixelsHigh(book.getPixelsHigh());
		b.setPixelsWide(book.getPixelsWide());
		b.setPixelSize(book.getPixelSize());
		
		return b;
	}

	public int getCoverImagePixelSize() {
		return coverImagePixelSize;
	}

	public void setCoverImagePixelSize(int coverImagePixelSize) {
		this.coverImagePixelSize = coverImagePixelSize;
	}

	public int getCoverFontSize() {
		return coverFontSize;
	}

	public void setCoverFontSize(int coverFontSize) {
		this.coverFontSize = coverFontSize;
	}

	public int getImageBufferSize() {
		return imageBufferSize;
	}

	public void setImageBufferSize(int imageBufferSize) {
		this.imageBufferSize = imageBufferSize;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
	public boolean isStickerBook()
	{
		if (bookType != null && bookType.equals(TYPE_STICKER_BOOK))
		{
			return true;
		}
		return false;
	}
	
	public boolean isColouringBook()
	{
		if (bookType != null && bookType.equals(TYPE_COLOURING_BOOK))
		{
			return true;
		}
		return false;
	}
	
	public boolean isBWColouringBook()
	{
		if (bookType != null && bookType.equals(TYPE_BW_COLOURING_BOOK))
		{
			return true;
		}
		return false;
	}
	
	public boolean isNumberColouringBook()
	{
		if (bookType != null && bookType.equals(TYPE_NUMBER_COLOURING_BOOK))
		{
			return true;
		}
		return false;
	}

	public boolean isNumberColouringSinglePageBook()
	{
		if (bookType != null && bookType.equals(TYPE_NUMBER_COLOURING_SINGLE_PAGE_BOOK))
		{
			return true;
		}
		return false;
	}

	public String getCoverType() {
		return coverType;
	}

	public void setCoverType(String coverType) {
		this.coverType = coverType;
	}
	
	public boolean isCoverMosaic()
	{
		if (coverType != null && coverType.equals(COVER_MOSAIC))
		{
			return true;
		}
		return false;
	}
	
	public boolean isCoverBorder()
	{
		if (coverType != null && coverType.equals(COVER_BORDER))
		{
			return true;
		}
		return false;
	}
	
	public boolean isCoverStrip()
	{
		if (coverType != null && coverType.equals(COVER_STRIP))
		{
			return true;
		}
		return false;
	}
	
	public static class SortByPixelColor implements Comparator<PixelColor>{

	    public int compare(PixelColor o1, PixelColor o2) {
	        return o1.getId() - o2.getId();
	    }
	}
	
	public boolean isAppSolvable()
	{
		System.out.println("Determining if book is solvable...");
		
		Color systemColor = Color.white;
		PixelColor systemPixelColor = null;
		int id = 1;
		List<PixelColor> colours = new ArrayList<PixelColor>();
		for (Color c : PixelBookColorsPanel.getColours(this))
		{
			if (c.getRGB() == systemColor.getRGB())
			{
				systemPixelColor = new PixelColor(0, c);
				colours.add(systemPixelColor);
			}
			else
			{
				colours.add(new PixelColor(id, c));
				id++;
			}
		}
		Collections.sort(colours, new SortByPixelColor());
		
		List<PixelPicture> pics = new ArrayList<PixelPicture>();
		pics.addAll(pictures);
		
		boolean solved = true;
		boolean done = false;
		while (!done)
		{
			//Check each picture
			for (PixelPicture pp : pics)
			{
				boolean picSolved = pp.isPictureNonogramSolvable(systemPixelColor, pp.getPixelColourList(systemPixelColor));
				if (picSolved == false)
				{
					solved = false;
				}
			}
			
			done = true;
		}
		
		if (solved == false)
		{
			System.out.println("Error! Book is not solvable!");
		}
		
		return solved;
	}
	
	public boolean isAppSolvableCustom()
	{
		System.out.println("Determining if book is solvable...");
		
		Color systemColor = Color.white;
		PixelColor systemPixelColor = null;
		int id = 1;
		List<PixelColor> colours = new ArrayList<PixelColor>();
		for (Color c : PixelBookColorsPanel.getColours(this))
		{
			if (c.getRGB() == systemColor.getRGB())
			{
				systemPixelColor = new PixelColor(0, c);
				colours.add(systemPixelColor);
			}
			else
			{
				colours.add(new PixelColor(id, c));
				id++;
			}
		}
		Collections.sort(colours, new SortByPixelColor());
		
		HashMap<PixelColor, Integer> colourMap = new HashMap<PixelColor, Integer>();
		for (PixelColor c : colours)
		{
			int count = getPixelColorCount(c);
			colourMap.put(c, new Integer(count));
			System.out.println("Pixel Colour: " + c.getId() + " : " + c.getColor() + " : Count=" + count);
		}
		
		List<PixelPicture> pics = new ArrayList<PixelPicture>();
		pics.addAll(pictures);
		//Collections.shuffle(pics);
		
//		for (PixelPicture pp : pics)
//		{
//			pp.resetPixelColorArray(systemPixelColor, colours);
//		}
		
		boolean solved = true;
		boolean done = false;
		while (!done)
		{
			//Check each picture
			for (PixelPicture pp : pics)
			{
//				PixelColor highest = getHighestRemaining(colours, colourMap);
//				PixelColor lowest = getLowestRemaining(colours, colourMap);
//				
//				//Check Columns
//				for (int i = 0; i < pixelsWide; i++)
//				{
//					
//				}
				
				//boolean picSolved = isPictureSolvable(pp, systemPixelColor, colours);
				boolean picSolved = isPictureSolvable(pp, systemPixelColor, pp.getPixelColourList(systemPixelColor));
				if (picSolved == false)
				{
					solved = false;
				}
			}
			
			done = true;
		}
		
		if (solved == false)
		{
			System.out.println("Error! Book is not solvable!");
		}
		
		return solved;
	}

	public List<Color> getSolvableColours() {
		return solvableColours;
	}

	public void setSolvableColours(List<Color> solvableColours) {
		this.solvableColours = solvableColours;
	}
	
	public int getPixelColorCount(PixelColor c)
	{
		int val = 0;
		for (PixelPicture pp : pictures)
		{
			for (int i = 0; i < pp.getWidth(); i++)
			{
				for (int j = 0; j < pp.getHeight(); j++)
				{
					if (pp.getPixelColours()[i][j].getRGB() == c.getColor().getRGB())
					{
						val++;
					}
				}
			}
		}
		
		return val;
	}
	
	public PixelColor getHighestRemaining(List<PixelColor> colours, HashMap<PixelColor, Integer> hashmap)
	{
		PixelColor col = null;
		int highest = 0;
		for (PixelColor c : colours)
		{
			Integer i = hashmap.get(c);
			if (i.intValue() > 0 && i > highest)
			{
				col = c;
				highest = i.intValue();
			}
		}
		return col;
	}
	
	public PixelColor getLowestRemaining(List<PixelColor> colours, HashMap<PixelColor, Integer> hashmap)
	{
		PixelColor col = null;
		int lowest = Integer.MAX_VALUE;
		for (PixelColor c : colours)
		{
			Integer i = hashmap.get(c);
			if (i.intValue() > 0 && i < lowest)
			{
				col = c;
				lowest = i.intValue();
			}
		}
		return col;
	}
	
	private void printPixelPictureValues(PixelPicture pp)
	{
		System.out.println("Pixel Picture: " + pp.getName());
		
		String str = "Column Counts: ";
		for (int i = 0; i < pp.getColumnCounts().length; i++)
		{
			str += "" + pp.getColumnCounts()[i] + ", ";
		}
		System.out.println(str);
		str = "Column Sizes: ";
		for (int i = 0; i < pp.getColumnSizes().length; i++)
		{
			str += "" + pp.getColumnSizes()[i] + ", ";
		}
		System.out.println(str);
		str = "Column Colours: ";
		for (int i = 0; i < pp.getColumnColours().length; i++)
		{
			str += "" + pp.getColumnColours()[i] + ", ";
		}
		System.out.println(str);
		str = "Column Colour Selection: ";
		for (int i = 0; i < pp.getColumnColourSelection().length; i++)
		{
			str += "[";
			for (int j = 0; j < pp.getColumnColourSelection()[i].length; j++)
			{
				str += pp.getColumnColourSelection()[i][j] + ",";
			}
			str += "], ";
		}
		System.out.println(str);
		
		str = "Row Counts: ";
		for (int i = 0; i < pp.getRowCounts().length; i++)
		{
			str += "" + pp.getRowCounts()[i] + ", ";
		}
		System.out.println(str);
		str = "Row Sizes: ";
		for (int i = 0; i < pp.getRowSizes().length; i++)
		{
			str += "" + pp.getRowSizes()[i] + ", ";
		}
		System.out.println(str);
		str = "Row Colours: ";
		for (int i = 0; i < pp.getRowColours().length; i++)
		{
			str += "" + pp.getRowColours()[i] + ", ";
		}
		System.out.println(str);
		str = "Row Colour Selection: ";
		for (int i = 0; i < pp.getRowColourSelection().length; i++)
		{
			str += "[";
			for (int j = 0; j < pp.getRowColourSelection()[i].length; j++)
			{
				str += pp.getRowColourSelection()[i][j] + ",";
			}
			str += "], ";
		}
		System.out.println(str);
	}
	
	private boolean isPictureSolvable(PixelPicture pp, PixelColor systemColour, List<PixelColor> colours)
	{
		pp.resetPixelColorArray(systemColour, colours, new PixelColor[pp.getWidth()][pp.getHeight()]);
		
		printPixelPictureValues(pp);
		
		int iterations = 0;
		boolean done = false;
		while (!done)
		{
			iterations++;
			System.out.println("Iteration: " + iterations);
			
			boolean pixelMatched = false;
			
			// Check columns
			for (int i = 0; i < pixelsWide; i++)
			{
				PixelColor matchedColour = null;
				
				if (pp.getColumnColours()[i] == 1)
				{
					int id = pp.getColumnCounts()[i] / pp.getColumnSizes()[i];
					for (PixelColor pc : colours)
					{
						if (pc.getId() == id)
						{
							matchedColour = pc;
						}
					}
				}
				
				
				
				for (int j = 0; j < pixelsHigh; j++)
				{
					if (matchedColour != null && pp.getPixelColorArray()[i][j] == null)
					{
						pp.getPixelColorArray()[i][j] = matchedColour;
						pixelMatched = true;
					}
				}
				
				for (PixelColor pc : pp.getColumnColourSelection()[i])
				{
					int pos = -1;
					for (int j = 0; j < pixelsHigh; j++)
					{
						if (matchedColour != null && pp.getPixelColorArray()[i][j] == null)
						{
							pp.getPixelColorArray()[i][j] = matchedColour;
						}
					}
				}
				
				pp.resetPixelColorArray(systemColour, colours, pp.getPixelColorArray());
			}
			
			// Check rows
			for (int j = 0; j < pixelsHigh; j++)
			{
				PixelColor matchedColour = null;
				
				if (pp.getRowColours()[j] == 1)
				{
					int id = pp.getRowCounts()[j] / pp.getRowSizes()[j];
					for (PixelColor pc : colours)
					{
						if (pc.getId() == id)
						{
							matchedColour = pc;
						}
					}
				}
				
				for (int i = 0; i < pixelsWide; i++)
				{
					if (matchedColour != null && pp.getPixelColorArray()[i][j] == null)
					{
						pp.getPixelColorArray()[i][j] = matchedColour;
						pixelMatched = true;
					}
				}
				
				pp.resetPixelColorArray(systemColour, colours, pp.getPixelColorArray());
			}
			
			if (!pixelMatched)
			{
				done = true;
			}
		}
		
		boolean solved = true;
		//Print image
		for (int j = 0; j < pixelsHigh; j++)
		{
			String str = "";
			for (int i = 0; i < pixelsWide; i++)
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
	
	
}
