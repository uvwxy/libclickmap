import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	private static final int xMin = 0;
	private static final int xMax = 1;
	private static final int yMin = 2;
	private static final int yMax = 3;

	/**
	 * @param args
	 *            the image to parse
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			out("Please provide a file name!");
			return;
		}

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			out(e.getLocalizedMessage());
		}

		int w = img.getWidth();
		int h = img.getHeight();

		// rectMap[r][g][xMin,yMin,xMax,yMax];
		int[][][] rectMap = new int[255][255][4];

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < 256; y++) {
				rectMap[255][255][0] = Integer.MAX_VALUE;
			}
		}

		int color = 0;
		int r, g, b;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				color = img.getRGB(x, y);
				Color c = new Color(color);
				r = c.getRed();
				g = c.getGreen();
				b = c.getBlue();
				// update min
				if (x < rectMap[r][g][xMin])
					rectMap[r][g][xMin] = x;
				if (y < rectMap[r][g][yMin])
					rectMap[r][g][yMin] = y;
				// update max
				if (x > rectMap[r][g][xMax])
					rectMap[r][g][xMax] = x;
				if (y > rectMap[r][g][yMax])
					rectMap[r][g][yMax] = y;
			}
		}

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < 256; y++) {
				if (rectMap[x][y][xMin] != Integer.MAX_VALUE)
					out("" + x + "," + y + "," + rectMap[x][y][xMin] + "," + rectMap[x][y][yMin] + ","
							+ rectMap[x][y][xMax] + "," + rectMap[x][y][yMax]);
			}
		}
	}

	private static void out(String s) {
		System.out.println(s);
	}

}
