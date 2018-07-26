package co.uk.fostorial.pixelbook;

public class PixelColorCount {

	private PixelColor pixelColour;
	private int count;
	
	public PixelColorCount(PixelColor c, int count)
	{
		this.pixelColour = c;
		this.count = count;
	}
	
	public PixelColor getPixelColour() {
		return pixelColour;
	}
	public void setPixelColour(PixelColor pixelColour) {
		this.pixelColour = pixelColour;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString()
	{
		return "[ID="+pixelColour.getId()+",Count="+count+"]";
	}
}
