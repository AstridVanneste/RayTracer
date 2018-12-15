package Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

public class Image implements Samplable
{
	private BufferedImage image;
	private ColorModel colorModel;

	public Image(String path) throws IOException
	{
		this.image = ImageIO.read(new File(path));
		this.colorModel = this.image.getColorModel();
	}

	@Override
	public Color sample(double u, double v)
	{
		int x = (int) Math.floor(u);
		int y = (int) Math.floor(v);

		int pixel = this.image.getRGB(x, y);
		Color color = new Color(this.colorModel.getRed(pixel), this.colorModel.getGreen(pixel), this.colorModel.getBlue(colorModel));
		return color;
	}
}
