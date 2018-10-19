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
		switch(this.format)
		{
			case P6:
				this.writeP6(pixels);
		}
	}

	private void writeP6(List<Pixel> pixels) throws IOException
	{
		this.writer.write("P3\n"); // TODO cleanup

		this.writer.write(Integer.toString(this.width));
		this.writer.write(" ");
		this.writer.write(Integer.toString(this.height));
		this.writer.write("\n");
		this.writer.write("255\n"); // TODO cleanup

		int x = 0;
		for(int i = 0; i < pixels.size(); i++)
		{
			Color color = pixels.get(i).getColor();
			if(pixels.get(i).x() > x)
			{
				this.writer.write("\n");
				x = pixels.get(i).x();
			}
			this.writer.write(Integer.toString(color.getRed()));
			this.writer.write(" ");
			this.writer.write(Integer.toString(color.getGreen()));
			this.writer.write(" ");
			this.writer.write(Integer.toString(color.getBlue()));
			this.writer.write(" ");
		}
	}
}
