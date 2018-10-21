package Util;

import RayTracer.Screen.Pixel;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PPMWriter
{
	public enum Format
	{
		P1,
		P2,
		P3,
		P4,
		P5,
		P6
	}

	private BufferedWriter writer;
	private Format format;
	private int height;
	private int width;

	public PPMWriter(String path, Format format, int width, int height) throws IOException
	{
		this.writer = new BufferedWriter(new FileWriter(path));
		this.format = format;
		this.height = height;
		this.width = width;
	}

	public void write(List<Pixel> pixels) throws IOException
	{
		Pixel ordered[][] = this.orderPixels(pixels);
		switch(this.format)
		{
			case P6:
				this.writeP6(ordered);
		}
	}

	private Pixel[][] orderPixels(List<Pixel> pixels)
	{
		Pixel result[][] = new Pixel[this.width][this.height];

		for(int i = 0; i < pixels.size(); i++)
		{
			Pixel pixel = pixels.get(i);

			result[pixel.x()][this.height - pixel.y() - 1] = pixel;
		}

		return result;
	}

	private void writeP6(Pixel pixels[][]) throws IOException
	{
		this.writer.write("P3\n"); // TODO cleanup

		this.writer.write(Integer.toString(this.width));
		this.writer.write(" ");
		this.writer.write(Integer.toString(this.height));
		this.writer.write("\n");
		this.writer.write("255\n"); // TODO cleanup

		for(int y = 0; y < this.height; y++ )
		{
			for(int x = 0; x < this.width; x++)
			{
				Color color = pixels[x][y].getColor();

				this.writer.write(Integer.toString(color.getRed()));
				this.writer.write(" ");
				this.writer.write(Integer.toString(color.getGreen()));
				this.writer.write(" ");
				this.writer.write(Integer.toString(color.getBlue()));
				this.writer.write(" ");
			}
		}
	}
}
