package de.uvwxy.clickmap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ClickMapFactory {
	private static final int xMin = 0;
	private static final int yMin = 1;
	private static final int xMax = 2;
	private static final int yMax = 3;

	public static int[][][] createClickMapOnAndroid(Context c, int resID) {

		Bitmap img = BitmapFactory.decodeResource(c.getResources(), resID);

		int w = img.getWidth();
		int h = img.getHeight();

		// rectMap[r][g][xMin,yMin,xMax,yMax];
		int[][][] rectMap = new int[256][256][4];

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < 256; y++) {
				rectMap[x][y][xMin] = Integer.MAX_VALUE;
				rectMap[x][y][yMin] = Integer.MAX_VALUE;
				rectMap[x][y][xMax] = Integer.MIN_VALUE;
				rectMap[x][y][yMax] = Integer.MIN_VALUE;
			}
		}

		boolean stillnotnull = true;

		int r, g, b;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int p = img.getPixel(x, y);
				
				r = android.graphics.Color.red(p);
				g = android.graphics.Color.green(p);
				b = android.graphics.Color.blue(p);

				if (b == 255 && r != 255 && g != 255) {
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
		}

		return rectMap;

	}

	/**
	 * Create a clickmap from a supplied image mask. The returned integer array has the following layout
	 * clickMap[idX][idY][xMin,yMin,xMax,yMax].
	 * 
	 * @param f
	 *            file handle pointing to an image
	 * @return the click map
	 * @throws IOException
	 */
	public static int[][][] createClickMap(InputStream f) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(f);

		int w = img.getWidth();
		int h = img.getHeight();

		// rectMap[r][g][xMin,yMin,xMax,yMax];
		int[][][] rectMap = new int[256][256][4];

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < 256; y++) {
				rectMap[x][y][xMin] = Integer.MAX_VALUE;
				rectMap[x][y][yMin] = Integer.MAX_VALUE;
				rectMap[x][y][xMax] = Integer.MIN_VALUE;
				rectMap[x][y][yMax] = Integer.MIN_VALUE;
			}
		}

		boolean stillnotnull = true;

		int r, g, b;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Color c = new Color(img.getRGB(x, y));
				r = c.getRed();
				g = c.getGreen();
				b = c.getBlue();

				if (b == 255 && r != 255 && g != 255) {
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
		}

		return rectMap;
	}

}
