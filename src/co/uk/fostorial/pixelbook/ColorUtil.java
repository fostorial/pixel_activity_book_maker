package co.uk.fostorial.pixelbook;

import java.awt.Color;
import java.util.HashMap;

public final class ColorUtil {

    static private HashMap<String,Color> KNOWNCOLORS = new HashMap<String,Color>();

    static {
        KNOWNCOLORS.put("Black",     new Color(0,0,0));
        KNOWNCOLORS.put("White",     new Color(255,255,255));
        KNOWNCOLORS.put("Cyan",      Color.cyan);
        KNOWNCOLORS.put("Peach",    new Color(255, 204, 153));
        
        KNOWNCOLORS.put("Light Brown",    new Color(204, 153, 0));
        KNOWNCOLORS.put("Brown",    new Color(153, 102, 0));
        KNOWNCOLORS.put("Dark Brown",    new Color(87, 63, 15));
        KNOWNCOLORS.put("Light Grey",    new Color(204, 204, 204));
        KNOWNCOLORS.put("Grey",    new Color(153, 153, 153));
        KNOWNCOLORS.put("Dark Grey",    new Color(102, 102, 102));
        KNOWNCOLORS.put("Dark Grey",    new Color(53, 53, 53));
        
        /* Pure Reds */
        KNOWNCOLORS.put("Red",    new Color(255, 0, 0));
        KNOWNCOLORS.put("Red",    new Color(173, 0, 0));
        KNOWNCOLORS.put("Dark Red",    new Color(98, 0, 0));
        KNOWNCOLORS.put("Dark Red",    new Color(54, 0, 0));
        KNOWNCOLORS.put("Light Pink",    new Color(255, 110, 110));
        KNOWNCOLORS.put("Pink",    new Color(173, 110, 110));
        KNOWNCOLORS.put("Dark Pink",    new Color(98, 110, 110));
        KNOWNCOLORS.put("Dark Pink",    new Color(54, 110, 110));
        
        /* Pure Greens */
        KNOWNCOLORS.put("Light Green",    new Color(0, 255, 0));
        KNOWNCOLORS.put("Green",    new Color(0, 184, 0));
        KNOWNCOLORS.put("Dark Green",    new Color(0, 101, 0));
        KNOWNCOLORS.put("Dark Green",    new Color(0, 57, 0));
        KNOWNCOLORS.put("Light Green",    new Color(0, 255, 140));
        KNOWNCOLORS.put("Green",    new Color(0, 201, 140));
        KNOWNCOLORS.put("Dark Green",    new Color(0, 106, 140));
        
        /* Lime Greens */
        KNOWNCOLORS.put("Light Green",    new Color(170, 255, 0));
        KNOWNCOLORS.put("Green",    new Color(122, 184, 0));
        KNOWNCOLORS.put("Dark Green",    new Color(80, 121, 0));
        KNOWNCOLORS.put("Dark Green",    new Color(48, 71, 0));
        KNOWNCOLORS.put("Light Green",    new Color(208, 255, 113));
        KNOWNCOLORS.put("Green",    new Color(155, 184, 85));
        KNOWNCOLORS.put("Dark Green",    new Color(81, 99, 44));
        
        /* Pure Blues */
        KNOWNCOLORS.put("Light Blue",    new Color(0, 0, 255));
        KNOWNCOLORS.put("Blue",    new Color(0, 0, 199));
        KNOWNCOLORS.put("Dark Blue",    new Color(0, 0, 135));
        KNOWNCOLORS.put("Dark Blue",    new Color(0, 0, 80));
        KNOWNCOLORS.put("Light Blue",    new Color(124, 124, 255));
        KNOWNCOLORS.put("Blue",    new Color(87, 87, 255));
        KNOWNCOLORS.put("Dark Blue",    new Color(57, 57, 153));
        
        /* Pure Yellows */
        KNOWNCOLORS.put("Light Yellow",    new Color(255, 255, 0));
        KNOWNCOLORS.put("Yellow",    new Color(233, 233, 0));
        KNOWNCOLORS.put("Dark Yellow",    new Color(194, 194, 0));
        KNOWNCOLORS.put("Dark Yellow",    new Color(154, 154, 0));
        KNOWNCOLORS.put("Light Yellow",    new Color(255, 255, 128));
        KNOWNCOLORS.put("Yellow",    new Color(233, 233, 128));
        KNOWNCOLORS.put("Dark Yellow",    new Color(194, 194, 128));
        
        /* Pure Orange */
        KNOWNCOLORS.put("Light Orange",    new Color(255, 170, 43));
        KNOWNCOLORS.put("Orange",    new Color(235, 141, 0));
        KNOWNCOLORS.put("Dark Orange",    new Color(166, 99, 0));
        KNOWNCOLORS.put("Light Orange",    new Color(255, 202, 124));
        KNOWNCOLORS.put("Orange",    new Color(194, 143, 67));
        KNOWNCOLORS.put("Dark Orange",    new Color(142, 102, 42));
        
        /* Pure Purples */
        KNOWNCOLORS.put("Light Purple",    new Color(255, 0, 255));
        KNOWNCOLORS.put("Purple",    new Color(199, 114, 199));
        KNOWNCOLORS.put("Dark Purple",    new Color(101, 65, 101));
        KNOWNCOLORS.put("Dark Purple",    new Color(65, 0, 65));
        KNOWNCOLORS.put("Light Purple",    new Color(255, 147, 255));
        KNOWNCOLORS.put("Purple",    new Color(199, 0, 199));
        KNOWNCOLORS.put("Dark Purple",    new Color(101, 0, 101));
        
        /* Purples With More Blue */
        KNOWNCOLORS.put("Light Purple",    new Color(202, 147, 255));
        KNOWNCOLORS.put("Purple",    new Color(131, 0, 255));
        KNOWNCOLORS.put("Dark Purple",    new Color(82, 0, 159));
        KNOWNCOLORS.put("Dark Purple",    new Color(50, 0, 98));
        
    }


    static public String getBestColorName(Color c) {
        int dist, diff = Integer.MAX_VALUE;
        String colorName = toHex(c);
        Color c2;

        for (String name : KNOWNCOLORS.keySet()) {
            c2 = KNOWNCOLORS.get(name);
            dist = Math.abs(c.getRed() - c2.getRed())
                    + Math.abs(c.getGreen() - c2.getGreen()) 
                    + Math.abs(c.getBlue() - c2.getBlue())
                    + Math.abs(c.getAlpha() - c2.getAlpha());

            if (dist < diff) {
                diff = dist;
                colorName = name; 
            }
        }

        return colorName;
    }

    static public int getColorValue(Color c) {
        return (c.getAlpha() << 24) 
            | (c.getRed() << 16)
            | (c.getGreen() << 8) 
            | (c.getBlue()); 
    }

    static public String toHex(Color c) {
        StringBuilder b = new StringBuilder();
        if (c.getAlpha() < 255) {
            b.append(padLeft(Integer.toString(c.getAlpha(), 16), 2));
        }
        b.append(padLeft(Integer.toString(c.getRed(), 16), 2));
        b.append(padLeft(Integer.toString(c.getGreen(), 16), 2));
        b.append(padLeft(Integer.toString(c.getBlue(), 16), 2));

        return b.toString();
    }

    static private String padLeft(String s, int n) {
        return String.format("%1$#" + n + "s", s).replace(" ", "0");
    }

    private ColorUtil() {}

    public static boolean invertText(Color c)
    {
        int avg = ((c.getRed() + c.getGreen() + c.getBlue()) / 3);

        int brightness =  (int)(((c.getRed() * 299) + (c.getGreen() * 587) + (c.getBlue() * 114)) / 1000);
        if (brightness > 200)
        {
            return true;
        }
        return false;
    }

}
