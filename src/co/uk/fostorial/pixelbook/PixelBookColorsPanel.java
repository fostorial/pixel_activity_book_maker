package co.uk.fostorial.pixelbook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class PixelBookColorsPanel extends AbstractColorChooserPanel implements ActionListener {

	private static final long serialVersionUID = 1659550811053648457L;

	private PixelBook pixelBook;
	private JLabel[][] pixelLabels;
	private JLabel[] labels;
	
	public PixelBookColorsPanel(PixelBook pixelBook, JLabel[][] pixelLabels) {
		this.setPixelBook(pixelBook);
		this.pixelLabels = pixelLabels;
	}

	@Override
	protected void buildChooser() {
		/* Get list of used colours */
		List<Color> colourList = new ArrayList<Color>();
		for (PixelPicture pic : pixelBook.getPictures())
		{
			for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					if (!colourList.contains(pic.getPixelColours()[i][j]))
					{
						colourList.add(pic.getPixelColours()[i][j]);
					}
				}
			}
		}
		for (int i = 0; i < pixelBook.getPixelsWide(); i++)
		{
			for (int j = 0; j < pixelBook.getPixelsHigh(); j++)
			{
				if (!colourList.contains(pixelLabels[i][j].getBackground()))
				{
					colourList.add(pixelLabels[i][j].getBackground());
				}
			}
		}
		Collections.sort(colourList, new SortByColor());
		
		/* Build buttons from colour list */
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 30));
		setLabels(new JLabel[colourList.size()]);
		int i = 0;
		for (Color c : colourList)
		{
			JLabel l = new JLabel("");
			l.setOpaque(true);
			l.setSize(new Dimension(15, 15));
			l.setPreferredSize(new Dimension(15, 15));
			l.setBackground(c);
			l.setBorder(BorderFactory.createBevelBorder(1));
			
			l.addMouseListener ( new MouseAdapter ()
            {
                public void mousePressed ( MouseEvent e )
                {
                		JLabel label = (JLabel)e.getSource();
                		getColorSelectionModel().setSelectedColor(label.getBackground());
                }
            } );
			
			panel.add(l);
			
			i++;
		}
		
		for(int j = i; j < (8*30); j++)
		{
			JLabel l = new JLabel("");
			l.setOpaque(true);
			l.setSize(new Dimension(15, 15));
			l.setPreferredSize(new Dimension(15, 15));
			l.setBackground(Color.white);
			l.setBorder(BorderFactory.createBevelBorder(1));
			
			l.addMouseListener ( new MouseAdapter ()
            {
                public void mousePressed ( MouseEvent e )
                {
                		JLabel label = (JLabel)e.getSource();
                		getColorSelectionModel().setSelectedColor(label.getBackground());
                }
            } );
			
			panel.add(l);
		}
		
		this.add(panel, BorderLayout.CENTER);
	}

	@Override
	public String getDisplayName() {
		return "Book Colours";
	}

	@Override
	public Icon getLargeDisplayIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getSmallDisplayIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChooser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public PixelBook getPixelBook() {
		return pixelBook;
	}

	public void setPixelBook(PixelBook pixelBook) {
		this.pixelBook = pixelBook;
	}
	
	public JLabel[] getLabels() {
		return labels;
	}

	public void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

	public static class SortByColor implements Comparator<Color>{

	    public int compare(Color o1, Color o2) {
	        return o1.getRGB() - o2.getRGB();
	    }
	}

	public static List<Color> getColours(PixelBook pixelBook)
	{
		List<Color> colourList = new ArrayList<Color>();
		for (PixelPicture pic : pixelBook.getPictures())
		{
			for (int i = 0; i < pic.getWidth(); i++)
			{
				for (int j = 0; j < pic.getHeight(); j++)
				{
					if (!colourList.contains(pic.getPixelColours()[i][j]))
					{
						colourList.add(pic.getPixelColours()[i][j]);
					}
				}
			}
		}
		Collections.sort(colourList, new SortByColor());
		return colourList;
	}
}
