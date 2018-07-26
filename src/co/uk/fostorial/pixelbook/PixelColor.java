package co.uk.fostorial.pixelbook;

import java.awt.Color;

public class PixelColor
{
	private int id;
	private Color color;
	
	public PixelColor(int id, Color color)
	{
		this.setId(id);
		this.setColor(color);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public String toString()
	{
		return "" + id + " : " + color;
	}
}